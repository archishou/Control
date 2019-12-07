package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.Masqalorian;

import Library4997.MasqWrappers.MasqLinearOpMode;

/**
 * Created by Keval Kataria on 12/6/2019
 */
@TeleOp(name = "EncoderTest", group = "Masqalorian")
public class EncoderTest extends MasqLinearOpMode {
    private Masqalorian robot = new Masqalorian();
    @Override
    public void runLinearOpMode() throws InterruptedException {
     robot.init(hardwareMap);

     waitForStart();

     robot.foundationHook.raise();

     while(opModeIsActive()) {
         robot.driveTrain.setPower(.125);
         dash.create("FL: ",robot.driveTrain.leftDrive.motor1.getCurrentPosition());
         dash.create("BL: ", robot.driveTrain.leftDrive.motor2.getCurrentPosition());
         dash.create("FR: ", robot.driveTrain.rightDrive.motor1.getCurrentPosition());
         dash.create("BR: ", robot.driveTrain.rightDrive.motor2.getCurrentPosition());
         dash.update();
     }
    }
}
