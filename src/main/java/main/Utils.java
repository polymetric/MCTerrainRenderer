package main;

import java.io.*;

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
}
