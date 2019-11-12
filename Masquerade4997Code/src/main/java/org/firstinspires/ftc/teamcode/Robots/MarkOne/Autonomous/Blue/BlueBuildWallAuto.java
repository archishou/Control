package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 9/15/2019
 */
@Autonomous(name = "BlueBuildWallAuto", group = "Prototype")
public class BlueBuildWallAuto extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();

    @Override
    public void runLinearOpMode() throws InterruptedException{
        robot.mapHardware(hardwareMap);
        robot.initializeAutonomous();
        robot.driveTrain.setClosedLoop(true);
        robot.lift.setClosedLoop(true);
        robot.lift.setKp(0.001);

        robot.resetServos();
        while(!opModeIsActive()) {
            dash.create("Hello");
            dash.update();
        }

        waitForStart();

        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        robot.strafe(25, Direction.LEFT, 1.5);
        robot.drive(40, 0.25);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(50, 0.25 ,Direction.BACKWARD,3);
        robot.foundationHook.raise();
        sleep();
        robot.strafe(70, Direction.RIGHT, 2);
        robot.foundationHook.mid();
        robot.turnAbsolute(0);
        robot.drive(1.5, Direction.BACKWARD);
        sleep();
        robot.strafe(30, Direction.RIGHT,2);
        robot.turnAbsolute(0);
    }
}
