import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.Iterator;


public class KdTree {

    private Node rootNode;

    private class Node
    {
        private Node leftNode;

        private Node rightNode;
        private RectHV rectangle;
        private Point2D data;
        private int depth;
    }

    private int size;

    public KdTree()
    {
    //Kd Tree
    }

    public boolean isEmpty()    {
       return rootNode == null;
    }

    public int size()          {
        return size;
    }

    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        if (rootNode == null)
        {
            rootNode = new Node();
            rootNode.data = p;
            rootNode.depth=0;
            rootNode.rectangle = new RectHV(0, 0, p.x(), 1);
            size++;
            return;
        }

        insert(rootNode, p, rootNode.rectangle, rootNode.depth+1);
    }


    private Node insert(Node node, Point2D p, RectHV parentRec, int depth)
    {

        if (node == null)
        {
            var newnode =  new Node();
            newnode.data = p;
            newnode.depth = depth;
            newnode.rectangle = new RectHV(parentRec.xmin(), parentRec.ymin(), p.x(), parentRec.ymax());
            size++;
            return newnode;
        }
        if (node.data.equals(p)){
            return node;
        }

        else if (node.depth%2==0)
        {
            if(p.x() <= node.data.x())
            {
                node.leftNode = insert(node.leftNode, p, node.rectangle, node.depth+1);
                return node;
            }
            node.rightNode = insert(node.rightNode, p, node.rectangle, node.depth+1);
            return node;
        }
        else
        {
            if (p.y() <= node.data.y())
            {
                node.leftNode = insert(node.leftNode, p, node.rectangle, node.depth+1);
                return node;
            }
            node.rightNode = insert(node.rightNode, p, node.rectangle, node.depth+1);
            return node;
        }
    }

    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        if (rootNode!=null){
            var node = traverse(rootNode, p);
            if (node!=null){
                return true;
            }
        }

        return false;
    }

    private Node traverse(Node node, Point2D p)
    {
        if (node == null)
        {
            return null;
        }
        if (node.data.equals(p))
        {
            return node;
        }
        else if (node.depth % 2 == 0)
        {
            if (p.x() <= node.data.x())
            {
                return traverse(node.leftNode, p);
            }
            return traverse(node.rightNode, p);
        }
        else
            {
            if (p.y() <= node.data.y())
            {
                return traverse(node.leftNode, p);
            }
            return traverse(node.rightNode, p);
        }
    }

    private void Draw(Node node)
    {
        if(node == null){
            return;
        }
        if(node.leftNode != null){
            Draw(node.leftNode);
        }
        node.data.draw();
        if (node.rightNode != null){
            Draw(node.rightNode);
        }
    }

    public void draw(){
        Draw(rootNode);
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
        {
            throw new IllegalArgumentException();
        }
        var list = new ArrayList<Point2D>();
        Range(rootNode, rect, list);
        var iterable = new PointIterable(list);

        return iterable;
    }

    private class PointIterable implements Iterable<Point2D>
    {
        private ArrayList<Point2D> list;
        public PointIterable(ArrayList<Point2D> list){
            this.list = list;
        }
        @Override
        public Iterator<Point2D> iterator() {
            return list.iterator();
        }
    }

    private void Range(Node node, RectHV rect, ArrayList<Point2D> list){

        if (node == null){
            return;
        }

        if (inRectangle(rect, node.data)) {
            if(!list.contains(node.data))
            {
                list.add(node.data);
            }
            Range(node.leftNode, rect, list);
            Range(node.rightNode, rect, list);
        }

//        Range(node.leftNode, rect, list);
//        Range(node.rightNode, rect, list);
        else if(node.depth%2 == 0){
            if (rect.xmin()<= node.data.x() && rect.xmax() <= node.data.x()){
                Range(node.leftNode, rect, list);
            }
            else if (rect.xmin() > node.data.x() && rect.xmax() > node.data.x()){
                Range(node.rightNode, rect, list);
            }
            else {
                Range(node.leftNode, rect, list);
                Range(node.rightNode, rect, list);
            }
        }
        else{
            if (rect.ymin() < node.data.y() && rect.ymax() < node.data.y()){
                Range(node.leftNode, rect, list);
            }
            else if (rect.ymin() > node.data.y() && rect.ymax() > node.data.y()){
                Range(node.rightNode, rect, list);
            }
            else {
                Range(node.leftNode, rect, list);
                Range(node.rightNode, rect, list);
            }
        }


    }

    private boolean inRectangle(RectHV rect, Point2D point)
    {
        if (point.x() >= rect.xmin() && point.x() <= rect.xmax() && point.y() >= rect.ymin() && point.y() <= rect.ymax())
        {
            return true;
        }

        return false;
    }

    private Node FindNearest(Node node, Point2D p, Node nearest, boolean skip){

        if (node == null)
        {
            return nearest;
        }
        var distance = p.distanceSquaredTo(node.data);
        var mindistance = p.distanceSquaredTo(nearest.data);
        if (distance < mindistance){
            nearest = node;
        }
        nearest = FindNearest(node.leftNode, p, nearest, skip);
        nearest = FindNearest(node.rightNode, p, nearest, skip);
//        if (node.depth%2 == 0){
//            if (p.x() <= node.data.x()) {
//                nearest = FindNearest(node.leftNode, p, nearest, skip);
//
//                if (node.depth >= nearest.depth) {
//                    nearest = FindNearest(node.rightNode, p, nearest, skip);
//                }
//            }
//            else {
//                nearest = FindNearest(node.rightNode, p, nearest, skip);
//
//                if (node.depth >= nearest.depth) {
//                    nearest = FindNearest(node.leftNode, p, nearest, skip);
//                }
//            }
//        }
//        else {
//            if (p.y() <= node.data.y()) {
//                nearest = FindNearest(node.leftNode, p, nearest, skip);
//
//                if (node.depth >= nearest.depth) {
//                    nearest = FindNearest(node.rightNode, p, nearest, skip);
//                }
//            }
//            else {
//                nearest = FindNearest(node.rightNode, p, nearest, skip);
//
//                if (node.depth >= nearest.depth) {
//                    nearest = FindNearest(node.leftNode, p, nearest, skip);
//                }
//            }
//        }
        return nearest;
    }

    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        if (rootNode == null){
            return null;
        }
        var nearest = FindNearest(rootNode, p, rootNode, false);
        return nearest.data;
    }

    public static void main(String[] args)
    {
    //Kd tree
        var tree = new KdTree();
//        tree.insert(new Point2D(.372, .497));
//        tree.insert(new Point2D(.564, .413));
//        tree.insert(new Point2D(.226, .577));
//        tree.insert(new Point2D(.144, .179));
//        tree.insert(new Point2D(.083, .51));
//        tree.insert(new Point2D(.32, .708));
//        tree.insert(new Point2D(.417, .362));
//        tree.insert(new Point2D(0.862, .825));
//        tree.insert(new Point2D(.785, .725));
//        tree.insert(new Point2D(.499, .208));
//        tree.insert(new Point2D(1.0, 0.0));
//        tree.insert(new Point2D(1.0, 1.0));
//        tree.insert(new Point2D(0625, 1.0));
//        tree.insert(new Point2D(0.0, 0.25));
//        tree.insert(new Point2D(1.0, 0.5));
//        tree.insert(new Point2D(0.25, 0.0));
//        tree.insert(new Point2D(.5, 1.0));
//        tree.insert(new Point2D(0.75, 0.0));
//        tree.insert(new Point2D(.372, 0.497));
//        tree.insert(new Point2D(0.564, 0.413));
//        tree.insert(new Point2D(0.226, .577));
//        tree.insert(new Point2D(.144, 0.179));
//        tree.insert(new Point2D(.083, .51));
        tree.insert(new Point2D(.7, 0.2));
        tree.insert(new Point2D(0.5, .4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(.4, 0.7));
        tree.insert(new Point2D(.9, 0.6));

        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        System.out.println(kdtree.nearest(new Point2D(.52, .66)));
        var ranges = kdtree.range(new RectHV(.0, .21875, .1875, .46875));
        for (var range : ranges){
            System.out.println(range.x()+" , "+  range.y());
        }

    }
}
