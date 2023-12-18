import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class ReadFile {

    public static String readCorpus(String filename) throws IOException {
        File file = new File(filename);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        reader.close();
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String content = stringBuilder.toString();
        return content;
    }

    public static List<Query> readQuery() throws IOException {
        File f = new File("query.txt");
        Scanner s = new Scanner(f);
        var l = new ArrayList<Query>();
        while (s.hasNextLine()) {
            var next = s.nextLine();
            var q = new Query(next);
            l.add(q);
        }
        s.close();
        return l;
    }

    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < length; i++) {
            int n = r.nextInt(str.length());
            stringBuilder.append(str.charAt(n));
        }
        return stringBuilder.toString();
    }

    public static List<String> getAllLine() throws IOException {
        var l = new ArrayList<String>();
        var filename = "tale.txt";
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.length() > 0) {
                l.add(line);
            }
        }

        reader.close();
        return l;

    }

    public static void generateQuery() throws IOException {
        File file = new File("query.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getName(), false);
        var lines = getAllLine();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 6000; i++) {
            int lineNum = r.nextInt(lines.size());
            String line = lines.get(lineNum);
            String sub = line.substring(r.nextInt(line.length()));
            fileWriter.write(sub + "\n");
        }
        for (int i = 0; i < 3000; i++) {
            int lineNum = r.nextInt(lines.size());
            String line = lines.get(lineNum);
            String sub = line.substring(r.nextInt(line.length()));
            if (sub.length() > 10) {
                sub = sub.substring(0, 10);
            }
            fileWriter.write(sub + "\n");
        }
        for (int i = 0; i < 3000; i++) {
            int length = r.nextInt(10) + 1;
            fileWriter.write(generateRandomString(length) + "\n");
        }
        for (int i = 0; i < 1000; i++) {
            fileWriter.write(UUID.randomUUID() + "\n");
        }

        fileWriter.close();

    }

    public static void main(String[] args) throws IOException {

        generateQuery();
    }
}
