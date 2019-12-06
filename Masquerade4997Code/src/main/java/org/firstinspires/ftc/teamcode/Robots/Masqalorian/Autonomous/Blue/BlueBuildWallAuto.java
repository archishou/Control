package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqResources.MasqHelpers.Strafe;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 9/15/2019
 */
@Autonomous(name = "BlueBuildWallAuto", group = "Masqalorian")
@Disabled
public class BlueBuildWallAuto extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

    @Override
    public void runLinearOpMode() throws InterruptedException{
        robot.init(hardwareMap);
        robot.initializeAutonomous();

        while(!opModeIsActive()) {
            dash.create("Hello");
            dash.update();
        }

        waitForStart();

        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        robot.strafe(25, Strafe.LEFT, 1.5);
        sleep();
        robot.drive(30, 0.25);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(30, 0.5 ,Direction.BACKWARD,3);
        robot.foundationHook.raise();
        sleep();
        robot.strafe(70, Strafe.RIGHT, 2);
        robot.foundationHook.mid();
        robot.turnAbsolute(0);
        sleep();
        robot.drive(1.5, Direction.BACKWARD);
        sleep();
        robot.strafe(30, Strafe.RIGHT,2);
        robot.turnAbsolute(0);
    }
}