package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 11/17/2019
 */
@TeleOp(name = "TestTeleop", group = "Masqalorian")
@Disabled
public class TestTeleop extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();
    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeTeleop();

        while (!opModeIsActive()) {
            dash.create("Hello");
            dash.update();
        }

        robot.foundationHook.raise();
        while(opModeIsActive()) {
            robot.MECH(controller1);
            dash.create(robot.tracker.getHeading());
            dash.update();
            controller1.update();
        }
    }
}
