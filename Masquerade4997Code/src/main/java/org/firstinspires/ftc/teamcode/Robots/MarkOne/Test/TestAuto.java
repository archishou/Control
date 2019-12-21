package org.firstinspires.ftc.teamcode.Robots.MarkOne.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import Library4997.MasqWrappers.MasqLinearOpMode;

import static Library4997.MasqResources.MasqHelpers.Strafe.*;

/**
 * Created by Keval Kataria on 11/3/2019
 */
@Autonomous(name = "TestAuto", group = "MarkOne")
public class TestAuto extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.blockRotater.setPosition(0);
        robot.initializeAutonomous();

        while (!opModeIsActive()) {
            dash.create("HEADING: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();

        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(20, -135);
        sleep();
        robot.strafe(20,135);
        sleep();
        robot.strafe(20,LEFT);
        sleep();
        robot.strafe(20,RIGHT);
        sleep();
        robot.strafe(20,45);
        sleep();
        robot.strafe(20,-135);
        sleep();
        robot.strafe(20,FORWARD);
        sleep();
        robot.strafe(20,BACKWARD);
    }
}