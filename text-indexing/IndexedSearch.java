import java.util.Arrays;
import java.util.Comparator;

public class IndexedSearch implements TextSearch {

    private int arr_length = 0;

    @Override
    public int search(String pattern) {
        int l = 0, r = arr_length - 1;
        boolean found = false;
        int mid = 0;
        int length = pattern.length();
        while (l <= r) {
            mid = l + (r - l) / 2;
            String s = this.content.substring(suffix_array[mid].index);
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
            for (int i = mid + 1; i < arr_length; i++) {
                String s = this.content.substring(suffix_array[i].index);
                int ret = s.substring(0, length > s.length() ? s.length() : length).compareTo(pattern);
                if (ret == 0) {
                    mid = i;
                    // find the first occurence
                } else {
                    break;
                }
            }
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
        public int index;

        public Suffix(int index) {
            this.index = index;
        }
    };

    private Suffix[] suffix_array;

    private void buildIndex(String content) {
        suffix_array = new Suffix[content.length()];
        int count = 0;
        for (int i = 0; i < content.length(); i++) {
            if (i == 0 || content.charAt(i - 1) == ' ' && content.charAt(i) != ' ') {
                var s = new Suffix(i);
                suffix_array[count++] = s;
            }

        }
        System.out.println("end");
        arr_length = count;
        Arrays.sort(suffix_array, 0, count, new Comparator<Suffix>() {

            @Override
            public int compare(IndexedSearch.Suffix o1, IndexedSearch.Suffix o2) {
                return content.substring(o1.index).compareTo(content.substring(o2.index));
            }

        });

    }

    private String content;

    public static void main(String[] args) {

        String content1 = "cabcabcdxyzgh abc z";
        var bfs = new IndexedSearch(content1);
        System.out.println(bfs.search("cabc"));
        System.out.println(bfs.search("abc"));
        System.out.println(bfs.search("pattern1") == -1);
        System.out.println(bfs.search("z"));
        String content2 = "abcdefg";
        IndexedSearch is = new IndexedSearch(content2);
        for (Suffix s : is.suffix_array) {
            if (s == null)
                break;
            System.out.println(content2.substring(s.index) + " " + s.index);
        }
        var content3 = "abcabda";
        is = new IndexedSearch(content3);
        for (Suffix s : is.suffix_array) {
            if (s == null)
                break;
            System.out.println(content3.substring(s.index) + " " + s.index);
        }

    }

}
