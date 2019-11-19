import edu.princeton.cs.algs4.In;

public class App {
    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        var bag = new WeightedBag(arr);
        bag.Union(0,1);
        bag.Union(2,3);
        bag.Union(3,4);
        bag.Union(4,5);
        bag.Union(5,6);
        bag.Union(6,7);
        bag.Union(7,8);
        bag.Union(8,9);
    }

}