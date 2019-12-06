package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;
import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.MasqalorianDetector;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 12/1/2019
 */
@Autonomous(name = "BlueQuarry", group = "Masqalorian")
public class BlueQuarryAuto extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeAutonomous();

        MasqalorianDetector.SkystonePosition position = robot.detector.getPosition();

        while(!opModeIsActive()) {
            dash.create("Position: ", position);
            dash.update();
        }
        waitForStart();

        robot.foundationHook.mid();
        robot.blockPusher.setPosition(1);
        sleep();

        if(position == MasqalorianDetector.SkystonePosition.LEFT) runStoneLeft();
        else if (position == MasqalorianDetector.SkystonePosition.MIDDLE) runStoneMiddle();
        else if (position == MasqalorianDetector.SkystonePosition.RIGHT) runStoneRight();
        sleep(25);

        robot.detector.stop();
    }
    public void runStoneLeft() {
        robot.strafe(Math.hypot(15,18),Math.toDegrees(Math.atan2(-18,15)));
        robot.intake.setVelocity(1);
        robot.drive(15);
        sleep(1);
        robot.intake.setVelocity(0);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        dash.create(1);
        dash.update();
        robot.drive(10, Direction.BACKWARD);
        /*robot.driveTrain.stopDriving();
        dash.create(2);
        dash.update();
        sleep(10);
        dash.create(3);
        dash.update();
        robot.strafe(90, Strafe.LEFT,500);
        robot.blockPusher.setPosition(1);
        robot.drive(5);
        robot.foundationHook.lower();
        sleep(1);
        robot.drive(30,Direction.BACKWARD);
        robot.foundationHook.raise();
        sleep(1);
        robot.strafe(30,Strafe.RIGHT);
        robot.foundationHook.mid();
        robot.drive(15);
        robot.turnAbsolute(90);
        robot.lift.runToPosition(15,0.5);
        robot.blockRotater.setPosition(1);
        sleep();
        robot.lift.runToPosition(0,0.5);
        robot.blockGrabber.setPosition(1);
        sleep();
        robot.lift.runToPosition(15,0.5);
        robot.blockRotater.setPosition(0);
        sleep();
        robot.lift.runToPosition(0,0.5);
        robot.strafe(20,Strafe.RIGHT);
        robot.turnAbsolute(0);
        robot.strafe(84,Strafe.RIGHT);
        robot.intake.setVelocity(1);
        robot.drive(5);
        sleep(15);
        robot.intake.setVelocity(0);
        robot.blockPusher.setPosition(0);
        robot.blockGrabber.setPosition(0);
        robot.drive(5,Direction.BACKWARD);
        robot.strafe(84,Strafe.LEFT);
        robot.blockPusher.setPosition(1);
        robot.turnAbsolute(90);
        robot.strafe(20,Strafe.LEFT);
        robot.lift.runToPosition(15,0.5);
        robot.blockRotater.setPosition(1);
        sleep();
        robot.lift.runToPosition(0,0.5);
        robot.blockGrabber.setPosition(1);
        sleep();
        robot.lift.runToPosition(15,0.5);
        robot.blockRotater.setPosition(0);
        sleep();
        robot.lift.runToPosition(0,0.5);
        robot.strafe(20,Strafe.RIGHT);
        robot.drive(15);*/
    }
    public void runStoneMiddle() {}
    public void runStoneRight() {}
}
