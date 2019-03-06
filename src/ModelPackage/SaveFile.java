package ModelPackage;

import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

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

    public void save(Model model){
        String toSave;

        ///////////////////////////////////////////// General Gizmos /////////////////////////////////////////////
        HashSet<Gizmo> gizmos = model.getGizmoList();
        for(Gizmo gizmo: gizmos){
            String toSave;
            if(gizmo.getGizmoType().equals("Absorber"))
                toSave =  gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition() + " " + gizmo.getEndxPosition() + " " + gizmo.getEndyPosition();
            else
                toSave = gizmo.getGizmoType() + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();
            infoToSave.add(toSave);
        }
        //Add blank line to seperate.
        infoToSave.add("");

            toSave = type + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();


        ////////////////////////Rotation//////////////////////////
        for(Gizmo gizmo: gizmos){
            int rotation = gizmo.getRotation();
            int rotates = rotation/90;
            //Rotations are stored as an integer between 0 and 360 but file wants each rotation line to be 90 degrees.
            for(int i = 0; i < rotates; i++)
                infoToSave.add("Rotate " + gizmo.getId());

        }
        infoToSave.add("");

            System.out.println(toSave);
            //actually save
        }

        ///////////////////////////////////////////// Ball /////////////////////////////////////////////
        Ball ball = model.getBall();
        toSave = "Ball B " + ball.getPos().x() + " " + ball.getPos().y() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y();
        System.out.println(toSave);

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
