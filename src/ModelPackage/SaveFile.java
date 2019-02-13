package ModelPackage;

import javafx.scene.input.KeyEvent;
import java.io.File;
import java.util.*;

public class SaveFile {

    private String filename;
    private File file;

    public SaveFile(String filename){
        this.filename = filename;
    }

    public void save(Model model){
        HashSet<Gizmo> gizmos = model.getGizmoList();



        List<String> infoToSave = new ArrayList<String>();

        ////////////////////////Gizmos//////////////////////////
        for(Gizmo gizmo: gizmos){
            gizmo.Rotate(90);
            String toSave = gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();
            infoToSave.add(toSave);
        }
        infoToSave.add("\n");


        ////////////////////////Rotation//////////////////////////
        for(Gizmo gizmo: gizmos){
            double rotation = gizmo.getRotation();
            while(rotation >= 0)
                rotation =- 90.0;
                infoToSave.add("Rotate " + gizmo.getId());
        }
        infoToSave.add("\n");

        ////////////////////////Gizmo Connection//////////////////////////
        for(Gizmo gizmo: gizmos){
            Set<String> connect = gizmo.getGizmoConnectionIds();
            for(String c: connect){
                infoToSave.add("Connect " + gizmo.getId() + " " + c);
            }
        }
        infoToSave.add("\n");

        ////////////////////////Key Connection//////////////////////////
        for(Gizmo gizmo: gizmos){
            HashSet<KeyEvent> connect = gizmo.getKeybindings();
            for(KeyEvent c: connect){
                //KeyConnect key 87 down RF137
                infoToSave.add("KeyConnect key " + c.getText() + " down " + gizmo.getId());
//                infoToSave.add();
                System.out.println(c);
            }
        }
        infoToSave.add("\n");




        ////////////////////////Ball//////////////////////////
        Ball ball = model.getBall();
        infoToSave.add("Ball B " + ball.getXPosition() + " " + ball.getYPosition() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y());


        ////////////////////////Actually save//////////////////////////
        for(String i : infoToSave)
            System.out.println(i);

    }

    private void save(List<String> toSave){

    }

}
