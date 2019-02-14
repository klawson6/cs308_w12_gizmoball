import ModelPackage.LoadFile;
import ModelPackage.Model;
import ModelPackage.SaveFile;
import view.GUIDriver;

public class Main {

    public static void main(String[] args) {


        GUIDriver gui = new GUIDriver();

        LoadFile r = new LoadFile();
        GetConfig c = new GetConfig();
        Model model = r.run();
//        model.addObserver(gui);
//        model.addGizmo(new GTriangle(1,1));

        SaveFile s = new SaveFile("Documents/test.txt");
        s.save(model);

    }

}
