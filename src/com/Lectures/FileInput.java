package com.Lectures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles loading files. The constructor does not initialize any variables. The class has one static method
 * which is passed a file name as a parameter, and reads the data in that file
 */
public class FileInput {

    private FileInput() {
    }

    /**
     * This method is passed a filename of the data that is to be loaded. It creates a new ArrayList of type String
     * and writes each line in the file to a new element in that ArrayList.
     * @param filename is the file name
     * @return files, an ArrayList containing each line of the document as a String in each element
     * @throws FileNotFoundException
     */
    public static ArrayList<String> loadFile(String filename) throws FileNotFoundException{
        //create a new File and pass the file name to it
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        //create an arrayList to hold the data
        ArrayList<String> files = new ArrayList<>();

        //so long as there is another line in the file, add the next line to a new element in the array
        while (scanner.hasNextLine()) {
            files.add(scanner.nextLine());
        }

        scanner.close();
        return files;
    }

}
