package application;

import java.io.*;

import java.net.URISyntaxException;
import java.util.Scanner;

public class ReadFile {

    static int level;
    Scanner scan = null;

    public ReadFile() {

        InputStream input = getClass().getResourceAsStream("/config/level.txt");

        try {
            scan = new Scanner(input);
            level = scan.nextInt();
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
    }


    public static void getLevel() throws URISyntaxException, FileNotFoundException {

        Scanner scan = null;

        //InputStream input = ReadFile.class.getResourceAsStream("/config/level.txt");
        File file = new File(ReadFile.class.getResource("/config/level.txt").toURI());

        scan = new Scanner(file);
        level = scan.nextInt();
       // System.out.println(level);
        scan.close();
    }

    public static void setLevel(String level) throws IOException, URISyntaxException {

        File writeFile = new File(ReadFile.class.getResource("/config/level.txt").toURI());
        FileWriter writer = new FileWriter(writeFile,false);
        writer.write(level);
        writer.close();
    }
}
