package controller;

import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Set;

public class KeyBindingHandler implements EventHandler<KeyEvent> {

    private IModel model;

    public KeyBindingHandler(IModel m) {
        model = m;
    }


    @Override
    public void handle(KeyEvent event) {


        Set<IGizmo> gizmos = model.getGizmoList();

        for (IGizmo g : gizmos) {

            HashMap<KeyEvent, String> allbindings = g.getKeybindings();
            Set<KeyEvent> bindings = allbindings.keySet();

            for (KeyEvent e : bindings) {


                KeyCode key = e.getCode();
                if(key != KeyCode.UNDEFINED){
                    if (event.getCode().equals(e.getCode()) && event.getEventType().getName().equals(e.getEventType().getName())   ) {
                        model.activateGizmo(g);
                        System.out.println("Keybind Pressed/Released " + event.getCode());

                    }
                }else{
                    if (event.getCharacter().equals(e.getCharacter()) && event.getEventType().getName().equals(e.getEventType().getName())   ) {
                        model.activateGizmo(g);
                        System.out.println("Keybind Pressed/Released " + event.getCharacter());

                    }
                }

            }

        }



    }
}
