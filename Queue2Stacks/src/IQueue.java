public interface IQueue <T> {

    public void Enqueue(T item);

    public T Dequeue();

    public int count = 0;
}
