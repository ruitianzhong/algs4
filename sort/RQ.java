import java.util.Random;

public class RQ implements Sort {
    Random r;
    volatile MemoryMonitor m;

    public void sort(int[] arr) {
        r = new Random(System.currentTimeMillis());
        m = new MemoryMonitor();
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int lo, int hi) {
        if (lo < hi) {
            int rn = r.nextInt(hi - lo + 1);
            int temp = arr[lo];
            arr[lo] = arr[lo + rn];
            arr[lo + rn] = temp;
            int pivot = arr[lo];
            int i = lo, j = hi;
            m.update();
            while (i < j) {
                while (arr[j] > pivot && i < j) {
                    j--;
                }
                if (i < j) {
                    arr[i] = arr[j];
                    i++;
                }
                while (arr[i] < pivot && i < j) {
                    i++;
                }
                if (i < j) {
                    arr[j] = arr[i];
                    j--;
                }
            }
            arr[i] = pivot;
            quickSort(arr, lo, i - 1);
            quickSort(arr, i + 1, hi);
            m.update();
        }

    }

    public long memory() {
        return m.getMaxMemory();
    }
}
