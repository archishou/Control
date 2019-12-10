package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 11/3/2019
 */
@Autonomous(name = "TestAuto", group = "Masqalorian")
public class TestAuto extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.blockRotater.setPosition(0);
        robot.initializeAutonomous();

        while (!opModeIsActive()) {
            dash.create("HEADING: ", robot.tracker.getHeading());
            dash.update();
        }

        waitForStart();

        robot.foundationHook.raise();
        robot.blockPusher.setPosition(1);
        sleep(1);
        robot.turnAbsolute(90,500);
    }
}