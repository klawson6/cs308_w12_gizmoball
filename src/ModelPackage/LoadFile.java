package ModelPackage;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

//import java.awt.event.KeyEvent;

public class LoadFile {

    private String filename;
    private Scanner wholeLine, scan;
    private File file;
    private boolean faulty;

    public LoadFile(File fileArg) {
        //Current file name, will want user prompt most likely in future
        //filename = "Documents/example_file.txt";
//        filename = "Documents/test.txt";

        //Check file exists
        file = fileArg;
        faulty = false;

        try {
            wholeLine = new Scanner(file);
            wholeLine.useDelimiter("\n");
        } catch (FileNotFoundException e) {
            System.out.println("Error. File " + filename + " not found");
        }
    }

    public Model run() {
        //Only proceed if file is there
        if (file.exists() && file.getName().endsWith(".txt")) {

            Model model = new Model(); //Create new model

            while (wholeLine.hasNext()) {
                //Put text from line into a scanner
                String info = wholeLine.next();
                scan = new Scanner(info);

                //Massive if else system, will go through all possibilities and process accordingly
                //The types could be parsed before this loop and used to compare instead if .startsWith.
                if (info.startsWith("Square")) {
                    scan.next();
                    GizmoType type = GizmoType.SQUARE;
                    //Get information from line.
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //Create gizmo with ID
                    model.createGizmo(type, xPos, yPos, xPos, yPos, name);

                } else if (info.startsWith("Circle")) {
                    scan.next();
                    GizmoType type = GizmoType.CIRCLE;
                    //Get information from line.
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //Create gizmo with ID
                    model.createGizmo(type, xPos, yPos, xPos, yPos, name);

                } else if (info.startsWith("Triangle")) {
                    scan.next();
                    GizmoType type = GizmoType.TRIANGLE;
                    //Get information from line.
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //Create gizmo with ID
                    model.createGizmo(type, xPos, yPos, xPos, yPos, name);

                } else if (info.startsWith("Rotate")) {
                    //Get information from line.
                    String type = scan.next();
                    String toRotate = scan.next();

                    Gizmo gizmo = model.getGizmo(toRotate);
                    if (gizmo != null)
                        model.rotateGizmo(gizmo); //Rotate
                    else {
                        System.out.println("Error. Gizmo " + toRotate + " is not valid");
                        System.out.println(info);
                        faulty = true;
                    }

                } else if (info.startsWith("LeftFlipper")) {
                    scan.next();
                    GizmoType type = GizmoType.LEFTFLIPPER;
                    //Get information from line.
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    //Create gizmo with ID
                    model.createGizmo(type, xPos, yPos, xPos, yPos, name);

                } else if (info.startsWith("RightFlipper")) {
                    scan.next();
                    GizmoType type = GizmoType.RIGHTFLIPPER;
                    //Get information from line.
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();
                    /**
                     * So basically for the right flipper, the coordinates given are the left coords
                     * but we will place it on the right.
                     *
                     * | 1 | 2 |
                     * | 3 | 4 |
                     *
                     * The coordinates given is 1 (& 3), but the flipper will be place in 2 (& 4).
                     * Therefore need to redo boundary check
                     *
                     * To make code more readable, going to just reassign xPos
                     */
                    xPos++;

                    //Create gizmo
                    model.createGizmo(type, xPos, yPos, xPos, yPos, name);

                } else if (info.startsWith("KeyConnect")) {
                    String type = scan.next();
                    String toPress = scan.next();
                    String keyID = scan.next();
                    String direction = scan.next();
                    String toMove = scan.next();

                    int id = Integer.parseInt(keyID);

                    String letter = java.awt.event.KeyEvent.getKeyText(id);
                    KeyCode keyCode = KeyCode.getKeyCode(letter);
                    KeyEvent k = null;
                    System.out.println(letter + " " + toMove);

                    if (direction.equalsIgnoreCase("up"))
                        k = new KeyEvent(KeyEvent.KEY_RELEASED, letter, "", keyCode, false, false, false, false);
                    else if (direction.equalsIgnoreCase("down"))
                        k = new KeyEvent(KeyEvent.KEY_TYPED, letter, "", keyCode, false, false, false, false);


                    //Check gizmo exists before adding key connection.
                    Gizmo gizmo = model.getGizmo(toMove);
                    if (gizmo != null)
                        model.addKeyConnection(k, gizmo); //Add connection
                    else {
                        System.out.println("Error. Gizmo " + toMove + " does not exist.");
                        faulty = true;
                    }

                    //KeyEvent.KEY_PRESSED, event.getCharacter(), event.getText(), event.getCode(),
                } else if (info.startsWith("Connect")) {
                    String type = scan.next();
                    String giz1 = scan.next();
                    String giz2 = scan.next();

                    Gizmo g1 = model.getGizmo(giz1);
                    Gizmo g2 = model.getGizmo(giz2);
                    if (g1 != null && g2 != null) {
                        model.addGizmoConnection(g1, g2); //Add connection
                    } else {
                        if (g1 == null) {
                            System.out.println("Error. Gizmo " + giz1 + " is not valid");
                            faulty = true;
                        }
                        if (g2 == null) {
                            System.out.println("Error. Gizmo " + giz2 + " is not valid");
                            faulty = true;
                        }
                    }

                } else if (info.startsWith("Ball")) {
                    //Get information from line.
                    String type = scan.next();
                    String name = scan.next();
                    double xPos = scan.nextDouble();
                    double yPos = scan.nextDouble();
                    double xVel = Double.parseDouble(scan.next());
                    double yVel = Double.parseDouble(scan.next());

                    //Add ball
                    model.addBall(new Ball(xPos, yPos, xVel, yVel));

                } else if (info.startsWith("Absorber")) {
                    scan.next();
                    GizmoType type = GizmoType.ABSORBER;
                    //Get information from line.
                    String name = scan.next();
                    int x1 = scan.nextInt();
                    int y1 = scan.nextInt();
                    int x2 = scan.nextInt();
                    int y2 = scan.nextInt();

                    //Create gizmo with ID
                    model.createGizmo(type, x1, y1, x2, y2, name);
                } else if (info.startsWith("Gravity")) {
                    String type = scan.next();
                    double gravity = scan.nextDouble();

                    model.setGravity(gravity);
                } else if(info.startsWith("Move")){
                    String type = scan.next();
                    String name = scan.next();
                    int xPos = scan.nextInt();
                    int yPos = scan.nextInt();

                    Gizmo gizmo = model.getGizmo(name);
                    if(gizmo != null)
                        gizmo.move(xPos,yPos);



                } else if(info.startsWith("Friction")){
                    String type = scan.next();
                    double mu1 = scan.nextDouble();
                    double mu2 = scan.nextDouble();

                    model.setMu(mu1);
                    model.setMu2(mu2);



                } else if(info.startsWith("")){

                } else{
                    faulty = false;
                }
            }
            errorAlert();
            //close scanners
            scan.close();
            wholeLine.close();

            return model;
        } else {
            System.out.println("File doesn't exist");
            errorAlertFileType();
        }
        return new Model(); //return empty model
    }

    private void errorAlert() {
        if (faulty) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("File Error");
            error.setHeaderText("There is an error(s) in the loaded file, game may be missing expected features.");
            error.show();
        }
    }

    private void errorAlertFileType() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("File Error");
        error.setHeaderText("File type not supported, please selected a .txt file.");
        error.show();
    }

    /**
     * @requires: The model is not null & the gizmo exists within the model
     *
     * @param: m The model that the gizmo should exist within
     * @param: name The ID of the gizmo
     * @return: The gizmo as a Gizmo object
     */

}
