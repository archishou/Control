package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;
import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MasqalorianDetector;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqWrappers.MasqLinearOpMode;

import static Library4997.MasqResources.MasqHelpers.Strafe.LEFT;
import static Library4997.MasqResources.MasqHelpers.Strafe.RIGHT;

/**
 * Created by Keval Kataria on 12/1/2019
 */
@Autonomous(name = "BlueQuarry", group = "MarkOne")
public class BlueQuarryAuto extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();

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
        robot.strafe(80, LEFT, 4);
        robot.drive(17);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(60,0.75,Direction.BACKWARD,4);
        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(35, RIGHT,1.5,0.7);
        robot.drive(24,0.6);
        sleep();
        robot.turnAbsolute(90,1);
        robot.drive(10,Direction.BACKWARD);
        robot.lift.runToPosition(12,1);
        robot.blockRotater.setPosition(1);
        sleep(1);
        robot.lift.runToPosition(0,1);
        robot.blockGrabber.setPosition(1);
        sleep();
        robot.strafe(5, LEFT,0.5);
        robot.drive(20);
    }
    private void runStoneMiddle() {
        robot.strafe(20, -45);
        robot.intake.setVelocity(1);
        robot.drive(55);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(32, Direction.BACKWARD);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        robot.strafe(83, LEFT, 4);
        robot.drive(20);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(60,0.75,Direction.BACKWARD,4);
        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(35, RIGHT,1.5,0.7);
        robot.drive(24,0.6);
        sleep();
        robot.turnAbsolute(90,1);
        robot.drive(15,Direction.BACKWARD);
        robot.lift.runToPosition(12,1);
        robot.blockRotater.setPosition(1);
        sleep(1);
        robot.lift.runToPosition(0,1);
        robot.blockGrabber.setPosition(1);
        sleep();
        robot.strafe(5, LEFT,0.5);
        robot.drive(20);
    }
    private void runStoneRight() {
        robot.strafe(4, RIGHT);
        robot.intake.setVelocity(1);
        robot.drive(50);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(24, Direction.BACKWARD);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        robot.strafe(100, LEFT, 4);
        robot.drive(20);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(60,0.75,Direction.BACKWARD,4);
        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.strafe(35, RIGHT,1.5,0.7);
        robot.drive(24,0.6);
        sleep();
        robot.turnAbsolute(90,1);
        robot.drive(10,Direction.BACKWARD);
        robot.lift.runToPosition(12,1);
        robot.blockRotater.setPosition(1);
        sleep(1);
        robot.lift.runToPosition(0,1);
        robot.blockGrabber.setPosition(1);
        sleep();
        robot.strafe(5, LEFT,0.5);
        robot.drive(20);
    }
}