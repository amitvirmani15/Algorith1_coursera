import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args)
    {
        var queue = new RandomizedQueue<String>();
        if (args.length != 1)
        {
            throw new IllegalArgumentException();
        }
        var k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty())
        {
            var str = StdIn.readString();
            queue.enqueue(str);
        }

        for (int i = 0; i < k; i++)
        {
            System.out.println(queue.dequeue());
        }
    }
}
