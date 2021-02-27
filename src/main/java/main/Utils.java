package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void writeStringToFile(String path, String contents) throws IOException {
        File file = new File(path);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        writer.append(contents);

        writer.flush();
        writer.close();
    }

    public static String readFileToString(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        reader.close();

        return sb.toString();
    }

    public static boolean validSeed(long a) {
        long b = 18218081;
        long c = 1L << 48;
        long d = 7847617;
        long e = ((((d * ((24667315 * (a >>> 32) + b * (int) a + 67552711) >> 32) - b * ((-4824621 * (a >>> 32) + d * (int) a + d) >> 32)) - 11) *
                0xdfe05bcb1365L) % c);
        return ((((0x5deece66dL * e + 11) % c) >> 16) << 32) + (int) (((0xbb20b4600a69L * e + 0x40942de6baL) % c) >> 16) == a;
    }

    public static List<Long> getValidSeeds(long seed) {
        ArrayList<Long> seeds = new ArrayList<>();
        for (long upper16 = 0; upper16 < 1 << 16; upper16++) {
            long testSeed = seed | (upper16 << 48);
            if (validSeed(testSeed)) {
                seeds.add(testSeed);
            }
        }
        return seeds;
    }
}
