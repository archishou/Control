package Library17822.MidnightSensors;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

import Library17822.MinightResources.MidnightHelpers.MidnightHardware;


/**
 * This is a Modern Robotics Color Sensor.
 * Provides the color number, as well as argb and hsv data.
 * Can detect red, white, and blue, and thresholds for those can be set.
 */

public class MidnightColorSensor implements MidnightHardware {

    private final I2cDevice colorSensor;
    private final I2cDeviceSynch colorSensorManager;
    private final String name;

    private float[] hsvValues = new float[3];

    private static final int
            COLOR_NUMBER_REGISTER = 0x04,
            RED_VALUE_REGISTER = 0x05,
            GREEN_VALUE_REGISTER = 0x06,
            BLUE_VALUE_REGISTER = 0x07,
            WHITE_VALUE_REGISTER = 0x08;

    private static final int
            READ_WINDOW_START = COLOR_NUMBER_REGISTER,
            READ_WINDOW_LENGTH = 5;

    private static final int
            COMMAND_REGISTER = 0x03,
            ACTIVE_MODE_COMMAND = 0x00,
            PASSIVE_MODE_COMMAND = 0x01;

    private int
            blueMinThreshold = 1, blueMaxThreshold = 4,
            redMinThreshold = 10, redMaxThreshold = 12,
            whiteMinThreshold = 14, whiteMaxThreshold = 16;


    public MidnightColorSensor(String name, int i2cAddress, HardwareMap hardwareMap) {
        this.name = name;

        colorSensor = hardwareMap.i2cDevice.get(name);
        colorSensor.resetDeviceConfigurationForOpMode();

        colorSensorManager = new I2cDeviceSynchImpl(colorSensor, I2cAddr.create8bit(i2cAddress), false);
        colorSensorManager.resetDeviceConfigurationForOpMode();
        colorSensorManager.engage();
        colorSensorManager.setReadWindow(new I2cDeviceSynch.ReadWindow(READ_WINDOW_START, READ_WINDOW_LENGTH, I2cDeviceSynch.ReadMode.REPEAT));
    }
    public MidnightColorSensor(String name, HardwareMap hardwareMap) {
        this.name = name;

        colorSensor = hardwareMap.i2cDevice.get(name);
        colorSensor.resetDeviceConfigurationForOpMode();

        colorSensorManager = new I2cDeviceSynchImpl(colorSensor, I2cAddr.create8bit(60), false);
        colorSensorManager.resetDeviceConfigurationForOpMode();
        colorSensorManager.engage();
        colorSensorManager.setReadWindow(new I2cDeviceSynch.ReadWindow(READ_WINDOW_START, READ_WINDOW_LENGTH, I2cDeviceSynch.ReadMode.REPEAT));
    }



    public void setActiveMode() {colorSensorManager.write8(COMMAND_REGISTER, ACTIVE_MODE_COMMAND);}
    public void setPassiveMode() {colorSensorManager.write8(COMMAND_REGISTER, PASSIVE_MODE_COMMAND);}

    public int colorNumber() {return colorSensorManager.read8(COLOR_NUMBER_REGISTER);}
    public int red() {return colorSensorManager.read8(RED_VALUE_REGISTER);}
    public int green() {return colorSensorManager.read8(GREEN_VALUE_REGISTER);}
    public int blue() {return colorSensorManager.read8(BLUE_VALUE_REGISTER);}
    public int alpha() {return colorSensorManager.read8(WHITE_VALUE_REGISTER);}

    public String color () {
        int value = colorNumber();
        if (value == 1) return "PURPLE, 1";
        else if (value == 2) return "DARK BLUE, 2";
        else if (value == 3) return "LIGHT BLUE, 3";
        else if (value == 4) return "GREEN BLUE, 4";
        else if (value == 5) return "GREEN, 5";
        else if (value == 6) return "GREEN YELLOW, 6";
        else if (value == 7) return "YELLOW, 7";
        else if (value == 8) return "YELLOW, 8";
        else if (value == 9) return "ORANGE YELLOW, 9";
        else if (value == 10) return "ORANGE, 10";
        else if (value == 11) return "RED, 11";
        else if (value == 12) return "PINK, 12";
        return "UNKNOWN";
    }

    private void rgb2hsv() {
        byte[] colorSensorCache = colorSensorManager.read(RED_VALUE_REGISTER, 3);
        Color.RGBToHSV(colorSensorCache[0], colorSensorCache[1], colorSensorCache[2], hsvValues);
    }
    public float hue() {rgb2hsv(); return hsvValues[0];}
    public float saturation() {rgb2hsv(); return hsvValues[1];}
    public float value() {rgb2hsv(); return hsvValues[2];}


    public void setBlueThresholds(int min, int max) {
        blueMinThreshold = min;
        blueMaxThreshold = max;
    }

    public void setRedThresholds(int min, int max) {
        redMinThreshold = min;
        redMaxThreshold = max;
    }

    public void setWhiteThresholds(int min, int max) {
        whiteMinThreshold = min;
        whiteMaxThreshold = max;
    }

    public boolean isBlue() {
        int colorNumber = colorNumber();
        return colorNumber >= blueMinThreshold && colorNumber <= blueMaxThreshold;
    }

    public boolean isRed() {
        int colorNumber = colorNumber();
        return colorNumber >= redMinThreshold && colorNumber <= redMaxThreshold;
    }
    public boolean isWhite () {
        int colorNumber = colorNumber();
        return colorNumber >= whiteMinThreshold && colorNumber <= whiteMaxThreshold;
    }
    public String getName() {
        return name;
    }
    public String[] getDash() {
        return new String[]{
                "Color #:" + colorNumber(),
                "Detect White" + isWhite(),
                "Detect Red" + isRed(),
                "Detect Blue" + isBlue()
        };
    }

}