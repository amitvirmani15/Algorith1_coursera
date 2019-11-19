import java.util.ArrayList;

public class App {

    public static void main(String[] args){
        ArrayList<Integer> arr=new ArrayList<Integer>();
        Object[] obj = new Integer[]{};


    }


}

class Array1<T>{
    private T[] Create(){
        return (T[])new Object[5];
    }

}
