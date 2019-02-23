package ModelPackage;

import javafx.scene.input.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SaveFile {

    private String filename;

    public SaveFile(String filename){
        this.filename = filename;
    }

    public void save(Model model){

        //Get all gizmos.
        Set<Gizmo> gizmos = model.getModelGizmoList();

        //Where all formatted information will be held
        List<String> infoToSave = new ArrayList<String>();


        //Gizmos must be first.
        ////////////////////////Gizmos//////////////////////////
        for(Gizmo gizmo: gizmos){
            String toSave = gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();
            infoToSave.add(toSave);
        }
        //Add blank line to seperate.
        infoToSave.add("");

        ////////////////////////Ball//////////////////////////
        //Currently only one ball, will need to be altered if more than one ball is used.
        List<Ball> balls = model.getModelBalls();
        for(Ball ball: balls) {
            infoToSave.add("Ball B " + ball.getXPosition() + " " + ball.getYPosition() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y());
        }
        infoToSave.add("");

        ////////////////////////Rotation//////////////////////////
        for(Gizmo gizmo: gizmos){
            double rotation = gizmo.getRotation();
            //Rotations are stored as an integer between 0 and 360 but file wants each rotation line to be 90 degrees.
            while(rotation >= 0)
                rotation =- 90.0;
                infoToSave.add("rotate " + gizmo.getId());
        }
        infoToSave.add("");

        ////////////////////////Gizmo Connection//////////////////////////
        for(Gizmo gizmo: gizmos){
            Set<String> connect = gizmo.getGizmoConnectionIds();
            for(String c: connect){
                infoToSave.add("Connect " + gizmo.getId() + " " + c);
            }
        }
        infoToSave.add("");

        ////////////////////////Key Connection//////////////////////////
        for(Gizmo gizmo: gizmos){
            HashMap<KeyEvent,String> allconnections = gizmo.getKeybindings();
            Set<KeyEvent> connect = allconnections.keySet();
            for(KeyEvent c: connect){
                //In format KeyConnect key 87 down RF137
                infoToSave.add("KeyConnect key " + c.getText() + " down " + gizmo.getId()); //todo: not sure this will properly work
            }
        }
        infoToSave.add("");





        ////////////////////////Actually save//////////////////////////
        try {
            saveToFile(infoToSave);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @param toSave All of the gizmos formatted in the correct format.
     * @throws IOException if file does not exist.
     */
    private void saveToFile  (List<String> toSave)throws IOException{
        File file = new File(filename);

        FileWriter fileWriter = new FileWriter(file);
        for(String s: toSave){
            fileWriter.write(s + "\n");
        }

        fileWriter.close();

    }

}
