package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.ExampleBot;

import java.util.ArrayList;
import java.util.List;

import Library17822.MidnightControlSystems.MidnightPurePursuit.MidnightWayPoint;
import Library17822.MidnightWrappers.MidnightLinearOpMode;

/**
 * Created by Archishmaan Peyyety on 2020-04-08.
 * Project: MasqLib
 */
@Autonomous(name = "PurePursuitExample", group = "ExampleBot")
public class PurePursuitExample extends MidnightLinearOpMode {
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

        List<MidnightWayPoint> wayPoints = new ArrayList<>();
        wayPoints.add(new MidnightWayPoint().setPoint(10, 10, 90));

        new Thread(robot.tracker).start();


        MidnightWayPoint[] points = new MidnightWayPoint[wayPoints.size() + 1];
        int index = 0;
        while (index < wayPoints.size()) {
            points[index] = wayPoints.get(index);
        }

        robot.xyPath(10, points);

        robot.tracker.setRunning(false);
    }
}