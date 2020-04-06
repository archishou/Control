package Library4997.MidnightCV.detectors;

import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import Library4997.MidnightCV.MidnightCVUtils;
import Library4997.MidnightCV.scoring.MidnightCVScorer;
import Library4997.MidnightWrappers.MidnightDashBoard;



/**
 * Created by Victo on 9/10/2018.
 */

public abstract class MidnightCVDetector extends OpenCvPipeline {

    public abstract Mat process(Mat input);
    public abstract void useDefaults();

    private List<MidnightCVScorer> scorers = new ArrayList<>();
    private Mat workingMat = new Mat();
    public double maxDifference = 10;
    
    protected boolean found = false;

    public MidnightCVUtils.DetectionSpeed speed = MidnightCVUtils.DetectionSpeed.BALANCED;
    protected String detectorName = "MidnightCV Detector";

    private Size size;

    protected enum Stage {
        FINAL_DISPLAY,
        THRESHOLD,
        //CONTOURS,
        //RAW_IMAGE
    }

    protected Stage stageToRenderToViewport = Stage.FINAL_DISPLAY;
    private Stage[] stages = Stage.values();

    public void setSpeed(MidnightCVUtils.DetectionSpeed speed){
        this.speed = speed;
    }
    
    public boolean isDetected(){
        return found;
    }

    public void addScorer(MidnightCVScorer newScorer){
        scorers.add(newScorer);
    }

    public double calculateScore(Mat input){
        double totalScore = 0;

        for(MidnightCVScorer scorer : scorers){
            totalScore += scorer.calculateScore(input);
        }

        return totalScore;
    }


    @Override
    public final Mat processFrame(Mat input) {
        size = input.size();

        Log.d("MidnightCVDetector", "Input mat size:" + input.size());
        input.copyTo(workingMat);

        if(workingMat.empty()){
            return input;
        }

        workingMat = process(workingMat);

        //Print Info
        Imgproc.putText(workingMat,"MidnightCV 2020.1 " + detectorName + ": " + stageToRenderToViewport.toString(), new Point(5,30),0,0.5,new Scalar(0,255,255),2);

        return workingMat;
    }

    @Override
    public void onViewportTapped()
    {
        /*
         * Note that this method is invoked from the UI thread
         * so whatever we do here, we must do quickly.
         */

        int currentStageNum = stageToRenderToViewport.ordinal();

        int nextStageNum = currentStageNum + 1;

        if(nextStageNum >= stages.length)
        {
            nextStageNum = 0;
        }

        stageToRenderToViewport = stages[nextStageNum];
    }

    public Size getSize() {
        return size;
    }

    public Mat cropMat(Mat input, Point tl, Point br)  {
        if (!(tl == null || br == null || tl.x >= input.width() || tl.y >= input.height() || tl.x < 0 || tl.y < 0 || br.x > input.width() || br.y > input.height() || br.x <= 0 || br.y <= 0)) {
            Imgproc.rectangle(input,new Point(tl.x, tl.y),new Point(br.x, br.y),new Scalar(0), -1);
        }
        else {
            MidnightDashBoard.getDash().create("Cropping failed due to invalid cropping margins");
        }
        return input;
    }
}
