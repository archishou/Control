package org.firstinspires.ftc.teamcode.Robots.MarkOne.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import Library4997.MasqControlSystems.MasqPurePursuit.MasqWayPoint;
import Library4997.MasqResources.MasqMath.MasqPoint;
import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 1/23/2020
 */
@Autonomous(name = "XYTankThenMechTest", group = "MarkOne")
public class XYTankThenMechTest extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();
    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeAutonomous();
        while (!opModeIsActive()) {
            dash.create("hello this is both xypaths test");
            dash.update();
        }

        waitForStart();

            MasqWayPoint p0 = new MasqWayPoint(new MasqPoint(0, 10, 0), 1,
                    0, 0.7, 10, 0.01);
            MasqWayPoint p1 = new MasqWayPoint(new MasqPoint(20, 30, 90), 1,
                    0, 0.7, 10, 0.02);
            MasqWayPoint p2 = new MasqWayPoint(new MasqPoint(35, 60, 0), 1,
                    0, 0.5, 10, 0.02);
            MasqWayPoint p3 = new MasqWayPoint(new MasqPoint(40, 80, 0), 1,
                    0, 0.3,10, 0.02);
            robot.xyPathTank(4,p0,p1);
            robot.xyPath(p2,p3);
    }
}
