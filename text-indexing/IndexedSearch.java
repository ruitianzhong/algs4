import java.util.Arrays;
import java.util.Comparator;

public class IndexedSearch implements TextSearch {

    @Override
    public int search(String pattern) {
        int l = 0, r = suffix_array.length - 1;
        boolean found = false;
        int mid = 0;
        int length = pattern.length();
        while (l <= r) {
            mid = l + (r - l) / 2;
            String s = suffix_array[mid].suffix;
            int ret = s.substring(0, length > s.length() ? s.length() : length).compareTo(pattern);
            if (ret == 0) {
                found = true;
                break;
            } else if (ret > 0) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }

        }
        if (found) {
            return suffix_array[mid].index;
        } else {
            return -1;
        }
    }

    public IndexedSearch(String content) {
        this.content = content;
        buildIndex(content);

    }

    class Suffix {
        public String suffix;
        public int index;

        public Suffix(String suffix, int index) {
            this.index = index;
            this.suffix = suffix;
        }
    };

    private Suffix[] suffix_array;

    private void buildIndex(String content) {
        suffix_array = new Suffix[content.length()];
        for (int i = 0; i < content.length(); i++) {
            var s = new Suffix(content.substring(i), i);
            suffix_array[i] = s;
        }
        Arrays.sort(suffix_array, new Comparator<Suffix>() {

            @Override
            public int compare(IndexedSearch.Suffix o1, IndexedSearch.Suffix o2) {
                return o1.suffix.compareTo(o2.suffix);
            }

        });

    }

    private String content;

    public static void main(String[] args) {

        String content1 = "cabcabcdxyzgh";
        var bfs = new IndexedSearch(content1);
        System.out.println(bfs.search("abc") == 1);
        System.out.println(bfs.search("xyz") == 8);
        System.out.println(bfs.search("pattern1") == -1);
        System.out.println(bfs.search("z") == 10);
        IndexedSearch is = new IndexedSearch("abcdefg");
        for (Suffix s : is.suffix_array) {
            System.out.println(s.suffix + " " + s.index);
        }
        is = new IndexedSearch("abcabda");
        for (Suffix s : is.suffix_array) {
            System.out.println(s.suffix + " " + s.index);
        }

    }

}
