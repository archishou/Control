package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;
import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.SubSystems.CVInterpreter;

import java.util.ArrayList;
import java.util.List;

import Library4997.MasqControlSystems.MasqPurePursuit.MasqWayPoint;
import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqResources.MasqMath.MasqPoint;
import Library4997.MasqWrappers.MasqLinearOpMode;

import static Library4997.MasqControlSystems.MasqPurePursuit.MasqWayPoint.PointMode.MECH;
import static Library4997.MasqControlSystems.MasqPurePursuit.MasqWayPoint.PointMode.SWITCH;
import static org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.SubSystems.CVInterpreter.SkystonePosition;
import static org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.SubSystems.CVInterpreter.SkystonePosition.LEFT;
import static org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.SubSystems.CVInterpreter.SkystonePosition.MIDDLE;

/**
 * Created by Keval Kataria on 1/4/2020
 */
@Autonomous(name = "Blue Three Stone", group = "MarkOne")
public class BlueThreeStone extends MasqLinearOpMode {
    private MarkOne robot = new MarkOne();
    private SkystonePosition position;
    private List<MasqWayPoint> stones = new ArrayList<>();
    private MasqWayPoint
            bridge1 = new MasqWayPoint().setPoint(-18, 17.5, -90).setSwitchMode(MECH),
            bridge2 = new MasqWayPoint().setPoint(-59, 21.5, -90),
            foundation = new MasqWayPoint().setPoint(-90, 30, -90).setTargetRadius(3)
                    .setMinVelocity(0.3);

    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeAutonomous();

        stones.add(null);

        stones.add(new MasqWayPoint().setPoint(-17, 30, -90).setMinVelocity(0));
        stones.add(new MasqWayPoint().setPoint(-9, 30.5, -90).setMinVelocity(0));
        stones.add(new MasqWayPoint().setPoint(0, 30, -90).setMinVelocity(0));

        stones.add(new MasqWayPoint().setPoint(5, 30.5, -90).setMinVelocity(0));
        stones.add(new MasqWayPoint().setPoint(15, 30, -90).setMinVelocity(0));
        stones.add(new MasqWayPoint().setPoint(22, 28.5, -90).setMinVelocity(0));

        robot.cv.start();

        while (!opModeIsActive()) {
            position = CVInterpreter.getPosition(robot.cv.detector);
            dash.create("Skystone Position: ", position);
            dash.update();
        }

        waitForStart();

        robot.sideGrabber.rightUp(0);
        robot.sideGrabber.leftUp(0);
        robot.sideGrabber.rightSlightClose(0);
        robot.sideGrabber.leftClose(0);


        if (position == LEFT) runSimultaneously(
                () -> mainAuto(stones.get(1), stones.get(4),stones.get(2)),
                () -> robot.cv.stop()
        );
        else if (position == MIDDLE) runSimultaneously(
                () -> mainAuto(stones.get(2), stones.get(5),stones.get(1)),
                () -> robot.cv.stop()
        );
        else runSimultaneously(
                () -> mainAuto(stones.get(3), stones.get(6),stones.get(1)),
                () -> robot.cv.stop()
        );
    }

    private void mainAuto(MasqWayPoint stone1, MasqWayPoint stone2, MasqWayPoint stone3) {
        grabStone(stone1, true);
        grabStone(stone2,false);
        grabStone(stone3, false);
        foundationPark();
    }

    private void grabStone(MasqWayPoint stone, boolean firstStone) {
        if (firstStone) robot.xyPath(4,stone);
        else robot.xyPath(9,bridge2.setSwitchMode(MECH),bridge1, stone);
        robot.driveTrain.stopDriving();
        robot.sideGrabber.rightDown(1);
        robot.sideGrabber.rightClose(1);
        robot.sideGrabber.rightMid(1);
        if (firstStone) robot.xyPath(5, bridge1, bridge2.setSwitchMode(MECH), foundation);
        else robot.xyPath(5, bridge1, bridge2, foundation);
        robot.driveTrain.stopDriving();
        robot.sideGrabber.rightSlightClose(0);
        robot.sideGrabber.rightLowMid(0);
    }

    private void foundationPark() {
        robot.turnRelative(95,Direction.LEFT);
        robot.xyPath(2, new MasqWayPoint().setPoint(robot.tracker.getGlobalX(),
                robot.tracker.getGlobalY()+5,robot.tracker.getHeading()).setTargetRadius(2)
                .setMinVelocity(0));
        robot.foundationHook.lower();
        sleep(1);
        MasqWayPoint p1 = new MasqWayPoint().setPoint(new MasqPoint(-68, 5, 90));
        MasqWayPoint p2 = new MasqWayPoint().setPoint(new MasqPoint(-75, 5, 90));
        robot.xyPath(4, p1, p2);
        MasqWayPoint park = new MasqWayPoint();
        robot.foundationHook.raise();
        sleep();
        robot.xyPath(4, park);
        dash.create("Completed Path");
        dash.update();
    }
}