import ModelPackage.LoadFile;
import ModelPackage.Model;

public class Main {

    public static void main(String[] args) {


        LoadFile r = new LoadFile();
        GetConfig c = new GetConfig();
        Model model = r.run();
        //model.addObserver(View.GUI);
//        model.addGizmo(new GTriangle(1,1));



    }

}
