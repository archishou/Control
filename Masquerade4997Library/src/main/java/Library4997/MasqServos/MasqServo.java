package Library4997.MasqServos;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import Library4997.MasqResources.MasqHelpers.MasqHardware;
import Library4997.MasqSensors.MasqClock;
import Library4997.MasqSensors.MasqLimitSwitch;

/**
 * Created by Archish on 10/28/16.
 */

public class MasqServo implements MasqHardware{
    private Servo servo;
    private String nameServo;
    MasqClock clock = new MasqClock();
    private double max = 1, min = 0;
    private MasqLimitSwitch limMin, limMax;
    private boolean limDetection;
    private double adjustedPosition;

    public static boolean currState=false, prevState=false, taskState=false;


    public MasqServo(String name, HardwareMap hardwareMap) {
        this.nameServo = name;
        servo = hardwareMap.servo.get(name);
    }
    public MasqServo(String name, Servo.Direction direction, HardwareMap hardwareMap){
        this.nameServo = name;
        servo = hardwareMap.servo.get(name);
        servo.setDirection(direction);
    }
    public void setPosition (double position) {
        adjustedPosition = ((max - min) * position) + min;
        servo.setPosition(adjustedPosition);
    }
    public void setDirection(Servo.Direction direction) {
        servo.setDirection(direction);
    }
    public void setLimits (MasqLimitSwitch min, MasqLimitSwitch max){
        limMin = min; limMax = max;
        limDetection = true;
    }
    private boolean limitPressed () {
        if (limDetection) return  limMin.isPressed() || limMax.isPressed();
        else return false;
    }
    public double getPosition () {
        return servo.getPosition();
    }
    public double getRawPosition() {
        return adjustedPosition;
    }
    public void setMax(double max){this.max = max;}
    public void setMin(double min){this.min = min;}
    public void scaleRange (double min, double max) {
        servo.scaleRange(min, max);
    }
    public void sleep (int time) throws InterruptedException {
        servo.wait(time);
    }
    public String getName() {
        return nameServo;
    }

    public String[] getDash() {
        return new String[]{
                "Current Position:" + servo.getPosition()
        };
    }

    public void toggle(boolean button) {
        /*if (tolerance(servo.getPosition(), prevPos,0.01) && button) {
            if (tolerance(servo.getPosition(), 0, 0.01)) servo.setPosition(1);
            else if (tolerance(servo.getPosition(), 1, 0.01)) servo.setPosition(0);
        }*/
        if (button) {
            currState = true;
        } else {
            currState = false;
            if (prevState) {
                taskState = !taskState;
            }
        }

        prevState = currState;

        if (taskState) {
            setPosition(1);
        } else {
            setPosition(0);
        }
    }
}