package ModelPackage;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class SaveFile {

    private String filename;
    private File file;

    public SaveFile(String filename){
        this.filename = filename;
    }

    public void save(Model model){
        HashSet<Gizmo> gizmos = new HashSet<>();
//        HashSet<Gizmo> gizmos = model.getGizmos();
        for(Gizmo gizmo: gizmos){
            String type  = "";
//            String type = gizmo.getType();

            switch(type){
                case "Square":
                    break;
                case "Circle":
                    break;
                case "Triangle":
                    break;
                case "Left Flipper":
                    break;
                case "Right Flipper":
                    break;
                case "KeyConnect":
                    break;
                case "Connect":
                    break;
                case "Ball":
                    break;
                case "Absorber":
                    break;
                default:
                    System.out.println("Shouldn't be possible");
                    System.out.println("Type is " + type);
                    break;
            }
        }




    }

}
