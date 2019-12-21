package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 12/13/2019
 */
@Autonomous(name = "ParkAuto", group = "MarkOne")
public class ParkAuto extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();
    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeAutonomous();

        while(!opModeIsActive()) {
            dash.create("HEADING", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();
        robot.foundationHook.raise();
        sleep(1);
        robot.drive(45);
    }
}
