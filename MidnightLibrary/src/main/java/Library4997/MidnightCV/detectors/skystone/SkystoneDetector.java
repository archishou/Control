package Library4997.MidnightCV.detectors.skystone;

import com.disnodeteam.dogecv.filters.DogeCVColorFilter;
import com.disnodeteam.dogecv.filters.GrayscaleFilter;
import com.disnodeteam.dogecv.filters.LeviColorFilter;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Library4997.MidnightCV.detectors.MidnightCVDetector;
import Library4997.MinightResources.MidnightUtils;
import Library4997.MidnightWrappers.MidnightDashBoard;

public class SkystoneDetector extends MidnightCVDetector {
    private DogeCVColorFilter blackFilter = new GrayscaleFilter(0, 50);
    private DogeCVColorFilter yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 90);
    public int blobDistanceThreshold;
    public int minimumArea;
    private int imageWidth = 320;
    private int imageHeight = 240;

    private Rect secondBlackRect = new Rect();
    private Rect foundRect = new Rect();

    private Mat rawImage = new Mat();
    private Mat workingMat = new Mat();
    private Mat displayMat = new Mat();
    private Mat blackMask = new Mat();
    private Mat yellowMask = new Mat();
    private Mat hierarchy  = new Mat();

    public int offset = 0;

    private Point tl, br;

    public Rect foundRectangle() {
        return foundRect;
    }

    @Override
    public Mat process(Mat input) {
        input.copyTo(rawImage);
        cropMat(input,tl, br);
        input.copyTo(workingMat);
        input.copyTo(displayMat);
        input.copyTo(blackMask);

        List<MatOfPoint> contoursYellow = findContours(yellowFilter, yellowMask);
        List<Rect> rectsYellow = contoursToRects(contoursYellow);
        List<List<Rect>> listOfYellowBlobs = groupIntoBlobs(rectsYellow, blobDistanceThreshold);
        Rect yellowBoundingRect = chooseBestYellow(listOfYellowBlobs);

        List<MatOfPoint> contoursBlack = findContours(blackFilter, blackMask);
        List<Rect> rectsBlack = contoursToRects(contoursBlack);
        List<Rect> rectsBlackInYellow = filterByBound(rectsBlack, yellowBoundingRect);
        List<List<Rect>> listOfBlackBlobs = groupIntoBlobs(rectsBlack, blobDistanceThreshold);
        Rect bestSkystoneRect = chooseBestBlack(listOfBlackBlobs);

        //draw(contoursYellow, new Scalar(255, 150, 0));
        draw(contoursBlack, new Scalar(80, 80, 80));
        //draw(yellowBoundingRect, new Scalar(255, 255, 0));

        found = bestSkystoneRect.area() > minimumArea;
        if (!found) {
            found = secondBlackRect.area() > minimumArea;
        }
        if (found) {
            draw(bestSkystoneRect, new Scalar(0, 255, 0));
            draw(getCenterPoint(bestSkystoneRect), new Scalar(0, 255, 0));
            foundRect = bestSkystoneRect;
            Imgproc.putText(displayMat, "Chosen", foundRect.tl(),0,1,new Scalar(255,255,255));
        }

        switch (stageToRenderToViewport) {
            case THRESHOLD:
                Imgproc.cvtColor(blackMask, blackMask, Imgproc.COLOR_GRAY2BGR);
                return blackMask;
            default:
                return displayMat;
        }
    }

    @Override
    public void useDefaults() {
        blackFilter = new GrayscaleFilter(0, 50);
        yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 90);
        blobDistanceThreshold = 50;
        minimumArea = 100;
    }


    private double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    private Point getCenterPoint(Rect rect) {
        return new Point(rect.x + rect.width/2, rect.y + rect.height/2);
    }

    private Rect boundingRect(List<Rect> rects) {
        int minX = 999;
        int minY = 999;
        int maxX = 0;
        int maxY = 0;
        for (Rect rect : rects) {
            minX = Math.min(rect.x, minX);
            minY = Math.min(rect.y, minY);
            maxX = Math.max(rect.x + rect.width, maxX);
            maxY = Math.max(rect.y + rect.height, maxY);
        }

        return new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    private List<MatOfPoint> findContours(DogeCVColorFilter filter, Mat mask) {
        filter.process(workingMat.clone(), mask);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }


    private void draw(Rect rect, Scalar color) {
        Imgproc.rectangle(displayMat, rect.tl(), rect.br(), color, 2);

    }

    private void draw(Point point, Scalar color) {
        Imgproc.circle(displayMat, point, 2, color);
    }

    private void draw(List<MatOfPoint> contours, Scalar color) {
        // Not draw contours for now, since can look messy
        Imgproc.drawContours(displayMat, contours, -1, color, 2);
    }


    private Rect chooseBestYellow(List<List<Rect>> listOfYellowBlobs) {
        Rect bestYellowRect = new Rect();
        for (List<Rect> blob : listOfYellowBlobs) {
            Rect blobBound = boundingRect(blob);
            draw(blobBound, new Scalar(255 , 100, 0));

            if (blobBound.area() > bestYellowRect.area()) {
                bestYellowRect = blobBound;
            }
        }
        return bestYellowRect;
    }

    private Rect chooseBestBlack(List<List<Rect>> listOfBlackBlobs) {
        Rect bestBlackRect = new Rect();
        for (List<Rect> blob : listOfBlackBlobs) {
            Rect blobBound = boundingRect(blob);
            draw(blobBound, new Scalar(0, 150, 0));

            if (blobBound.area() > bestBlackRect.area()) {
                secondBlackRect = bestBlackRect;
                bestBlackRect = blobBound;
            }
        }
        return bestBlackRect;
    }

    private  List<Rect> contoursToRects(List<MatOfPoint> contours) {
        List<Rect> rects = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            rects.add(Imgproc.boundingRect(contour));
        }
        return rects;
    }

    private List<List<Rect>> groupIntoBlobs(List<Rect> rects, int blobDistanceThreshold) {
        List<List<Rect>> listOfBlobs = new ArrayList<>();
        List<Rect> unusedRects = new ArrayList<>(rects);

        while (!unusedRects.isEmpty()) {
            LinkedList<Rect> toProcess = new LinkedList<>();
            toProcess.add(unusedRects.remove(0));
            List<Rect> currentBlob = new ArrayList<>();
            while (!toProcess.isEmpty()) {
                Rect currentRect = toProcess.poll();
                currentBlob.add(currentRect);

                for (int i = 0; i < unusedRects.size(); i++) {
                    if (distance(getCenterPoint(currentRect), getCenterPoint(unusedRects.get(i))) < blobDistanceThreshold) {
                        toProcess.add(unusedRects.remove(i));
                        i--;
                    }
                }
            }
            listOfBlobs.add(currentBlob);
        }

        return listOfBlobs;
    }

    private List<Rect> filterByBound(List<Rect> rects, Rect boundingRect) {
        List<Rect> rectsInsideBound = new ArrayList<>();
        for (Rect rect : rects) {
            if (boundingRect.contains(getCenterPoint(rect))) {
                rectsInsideBound.add(rect);
            }
        }
        return rectsInsideBound;
    }
    public Mat cropMat(Mat input, Point tl, Point br)  {
        if (!(tl == null || br == null || tl.x >= input.width() || tl.y >= input.height() || tl.x < 0 || tl.y < 0 || br.x > input.width() || br.y > input.height() || br.x <= 0 || br.y <= 0)) {
            Imgproc.rectangle(input,new Point(0,0),new Point(input.width(), tl.y), new Scalar(255,255,255), -1);
            Imgproc.rectangle(input, new Point(0,0), new Point(tl.x, input.height()), new Scalar(255,255,255), -1);
            Imgproc.rectangle(input, new Point(input.width(), input.height()), new Point(br.x, 0), new Scalar(255,255,255), -1);
            Imgproc.rectangle(input, new Point(input.width(), input.height()), new Point(0, br.y), new Scalar(255,255,255), -1);
        }
        else {
            MidnightDashBoard.getDash().create("Cropping failed due to invalid cropping margins");
        }
        return input;
    }
    public void setClippingMargins(Point tl, Point br) {
        this.tl = tl;
        this.br = br;
        imageWidth = (int) (br.x - tl.x);
        imageHeight = (int) (tl.y - br.y);
        offset = (int) tl.x;
    }
    public void setClippingMargins(int top, int left, int bottom, int right) {
        tl = new Point(left, top);
        br = new Point(320 - right,240 - bottom);
        imageWidth = 320 - right - left;
        imageHeight = top - 240 + bottom;
        offset = left;
    }
    public enum SkystonePosition {
        LEFT("LEFT"), MIDDLE("MIDDLE"), RIGHT("RIGHT");
        public final String position;
        SkystonePosition (String value) {this.position = value;}
    }
    public SkystonePosition getPosition() {
        if ((MidnightUtils.getCenterPoint(foundRectangle()).x - offset) < getImageWidth()/3) return SkystonePosition.LEFT;
        else if ((MidnightUtils.getCenterPoint(foundRectangle()).x - offset) < (2 * getImageWidth()/3)) return SkystonePosition.MIDDLE;
        else return SkystonePosition.RIGHT;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}