package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;
import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MasqalorianDetector;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqWrappers.MasqLinearOpMode;

import static Library4997.MasqResources.MasqHelpers.Strafe.LEFT;
import static Library4997.MasqResources.MasqHelpers.Strafe.RIGHT;

/**
 * Created by Keval Kataria on 12/14/2019
 */
@Autonomous(name = "BlueHalfQuarry", group = "MarkOne")
@Disabled
public class BlueHalfQuarryAuto extends MasqLinearOpMode {
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
        sleep();

        if(position == MasqalorianDetector.SkystonePosition.LEFT) runStoneLeft();
        else if (position == MasqalorianDetector.SkystonePosition.MIDDLE) runStoneMiddle();
        else if (position == MasqalorianDetector.SkystonePosition.RIGHT) runStoneRight();

        robot.detector.stop();
    }
    private void runStoneLeft() {
        robot.strafe(40, -45);
        robot.intake.setVelocity(1);
        robot.drive(42);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(30, Direction.BACKWARD);
        robot.strafe(40, LEFT, 4,0.7);
        robot.intake.setVelocity(-1);
        robot.strafe(10,RIGHT);
    }
    private void runStoneMiddle() {
        robot.strafe(20, -45);
        robot.intake.setVelocity(1);
        robot.drive(55);
        sleep(0.6);
        robot.intake.setVelocity(0);
        robot.drive(32, Direction.BACKWARD);
        robot.strafe(41, LEFT, 4,0.7);
        robot.intake.setVelocity(-1);
        robot.strafe(10,RIGHT);
    }
    private void runStoneRight() {
        robot.strafe(6, RIGHT);
        robot.intake.setVelocity(1);
        robot.drive(57);
        sleep(0.7);
        robot.intake.setVelocity(0);
        robot.drive(22, Direction.BACKWARD);
        robot.strafe(58, LEFT, 4,0.7);
        robot.intake.setVelocity(-1);
        robot.strafe(10,RIGHT);
    }
}
