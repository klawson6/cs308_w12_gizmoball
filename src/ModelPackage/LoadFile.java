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
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GSquare(xPos,yPos,name));
                } else if (info.startsWith("Circle")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GCircle(xPos,yPos,name));
                } else if (info.startsWith("Triangle")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GTriangle(xPos,yPos,name));
                } else if (info.startsWith("Rotate")) {
                    String type = scan.next();
                    String toRotate = scan.next();

                    System.out.println("Rotating object " + toRotate);
                    //TODO: Implement rotation in model.
                    model.RotateGizmo(getGizmo(model,toRotate));
                } else if (info.startsWith("LeftFlipper")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GFlipper(xPos,yPos,true,name));
                } else if (info.startsWith("RightFlipper")) {
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    model.addGizmo(new GFlipper(xPos,yPos,false,name));
                } else if (info.startsWith("KeyConnect")) {
                    String type = scan.next();
                    String toPress = scan.next();
                    String keyID = scan.next();
                    String direction = scan.next();
                    String toMove = scan.next();


                    System.out.println("When " + toPress + " ID " + keyID + " is pressed " + toMove + " is triggered");


                    KeyEvent k = new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, ""+keyID, KeyCode.getKeyCode(keyID),false,false,false,false);
                    model.addKeyConnection(k,getGizmo(model, toMove));

                } else if (info.startsWith("Connect")) {
                    String type = scan.next();
                    String obj1 = scan.next();
                    String obj2 = scan.next();

                    System.out.println("Connecting " + obj1 + " to " + obj2);
                    model.addGizmoConnection(getGizmo(model,obj1),getGizmo(model,obj2));
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
                    model.addGizmo(new GAbsorber(x1,y1,x2,y2,name));
                } else if (info.equals("")) {
                    System.out.print("\n"); //debug just to make look neater
                } else {
//                    System.out.println("\n\n\n\n\nStill to add " + scan.next()); //debug, to be removed
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

    private Gizmo getGizmo(Model m, String name){
        //TODO add error handling
        HashSet<Gizmo> gizmoList = m.getGizmoList();

        for(Gizmo g :gizmoList ){
            if(g.getId().equals(name))
                return g;
//            if(name.equals("A")){ //TODO fix how absorber is given its name
//                if(g.getId().startsWith("A"))
//                    return g;
//            }
        }


        return null;
    }

}
