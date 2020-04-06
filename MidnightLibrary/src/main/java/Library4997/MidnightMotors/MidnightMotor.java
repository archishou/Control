package Library4997.MidnightMotors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import Library4997.MidnightSensors.MidnightClock;
import Library4997.MidnightSensors.MidnightLimitSwitch;
import Library4997.MinightResources.MidnightHelpers.MidnightHardware;
import Library4997.MinightResources.MidnightUtils;
import Library4997.MidnightSensors.MidnightEncoder;

/**
 * This is a custom motor that includes stall detection and telemetry
 */
public class MidnightMotor implements MidnightHardware {
    private double minPower = 0;
    public DcMotor motor;
    private boolean stallDetection = false;
    private String nameMotor;
    private double targetPower;
    private boolean velocityControlState = false;
    public double error;
    private double kp = 0.1, ki = 0, kd = 0;
    public MidnightEncoder encoder;
    private double prevPos = 0;
    private boolean stalled = false;
    private double previousTime = 0;
    public double destination = 0;
    private double motorPower;
    private double currentMax, currentMin;
    private double currentZero;
    public double rpmIntegral = 0;
    public double rpmDerivative = 0;
    private double rpmPreviousError = 0;
    private int stalledRPMThreshold = 10;
    private double prevRate = 0;
    private Runnable
            stallAction = () -> {

            },
            unStalledAction = () -> {

            };
    private double minPosition, maxPosition;
    private boolean
            limitDetection,
            positionDetection,
            halfDetectionMin = false,
            halfDetectionMax = false,
            closedLoop = false;
    private MidnightLimitSwitch minLim, maxLim = null;

    public MidnightMotor(String name, MidnightMotorModel model, HardwareMap hardwareMap) {
        limitDetection = positionDetection = false;
        this.nameMotor = name;
        motor = hardwareMap.get(DcMotor.class, name);
        encoder = new MidnightEncoder(this, model);
    }
    public MidnightMotor(String name, MidnightMotorModel model, DcMotor.Direction direction, HardwareMap hardwareMap) {
        limitDetection = positionDetection = false;
        this.nameMotor = name;
        motor = hardwareMap.dcMotor.get(name);
        motor.setDirection(direction);
        encoder = new MidnightEncoder(this, model);
    }

    public void setLimits(MidnightLimitSwitch min, MidnightLimitSwitch max){
        maxLim = max; minLim = min;
        limitDetection = true;
    }
    public MidnightMotor setLimit(MidnightLimitSwitch min) {
        minLim = min; maxLim = null;
        limitDetection = true;
        return this;
    }
    public MidnightMotor setPositionLimits (double min, double max) {
        minPosition = min; maxPosition = max;
        positionDetection = true;
        return this;
    }
    public MidnightMotor setHalfLimits(MidnightLimitSwitch min, double max){
        maxPosition = max;
        minLim = min; halfDetectionMin = true;
        return this;
    }
    public MidnightMotor setHalfLimits(double min, MidnightLimitSwitch max){
        minPosition = min;
        maxLim = max; halfDetectionMax = true;
        return this;
    }
    public MidnightMotor setPositionLimit (double min) {
        minPosition = min;
        positionDetection = true;
        return this;
    }

    public void runWithoutEncoders () {motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);}
    public void resetEncoder() {
        encoder.resetEncoder();
    }

    public void runUsingEncoder() {motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}
    public void setDistance (double distance) {
        resetEncoder();
        destination = distance;
    }
    public void runToPosition(int inches, double speed){
        MidnightClock clock = new MidnightClock();
        resetEncoder();
        double clicks = -inches * encoder.getClicksPerInch();
        motor.setTargetPosition((int) clicks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setVelocity(speed);
        while (opModeIsActive() && motor.isBusy() &&
                !clock.elapsedTime(5, MidnightClock.Resolution.SECONDS)) {}
        setVelocity(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    boolean isBusy () {
        return motor.isBusy();
    }
    public void setBreakMode () {
        motor.setPower(0);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void unBreakMode () {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public double getCurrentPosition() {
        return encoder.getRelativePosition();
    }
    public double getAbsolutePosition () {
        return motor.getCurrentPosition();
    }
    public double getVelocity(double deltaPosition, double tChange, double CPR) {
        previousTime = System.nanoTime();
        tChange = tChange / 1e9;
        prevPos = getCurrentPosition();
        double rate = deltaPosition / tChange;
        rate = (rate * 60) / CPR;
        if (rate != 0) return rate;
        else {
            prevRate = rate;
            return rate;
        }
    }
    public double getVelocity() {
        return getVelocity(getCurrentPosition() - prevPos,System.nanoTime() - previousTime,encoder.getClicksPerRotation());
    }
    public double getAngle (double currentPosition, double CPR) {
        return (currentPosition * CPR) / 360;
    }
    public double getAngle() {
        return getAngle(motor.getCurrentPosition(), encoder.getClicksPerRotation());
    }
    public double setPower (double power) {
        power = Range.clip(power, -1, 1);
        motorPower = power;
        motor.setPower(power);
        return power;
    }
    public double setVelocity(double power, double error, double tChange) {
        targetPower = power;
        motorPower = calculateVelocityCorrection(power, error, tChange);
        if (!closedLoop) motorPower = power;
        if (limitDetection) {
            if (minLim != null && minLim.isPressed() && power < 0 ||
                    maxLim != null && maxLim.isPressed() && power > 0)
                motorPower = 0;
            else if (minLim != null && minLim.isPressed()
                    && power < 0 && maxLim == null)
                motorPower = 0;
        }
        else if (positionDetection) {
            if ((motor.getCurrentPosition() < minPosition && power < 0) ||
                    (motor.getCurrentPosition() > maxPosition && power > 0))
                motorPower = 0;
            else if (motor.getCurrentPosition() < minPosition && power < 0)
                motorPower = 0;
        }
        else if (halfDetectionMin) {
            if (minLim.isPressed()) {
                currentZero = motor.getCurrentPosition();
                currentMax = currentZero + maxPosition;
            }
            if (minLim != null && minLim.isPressed() && power < 0) motorPower = 0;
            else if (motor.getCurrentPosition() > currentMax && power > 0) motorPower = 0;
        }
        else if (halfDetectionMax) {
            if (maxLim.isPressed()) {
                currentZero = motor.getCurrentPosition();
                currentMin = currentZero - minPosition;
            }
            if (maxLim != null && maxLim.isPressed() && power >0) motorPower = 0;
            else if (motor.getCurrentPosition() < currentMin && power < 0) motorPower = 0;
        }
        if (Math.abs(motorPower) < minPower && minPower != 0) motorPower = 0;
        motor.setPower(motorPower);
        return motor.getPower();
    }

    public double setVelocity(double power) {
        return setVelocity(power, (encoder.getRPM() * power) - getVelocity(), (System.nanoTime() - previousTime)/1e9);
    }
    //For testing purposes input parameters for error and time
    public  double calculateVelocityCorrection(double power, double error, double tChange) {
        this.error = error;
        rpmIntegral += error * tChange;
        rpmDerivative = (error - rpmPreviousError) / tChange;
        double p = error*kp;
        double i = rpmIntegral*ki;
        double d = rpmDerivative*kd;
        double motorPower = power + (p + i + d);
        rpmPreviousError = error;
        previousTime = System.nanoTime();
        return motorPower;
    }

    public void setVelocityControlState(boolean velocityControlState) {
        this.velocityControlState = velocityControlState;
    }
    public void startVelocityControl () {
        setVelocityControlState(true);
        Runnable velocityControl = () -> {
            while (opModeIsActive() && velocityControlState) {
                setVelocity(targetPower);
            }
        };
        Thread velocityThread = new Thread(velocityControl);
        velocityThread.start();
    }

    //Use this one for testing
    public boolean getStalled(double deltaPosition, double tChange, double CPR) {
        return Math.abs(getVelocity(deltaPosition, tChange, CPR)) < stalledRPMThreshold;
    }
    //Use for normal use
    private boolean getStalled() {
        return Math.abs(getVelocity()) < stalledRPMThreshold;
    }
    public void setStalledAction(Runnable action) {
        stallAction = action;
    }
    public void setUnStalledAction(Runnable action) {
        unStalledAction = action;
    }
    public void setStallDetection(boolean bool) {stallDetection = bool;}
    private boolean getStallDetection () {return stallDetection;}
    public synchronized boolean isStalled() {
        return stalled;
    }
    public int getStalledRPMThreshold() {
        return stalledRPMThreshold;
    }
    public void setStalledRPMThreshold(int stalledRPMThreshold) {
        this.stalledRPMThreshold = stalledRPMThreshold;
    }
    //For testing
    public void enableStallDetection(double deltaPosition, double tChange, double CPR) {
        setStallDetection(true);
        Runnable mainRunnable = () -> {
            while (opModeIsActive()) {
                stalled = getStalled(deltaPosition, tChange, CPR);
                if (getStallDetection()) {
                    if (stalled) stallAction.run();
                    else unStalledAction.run();
                }
                MidnightUtils.sleep(100, MidnightClock.Resolution.MILLISECONDS);
            }
        };
        Thread thread = new Thread(mainRunnable);
        thread.start();
    }
    //For normal use
    public void enableStallDetection() {
        setStallDetection(true);
        Runnable mainRunnable = () -> { while (opModeIsActive()) {
            stalled = getStalled();
            if (getStallDetection()) {
                if (stalled) stallAction.run();
                else unStalledAction.run();
            }
            MidnightUtils.sleep(100, MidnightClock.Resolution.MILLISECONDS);
        }};
        Thread thread = new Thread(mainRunnable);
        thread.start();
    }

    public void setClosedLoop(boolean closedLoop) {
        this.closedLoop = closedLoop;
    }

    public double getPower () {
        return motorPower;
    }

    public MidnightEncoder getEncoder () {
        return encoder;
    }

    public double getKp() {return kp;}
    public void setKp(double kp) {
        this.kp = kp;
    }
    public double getKi() {return ki;}
    public void setKi(double ki) {
        this.ki = ki;
    }
    public double getKd() {return kd;}
    public void setKd(double kd) {
        this.kd = kd;
    }

    private boolean opModeIsActive() {
        return MidnightUtils.opModeIsActive();
    }
    public DcMotorController getController () {
        return motor.getController();
    }
    public int getPortNumber () {
        return motor.getPortNumber();
    }

    public void setMotorModel (MidnightMotorModel model) {
        encoder.setModel(model);
    }

    public boolean isClosedLoop() {
        return closedLoop;
    }

    public double getMinPower() {
        return minPower;
    }

    public void setMinPower(double minPower) {
        this.minPower = minPower;
    }

    public void setWheelDiameter(double diameter) {
        encoder.setWheelDiameter(diameter);
    }

    public double getInches() {
        return encoder.getInches();
    }

    public String getName() {
        return nameMotor;
    }
    public String[] getDash() {
        return new String[] {
                "Current Position: " + getCurrentPosition(),
                "Velocity: " + getVelocity()};
    }
}