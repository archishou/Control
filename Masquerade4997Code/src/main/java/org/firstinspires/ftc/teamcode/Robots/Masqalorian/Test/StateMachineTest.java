package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 11/3/2019
 */
@Autonomous(name = "StateMachineTest", group = "Z_Test")
public class StateMachineTest extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();

    enum State {
        FULL_DRIVER,
        ARM_AUTOMATION
    }

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeAutonomous();

        while (!opModeIsActive()) {
            dash.create("HEADING: ", robot.tracker.getHeading());
            dash.update();
        }

        State state = State.FULL_DRIVER;

        while (opModeIsActive()) {
            switch (state){
                case FULL_DRIVER:
                    robot.MECH(controller1);
                    if (!controller1.leftBumper()) state = State.ARM_AUTOMATION;
                    else break;
                case ARM_AUTOMATION:
                    robot.foundationHook.lower();
                    if (controller1.leftBumper()) {
                        robot.foundationHook.raise();
                        state = State.FULL_DRIVER;
                    }
                    break;
            }
        }

    }
}
