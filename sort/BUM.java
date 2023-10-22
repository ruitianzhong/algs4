
public class BUM implements Sort{
    int[] aux;

    public void sort(int[] arr) {
        aux = new int[arr.length];
        for (int sz = 1; sz < arr.length; sz = sz + sz) {
            for (int k = 0; k + sz < arr.length; k += sz + sz) {
                merge(arr, k, k + sz - 1, min(k + sz + sz - 1, arr.length - 1));
            }

        }
    }

    private int min(int x, int y) {
        return x < y ? x : y;
    }

    private void merge(int arr[], int lo, int mid, int hi) {

        int i = lo, j = mid + 1, n = 0;
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

    }
}
