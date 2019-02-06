package ModelPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Physics.Vect;

import ModelPackage.*;

public class LoadFile {

    private String filename;
    private Scanner wholeLine, scan;
    private File file;

    public LoadFile(){
        //Current file name, will want user prompt most likely in future
        filename = "Documents/example_file.txt";

        //Check file exists
        file = new File(filename);
        try {
            wholeLine = new Scanner(file);
            wholeLine.useDelimiter("\n");
        }catch(FileNotFoundException e){
            System.out.println("Error. File " + filename + " not found");
        }
    }

    public Model run(){
        //Only proceed if file is there
        if(file.exists()) {

            Model model = new Model(); //Create new model

            while (wholeLine.hasNext()) {
                //Put text from line into a scanner
                String info = wholeLine.next();
                scan = new Scanner(info);

                //Massive if else system, will go through all possibilities and process accordingly
                //The types could be parsed before this loop and used to compare instead if .startsWith.
                if (info.startsWith("Square")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in gizmo setup, TODO: add this
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GSquare(xPos,yPos));
                } else if (info.startsWith("Circle")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in gizmo setup, TODO: add this
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GCircle(xPos,yPos));
                } else if (info.startsWith("Triangle")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in gizmo setup, TODO: add this
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GTriangle(xPos,yPos));
                } else if (info.startsWith("Rotate")) {
                    String type = scan.next();
                    String toRotate = scan.next();

                    System.out.println("Rotating object " + toRotate);
                    //TODO: Implement rotation in model.
                    //model.addRotation(toRotate); //or similar
                } else if (info.startsWith("LeftFlipper")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in flipper setup, TODO: add this
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GFlipper(xPos,yPos,true));
                } else if (info.startsWith("RightFlipper")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in flipper setup, TODO: add this
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GFlipper(xPos,yPos,false));
                } else if (info.startsWith("KeyConnect")) {
                    String type = scan.next();
                    String toPress = scan.next();
                    String keyID = scan.next();
                    String direction = scan.next();
                    String toMove = scan.next();


                    System.out.println("When " + toPress + " ID " + keyID + " is pressed " + toMove + " is triggered");
                    //TODO: Implement key connection in model.
                } else if (info.startsWith("Connect")) {
                    String type = scan.next();
                    String obj1 = scan.next();
                    String obj2 = scan.next();

                    System.out.println("Connecting " + obj1 + " to " + obj2);
                    //TODO: Implement connection in model
                    //model.addConnection(obj1,obj2); //or similar
                } else if (info.startsWith("Ball")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in ball setup, TODO: add this
                    double xPos = scan.nextDouble();
                    double yPos = scan.nextDouble();
                    double xVel = Double.parseDouble(scan.next());
                    double yVel = Double.parseDouble(scan.next());


//                    System.out.println("Ball called " + name + " at (" + xPos + ", " + yPos + ") going at " + xVel + "L/sec on the x axis and " + yVel + "L/sec on the y axis");
                    model.addBall(new Ball(xPos,yPos,xVel, yVel));
                } else if (info.startsWith("Absorber")) {
                    String type = scan.next();
                    String name = scan.next(); //currently not included in gizmo setup, TODO: add this
                    int x1 = scan.nextInt();
                    int y1 = scan.nextInt();
                    int x2 = scan.nextInt();
                    int y2 = scan.nextInt();

                    Vect start = new Vect(x1,y1);
                    Vect end = new Vect(x2,y2);

//                    System.out.println("Absorber called " + name + " goes from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
                    model.addGizmo(new GAbsorber(start,end));
                } else if (info.equals("")) {
                    System.out.print("\n"); //debug just to make look neater
                } else {
                    System.out.println("\n\n\n\n\nStill to add " + scan.next()); //debug, to be removed
                }
            }
            //close scanners
            scan.close();
            wholeLine.close();

            return model;
        }else{
            System.out.println("File doesn't exist");
        }
        return new Model(); //return empty model
    }

}
