package Library4997.MasqControlSystems.MasqSplineGeneration;

import java.util.Arrays;
import java.util.List;

import Library4997.MasqControlSystems.MasqPurePursuit.MasqWayPoint;

/**
 * Created by Archishmaan Peyyety on 2020-01-19.
 * Project: MasqLib
 */
public class MasqSpline {
    private List<MasqWayPoint> wayPoints;
    public MasqSpline(MasqWayPoint... wayPoints) {
        this.wayPoints = Arrays.asList(wayPoints);
    }

}
