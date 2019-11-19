import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
    private Item[] deque;

    private int frontIndex, lastIndex;

    private Integer count;

    // construct an empty deque
    public Deque()
    {
        count = 0;
        frontIndex = 0;
        lastIndex = 0;
        deque = (Item[]) new Object[2];
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return count == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
        if (deque.length-1 == size())
        {
            reSize(deque.length * 2);
        }
        if (size()!=0)
        {
            if (frontIndex == 0)
            {
                frontIndex = deque.length - 1;
            } else
                {
                frontIndex = frontIndex - 1;
            }
        }
        deque[frontIndex] = item;
        count = count+1;

    }


    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
        if (deque.length-1 == size())
        {
            reSize(deque.length * 2);
        }
        if (size()!=0)
        {
            if (lastIndex == deque.length - 1)
            {
                lastIndex = 0;
            } else
            {
                lastIndex = lastIndex + 1;
            }
        }
        deque[lastIndex] = item;
        count = count+1;

    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (size() == 0)
        {
            throw new java.util.NoSuchElementException();
        }
        if (size() <= deque.length/4)
        {
            reSize(deque.length / 2);
        }
        var item = deque[frontIndex];
        deque[frontIndex] = null;
        count = count-1;
        if (size()!=0)
        {
            if (frontIndex == deque.length - 1)
            {
                frontIndex = 0;
            } else
            {
                frontIndex++;
            }
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (size() == 0)
        {
            throw new java.util.NoSuchElementException();
        }
        if (size() <= deque.length/3)
        {
            reSize(deque.length / 2);
        }
        var item = deque[lastIndex];
        deque[lastIndex] = null;
        count = count-1;
        if (size()!=0)
        {
            if (lastIndex == 0)
            {
                lastIndex = deque.length - 1;
            } else
            {
                lastIndex--;
            }
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterable();
    }

    private class DequeIterable implements Iterator<Item>
    {
        private int i = 0;

        private Item[] array;

    @Override
    public boolean hasNext()
    {
        return i<size();
    }

    @Override
    public Item next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }

        array = copy();
        return array[i++];
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
        var deque = new Deque<Integer>();
        deque.addFirst(3);
        System.out.println(deque.removeLast());
        deque.addFirst(5);
        System.out.println(deque.removeLast());

        deque.addLast(3);
        deque.addLast(5);
        System.out.println(deque.removeLast());

        System.out.println(deque.removeLast());

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
    }

    private void reSize(int length)
    {
        var copy = deque.clone();
        ArrayList<Item> newDeque = new ArrayList<Item>();
        var index = frontIndex;
        var i = 0;
        while (true) {
            newDeque[i++] = copy[index];
            if (index == lastIndex)
            {
                break;
            }
            index = index+1;
            if (index == copy.length)
            {
                index = 0;
            }
        }
        frontIndex = 0;
        lastIndex = i-1;
        copy = null;
        deque = newDeque;
    }

    private Item[] copy()
    {
        var copy = deque.clone();
        Item[] newDeque = (Item[]) new Object[copy.length];
        var index = frontIndex;
        var i = 0;
        while (true) {
            newDeque[i++] = copy[index];
            if (index == lastIndex)
            {
                break;
            }
            index = index+1;
            if (index == copy.length)
            {
                index = 0;
            }
        }
        return newDeque;
    }
}