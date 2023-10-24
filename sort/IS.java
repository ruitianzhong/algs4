
public class IS implements Sort {
    volatile MemoryMonitor m;

    public void sort(int[] arr) {
        m = new MemoryMonitor();
        m.update(3 * 4);
        for (int i = 0; i < arr.length - 1; i++) {
            int j = i + 1;
            while (j > 0 && arr[j] < arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
        m.update(-3 * 4);

    }

    public long memory() {
        return m.getMaxMemory();
    }
}
