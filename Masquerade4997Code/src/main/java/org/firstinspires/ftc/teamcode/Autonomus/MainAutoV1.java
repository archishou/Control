package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.Falcon.Falcon;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Archishmaan Peyyety on 10/20/18.
 * Project: MasqLib
 */
@Autonomous(name = "MainAutoV1", group = "NFS")
public class MainAutoV1 extends MasqLinearOpMode implements Constants {
    private Falcon falcon = new Falcon();
    @Override
    public void runLinearOpMode() throws InterruptedException {
        falcon.mapHardware(hardwareMap);
        falcon.initializeAutonomous();
        falcon.hangLatch.setPosition(AUTON_HANG_IN);
        while (!opModeIsActive()) {
            dash.create(falcon.imu.getAbsoluteHeading());
            dash.update();
        }
        waitForStart();
        falcon.hangLatch.setPosition(AUTON_HANG_OUT);
        falcon.sleep(1);
        falcon.imu.reset();
        double startAngle = falcon.imu.getRelativeYaw();
        falcon.turnRelative(45, Direction.LEFT);
        falcon.turnTillGold(0.5, Direction.LEFT);
        double endAngle = falcon.imu.getRelativeYaw();
        double dHeading = endAngle - startAngle;
        falcon.turnRelative(30, Direction.RIGHT);
        if (dHeading < 60) {
            falcon.drive(20, 0.8, Direction.BACKWARD);
            falcon.turnRelative(30, Direction.LEFT);
            falcon.drive(20, 0.8, Direction.BACKWARD);
        }
        else if (dHeading >= 60 && dHeading < 145) {
            falcon.drive(30, 0.8, Direction.BACKWARD);
        }
        else if (dHeading >= 1458) {
            falcon.drive(20, 0.8, Direction.BACKWARD);
            falcon.turnRelative(30, Direction.RIGHT);
            falcon.drive(20, 0.8, Direction.BACKWARD);
        }
        falcon.goldAlignDetector.disable();

    }
}
