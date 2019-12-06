package org.firstinspires.ftc.teamcode.Robots.Masqalorian.Robot.SubSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import Library4997.MasqResources.MasqHelpers.MasqHardware;
import Library4997.MasqServos.MasqServoSystem;
import Library4997.MasqSubSystem;
import Library4997.MasqWrappers.MasqController;

/**
 * Created by Archishmaan Peyyety on 2019-11-10.
 * Project: MasqLib
 */
public class MasqalorianFoundationHook implements MasqSubSystem {
    private MasqServoSystem foundationHook;

    public MasqalorianFoundationHook(HardwareMap hardwareMap) {
        foundationHook = new MasqServoSystem("rightGrabber", "leftGrabber", hardwareMap);
        foundationHook.scaleRange(0, 1);
    }

    @Override
    public void DriverControl(MasqController controller) {
        if(controller.b()) lower();
        else if (controller.x()) raise();
        else mid();
    }

    public void lower() {
        foundationHook.servo1.setPosition(1);
        foundationHook.servo2.setPosition(0);
    }
    public void raise() {
        foundationHook.servo1.setPosition(0);
        foundationHook.servo2.setPosition(1);
    }
    public void mid() {
        foundationHook.servo1.setPosition(0.2);
        foundationHook.servo2.setPosition(0.8);
    }

    @Override
    public String getName() {
        return "FoundatonHook";
    }
    @Override
    public MasqHardware[] getComponents() {
        return new MasqHardware[0];
    }
}
