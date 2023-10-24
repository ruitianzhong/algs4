
public class TDM implements Sort {
    int[] aux;
    volatile MemoryMonitor m;

    public void sort(int arr[]) {
        m = new MemoryMonitor();
        aux = new int[arr.length];
        m.update(4 * arr.length);
        mergeSort(arr, 0, arr.length - 1);
        m.update(-4 * arr.length);
    }

    private void mergeSort(int arr[], int lo, int hi) {
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            m.update(4);
            mergeSort(arr, lo, mid);
            mergeSort(arr, mid + 1, hi);
            merge(arr, lo, mid, hi);
            m.update(-4);
        }
    }

    private void merge(int arr[], int lo, int mid, int hi) {

        int i = lo, j = mid + 1, n = 0;
        m.update(4 * 3);
        while (i <= mid && j <= hi) {
            if (arr[i] <= arr[j]) {
                aux[n++] = arr[i];
                i++;
            } else {
                aux[n++] = arr[j];
                j++;
            }
        }
        while (i <= mid) {
            aux[n++] = arr[i];
            i++;
        }
        while (j <= hi) {
            aux[n++] = arr[j];
            j++;
        }
        for (i = 0; i < n; i++) {
            arr[lo + i] = aux[i];
        }
        m.update(-4 * 3);

    }

    public long memory() {
        return m.getMaxMemory();
    }
}
