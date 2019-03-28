package com.Lectures;

import java.io.*;
import java.util.List;

/**
 * This class handles saving files. The constructor does not initialize any variables. The class has one static method
 * which is passed a file name, and an ArrayList of type String as parameters, and saves the data to a file
 */

public class FileOutput {

    private FileOutput(){
    }

    /**
     * This method is passed a filename and an ArrayList of the data that is to be saved.
     * @param filename is the name of the file
     * @param files is an ArrayList of type String containing the data to be saved
     * @throws IOException
     */
    public static void saveFile(String filename, List<String> files) throws IOException {
        //create a new object of type File and pass the file name to it
        File file = new File(filename);
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter= new PrintWriter(fileWriter);

        //for each element in the arrayList files
        for(String eachString: files){
            //print that string to the file and move to a new line
            printWriter.println(eachString);
        }

        //close our file and print writers
        fileWriter.close();
        printWriter.close();
    }
}
