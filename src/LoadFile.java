import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import ModelPackage.*;
import Physics.Vect;

public class LoadFile {

    private String filename;
    private Scanner wholeLine, scan;
    private File file;

    public LoadFile(){
        filename = "example file.txt";

        file = new File(filename);
        try {
            wholeLine = new Scanner(file);
            wholeLine.useDelimiter("\n");
        }catch(FileNotFoundException e){
            System.out.println("Error. File " + filename + " not found");
        }
    }

    public Model run(){
        if(file.exists()) {
            System.out.println("Exists.");

            Model model = new Model();
            while (wholeLine.hasNext()) {
                String info = wholeLine.next();
                scan = new Scanner(info);
                if (info.startsWith("Square")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GSquare(xPos,yPos));
                } else if (info.startsWith("Circle")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GCircle(xPos,yPos));
                } else if (info.startsWith("Triangle")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GTriangle(xPos,yPos));
                } else if (info.startsWith("Rotate")) {
                    String type = scan.next();
                    String toRotate = scan.next();

                    System.out.println("Rotating object " + toRotate);
                    //TODO: Implement rotation in model.

                } else if (info.startsWith("LeftFlipper")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GFlipper(xPos,yPos,true));
                } else if (info.startsWith("RightFlipper")) {
                    String type = scan.next();
                    String name = scan.next();
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
                } else if (info.startsWith("Ball")) {
                    String type = scan.next();
                    String name = scan.next();
                    double xPos = scan.nextDouble();
                    double yPos = scan.nextDouble();
                    String xVel = scan.next();
                    String yVel = scan.next();


//                    System.out.println("Ball called " + name + " at (" + xPos + ", " + yPos + ") going at " + xVel + "L/sec on the x axis and " + yVel + "L/sec on the y axis");
                    model.addBall(new Ball(xPos,yPos,1)); //TODO: Radius is not provided and velocity is?
                } else if (info.startsWith("Absorber")) {
                    String type = scan.next();
                    String name = scan.next();
                    int x1 = scan.nextInt();
                    int y1 = scan.nextInt();
                    int x2 = scan.nextInt();
                    int y2 = scan.nextInt();

                    Vect start = new Vect(x1,y1);
                    Vect end = new Vect(x2,y2);

                    System.out.println("Absorber called " + name + " goes from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
                    model.addGizmo(new GAbsorber(start,end));
                } else if (info.equals("\r")) {
                    System.out.print("\n");
                } else {

                    System.out.println("\n\n\n\n\nStill to add " + scan.next());
                }
            }
            wholeLine.close();

            return model;
        }else{
            System.out.println("doesnt exist");
        }
        return new Model();
    }

}
