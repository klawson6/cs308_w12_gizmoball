package ModelPackage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            else
                toSave = gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();
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
            int rotation = gizmo.getRotation();
            int rotates = rotation/90;
            //Rotations are stored as an integer between 0 and 360 but file wants each rotation line to be 90 degrees.
            for(int i = 0; i < rotates; i++)
                infoToSave.add("Rotate " + gizmo.getId());

        }
        infoToSave.add("");


        String toSave;
        ///////////////////////////////////////////// Ball /////////////////////////////////////////////
        IBall ball = model.getBalls().get(0); //todo fix
        toSave = "Ball B " + ball.getXPosition() + " " + ball.getYPosition() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y();
        infoToSave.add(toSave);


        ///////////////////////////////////////////// Key Connects /////////////////////////////////////////////
        //todo add key connects



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
