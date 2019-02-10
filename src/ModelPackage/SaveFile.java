package ModelPackage;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class SaveFile {

    private String filename;
    private File file;

    public SaveFile(String filename){
        this.filename = filename;
        System.out.println("Saving to file " + filename);
    }

    public void save(Model model){
        String toSave;

        ///////////////////////////////////////////// General Gizmos /////////////////////////////////////////////
        HashSet<Gizmo> gizmos = model.getGizmoList();
        for(Gizmo gizmo: gizmos){
            String type = gizmo.getGizmoType();

            toSave = type + " " + gizmo.getId() + " " + gizmo.getStartxPosition() + " " + gizmo.getStartyPosition();


            //todo fix absorbers as they dont seem to have a name in Murrays example file.

            System.out.println(toSave);
            //actually save
        }

        ///////////////////////////////////////////// Ball /////////////////////////////////////////////
        Ball ball = model.getBall();
        toSave = "Ball B " + ball.getPos().x() + " " + ball.getPos().y() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y();
        System.out.println(toSave);

        ///////////////////////////////////////////// Key Connects /////////////////////////////////////////////
        //todo add key connects

        ///////////////////////////////////////////// Connections /////////////////////////////////////////////
        //todo add connections

    }

}
