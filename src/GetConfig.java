import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GetConfig {

    /**
     *
     *  Class to read in config information from config file
     *
     *  Currently in its own class, could be added to another class
     *  at a later date. Separated just for ease.
     *
     *  -Mark Falconer
     */

    private final String filename = "Documents/config.txt";
    private double squareCo, circleCo, triangleCo, flipperCo, lineCo; //All coefficents
    private File file;
    public GetConfig(){
        file = new File(filename);

        if(!file.exists()) {
            System.out.println("Error. Config file does not exist.");
            System.out.println("Creating new config file with standard values.");
            createConfig();
        }
        try {
            readInValues();
        }catch(FileNotFoundException e){
            //should be impossible as if file doesn't exist it gets created but it throws an error if i dont put this in
            System.out.println("Error. File not found.");
        }
    }

    private void readInValues() throws FileNotFoundException{
        Scanner scan = new Scanner(file);

        while(scan.hasNext()){
            String line = scan.nextLine();
            //'#' is the prefix for a comment line. Should ignore these lines
            if(!line.startsWith("#")){

            }
        }
    }


    private void createConfig(){
        assert !file.exists() : "Error. File exists so will not have to create a new file.";

        //Create generic file with basic settings
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("# Gizmoball config file for Coefficients\n");
            fileWriter.write("# Group W12 - Aleksi Daskalov, Cameron Taylor, John McMenemy, Kyle Lawson, Mark Falconer\n\n");
            fileWriter.write("Square 1.0\n\n");
            fileWriter.write("Circle 1.0\n\n");
            fileWriter.write("Triangle 1.0\n\n");
            fileWriter.write("Flipper 1.0\n\n");
            fileWriter.write("Line 1.0");
            fileWriter.close();
        }catch(IOException e){
            System.out.println("IO Exception for file " + filename);
        }


    }
}
