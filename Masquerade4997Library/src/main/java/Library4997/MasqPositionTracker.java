package Library4997;

import com.qualcomm.robotcore.hardware.HardwareMap;

import Library4997.MasqMotors.MasqMotor;
import Library4997.MasqResources.MasqHelpers.MasqHardware;
import Library4997.MasqResources.MasqUtils;
import Library4997.MasqSensors.MasqAdafruitIMU;

/**
 * Created by Archishmaan Peyyety on 8/9/18.
 * Project: MasqLib
 */

public class MasqPositionTracker implements MasqHardware, Runnable {
    private MasqMotor xSystem, yLSystem, yRSystem;
    public MasqAdafruitIMU imu;
    private double heading, globalX, globalY, dH;
    private boolean running = true;

    private double prevX, prevYR, prevYL, xRadius, trackWidth, threadSleep = 0;

    public MasqPositionTracker(MasqMotor xSystem, MasqMotor yLSystem, MasqMotor yRSystem, HardwareMap hardwareMap) {
        this.xSystem = xSystem;
        this.yLSystem = yLSystem;
        this.yRSystem = yRSystem;
        imu = new MasqAdafruitIMU("imu", hardwareMap);
        reset();
    }

    public double getHeading () {
        return Math.toDegrees(heading);
    }

    public void reset() {
        xSystem.resetEncoder();
        yLSystem.resetEncoder();
        yRSystem.resetEncoder();
        imu.reset();
    }

    public void updateSystem() {
        three();
    }

    private void three() {
        double xPosition = xSystem.getInches();
        double yLPosition = yLSystem.getInches();
        double yRPosition = yRSystem.getInches();
        heading = MasqUtils.adjustAngleRad((yLPosition - yRPosition) / trackWidth);
        double dX = xPosition - prevX;
        prevX = xPosition;
        double dYR = yRPosition - prevYR;
        prevYR = yRPosition;
        double dYL = yLPosition - prevYL;
        prevYL = yLPosition;
        dH = (dYL - dYR) / trackWidth;

        double dTranslationalY = (dYR + dYL) / 2;
        double angularComponentX = xRadius * dH;
        double dTranslationalX = dX - angularComponentX;
        if (Math.abs(dH) > 0) {
            double radiusMov = (dYR+dYL) / (2 * dH);
            double radiusStrafe = dTranslationalX/dH;
            dTranslationalY = (radiusMov * Math.sin(dH)) - (radiusStrafe * (1 - Math.cos(dH)));
            dTranslationalX = radiusMov * (1 - Math.cos(dH)) + (radiusStrafe * Math.sin(dH));
        }
        double dGlobalX = dTranslationalX * Math.cos(heading) + dTranslationalY * Math.sin(heading);
        double dGlobalY = -dTranslationalX * Math.sin(heading) + dTranslationalY * Math.cos(heading);
        globalX += dGlobalX;
        globalY += dGlobalY;
    }
    public double getYaw () {
        return imu.getRelativeYaw();
    }

    public double getThreadSleep() {
        return threadSleep;
    }

    public void setThreadSleep(double threadSleep) {
        this.threadSleep = threadSleep;
    }

    public double getGlobalX() {
        return globalX;
    }
    public double getGlobalY() {
        return globalY;
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setTrackWidth(double trackWidth) {
        this.trackWidth = trackWidth;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public double getdH() {
        return dH;
    }

    @Override
    public String getName() {
        return "Tracker";
    }

    @Override
    public String[] getDash() {
        return new String[] {
                getName() +
                "GlobalX: " + globalX,
                "GlobalY: " + globalY,
                "Heading: " + getHeading(),
        };
    }

    @Override
    public void run() {
        while (running && MasqUtils.opModeIsActive()) {
            updateSystem();
        }
    }
}
