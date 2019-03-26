package com.Lectures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInput {
    private Scanner scanner;
    private File file;

    public FileInput(String filename) throws FileNotFoundException {
        this.file = new File(filename);
        scanner = new Scanner(file);
    }

    public ArrayList<String> loadFile(){
        ArrayList<String> files = new ArrayList<>();

        while(scanner.hasNextLine()){
            files.add(scanner.nextLine());
        }

        scanner.close();
        return files;
    }

//    public ArrayList<String> loadFile(VendingMachine vendingMachine, String filename) throws FileNotFoundException {
//        ArrayList<String> files = new ArrayList<>();
//        String[] currentFile;
//
//        file = new File(filename);
//        scanner = new Scanner(file);
//
//        while(scanner.hasNextLine()){
//            currentFile = scanner.nextLine().split(",");
//            vendingMachine.addProduct(currentFile[0], Double.parseDouble(currentFile[1]), currentFile[2], Integer.parseInt(currentFile[3]));
//        }
//
//        scanner.close();
//        return files;
//    }


}
