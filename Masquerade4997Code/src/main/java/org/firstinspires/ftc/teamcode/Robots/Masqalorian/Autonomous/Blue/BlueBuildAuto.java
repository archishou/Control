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
@Autonomous(name = "BlueBuild", group = "Masqalorian")
public class BlueBuildAuto extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

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
        robot.blockPusher.setPosition(1);
        robot.strafe(15, Strafe.LEFT);
        sleep();
        robot.drive(40);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(50, 0.5 ,Direction.BACKWARD,3);
        robot.foundationHook.raise();
        sleep();
        robot.strafe(33,Strafe.RIGHT,3);
        robot.foundationHook.mid();
        robot.drive(27);
        robot.strafe(22,Strafe.RIGHT);
    }
}