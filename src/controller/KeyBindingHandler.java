package controller;

import ModelPackage.GizmoType;
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

            for (IGizmo g : gizmos) {

                HashMap<KeyEvent, String> allbindings = g.getKeybindings();
                Set<KeyEvent> bindings = allbindings.keySet();

                for (KeyEvent e : bindings) {

                    if (event.getCode().equals(e.getCode()) && event.getEventType().getName().equals(e.getEventType().getName())) {
                        model.activate(g);
                        //g.activate();
                        System.out.println("Keybind Pressed/Released");

                        if (event.getEventType().equals(KeyEvent.KEY_RELEASED) && pressed) {

                            for (IGizmo g2 : gizmos) {

                                HashMap<KeyEvent, String> allbindings2 = g2.getKeybindings();
                                Set<KeyEvent> bindings2 = allbindings2.keySet();
                                for (KeyEvent e2 : bindings2) {
                                    if (event.getCode().equals(e2.getCode()) && event.getEventType().getName().equals(e.getEventType().getName()) && pressed) {
                                        System.out.println("Keybind Released");
                                        if (g2.getGizmoType().equals(GizmoType.RIGHTFLIPPER) || g2.getGizmoType().equals(GizmoType.LEFTFLIPPER)) {
                                            model.deactivateGizmo(g2);
                                        } else {
                                            model.activateGizmo(g2);
                                        }
                                        pressed = false;
                                        releaseyet = true;
                                    }
                                }
                            }
                        }

        }
            }


        }
    }
}

