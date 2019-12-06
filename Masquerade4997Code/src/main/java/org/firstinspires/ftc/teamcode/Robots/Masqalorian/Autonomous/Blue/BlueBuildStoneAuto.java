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
@Autonomous(name = "BlueBuildStoneAuto", group = "Masqalorian")
@Disabled
public class BlueBuildStoneAuto extends MasqLinearOpMode {
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
        robot.strafe(40, Strafe.LEFT, 1.5);
        robot.drive(42, 0.25);
        robot.turnAbsolute(-10);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(50, 0.25 ,Direction.BACKWARD,3);
        robot.foundationHook.raise();
        sleep();
        robot.strafe(65, Strafe.RIGHT, 2);
        robot.turnAbsolute(5);
        robot.foundationHook.mid();
        robot.drive(23.5);
        robot.turnAbsolute(90);
        robot.drive(30);
        robot.turnAbsolute(-45);
        robot.intake.setVelocity(1);
        robot.drive(15, 0.5);
        sleep(.1);
        robot.intake.setVelocity(0);
        robot.drive(15, Direction.BACKWARD);
        robot.turnAbsolute(-90);
        robot.drive(45);
        sleep();
        robot.intake.setVelocity(-1);
        robot.drive(30, Direction.BACKWARD);
        robot.strafe(5, Strafe.RIGHT);



    }
}
