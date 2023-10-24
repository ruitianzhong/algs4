
public class QD3P implements Sort {
    volatile MemoryMonitor m;

    public void sort(int[] arr) {
        m = new MemoryMonitor();
        quickSort3Way(arr, 0, arr.length - 1);
    }

    private void quickSort3Way(int[] arr, int lo, int hi) {
        if (lo < hi) {
            int lt = lo, i = lo + 1, gt = hi;
            int v = arr[lo];
            m.update(4 * 4);
            while (i <= gt) {
                if (arr[i] < v) {
                    int temp = arr[lt];
                    arr[lt] = arr[i];
                    arr[i] = temp;
                    lt++;
                    i++;
                } else if (arr[i] > v) {
                    int temp = arr[gt];
                    arr[gt] = arr[i];
                    arr[i] = temp;
                    gt--;

                } else {
                    i++;
                }
            }
            quickSort3Way(arr, lo, lt - 1);
            quickSort3Way(arr, gt + 1, hi);
            m.update(-4 * 4);
        }

    }

    public long memory() {
        return m.getMaxMemory();
    }
}
