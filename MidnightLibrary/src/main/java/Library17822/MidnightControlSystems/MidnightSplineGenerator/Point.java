package Library17822.MidnightControlSystems.MidnightSplineGenerator;

public class Point {
    private double x, y, h;

    public Point(double x, double y, double h) {
        this.x = x;
        this.y = y;
        this.h = h;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
