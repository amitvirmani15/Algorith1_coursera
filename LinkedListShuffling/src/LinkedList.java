import java.util.Random;
public class LinkedList<T> {

    private Node<T> root;
    private int size;

    private class Node<T> {
        T Item;
        Node<T> Next;

        private Node(T Item) {
            this.Item = Item;
        }
    }

    public void Add(T item) {
        if (root == null) {
            root = new Node<>(item);
        } else {
            var node = root;
            while (node.Next != null) {
                node = node.Next;
            }

            node.Next = new Node<>(item);

        }
        size++;
    }

    public void Shuffle() {
        var n = (int) (Math.log(size) / Math.log(2));
        Node[] arr = new Node[n];
        int i = 0;
        arr[i++] = root;
        var node = root;
        while (true) {
            if (i == arr.length) {
                break;
            }
            node = node.Next;
            arr[i] = node;
            i++;
        }
        i = 0;

        int rand_int1 = new Random().nextInt(arr.length);

        while (true) {
            node= node.Next;
            if (node == null){
                break;
            }
            arr[i] = node;
            rand_int1 = new Random().nextInt(arr.length);
            var temp = arr[i].Item;
            arr[i].Item = arr[rand_int1].Item;
            arr[rand_int1].Item = temp;

            i++;
            i = i/arr.length;
        }


    }
}

