package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.SubSystems.MasqalorianFoundationHook;

import Library4997.MasqControlSystems.MasqPID.MasqPIDController;
import Library4997.MasqControlSystems.MasqPurePursuit.MasqPositionTracker;
import Library4997.MasqDriveTrains.MasqMechanumDriveTrain;
import Library4997.MasqMotors.MasqMotor;
import Library4997.MasqMotors.MasqMotorSystem;
import Library4997.MasqResources.MasqHelpers.MasqMotorModel;
import Library4997.MasqResources.MasqUtils;
import Library4997.MasqRobot;
import Library4997.MasqServos.MasqServo;
import Library4997.MasqWrappers.DashBoard;

/**
 * Created by Archishmaan Peyyety on 2019-08-06.
 * Project: MasqLib
 */
public class Masqalorian extends MasqRobot {

    public MasqServo blockGrabber, blockRotater, blockPusher, capper, blockStopper;
    public MasqalorianFoundationHook foundationHook;
    public MasqMotor  lift;
    public MasqMotorSystem intake;
    public MasqalorianDetector detector;

    @Override
    public void mapHardware(HardwareMap hardwareMap) {
        driveTrain = new MasqMechanumDriveTrain(hardwareMap);
        blockGrabber = new MasqServo("blockGrabber", hardwareMap);
        lift = new MasqMotor("lift", MasqMotorModel.NEVEREST60, hardwareMap);
        blockRotater = new MasqServo("blockRotater", hardwareMap);
        intake = new MasqMotorSystem("intakeRight", DcMotorSimple.Direction.FORWARD, "intakeLeft", DcMotorSimple.Direction.REVERSE,MasqMotorModel.REVHDHEX40, hardwareMap);
        blockPusher = new MasqServo("blockPusher", hardwareMap);
        capper = new MasqServo("capper", hardwareMap);
        blockStopper = new MasqServo("blockStopper", hardwareMap);
        tracker = new MasqPositionTracker(lift, intake.motor1, hardwareMap); //Replace motors when odometry is incorporating
        foundationHook = new MasqalorianFoundationHook(hardwareMap);
        dash = DashBoard.getDash();
        detector = new MasqalorianDetector(hardwareMap);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        mapHardware(hardwareMap);
        scaleServos();
        resetServos();
        lift.encoder.setWheelDiameter(1);
        MasqUtils.driveController = new MasqPIDController(1,0,0);
        MasqUtils.angleController = new MasqPIDController(0.006,0,0);
        MasqUtils.turnController = new MasqPIDController(0.02,0,0);
        MasqUtils.velocityTeleController = new MasqPIDController(0.002, 0, 0);
        MasqUtils.velocityAutoController = new MasqPIDController(0.002, 0, 0);
        driveTrain.setClosedLoop(true);
        lift.setClosedLoop(true);
        lift.setKp(0.005);
        driveTrain.resetEncoders();
    }

    private void scaleServos() {
        blockPusher.scaleRange(0, 0.5);
        blockGrabber.scaleRange(0, 0.5);
        blockRotater.scaleRange(0.02, 0.7);
        capper.scaleRange(0.5,1);
        blockStopper.scaleRange(0.35,1);
    }

    private void resetServos() {
        blockPusher.setPosition(0);
        blockGrabber.setPosition(1);
        foundationHook.lower();
        capper.setPosition(0);
        blockStopper.setPosition(1);
    }
}