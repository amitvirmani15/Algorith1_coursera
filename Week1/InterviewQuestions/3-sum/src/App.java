import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.*;

import java.util.Arrays;

public class App
{
    public static void main(String[] args) {
        int test = 10;
        int[] integer = new In(args[0]).readAllInts();
        //int[] integer = new int[]{ -9, -8, -7, -6, -5 , -4, -3, -2, -1 , 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Arrays.sort(integer);
        Stopwatch timer = new Stopwatch();
        int count = 0;
        for (int i =0; i< test; i++) {
            count = count+counta(integer);

        }

        System.out.println("elapsed time = " + timer.elapsedTime()/test);
        System.out.println(count/test);

        timer = new Stopwatch();
        count =0;
        for (int i =0; i< test; i++) {
            count = count+findTriplets(integer, integer.length);
        }

        System.out.println("elapsed time = " + timer.elapsedTime()/test);
        System.out.println(count/test);
    }

    public static int countnlog(int[] integer){
        int count = 0;
        for (int i=0; i< integer.length-1; i++){

            for (int j = i+1; j < integer.length; j++){
                var z = BinarySearch(integer, -(integer[i]+integer[j]), j, integer.length-1);
                if (z <= j){
                    break;
                }
                if (integer[i]+integer[j]+integer[z] ==0) {

                    count++;
                }
            }

        }
        return count;
    }

    public static int counta(int[] integer){
        int count = 0;
        for (int i=0; i< integer.length-1; i++){
            var startIndex = FindValue(integer, i, integer.length-1, integer.length-1);
            for (int j = startIndex; integer[j] < -(integer[i]+integer[startIndex]); j++){
                var z = BinarySearch(integer, -(integer[i]+integer[j]), startIndex, integer.length-1);
                if (z <= j){
                    break;
                }
                if (integer[i]+integer[j]+integer[z] ==0) {

                    count++;
                }
            }

        }
        return count;
    }

    public static int FindValue(int[] arr, int startIndex, int nthIndex, int mid){
        if (startIndex+1<nthIndex && arr[startIndex]+arr[startIndex+1]+arr[nthIndex]>0) {
        return startIndex+1;
        }

        mid = (startIndex+mid)/2;
        if (mid< startIndex){
            return mid+1;
        }
        if (arr[startIndex]+ arr[mid]+arr[nthIndex]<0)
            return mid;
       return FindValue(arr, startIndex, nthIndex, mid-1);

    }
    public static int count(int[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
count++;
                    }
                }
            }
        }
        return count;
    }

    static int findTriplets(int arr[], int n)
    {
        int found = 0;

        for (int i = 0; i < n - 1; i++)
        {
            // Find all pairs with sum equals to
            // "-arr[i]"
            HashSet<Integer> s = new HashSet<Integer>();
            for (int j = i + 1; j < n; j++)
            {
                int x = -(arr[i] + arr[j]);
                if (s.contains(x))
                {
                    found++;
                }
                else
                {
                    s.add(arr[j]);
                }
            }
        }

        return found;
    }



    public static int BinarySearch(int[] arr, int value, int start, int last){
        var mid = (start+last)/2;
        if (start> last){
            return last;
        }
        if (arr[mid] == value){
            return mid;
        }

        if (arr[mid]<value){
            return BinarySearch(arr, value, mid+1, last);
        }
        else {
            return BinarySearch(arr, value, start, mid-1);
        }
    }
    }
