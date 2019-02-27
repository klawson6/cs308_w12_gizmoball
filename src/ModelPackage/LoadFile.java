package ModelPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import Physics.Vect;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoadFile {

    private String filename;
    private Scanner wholeLine, scan;
    private File file;

    public LoadFile(File fileArg){
        //Current file name, will want user prompt most likely in future
        //filename = "Documents/example_file.txt";
//        filename = "Documents/test.txt";

        //Check file exists
        file = fileArg;

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
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //model.addGizmo(new GSquare(xPos,yPos,name));
                    model.createGizmo("Square",xPos,yPos,xPos,yPos,name);
                } else if (info.startsWith("Circle")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //model.addGizmo(new GCircle(xPos,yPos,name));
                    model.createGizmo("Circle",xPos,yPos,xPos,yPos,name);
                } else if (info.startsWith("Triangle")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //model.addGizmo(new GTriangle(xPos,yPos,name));
                    model.createGizmo("Triangle",xPos,yPos,xPos,yPos,name);
                } else if (info.startsWith("Rotate")) {
                    String type = scan.next();
                    String toRotate = scan.next();

                    System.out.println("Rotating object " + toRotate);
                    model.RotateGizmo(model.getGizmo(toRotate));
                } else if (info.startsWith("LeftFlipper")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //model.addGizmo(new GFlipper(xPos,yPos,true,name));
                    model.createGizmo("LeftFlipper",xPos,yPos,xPos,yPos,name);
                } else if (info.startsWith("RightFlipper")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //model.addGizmo(new GFlipper(xPos,yPos,false,name));
                    model.createGizmo("RightFlipper",xPos,yPos,xPos,yPos,name);
                } else if (info.startsWith("KeyConnect")) {
                    String type = scan.next();
                    String toPress = scan.next();
                    String keyID = scan.next();
                    String direction = scan.next();
                    String toMove = scan.next();


//                    System.out.println("When " + toPress + " ID " + keyID + " is pressed " + toMove + " is triggered");

                    //todo not sure this will work
                    KeyEvent k = new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, ""+keyID, KeyCode.getKeyCode(keyID),false,false,false,false);
                    model.addKeyConnection(k,model.getGizmo(toMove));

                } else if (info.startsWith("Connect")) {
                    String type = scan.next();
                    String obj1 = scan.next();
                    String obj2 = scan.next();

                    model.addGizmoConnection(model.getGizmo(obj1),model.getGizmo(obj2));
                } else if (info.startsWith("Ball")) {
                    String type = scan.next();
                    String name = scan.next();
                    double xPos = scan.nextDouble();
                    double yPos = scan.nextDouble();
                    double xVel = Double.parseDouble(scan.next());
                    double yVel = Double.parseDouble(scan.next());


                    model.addBall(new Ball(xPos,yPos,xVel, yVel));
                } else if (info.startsWith("Absorber")) {
                    String type = scan.next();
                    String name = scan.next();
                    int x1 = scan.nextInt();
                    int y1 = scan.nextInt();
                    int x2 = scan.nextInt();
                    int y2 = scan.nextInt();
                    //model.addGizmo(new GAbsorber(x1,y1,x2,y2,name));
                    model.createGizmo("Absorber",x1,y1,x2,y2,name);
                } else if (info.equals("")) {

                } else {
                    //System.out.println("\n\n\n\n\nStill to add " + scan.next()); //debug, to be removed
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

    /**
     * @requires: The model is not null & the gizmo exists within the model
     *
     * @param: m The model that the gizmo should exist within
     * @param: name The ID of the gizmo
     * @return: The gizmo as a Gizmo object
     */

}
