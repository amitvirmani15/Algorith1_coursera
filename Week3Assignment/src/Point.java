import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point>
{
    private final int x;
    private final int y;
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw()
    {
        StdDraw.point(x, y);
    }
    public void drawTo(Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that)
    {
        if (y < that.y)
        {
            return -1;
        }
        if (y > that.y)
        {
            return 1;
        }
        if (x < that.x)
        {
            return -1;
        }
        if (x > that.x)
        {
            return 1;
        }
        return 0;
    }

    public double slopeTo(Point that)
    {
        if (y == that.y && x == that.x)
        {
            return Double.NEGATIVE_INFINITY;
        }

        if (x == that.x)
        {
            return Double.POSITIVE_INFINITY;
        }
        if (y == that.y)
        {
            return 0;
        }

        var slope = (double)(that.y - y) / (that.x - x);
        return slope;

    }

    public Comparator<Point> slopeOrder()
    {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point>
    {
        @Override
        public int compare(Point point, Point t1)
        {
            var firstSlope = slopeTo(point);
            var secondSlope = slopeTo(t1);
            if (firstSlope<secondSlope)
            {
                return -1;
            }
            if (firstSlope > secondSlope)
            {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args)
    {
        var point = new Point(386,198);
        var point1 = new Point(218,405);

        var point2 = new Point(299,309);
        point.draw();
        point1.draw();
        point.drawTo(point1);
        var slope = point.slopeTo(point1);
        var compare = point.compareTo(point1);
        var comparator = point.slopeOrder().compare(point1, point2);
        System.out.println(slope);
        System.out.println(comparator);
        System.out.println(compare);
    }
}
