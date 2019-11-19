public class App {
    public static void main(String[] args){
        var arr = new int[]{1,1,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,3,3,3,4};
        var arr1 = new int[]{1,2,3,4,6,7,8,9};
        var response = rank(0, arr1.length, arr1, 9);
        System.out.println(response);
var ff = new Find10Items(arr);
ff.ReturnItems();

    }

    private static int rank(int lo, int hi, int[] items, int element) {
        int mid = 0;
        int rankCount = hi;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (element < items[mid]) {
                hi = mid - 1;
            } else if (element > items[mid]) {
                lo = mid + 1;
            } else {
                return rankCount + 1 - mid;
            }

        }
        int r = hi;
        if (r > rankCount) {
            r = 1;
        } else if (r < 0) {
            r = rankCount + 1 + 1;
        } else {
            r = rankCount + 1 - r;
        }
        return r;
    }
}
