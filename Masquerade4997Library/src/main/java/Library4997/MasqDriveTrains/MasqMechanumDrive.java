package Library4997.MasqDriveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Library4997.MasqMotors.MasqMotorSystem;
import Library4997.MasqUtilities.MasqHardware;
import Library4997.MasqUtilities.MasqUtils;
import Library4997.MasqWrappers.MasqController;
import SubSystems4997.MasqSubSystem;

/**
 * Created by Archish on 4/11/18.
 */

public class MasqMechanumDrive implements MasqSubSystem, MasqHardware {
    private double speedMultiplier = 1.4, turnMultiplier = .4;
    public MasqMotorSystem leftDrive, rightDrive = null;
    public MasqMechanumDrive(String name1, String name2, String name3, String name4, HardwareMap hardwareMap) {
        leftDrive = new MasqMotorSystem(name1, DcMotor.Direction.REVERSE, name2, DcMotor.Direction.REVERSE, "LEFTDRIVE", hardwareMap);
        rightDrive = new MasqMotorSystem(name3, DcMotor.Direction.FORWARD, name4, DcMotor.Direction.FORWARD, "RIGHTDRIVE", hardwareMap);
    }
    public MasqMechanumDrive(HardwareMap hardwareMap){
        leftDrive = new MasqMotorSystem("leftFront", DcMotor.Direction.REVERSE, "leftBack", DcMotor.Direction.REVERSE, "LEFTDRIVE", hardwareMap);
        rightDrive = new MasqMotorSystem("rightFront", DcMotor.Direction.FORWARD, "rightBack", DcMotor.Direction.FORWARD, "RIGHTDRIVE", hardwareMap);
    }
    public void resetEncoders () {
        leftDrive.resetEncoder();
        rightDrive.resetEncoder();
    }
    public void setPower (double leftPower, double rightPower) {
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
    public void setPower(double power){
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }
    public void setKp(double kp){
        leftDrive.setKp(kp);
        rightDrive.setKp(kp);
    }
    public void setKi(double ki){
        leftDrive.setKi(ki);
        rightDrive.setKi(ki);
    }
    public void setKd(double kd){
        leftDrive.setKd(kd);
        rightDrive.setKd(kd);
    }
    public double getRate() {
        return (leftDrive.getVelocity() + rightDrive.getVelocity())/2;
    }
    public double getPower() {
        return (leftDrive.getPower() + rightDrive.getPower()) /2;
    }
    public void setPowerLeft (double power) {
        leftDrive.setPower(power);
    }
    public void setPowerRight (double power) {
        rightDrive.setPower(power);
    }
    public void runUsingEncoder() {
        leftDrive.runUsingEncoder();
        rightDrive.runUsingEncoder();
    }
    public void setClosedLoop(boolean closedLoop){
        leftDrive.setClosedLoop(closedLoop);
        rightDrive.setClosedLoop(closedLoop);
    }
    public void runWithoutEncoders() {
        leftDrive.runUsingEncoder();
        rightDrive.runUsingEncoder();
    }
    public void setEncoderCounts(double counts) {
        leftDrive.setEncoderCounts(counts);
        rightDrive.setEncoderCounts(counts);
    }
    public void stopDriving() {
        setPower(0,0);
    }
    private boolean opModeIsActive() {
        return MasqUtils.opModeIsActive();
    }
    public void zeroPowerBehavior(){
        rightDrive.breakMotors();
    }
    public double getCurrentPosition() {
        return (leftDrive.getCurrentPosition() + rightDrive.getCurrentPosition())/2;
    }

    public void setPowerAtAngle(double angle, double speed, double turnPower) {
        angle = Math.toRadians(angle);
        double adjustedAngle = angle + Math.PI/4;
        double leftFront = (Math.sin(adjustedAngle) * speed * speedMultiplier) - turnPower * turnMultiplier;
        double leftBack = (Math.cos(adjustedAngle) * speed * speedMultiplier) - turnPower  * turnMultiplier;
        double rightFront = (Math.cos(adjustedAngle) * speed * speedMultiplier) + turnPower * turnMultiplier;
        double rightBack = (Math.sin(adjustedAngle) * speed * speedMultiplier) + turnPower * turnMultiplier;
        double max = Math.max(Math.max(Math.abs(leftFront), Math.abs(leftBack)), Math.max(Math.abs(rightFront), Math.abs(rightBack)));
        if (max > 1) {
            leftFront /= max;
            leftBack /= max;
            rightFront /= max;
            rightBack /= max;
        }
        leftDrive.motor1.setPower(leftFront);
        leftDrive.motor2.setPower(leftBack);
        rightDrive.motor1.setPower(rightFront);
        rightDrive.motor2.setPower(rightBack);
    }


    @Override
    public void DriverControl(MasqController controller) {
        double x = -controller.leftStickY();
        double y = controller.leftStickX();
        double xR = -controller.rightStickX();
        double angle = Math.toDegrees(Math.atan2(y, x));
        double speedMagnitude = Math.hypot(x, y);
        if (controller.leftTriggerPressed()) setPowerAtAngle(angle, speedMagnitude / 3, xR);
        else setPowerAtAngle(angle, speedMagnitude, xR);
    }

    @Override
    public String getName() {
        return "DriveTrain: ";
    }

    @Override
    public String[] getDash() {
        return new String[0];
    }

    @Override
    public MasqHardware[] getComponents() {
        return new MasqHardware[0];
    }
}
