import ModelPackage.IModel;
import ModelPackage.LoadFile;
import ModelPackage.Model;
import view.GUIDriver;

public class Main {

    public static void main(String[] args) {


        GUIDriver gui = new GUIDriver();

        LoadFile r = new LoadFile();
        GetConfig c = new GetConfig();
        IModel model = r.run();
        model.addObserver(gui);
//        model.addGizmo(new GTriangle(1,1));



    }

}
