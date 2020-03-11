package org.firstinspires.ftc.teamcode.Robots.MarkOne.Test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Archishmaan Peyyety on 2020-01-12.
 * Project: MasqLib
 */
@TeleOp(name = "XYMechTest", group = "MarkOne")
public class XYMechTest extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();

    @Override
    public void runLinearOpMode() {
        robot.init(hardwareMap);
        robot.initializeTeleop();

        while(!opModeIsActive()) {
            dash.create("Heading: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();

        new Thread(robot.tracker).start();
        while(opModeIsActive()) {
            double angle;
            double x = -controller1.leftStickY();
            double y = controller1.leftStickX();
            double xSpeed = -controller2.leftStickY();
            double ySpeed = controller2.leftStickX();
            angle = Math.atan2(y, x) + (Math.toRadians(robot.tracker.getHeading()));
            double adjustedAngle = angle + Math.PI/4;
            robot.driveTrain.setVelocityMECHXY(adjustedAngle, xSpeed, ySpeed, 0);
            dash.create("X: ",robot.tracker);
            dash.create("Raw X: ",robot.tapeMeasure.getCurrentPosition());
            dash.create("Raw YL: ",robot.intake.motor2.getCurrentPosition());
            dash.create("Raw YR: ", robot.intake.motor1.getCurrentPosition());
            dash.create("dH: ", robot.tracker.getdH());
            dash.update();
        }
        robot.tracker.setRunning(false);
    }
}
