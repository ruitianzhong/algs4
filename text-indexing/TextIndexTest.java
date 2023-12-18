import java.io.IOException;
import java.util.List;

public class TextIndexTest {
    public static void main(String[] args) throws IOException {
        String content1 = "it was the best of times it was the worst of times it was the age of wisdom it was the age of foolishness it was the epoch\n"
                +
                "of belief it was the epoch of incredulity it was the season of light it was the season of darkness it was the spring of\n"
                +
                "hope it was the winter of despair\"";
        TextSearch indexedSearch = new IndexedSearch(content1), bruteForce = new BruteForceSearch(content1),
                kmp = new KMPSearch(content1);
        TextSearch[] ts = { indexedSearch, bruteForce, kmp };

        for (int i = 0; i < ts.length; i++) {
            System.out.println("wisdom " + ts[i].search("wisdom"));
            System.out.println("season " + ts[i].search("season"));
            System.out.println("age of foolishness " + ts[i].search("age of foolishness"));
            System.out.println("age of fools " + ts[i].search("age of fools"));
            System.out.println();
        }

        String content2 = ReadFile.readCorpus("tale.txt");
        var queries = ReadFile.readQuery();

        kmp = new KMPSearch(content2);
        testHelper("kmp", kmp, queries);
        kmp = null;
        bruteForce = new BruteForceSearch(content2);
        testHelper("Brute force", bruteForce, queries);

        bruteForce = null;
        indexedSearch = new IndexedSearch(content2);
        testHelper("Indexed search", indexedSearch, queries);

    }

    private static void testHelper(String testName, TextSearch ts, List<Query> queries) {
        long start = System.currentTimeMillis(), end;
        for (Query query : queries) {
            query.positon = ts.search(query.pattern);

        }
        end = System.currentTimeMillis();
        System.out.println(testName + " total time:" + (end - start) + "ms");
    }

}