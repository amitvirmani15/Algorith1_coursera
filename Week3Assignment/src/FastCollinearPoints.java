import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegment;

    private int count;
    public FastCollinearPoints(Point[] points)
    {

        if (points == null)
        {
            throw new IllegalArgumentException("Arhument not valid");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Argument Not valid");
            }
        }
        Arrays.sort(points);
        Point previousPoint = null;
        for (Point p : points)
        {
            if (previousPoint != null && previousPoint.equals(p))
            {
                throw new IllegalArgumentException("Argument Not valid");
            }
            previousPoint = p;
        }

        this.points = Arrays.copyOfRange(points, 0, points.length);
    }

    public int numberOfSegments()
    {
        return count;
    }
    public LineSegment[] segments()
    {
        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
        var length = points.length;

        for (int i = 0; i < length-1; i++) {
            var copy = points.clone();
            double[] p = new double[length];

            for (int j = 0; j < length; j++) {
                var slope = copy[i].slopeTo(copy[j]);
                p[j] = slope;
            }
            sort(p, copy, 0 , length-1);

            for (int k = 0; k < length; k++) {
                var ref = p[k];

                if ((k > 0 &&  Double.compare(p[k], p[k - 1]) == 0) ||(k > 0 && copy[0].compareTo(copy[k]) > 0))
                {
                    continue;
                }

                var indesx = Arrays.binarySearch(p, ref);
                if (indesx < 0) {
                    continue;
                }
                var frontIndex = indesx;
                var lastIndex = indesx;
                while (true)
                {

                    if (frontIndex - 1 > 0 && Double.compare(p[frontIndex - 1], ref) == 0)
                    {
                        frontIndex--;
                        continue;
                    }
                    if (lastIndex + 1 < length && Double.compare(p[lastIndex + 1], ref) == 0)
                    {
                        lastIndex++;
                        continue;
                    }
                    break;
                }


                var count = lastIndex - frontIndex + 1;
                if (count >= 3) {

                    lineSegments.add(new LineSegment(copy[0], copy[lastIndex]));
                    this.count++;
                }
            }

        }
        lineSegment = lineSegments.toArray(LineSegment[]::new);
        return lineSegment;
    }

    private void sort(Point[] p, double[] slopes){

        for (int i =1; i< p.length; i++){

            for (int j =i-1; j>=0; j--){

               if (slopes[j+1]<slopes[j]){
                   var temp = p[j+1];
                   p[j+1] = p[j];
                   p[j]=temp;
                   var temp1 = slopes[j+1];
                   slopes[j+1] = slopes[j];
                   slopes[j]=temp1;
                   }
            }

        }
    }

    private void merge(double arr[],Point[] p, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        double L[] = new double [n1];
        double R[] = new double [n2];

        Point PL[] = new Point[n1];
        Point PR[] = new Point[n2];

        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i)
        {
            L[i] = arr[l + i];
            PL[i] = p[l+i];
        }
        for (int j=0; j<n2; ++j)
        {
            R[j] = arr[m + 1+ j];
            PR[j] = p[m+1+j];
        }


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                p[k] = PL[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                p[k] = PR[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            p[k] = PL[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            p[k] = PR[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    private void sort(double arr[], Point[] p, int l, int r)
    {
        if (l<r) {

            // Find the middle point
            int m = (l+r)/2;

            // Sort first and second halves
            sort(arr, p, l, m);
            sort(arr, p , m+1, r);

            // Merge the sorted halves
            merge(arr, p, l, m, r);
        }
}
}
