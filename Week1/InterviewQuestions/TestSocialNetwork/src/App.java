import edu.princeton.cs.algs4.In;

public class App {
    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int i = 0;
        String[] friends = new In(args[0]).readAllLines();
        int[] person1 = new int[friends.length];
        int[] person2 = new int[friends.length];
        for (String var : friends) {
            String[] peronsDetails = var.split(" ");
            person1[i] = Integer.parseInt(peronsDetails[0]);
            person2[i] = Integer.parseInt(peronsDetails[1]);
            i++;
        }

        int ret = 0;
        WeightedBag bag = new WeightedBag(arr);
        for (int j = 0; j < person1.length; j++) {
            if (bag.Union(person1[j], person2[j]) > 0) {
                System.out.println(person1[j] |person2[j]);
                break;
            }
        }
    }

    }