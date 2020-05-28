package application;

import java.io.*;

import java.net.URISyntaxException;
import java.util.Scanner;

public class ReadFile {

    static int level;
    Scanner scan = null;

    public ReadFile() {

        InputStream input = getClass().getResourceAsStream("/config/level.sav");

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

        String homeDir = System.getProperty("user.home") + "\\EmpireOfCards";
        File theDir = new File(homeDir);
        if (!theDir.exists())
        {
            try
            {
                theDir.mkdir();
            }
            catch (SecurityException e)
            {
                Scanner scan2 = null;

                InputStream input2 = ReadFile.class.getResourceAsStream("/config/level.txt");
                //File file = new File(ReadFile.class.getResource("/config/level.txt").toURI());

                scan2 = new Scanner(input2);
                level = scan2.nextInt();
                // System.out.println(level);
                scan2.close();
            }
        }
        String fullPath = homeDir+"\\level.sav";
        File save = new File(fullPath);
        if (!save.exists())
        {
            try
            {
                save.createNewFile();
                FileWriter writer = new FileWriter(save,false);
                writer.write("1");
                writer.close();

            }
            catch (SecurityException | IOException e)
            {
                Scanner scan3 = null;

                InputStream input3 = ReadFile.class.getResourceAsStream("/config/level.txt");
                //File file = new File(ReadFile.class.getResource("/config/level.txt").toURI());

                scan3 = new Scanner(input3);
                level = scan3.nextInt();
                // System.out.println(level);
                scan3.close();

            }
        }
        if (save.exists()) {
            System.out.println(fullPath);
            InputStream input = new FileInputStream(save);
            //File file = new File(ReadFile.class.getResource("/config/level.txt").toURI());
            System.out.println(input);
            scan = new Scanner(input);
            level = scan.nextInt();
            // System.out.println(level);
            scan.close();
        }

    }

    public static void setLevel(String level) throws IOException, URISyntaxException {
        String homeDir = System.getProperty("user.home") + "\\EmpireOfCards";
        String fullPath = homeDir+"\\level.sav";

        File file = new File(fullPath);
        if(!file.exists()) {
            file.createNewFile();
        }

        //  File writeFile = new File(ReadFile.class.getResource("/config/level.txt").toURI());
        FileWriter writer = new FileWriter(file,false);
        writer.write(level);
        writer.close();
    }

    public static void getLevelByOpen(InputStream input)
    {
        Scanner scan = null;
        scan = new Scanner(input);
        level = scan.nextInt();
        // System.out.println(level);
        scan.close();

    }
}
