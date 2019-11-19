import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] queue;

    private int size;

    private int endIndex = 0;
    // construct an empty randomized queue
    public RandomizedQueue()
    {
        queue = (Item[])new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return size;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
        if (endIndex == queue.length)
        {
            reSize(queue.length*2);
        }
        queue[endIndex++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (size == 0)
        {
            throw  new java.util.NoSuchElementException();
        }

        if (size == queue.length/4)
        {
            reSize(queue.length/2);
        }

        var index = StdRandom.uniform(0, size);
        var item = queue[index];
        endIndex = endIndex-1;
        queue[index] = queue[endIndex];
        queue[endIndex] = null;

        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (size == 0)
        {
            throw  new java.util.NoSuchElementException();
        }

        var index = StdRandom.uniform(0, size);
        var item = queue[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandmizedQueueIterable();
    }

    private class RandmizedQueueIterable implements Iterator<Item>
    {
        @Override
        public boolean hasNext()
        {
            return size > 0;
        }

        @Override
        public Item next()
        {
            if (!hasNext())
            {
                throw new java.util.NoSuchElementException();
            }

            return dequeue();
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    // unit testing (required)
    public static void main(String[] args)
    {
        var queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println(queue.size());
        System.out.println(queue.size());
        System.out.println(queue.size());
        queue.enqueue(4);
        System.out.println(queue.dequeue());
    }

    private void reSize(int length)
    {
        var copy = queue.clone();
        Item[] newQueue = (Item[]) new Object[length];

        for (int i = 0; i < size; i++)
        {
            newQueue[i] = copy[i];
        }
        queue = newQueue;
    }
}
