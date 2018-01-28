package Library4997.MasqSensors;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Library4997.MasqExternal.MasqHardware;
import Library4997.MasqExternal.MasqSensor;

/**
 * Created by Archish on 10/29/17.
 */

public class MasqREVColorSensor implements MasqHardware, MasqSensor {
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;
    private String name;
    float hsvValues[] = {0F, 0F, 0F};
    final double SCALE_FACTOR = 255;

    public MasqREVColorSensor(String name, HardwareMap hardwareMap){
        this.name = name;
        colorSensor = hardwareMap.colorSensor.get(name);
        distanceSensor = hardwareMap.get(DistanceSensor.class, name);
    }
    public double getDistance(DistanceUnit unit) {
        return distanceSensor.getDistance(unit);
    }
    public int getBlue () {
        Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                (int) (colorSensor.green() * SCALE_FACTOR),
                (int) (colorSensor.blue() * SCALE_FACTOR),
                hsvValues);
        return colorSensor.blue();
    }
    public int getRed () {
        Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                (int) (colorSensor.green() * SCALE_FACTOR),
                (int) (colorSensor.blue() * SCALE_FACTOR),
                hsvValues);
        return colorSensor.red();
    }
    public int getGreen () {
        Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                (int) (colorSensor.green() * SCALE_FACTOR),
                (int) (colorSensor.blue() * SCALE_FACTOR),
                hsvValues);
        return colorSensor.green();
    }
    public void setActive () {colorSensor.enableLed(true);}
    public void setPassive() {colorSensor.enableLed(false);}

    public boolean isBlue () {return getBlue() > getRed();}
    public boolean isRed () {return getRed() > getBlue();}

    @Override
    public boolean stop() {
        return (getDistance(DistanceUnit.CM) > 6) || (getDistance(DistanceUnit.CM) != getDistance(DistanceUnit.CM));
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String[] getDash() {
        return new String[] {
                name,
                "Detect Blue: ", Boolean.toString(isBlue()),
                "Detect Red: ", Boolean.toString(isRed()),
                "Green" + getGreen(),
                "Blue" + getBlue(),
                "Red" + getRed(),
                "Distance in CM" + getDistance(DistanceUnit.INCH)
        };
    }
}
