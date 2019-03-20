package controller;

import ModelPackage.IModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MagicKeyHandler implements EventHandler<KeyEvent> {

    private KeyBindingHandler adaptee;

    private final Set<KeyCode> real = new HashSet<KeyCode>();
    private final Set<KeyCode> announced = new HashSet<KeyCode>();
    private final boolean assumeAllReleased = false;

    public MagicKeyHandler(KeyBindingHandler adaptee)
    {
        this.adaptee = adaptee;
    }




   // @Override
    public void handle(KeyEvent event) {
        if(event.getEventType().equals(KeyEvent.KEY_PRESSED))
        {
            real.add(event.getCode());

            Platform.runLater((Runnable) new Runnable() {
                @Override
                public void run() {
                    KeyCode key = event.getCode();
                    if (real.contains(key) && !announced.contains(key)) {
                        announced.add(key);
                        adaptee.handle(event);
                    }

                }
            });
        }

        if(event.getEventType().equals(KeyEvent.KEY_TYPED))
        {
            real.add(event.getCode());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    adaptee.handle(event);
                }
            });
        }


        if(event.getEventType().equals(KeyEvent.KEY_RELEASED))
                real.remove(event.getCode());
                 Platform.runLater(new Runnable() {
                     @Override
                     public void run() {
                         KeyCode key = event.getCode();
                         if (!real.contains(key) && announced.contains(key)) {
                             announced.remove(key);
                             adaptee.handle(event);
                         }
                     }
                 });


        if (assumeAllReleased) //
            while (!real.isEmpty()) {
                KeyCode marker;
                {
                    Iterator<KeyCode> chooser = real.iterator();
                    marker = chooser.next();
                    chooser.remove();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        KeyCode key = event.getCode();
                        if (!real.contains(key) && announced.contains(key)) {
                            announced.remove(key);
                            adaptee.handle(event);
                        }

                    }
                });
            }
        }

    }




