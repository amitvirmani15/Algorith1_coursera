import java.util.Stack;

public class Queue<T> implements IQueue<T> {
    private Stack<T> stack1 ;
    private Stack<T> stack2;
    public Queue(){
        stack1 = new Stack<T>();
        stack2 = new Stack<T>();
    }
    @Override
    public void Enqueue(T item) {
        stack1.push(item);
    }

    @Override
    public T Dequeue() {
        if (stack2.empty()){
            while (!stack1.empty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
