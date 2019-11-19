import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;
    public PointSET(){
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty()   {
       return points.isEmpty();
    }
    public               int size()    {
return points.size();
    }
    public void insert(Point2D p)  {
        if (p == null){
            throw new IllegalArgumentException();
        }
        points.add(p);
    }
    public boolean contains(Point2D p)
    {
        if(p == null){
            throw new IllegalArgumentException();
        }

        return points.contains(p);
    }
    public void draw(){
        for (var point: points) {
            StdDraw.point(point.x(), point.y());
        }


    }
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null){
            throw new IllegalArgumentException();
        }

        var list =new ArrayList<Point2D>();
        for(var point : points){
            if (rect.xmin() > point.x()){
                continue;
            }
            if (rect.xmax() < point.x()){
                continue;
            }
            if (rect.ymax()<point.y()){
                continue;
            }
            if (rect.ymin() > point.y()){
                continue;
            }
            list.add(point);

        }

        return new PointIterable(list);
    }
    public Point2D nearest(Point2D p)
    {
        if(p == null){
            throw new IllegalArgumentException();
        }

        if (points.isEmpty())
        {
            return null;
        }

        var min = points.first();

        for (var point : points)
        {
            var minDistance = p.distanceSquaredTo(min);
            if(p.distanceSquaredTo(point) < minDistance)
            {
                min = point;
            }
        }
        return min;
    }

    private class PointIterable implements Iterable<Point2D>{

        private ArrayList<Point2D> list;
        public PointIterable(ArrayList<Point2D> list){
            this.list = list;
        }
        @Override
        public Iterator<Point2D> iterator() {
            return list.iterator();
        }
    }

    public static void main(String[] args)      {
        var points = new PointSET();
        points.nearest(new Point2D(0.0, 0.5));
        points.insert(new Point2D(0.25, 0.5));
        points.insert(new Point2D(0.25, 1.0));
        points.insert(new Point2D(0.25, 0.5));
        points.insert(new Point2D(1.0, 0.75));
        points.insert(new Point2D(0.25, 0.0));
        points.insert(new Point2D(1.0, 0.75));
        points.insert(new Point2D(1.0, 0.25));
        points.insert(new Point2D(0.5, 0.75));
        points.insert(new Point2D(1.0, 0.25));

        System.out.println(points.nearest(new Point2D(1.0, 1.0)));
    }
}