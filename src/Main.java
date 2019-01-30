import ModelPackage.Model;
import ModelPackage.GTriangle;

public class Main {

    public static void main(String[] args) {

        Model model = new Model();
        //model.addObserver(View.GUI);
        model.addGizmo(new GTriangle(1,1));

    }

}
