package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Readin {

    private String filename;
    private Scanner wholeLine, scan;

    public Readin(){
        filename = "example file.txt";
        try {
            wholeLine = new Scanner(new File(filename));
            wholeLine.useDelimiter("\n");
        }catch(FileNotFoundException e){
            System.out.println("Error. File " + filename + " not found");
        }
    }

    public void run(){
        while(wholeLine.hasNext()){
            String info = wholeLine.next();
            scan = new Scanner(info);
            if(info.startsWith("Square")){
                String type = scan.next();
                String name = scan.next();
                String xPos = scan.next();
                String yPos = scan.next();

                System.out.println("Square object called " + name + " at position (" + xPos + ", " + yPos + ")");
//                Square a = new Square(xPos,yPos);
            }else if(info.startsWith("Circle")){
                String type = scan.next();
                String name = scan.next();
                String xPos = scan.next();
                String yPos = scan.next();

                System.out.println("Circle object called " + name + " at position (" + xPos + ", " + yPos + ")");
            }else if(info.startsWith("Triangle")){
                String type = scan.next();
                String name = scan.next();
                String xPos = scan.next();
                String yPos = scan.next();

                System.out.println("Triangle object called " + name + " at position (" + xPos + ", " + yPos + ")");
            }else if(info.startsWith("Rotate")){
                String type = scan.next();
                String toRotate = scan.next();

                System.out.println("Rotating object " + toRotate);
            }else if(info.startsWith("LeftFlipper")){
                String type = scan.next();
                String name = scan.next();
                String xPos = scan.next();
                String yPos = scan.next();

                System.out.println("Left flipper called " + name + " at position (" + xPos + ", " + yPos + ")");
            }else if(info.startsWith("RightFlipper")){
                String type = scan.next();
                String name = scan.next();
                String xPos = scan.next();
                String yPos = scan.next();

                System.out.println("Right flipper called " + name + " at position (" + xPos + ", " + yPos + ")");
            }else if(info.startsWith("KeyConnect")){
                String type = scan.next();
                String toPress = scan.next();
                String keyID = scan.next();
                String direction = scan.next();
                String toMove = scan.next();

                System.out.println("When " + toPress + " ID " + keyID + " is pressed " + toMove + " is triggered");
            }else if(info.startsWith("Connect")){
                String type = scan.next();
                String obj1 = scan.next();
                String obj2 = scan.next();

                System.out.println("Connecting " + obj1 + " to " + obj2);
            } else if(info.startsWith("Ball")){
                String type = scan.next();
                String name = scan.next();
                String xPos = scan.next();
                String yPos = scan.next();
                String xVel = scan.next();
                String yVel = scan.next();


                System.out.println("Ball called " + name +" at (" + xPos + ", " + yPos + ") going at " + xVel + "L/sec on the x axis and " + yVel + "L/sec on the y axis");
            } else if(info.startsWith("Absorber")) {
                String type = scan.next();
                String name = scan.next();
                String x1 = scan.next();
                String y1 = scan.next();
                String x2 = scan.next();
                String y2 = scan.next();

                System.out.println("Absorber called " + name + " goes from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");

            }else if(info.equals("\r")){
                System.out.println("");
            } else{

                System.out.println("\n\n\n\n\nStill to add " + scan.next());
            }
        }
        wholeLine.close();
    }

}
