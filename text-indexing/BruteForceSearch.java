public class BruteForceSearch implements TextSearch {

    @Override
    public int search(String pattern) {
        int i = 0, j = 0;
        while (j < content.length()) {
            for (i = 0; i < pattern.length() && i + j < content.length(); i++) {
                if (pattern.charAt(i) != content.charAt(j + i)) {
                    break;
                }
            }
            if (i == pattern.length()) {
                return j;
            }
            j++;
        }
        return -1;
    }

    public BruteForceSearch(String content) {
        this.content = content;
    }

    private String content;

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
