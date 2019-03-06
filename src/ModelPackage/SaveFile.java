package ModelPackage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SaveFile {

    private String filename;
    private Stage stage;

    public SaveFile(String filename){
        this.filename = filename;
        System.out.println("Saving to file " + filename);
    }

    public SaveFile(Stage stage){
        this.stage = stage;
    }

    public void save(IModel model){
        List<String> infoToSave = new ArrayList<>();
        ///////////////////////////////////////////// General Gizmos /////////////////////////////////////////////
        HashSet<Gizmo> gizmos = model.getGizmoList();
        for(Gizmo gizmo: gizmos){
            String toSave;
            if(gizmo.getGizmoType().equals("Absorber"))
                toSave =  gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartXPosition() + " " + gizmo.getStartyPosition() + " " + gizmo.getEndxPosition() + " " + gizmo.getEndyPosition();
            else
                toSave = gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartXPosition() + " " + gizmo.getStartyPosition();
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
        Ball ball = model.getBall();
        toSave = "Ball B " + ball.getPos().x() + " " + ball.getPos().y() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y();
        infoToSave.add(toSave);
        infoToSave.add("");
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

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT",".txt"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showSaveDialog(stage);

        if(file != null) {
            FileWriter fileWriter = new FileWriter(file);
            for (String s : toSave) {
                fileWriter.write(s + "\n");
            }

            fileWriter.close();
        }

    }

}
