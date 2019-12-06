package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Autonomous.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqResources.MasqHelpers.Strafe;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 9/15/2019
 */
@Autonomous(name = "RedBuildWallAuto", group = "Masqalorian")
@Disabled
public class RedBuildWallAuto extends MasqLinearOpMode {
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
        sleep();
        robot.strafe(30, Strafe.RIGHT, 1.5);
        robot.turnAbsolute(0);
        robot.drive(35, 0.25);
        robot.foundationHook.lower();
        sleep();
        robot.drive(50, 0.25 ,Direction.BACKWARD,3);
        robot.foundationHook.raise();
        sleep();
        robot.drive(5,Direction.BACKWARD);
        robot.strafe(50, Strafe.LEFT, 2);
        robot.foundationHook.mid();
        sleep();
        robot.turnAbsolute(0);
        robot.drive(3,Direction.BACKWARD);
        robot.strafe(35, Strafe.LEFT,2);
        robot.turnAbsolute(0);
        robot.drive(1);

    }
}
