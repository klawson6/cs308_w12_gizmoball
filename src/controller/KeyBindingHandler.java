package controller;

import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Set;

public class KeyBindingHandler implements EventHandler<KeyEvent> {

    private IModel model;
    private boolean pressed = false;
    private boolean releaseyet = true;

    public KeyBindingHandler(IModel m) {
        model = m;
    }



    @Override
    public void handle(KeyEvent event) {

        Set<IGizmo> gizmos = model.getGizmoList();
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && releaseyet ) {

            for (IGizmo g : gizmos) {

                HashMap<KeyEvent, String> allbindings = g.getKeybindings();
                Set<KeyEvent> bindings = allbindings.keySet();

                for (KeyEvent e : bindings) {

                    if (event.getCode().equals(e.getCode()) && event.getEventType().getName().equals(e.getEventType().getName())   ) {
                        model.activateGizmo(g);
                        System.out.println("Keybind Pressed");
                        pressed = true;
                        releaseyet = false;
                    }
                }
            }
        }

        if (event.getEventType().equals(KeyEvent.KEY_RELEASED) && pressed ) {

                for (IGizmo g : gizmos) {

                    HashMap<KeyEvent, String> allbindings = g.getKeybindings();
                    Set<KeyEvent> bindings = allbindings.keySet();
                    for (KeyEvent e : bindings) {
                        if (event.getCode().equals(e.getCode()) && event.getEventType().getName().equals(e.getEventType().getName()) && pressed) {
                            System.out.println("Keybind Released");
                            model.activateGizmo(g);
                            pressed = false;
                            releaseyet = true;
                        }
                    }

                }
            }
            event.consume();





    }
}
