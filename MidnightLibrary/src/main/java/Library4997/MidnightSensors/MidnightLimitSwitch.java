package Library4997.MidnightSensors;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Library4997.MinightResources.MidnightHelpers.MidnightHardware;


/**
 * This is a basic limit switch.
 * Assumes that a pull-up resistor is used, like is necessary to use VEX limit switches.
 * To wire this you need a 1.5k ohm resistor that wires from the power to signal wires.
 */

public class MidnightLimitSwitch implements MidnightHardware {
    private final DigitalChannel limitSwitch;
    private final String name;

    private boolean signalValue;
    private boolean logicalState;

    public MidnightLimitSwitch(String name, HardwareMap hardwareMap) {
        this.name = name;
        limitSwitch = hardwareMap.digitalChannel.get(name);
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
    }
    public void updateState() {
        signalValue = limitSwitch.getState();
        logicalState = !signalValue;
    }
    public boolean getSignalValue() {
        updateState();
        return signalValue;
    }

    public boolean getState() {
        updateState();
        return logicalState;
    }

    public boolean isPressed() {
        return !getState();
    }
    public boolean pr() {
        boolean pressed = false, released = false;
        while (isPressed()) {
            released = false;
            pressed = true;
        }
        while (!isPressed()) released = true;
        return pressed && released;
    }
    public boolean isReleased () {
        boolean released = false;
        while (isPressed()) released = false;
        while (!isPressed()) released = true;
        return released;
    }
    public String getName() {return name;}
    public String[] getDash() {
        return new String[] {
            "Is Pressed: " + isPressed(),
            "Pressed and Released: " + pr(),
            "Released: " + isReleased()
        };
    }
}