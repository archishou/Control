package org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import Library17822.MidnightDriveTrains.MidnightMechanumDriveTrain;
import Library17822.MidnightPositionTracker;
import Library17822.MidnightRobot;
import Library17822.MidnightWrappers.MidnightDashBoard;


/**
 * Created by Archishmaan Peyyety on 2019-08-06.
 * Project: MasqLib
 */
public class ExampleBot extends MidnightRobot {

    public MidnightPositionTracker tracker;

    @Override
    public void mapHardware(HardwareMap hardwareMap) {
        driveTrain = new MidnightMechanumDriveTrain(hardwareMap);
        // These are just three random motors and will most likely not work.
        tracker = new MidnightPositionTracker(driveTrain.leftDrive.motor1, driveTrain.leftDrive.motor2, driveTrain.rightDrive.motor1, hardwareMap);
        dash = MidnightDashBoard.getDash();
    }

    public void init(HardwareMap hardwareMap) {
        mapHardware(hardwareMap);
        tracker.setXRadius(5.68);
        tracker.setTrackWidth(14.625);

        driveTrain.setTracker(tracker);

    }

}