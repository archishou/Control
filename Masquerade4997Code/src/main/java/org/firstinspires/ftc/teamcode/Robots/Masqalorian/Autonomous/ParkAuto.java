package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 12/13/2019
 */
@Autonomous(name = "ParkAuto", group = "Masqalorian")
public class ParkAuto extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();
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
