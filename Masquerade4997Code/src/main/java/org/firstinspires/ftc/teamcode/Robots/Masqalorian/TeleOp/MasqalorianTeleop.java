package org.firstinspires.ftc.teamcode.Robots.Masqalorian.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqResources.MasqUtils;
import Library4997.MasqWrappers.MasqLinearOpMode;
import java.lang.*;

import static Library4997.MasqResources.MasqUtils.toggle;

/**
 * Created by Keval Kataria on 9/14/2019
 */
@TeleOp(name = "MasqalorianTeleop", group = "Masqalorian")
public class MasqalorianTeleop extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeTeleop();

        double prevGrabber = 1;
        double prevRotater = 0;
        double prevCapper = 0;

        while(!opModeIsActive()) {
            dash.create("HEADING: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();

        robot.blockPusher.setPosition(1);
        double prevPusher = 1;

        robot.foundationHook.mid();

        while(opModeIsActive()) {
            if (controller1.rightBumper() || controller1.leftBumper()) {robot.MECH(controller1,0.5, 0.3);}
            else {robot.MECH(controller1,1, 0.6);}

            if (Math.abs(robot.lift.getEncoder().getInches()) > 10) toggle(controller2.yOnPress(),robot.blockRotater,prevRotater);
            toggle(controller2.xOnPress(), robot.blockGrabber, prevGrabber);
            toggle(controller2.aOnPress(), robot.blockPusher,prevPusher);
            toggle(controller2.dPadUpOnPress(), robot.capper, prevCapper);
            robot.foundationHook.DriverControl(controller1);

            if (controller1.leftTriggerPressed()) robot.intake.setVelocity(-1);
            else if (controller1.rightTriggerPressed()) robot.intake.setVelocity(1);
            else robot.intake.setVelocity(0);

            if (controller2.leftStickY() < 0) robot.lift.setVelocity(-Math.pow(controller2.leftStickY(),2));
            else robot.lift.setVelocity(Math.pow(controller2.leftStickY(),2));

            prevGrabber = robot.blockGrabber.getPosition();
            prevPusher = robot.blockPusher.getPosition();
            prevRotater = robot.blockRotater.getPosition();
            prevCapper = robot.capper.getPosition();

            dash.create("Heading: ", String.valueOf(robot.tracker.getHeading()));
            dash.update();

            controller1.update();
            controller2.update();
        }
    }
}