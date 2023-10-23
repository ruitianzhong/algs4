import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class MemoryMonitor {
    long max = 0;
    long base;
    MemoryMXBean mxb;

    MemoryMonitor() {
        mxb = ManagementFactory.getMemoryMXBean();
        base = total();
    }

    private long total() {
        long heap = mxb.getHeapMemoryUsage().getUsed(), nonHeap = mxb.getNonHeapMemoryUsage().getUsed();
        return heap + nonHeap;
    }

    public void update() {
        long n = total();
        if (n - base > max) {
            max = n - base;
        }
    }

    public long getMaxMemory() {
        return max;
    }

}
