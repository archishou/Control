package Library4997;

import Library4997.MinightResources.MidnightHelpers.MidnightHardware;
import Library4997.MidnightWrappers.MidnightController;

/**
 * Created by Archish on 2/12/18.
 */

public interface MidnightSubSystem {
    void DriverControl(MidnightController controller) throws InterruptedException;
    String getName();
    MidnightHardware[] getComponents();
}
