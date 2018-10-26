package main.java.decks.FileReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileReader {
    /**
     * return string representation of the file.
     * @param path path of the file
     * @return ArrayList of strings
     * @throws IOException
     */
    public static ArrayList<String> getArrayListFromFile(String path) throws IOException {
        return new ArrayList<String>(Files.readAllLines(Paths.get(path), StandardCharsets.ISO_8859_1));
    }
}
