package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;
import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.MasqalorianDetector;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqResources.MasqHelpers.Strafe;
import Library4997.MasqResources.MasqUtils;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 12/1/2019
 */
@Autonomous(name = "BlueFullQuarry", group = "Masqalorian")
public class BlueFullQuarryAuto extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.blockRotater.setPosition(0);
        robot.initializeAutonomous();
        robot.detector.start();
        robot.detector.setClippingMargins(100,80,110,70);

        MasqalorianDetector.SkystonePosition position = robot.detector.getPosition();

        while(!opModeIsActive()) {
            position = robot.detector.getPosition();
            dash.create("Position: ", position);
            dash.create("HEADING: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();
        robot.tracker.reset();
        robot.foundationHook.mid();
        robot.blockPusher.setPosition(1);
        sleep();

        if(position == MasqalorianDetector.SkystonePosition.LEFT) runStoneLeft();
        else if (position == MasqalorianDetector.SkystonePosition.MIDDLE) runStoneMiddle();
        else if (position == MasqalorianDetector.SkystonePosition.RIGHT) runStoneRight();

        robot.detector.stop();
    }

    private void runStoneLeft() {
        robot.strafe(38, -45);
        robot.intake.setVelocity(1);
        robot.drive(42);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(30, Direction.BACKWARD);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        robot.strafe(80, Strafe.LEFT, 4);
        robot.drive(17);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(60,0.5,Direction.BACKWARD,4);
        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(35,Strafe.RIGHT,2,0.7);
        robot.drive(20,0.6);
        sleep();
        robot.turnAbsolute(90,1);
        robot.drive(10,Direction.BACKWARD);/*
        robot.lift.runToPosition(12,0.5);
        robot.blockRotater.setPosition(1);
        sleep(1);
        robot.lift.runToPosition(0,0.5);
        robot.blockGrabber.setPosition(1);
        sleep();*/
        sleep(5);
        robot.strafe(3,Strafe.LEFT,0.5);
        robot.drive(20);
    }
    private void runStoneMiddle() {
        robot.strafe(20, -45);
        robot.intake.setVelocity(1);
        robot.drive(50);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(27, Direction.BACKWARD);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        robot.strafe(90, Strafe.LEFT, 4);
        robot.drive(20);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(60,0.5,Direction.BACKWARD,4);
        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(35,Strafe.RIGHT,2,0.7);
        robot.drive(20,0.6);
        sleep();
        robot.turnAbsolute(90,1);
        robot.drive(10,Direction.BACKWARD);
        /*
        robot.lift.runToPosition(12,0.5);
        robot.blockRotater.setPosition(1);
        sleep(1);
        robot.lift.runToPosition(0,0.5);
        robot.blockGrabber.setPosition(1);
        sleep();*/
        sleep(5
        );
        robot.strafe(3,Strafe.LEFT,0.5);
        robot.drive(20);
    }
    private void runStoneRight() {
        robot.strafe(3,Strafe.RIGHT);
        robot.intake.setVelocity(1);
        robot.drive(50);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(24, Direction.BACKWARD);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        robot.strafe(100, Strafe.LEFT, 4);
        robot.drive(20);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(60,0.5,Direction.BACKWARD,4);
        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(35,Strafe.RIGHT,2,0.7);
        robot.drive(20,0.6);
        sleep();
        robot.turnAbsolute(90,1);
        robot.drive(10,Direction.BACKWARD);
        /*
        robot.lift.runToPosition(12,0.5);
        robot.blockRotater.setPosition(1);
        sleep(1);
        robot.lift.runToPosition(0,0.5);
        robot.blockGrabber.setPosition(1);
        sleep();*/
        sleep(5
        );
        robot.strafe(3,Strafe.LEFT,0.5);
        robot.drive(20);
    }
}