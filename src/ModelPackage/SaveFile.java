package ModelPackage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SaveFile {

    private File file;
    private Stage stage;

    public SaveFile(File file){
        this.file = file;
    }

    public SaveFile(Stage stage){
        this.stage = stage;
    }

    public void save(Model model){
        List<String> infoToSave = new ArrayList<>();
        ///////////////////////////////////////////// General Gizmos /////////////////////////////////////////////
        Set<Gizmo> gizmos = model.getModelGizmoList();
        for(Gizmo gizmo: gizmos){
            String toSave;
            if(gizmo.getGizmoType() == GizmoType.ABSORBER)
                toSave =  gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition() + " " + gizmo.getEndxPosition() + " " + gizmo.getEndyPosition();
            else if(gizmo.getGizmoType() == GizmoType.RIGHTFLIPPER)
                toSave = gizmo.getGizmoType().toString().replace(" ","") + " " + gizmo.getId() + " " + (gizmo.getStartxPosition()-1) + " " + gizmo.getStartyPosition();
            else
                toSave = gizmo.getGizmoType().toString().replace(" ","") + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();
            infoToSave.add(toSave);
        }
        //Add blank line to seperate.
        infoToSave.add("");

        ////////////////////////Rotation//////////////////////////
        for(Gizmo gizmo: gizmos){
            int rotation = gizmo.getRotation();
            int rotates = rotation/90;
            //Rotations are stored as an integer between 0 and 360 but file wants each rotation line to be 90 degrees.
            for(int i = 0; i < rotates; i++)
                infoToSave.add("Rotate " + gizmo.getId());

        }
        infoToSave.add("");


        String toSave;
        ///////////////////////////////////////////// Ball /////////////////////////////////////////////
        List<IBall> balls = model.getBalls();
        for(IBall ball: balls){
            toSave = "Ball B " + ball.getXPosition() + " " + ball.getYPosition() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y();
            infoToSave.add(toSave);
        }
        infoToSave.add("");

        ///////////////////////////////////////////// Key Connects /////////////////////////////////////////////
        for(Gizmo gizmo: gizmos){
            HashMap <KeyEvent, String> connectHashMap = gizmo.getKeybindings();
            Set<KeyEvent> connections = connectHashMap.keySet();
            for(KeyEvent k: connections){
                //KeyConnect key 87 up RF137
                toSave = "KeyConnect key " + k.getCode().getCode() + " up " + gizmo.getId();
                infoToSave.add(toSave);
                toSave = "KeyConnect key " + k.getCode().getCode() + " down " + gizmo.getId();
                infoToSave.add(toSave);
            }
        }
        infoToSave.add("");

        ///////////////////////////////////////////// Connections /////////////////////////////////////////////
        for(Gizmo gizmo: gizmos){
            Set <String> connections = gizmo.getGizmoConnectionIds();
            for(String c: connections){
                //KeyConnect key 87 up RF137
                toSave = "Connect " + gizmo.getId() + " " + c;
                infoToSave.add(toSave);
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

        if(file != null) {
            FileWriter fileWriter = new FileWriter(file);
            for (String s : toSave) {
                fileWriter.write(s + "\n");
            }

            fileWriter.close();
        }

    }

}
