import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GetConfig {

    /**
     * Class to read in config information from config file
     * <p>
     * Currently in its own class, could be added to another class
     * at a later date. Separated just for ease.
     * <p>
     * -Mark Falconer
     */

    private final String filename = "Documents/config.txt";
    private double squareCo = -1; //All coefficents
    private double circleCo = -1;
    private double triangleCo = -1;
    private double flipperCo = -1;
    private double lineCo = -1;
    private File file;

    public GetConfig() {
        file = new File(filename);

        if (!file.exists()) {
            System.out.println("Error. Config file does not exist.");
            System.out.println("Creating new config file with standard values.");
            createConfig();
        }

        //since lineCo is the last to be set, once this has been set, all info has been set
        while(lineCo == -1) {
            //Get information from config
            try {
                readInValues();
            } catch (FileFormatException e) {
                System.out.println("Error in format of config file " + e.getError());
                System.out.println("Recreating config file");
                file.delete();
                createConfig();
            } catch (FileNotFoundException e) {
                //should be impossible as if file doesn't exist it gets created but it throws an error if i dont put this in
                System.out.println("Error. File not found.");
            }
        }
        System.out.print("Coefficients read in: ");
        System.out.println("S: " + squareCo + " C: " + circleCo + " T: " + triangleCo + " F: " + flipperCo + " L: " + lineCo);

    }

    private void readInValues() throws FileNotFoundException, FileFormatException {
        Scanner scan = new Scanner(file);

        while (scan.hasNext()) {
            String line = scan.nextLine();
            //'#' is the prefix for a comment line. Should ignore these lines
            if (!line.startsWith("#") && !line.equals("")) {
                Scanner data = new Scanner(line);
                String shape = data.next();
                if(!data.hasNext()){
                    //Only one readable element
                    throw new FileFormatException("Not enough elements");
                }
                double coefficient = data.nextDouble();

                if(data.hasNext()){
                    //Should only be 2 elements on readable lines
                    throw new FileFormatException("Too many elements");
                }
                switch (shape) {
                    case "Square":
                        squareCo = coefficient;
                        break;
                    case "Circle":
                        circleCo = coefficient;
                        break;
                    case "Triangle":
                        triangleCo = coefficient;
                        break;
                    case "Flipper":
                        flipperCo = coefficient;
                        break;
                    case "Line":
                        lineCo = coefficient;
                        break;
                    default:
                        data.close();
                        scan.close();
                        throw new FileFormatException("Invalid shape (" + shape + ") provided.");
                }
                data.close();
            }

        }
        scan.close();
    }


    private void createConfig() {
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
            fileWriter.write("Flipper 0.95\n\n");
            fileWriter.write("Line 1.0");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("IO Exception for file " + filename);
        }


    }
}
