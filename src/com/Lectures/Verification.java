package com.Lectures;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains ArrayLists for Clients and Admins. The class handles loading and saving users, and also verifies
 * if a user is in the class.
 */
public class Verification {
    private List<Client> clients;
    private List<Admin> admins;

    /**
     * constructor
     */
    public Verification(){
        clients = new ArrayList<>();
        admins = new ArrayList<>();
    }

    /**
     * Calls methods to load users into their respective ArrayLists
     * @throws FileNotFoundException
     */
    public void loadUsers() throws FileNotFoundException{
        loadClients();
        loadAdmins();
    }

    /**
     * Loads Clients from Clients.dat into the ArrayList admins by calling FileInput.loadFile() and passing
     * it the file name. FileInput.loadFile() returns a ArrayList of type string. Each element is split into a new
     * array by each ",", and each element of that new array is used to create a new Client object and add it to the
     * clients arrayList
     */
    private void loadClients() throws FileNotFoundException {
        //create a String arrayList and assign it the return from loadFile()\d+\.\d\d?
        String pattern = "^[A-Za-z0-9]+,\\S+,\\d+\\.\\d\\d?$";//
        List<String> inputFiles;
        inputFiles = FileInput.loadFile("Clients.dat");
        String[] currentFile;

        //break up each String element on the "," and add a new product
        for (int i = 0; i < inputFiles.size(); i++) {
            currentFile = inputFiles.get(i).split(",");

            try {
                if(inputFiles.get(i).matches(pattern)) {
                    clients.add(new Client(currentFile[0], currentFile[1], Double.parseDouble(currentFile[2])));
                }else{
                    throw new VendingException("Could not successfully add " + currentFile[0] + " to the machine");
                }
            }catch(VendingException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Loads Admins from Admins.dat into the ArrayList admins by calling FileInput.loadFile() and passing
     * it the file name. FileInput.loadFile() returns a ArrayList of type string. Each element is split into a new
     * array by each ",", and each element of that new array is used to create a new Admin object and add it to the
     * admins arrayList
     */
    private void loadAdmins() throws FileNotFoundException{
        //create a String arrayList and assign it the return from loadFile()
        String pattern = "^[A-Za-z0-9.]+,[A-Za-z0-9.]+$";
        List<String> inputFiles;
        inputFiles = FileInput.loadFile("Admins.dat");
        String[] currentFile;

        //break up each String element on the "," and add a new product

        for (int i = 0; i < inputFiles.size(); i++) {
            currentFile = inputFiles.get(i).split(",");

            try {
                if (inputFiles.get(i).matches(pattern)) {
                    admins.add(new Admin(currentFile[0], currentFile[1]));
                } else {
                    throw new VendingException("Could not successfully add " + currentFile[0] + " to the machine");
                }
            }catch(VendingException e){
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Saves all updated client objects by adding each object's .toString() to a new ArrayList outputFiles.
     * outputFiles is passed the static method FileOutput.saveFile(), which writes to Clients.dat by calling .
     * @throws IOException
     */
    public void saveClients() throws IOException {
        List<String> outputFiles = new ArrayList<>();
        for(Client client : clients){
            outputFiles.add(client.toString());
        }
        FileOutput.saveFile("Clients.dat",outputFiles);
    }

    /**
     * if the passed username and password match a Client object in clients, return that object. Else, return null
     */
    public Client validateClient(String username, String password){
        for(Client client : clients){
            if(username.equalsIgnoreCase(client.getUsername()) && password.equals(client.getPassword())){
                return client;
            }
        }
        return null;
    }

    /**
     * if the passed username and password match a Admin object in admins, return that object. Else, return null
     */
    public boolean validateAdmin(String username, String password){
        for(Admin admin : admins){
            if(username.equalsIgnoreCase(admin.getUsername()) && password.equals(admin.getPassword())){
                return true;
            }
        }
        return false;
    }



    public void printClients(){
        for(Client client:clients){
            System.out.println(client.toString());
        }
    }

    public void printAdmins(){
        for(Admin admin:admins){
            System.out.println(admin.toString());
        }
    }

    /**
     * Adds a new Client object to clients
     */
    public void addTo(String name,String password, Double balance){
        clients.add(new Client(name,password,balance));
    }
}
