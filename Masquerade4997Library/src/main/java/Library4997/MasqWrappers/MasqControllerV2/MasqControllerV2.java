package Library4997.MasqWrappers.MasqControllerV2;

import com.qualcomm.robotcore.hardware.Gamepad;

import Library4997.MasqUtilities.MasqUtils;

/**
 * Created by Archish on 4/11/18.
 */

public class MasqControllerV2 {
    public Runnable updateThread;
    private Gamepad gamepad;
    private String name;
    private boolean
            aPrev = false, bPrev = false, xPrev = false, yPrev = false,
            leftBumperPrev = false, rightBumperPrev = false, rightTriggerPrev = false, leftTriggerPrev = false;
    public MasqControllerV2(Gamepad g, String name){
        this.name = name;
        this.gamepad = g;
    }
    public boolean a() {return gamepad.a;}
    public boolean x() {return gamepad.x;}
    public boolean y() {return gamepad.y;}
    public boolean b() {return gamepad.b;}

    public boolean aOnPress() {return a() && !aPrev;}
    public boolean bOnPress() {return b() && !bPrev;}
    public boolean yOnPress() {return y() && !yPrev;}
    public boolean xOnPress() {return x() && !xPrev;}

    public float leftStickX(){return gamepad.left_stick_x;}
    public float leftStickY() {
        return -gamepad.left_stick_y;
    }
    public float rightStickX() {
        return gamepad.right_stick_x;
    }
    public float rightStickY() {
        return -gamepad.right_stick_y;
    }

    public boolean dPadUp() {return gamepad.dpad_up;}
    public boolean dPadDown() {
        return gamepad.dpad_down;
    }
    public boolean dPadLeft() {
        return gamepad.dpad_left;
    }
    public boolean dPadRight() {
        return gamepad.dpad_right;
    }

    public boolean leftBumper() {
        return gamepad.left_bumper;
    }
    public boolean rightBumper() {
        return gamepad.right_bumper;
    }

    public float leftTrigger() {return gamepad.left_trigger;}
    public float rightTrigger() {return gamepad.right_trigger;}

    public boolean leftBumperOnPress () {
        return leftBumper() && !leftBumperPrev;
    }
    public boolean rightBumperOnPress () {return rightBumper() && !rightBumperPrev;}

    public boolean leftStickButton() {
        return gamepad.left_stick_button;
    }
    public boolean rightStickButton() {
        return gamepad.right_stick_button;
    }

    public boolean leftTriggerPressed() {
        return leftTrigger() > 0;
    }
    public boolean rightTriggerPressed() {
        return rightTrigger() > 0;
    }
    public boolean leftTriggerOnPress() {
        return leftTriggerPressed() && !leftTriggerPrev;
    }
    public boolean rightTriggerOnPress() {return rightTriggerPressed() && !rightTriggerPrev;}
    public double getLeftStickAngle () {
        return Math.toDegrees(Math.atan2(leftStickY(), leftStickX()));
    }
    public double getRightStickAngle () {
        return Math.toDegrees(Math.atan2(rightStickY(), rightStickX()));
    }

    public void startUpdate () {
        updateThread = new Runnable() {
            @Override
            public void run() {
                while (MasqUtils.opModeIsActive()) {
                    aPrev = gamepad.a;
                    bPrev = gamepad.b;
                    xPrev = gamepad.x;
                    yPrev = gamepad.y;
                    leftBumperPrev = gamepad.left_bumper;
                    rightBumperPrev = gamepad.right_bumper;
                    rightTriggerPrev = rightTriggerPressed();
                    leftTriggerPrev = leftTriggerPressed();
                    MasqUtils.sleep(10);
                }
            }
        };
        Thread t = new Thread(updateThread);
        t.start();
    }
}
