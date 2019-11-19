import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        int[] array1 = new int[]{1, 3, 5, 7, 9, 11, 13};
        int[] array2 = new int[]{2, 4, 6, 8, 10, 12, 14};
        var n = array1.length;
        int[] com = new int[n];

        Integer i = 0, j =0, z =0;
        while (z<n){
        if (array1[i]<=array2[j]){
            com[z] = array1[i];
            z++;
            i++;
            continue;
        }
        com[z] = array2[j];
        z++;
        j++;
        }
            for (int index =0; index<i;index++) {
                array1[index] = array2[j + index];
            }

            array2 = com.clone();


        var temp =i;
        i =0; j =temp; z =0;
        while (z<n){
            if (j==n){
                com[z] = array1[i];
                z++;
                i++;
                continue;
            }
            if (i==temp){
                com[z] = array1[j];
                z++;
                j++;
                continue;
            }
            if (array1[i]<=array1[j]){
                com[z] = array1[i];
                z++;
                i++;
                continue;
            }
            com[z] = array1[j];
            z++;
            j++;
        }

        for (var t : array2){
            System.out.println(t);
        }
        for (var t : com){
            System.out.println(t);
        }
    }

}