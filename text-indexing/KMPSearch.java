public class KMPSearch implements TextSearch {

    @Override
    public int search(String pattern) {
        int i = 0, j = 0;
        int index = -1;
        int[] next = computeNext(pattern);
        while (i < pattern.length() && j < content.length()) {
            if (i == -1 || pattern.charAt(i) == content.charAt(j)) {
                i++;
                j++;
            } else {
                i = next[i];
            }
        }
        if (i == pattern.length()) {
            index = j - pattern.length();
        }
        return index;
    }

    public KMPSearch(String content) {
        this.content = content;

    }

    private String content;

    private int[] computeNext(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0, k = -1;
        next[0] = -1;
        while (j < pattern.length() - 1) {
            if (k == -1 || pattern.charAt(k) == pattern.charAt(j)) {
                j++;
                k++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    // unit testing
    public static void main(String[] args) {

        String content1 = "cabcabcdxyzgh";
        BruteForceSearch bfs = new BruteForceSearch(content1);
        System.out.println(bfs.search("abc") == 1);
        System.out.println(bfs.search("xyz") == 8);
        System.out.println(bfs.search("pattern1") == -1);
        System.out.println(bfs.search("z") == 10);

    }
}
