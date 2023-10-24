
public class MemoryMonitor {
    long max = 0;
    long base = 0;

    public void update(long x) {
        base += x;
        if (base > max) {
            max = base;
        }

    }

    public long getMaxMemory() {
        return max;
    }

}
