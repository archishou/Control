package Library17822.MidnightSensors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import Library17822.MinightResources.MidnightHelpers.MidnightHardware;

import static Library17822.MinightResources.MidnightUtils.formatAngle;


/**
 * Created by Archish on 1/8/18.
 */

public class MidnightAdafruitIMU implements MidnightHardware {
    BNO055IMU imu;
    Orientation angles;
    double zeroPos = 0;
    public MidnightAdafruitIMU(String name, HardwareMap hardwareMap) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, name);
        imu.initialize(parameters);

    }
    public double getAbsoluteHeading() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return formatAngle(angles.angleUnit, angles.firstAngle);
    }
    public double getRelativeYaw() {
        return getAbsoluteHeading() - zeroPos;
    }
    public void reset(){
        zeroPos = getAbsoluteHeading();
    }
    public double getPitch() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return formatAngle(angles.angleUnit, angles.thirdAngle);
    }
    public double getRoll() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return formatAngle(angles.angleUnit, angles.secondAngle);
    }

    public double x () {
        return imu.getPosition().x;
    }
    public double y () {
        return imu.getPosition().y;
    }
    public double z () {
        return imu.getPosition().z;
    }

    public String getName() {
        return "IMU";
    }
    public String[] getDash() {
        return new String[]{
                "Heading:" + Double.toString(getAbsoluteHeading()),
                "Roll" + Double.toString(getRoll()),
                "Pitch" + Double.toString(getPitch())
        };
    }
}