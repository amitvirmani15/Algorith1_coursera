import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] points;
    private LineSegment[] lineSegment;
    private int count = 0;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Arhument not valid");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Argument Not valid");
            }
        }
        Arrays.sort(points);
        Point previousPoint = null;
        for (Point p : points) {
            if (previousPoint != null && previousPoint.equals(p)) {
                throw new IllegalArgumentException("Argument Not valid");
            }
            previousPoint = p;
        }
        this.points = Arrays.copyOfRange(points, 0, points.length);
    }
    public int numberOfSegments(){
    return count;
    }
    public LineSegment[] segments() {

        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
var length = points.length;
    for (int i =0; i< length; i++) {
        {
            for (int j = i + 1; j < length; j++) {
                var slope = points[i].slopeTo(points[j]);
                {
                    for (int k = j + 1; k < length; k++) {
                        if (Double.compare(points[j].slopeTo(points[k]), slope)==0) {
                            for (int l = k + 1; l < length; l++) {
                                if (Double.compare(points[k].slopeTo(points[l]), slope) == 0) {
                                    lineSegments.add(new LineSegment(points[i], points[l]));
                                    count++;

                                }
                            }

                        }
                    }
                }
            }
        }
    }
        lineSegment = lineSegments.toArray(LineSegment[]::new);
        return lineSegment;
    }
}