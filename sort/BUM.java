
public class BUM implements Sort {
    int[] aux;
    volatile MemoryMonitor m;

    public void sort(int[] arr) {
        m = new MemoryMonitor();
        aux = new int[arr.length];
        m.update();
        for (int sz = 1; sz < arr.length; sz = sz + sz) {
            for (int k = 0; k + sz < arr.length; k += sz + sz) {
                m.update();
                merge(arr, k, k + sz - 1, min(k + sz + sz - 1, arr.length - 1));
            }

        }
    }

    private int min(int x, int y) {
        return x < y ? x : y;
    }

    private void merge(int arr[], int lo, int mid, int hi) {

        int i = lo, j = mid + 1, n = 0;
        m.update();
        while (i <= mid && j <= hi) {
            if (arr[i] <= arr[j]) {
                aux[n++] = arr[i];
                i++;
            } else {
                aux[n++] = arr[j];
                j++;
            }
        }
        m.update();
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

    }

    public long memory() {
        return m.getMaxMemory();
    }
}
