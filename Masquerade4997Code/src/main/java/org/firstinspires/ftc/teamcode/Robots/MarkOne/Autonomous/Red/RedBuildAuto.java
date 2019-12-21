package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqResources.MasqHelpers.Strafe;
import Library4997.MasqWrappers.MasqLinearOpMode;

import static Library4997.MasqResources.MasqHelpers.Strafe.RIGHT;

/**
 * Created by Keval Kataria on 9/15/2019
 */
@Autonomous(name = "RedBuild", group = "MarkOne")
public class RedBuildAuto extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();

    @Override
    public void runLinearOpMode() throws InterruptedException{
        robot.init(hardwareMap);
        robot.blockRotater.setPosition(0);
        robot.initializeAutonomous();

        while(!opModeIsActive()) {
            dash.create("HEADING: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();

        robot.foundationHook.raise();
        robot.strafe(20, RIGHT);
        sleep();
        robot.drive(40);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(57, 0.5 ,Direction.BACKWARD,3);
        robot.foundationHook.raise();
        sleep();
        robot.strafe(35,Strafe.LEFT,3);
        robot.foundationHook.mid();
        robot.drive(25);
        robot.strafe(10,RIGHT);
        sleep(1);
        robot.strafe(30,Strafe.LEFT);
    }
}