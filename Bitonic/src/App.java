import java.util.Set;

public class App {
    public static void main(String[] args) {
        var arr = new int[]{1, 2, 3, 4, 9, 8, 7, 6, 5};

        var index = BinarySearch(arr, 9, 0, arr.length-2);
        System.out.println(index);
    }

    public static int FindElement(int[] arr, int value) {
        int start = 0, end = arr.length;
        while (start <= end) {
            var mid = (start + end) / 2;
            if (arr[mid] == value) {
                return mid;
            }
            if (arr[mid] > arr[mid + 1]) {
                start = mid + 1;
            } else if (arr[mid] < value) {
                end = end - 1;
            }
        }
        return -1;
    }

    public static int BinarySearch(int[] arr, int value, int start, int last) {
        var mid = (start + last) / 2;
        if (start > last) {
            return -1;
        }
        if (arr[mid] == value) {
            return mid;
        }

        if (arr[mid] < value && arr[mid] < arr[mid + 1]) {
            return BinarySearch(arr, value, mid + 1, last);
        }
        if (arr[mid] > value && arr[mid] < arr[mid + 1]) {
            return BinarySearch(arr, value, start, mid - 1);
        }
        if (arr[mid] > value && arr[mid] > arr[mid + 1]) {

            int leftarray = BinarySearch(arr, value, start, mid - 1);
            int rightarray = BinarySearch(arr, value, mid + 1, last);
            return (leftarray&rightarray) == -1? -1 : leftarray == -1 ? rightarray : leftarray;
        }
        if (arr[mid] < value && arr[mid] > arr[mid + 1]) {

            int leftarray = BinarySearch(arr, value, start, mid - 1);
            int rightarray = BinarySearch(arr, value, mid + 1, last);
            return (leftarray&rightarray) == -1? -1 : leftarray == -1 ? rightarray : leftarray;
        }
        return -1;
    }
}


