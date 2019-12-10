package Library4997;

import Library4997.MasqControlSystems.MasqPID.MasqPIDController;
import Library4997.MasqResources.MasqUtils;
import Library4997.MasqSensors.MasqClock;

import static Library4997.MasqResources.MasqUtils.DEFAULT_SLEEP_TIME;
import static Library4997.MasqResources.MasqUtils.sleep;
import static Library4997.MasqResources.MasqUtils.turnController;

/**
 * Created by Keval Kataria on 9/28/2019
 */
public class MasqRobot_Tests {

    public static void main(String[] args) {
        turnAbsolute(180,500,1,DEFAULT_SLEEP_TIME,0);
    }
    private static void turnAbsolute(double angle, double timeout, double speed, double sleepTime, double heading) {
        double targetAngle = MasqUtils.adjustAngle(angle);
        double acceptableError = .5;
        double error = MasqUtils.adjustAngle(targetAngle - heading);
        double power;
        MasqClock timeoutClock= new MasqClock();
        turnController = new MasqPIDController(0.02);
        while ((MasqUtils.adjustAngle(Math.abs(error)) > acceptableError)
                && !timeoutClock.elapsedTime(timeout, MasqClock.Resolution.SECONDS)) {
            error = MasqUtils.adjustAngle(targetAngle - heading);
            power = turnController.getOutput(error) * speed;
            if (Math.abs(power) > 1) {
                power /= Math.abs(power);
            }
            System.out.println(power);
        }
        System.out.println(0);
        sleep(sleepTime);
    }
}
