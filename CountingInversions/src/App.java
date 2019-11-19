import java.util.HashSet;

public class App {
    public static void main(String[] args){



        HashSet<String> ss = new HashSet<>();
        ss.add(GetString());
        ss.add(GetString());
        for(String i : ss){
            System.out.println(i);
        }
    }

    private static String GetString()
    {
        return new StringBuilder("3\n8 4 7 \n1 5 6 \n2 3 0 \n").toString();
    }
}
