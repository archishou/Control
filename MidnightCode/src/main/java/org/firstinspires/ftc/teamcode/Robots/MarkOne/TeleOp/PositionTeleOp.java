package org.firstinspires.ftc.teamcode.Robots.MarkOne.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.ExampleBot;

import Library17822.MidnightWrappers.MidnightLinearOpMode;

/**
 * Created by Keval Kataria on 9/14/2019
 */
@TeleOp(name = "PositionTeleOp", group = "ExampleBot")
public class PositionTeleOp extends MidnightLinearOpMode {
    private long start, end, sum, num;
    private ExampleBot robot = new ExampleBot();

    @Override
    public void runLinearOpMode() {
        robot.init(hardwareMap);
        robot.initializeTeleop();

        while(!opModeIsActive()) {
            dash.create("Heading: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();

        new Thread(robot.tracker).start();
        while(opModeIsActive()) {
            robot.MECH(controller1, false, true);
            dash.create("X: ",robot.tracker.getGlobalX());
            dash.create("Heading: ", robot.tracker.getHeading());
            dash.create("Y: ",robot.tracker.getGlobalY());
            dash.update();
        }
        robot.tracker.setRunning(false);
    }
}