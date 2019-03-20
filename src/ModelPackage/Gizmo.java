package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Gizmo implements IGizmo {

    int x;
    int y;
    Color color = Color.RED;
    Set<LineSegment> composingLines = new HashSet<>();
    Set<Circle> composingCircles = new HashSet<>();
    private HashMap<KeyEvent, String> keybindings = new HashMap<>();
    private HashSet<Gizmo> connections = new HashSet<>();
    double coefficent = 1;
    String id;

    //Will be different for each sub-class
    public abstract GizmoType getGizmoType();

    //some gizmo's may have different end positions from start
    public abstract int getEndxPosition();
    public abstract int getEndyPosition();

    //Causes certain Gizmo to rotate 90 degrees if possible
    //True = rotated 90 degrees
    //False = unable to rotate
    public abstract boolean rotate();

    //Causes Gizmo action
    public abstract void activate();

    //Gets current rotation of gizmo
    public abstract int getRotation();

    //Allows setting of x start co-ordinate
    public void setxPosition(int xPos) {
        x = xPos;
    }

    //Allows setting of y start co-ordinate
    public void setyPosition(int yPos) {
        y = yPos;
    }

    //Allows setting of gizmo id
    public void setId(String id) {
        this.id = id;
    }

    //Allows gizmo's colour to change
    public void setColor(Color color) {
        this.color = color;
    }

    //Allows for gizmo coefficent to be set
    public void setCoefficent(double coefficent) {
        this.coefficent = coefficent;
    }

    //Getter Methods

    public int getStartxPosition() {
        return x;
    }

    public int getStartyPosition() {
        return y;
    }

    public Color getColour() {
        return color;
    }

    Set<LineSegment> getComposingLines() {
        return composingLines;
    }

    Set<Circle> getComposingCircles() {
        return composingCircles;
    }

    double getReflectionCoef() {
        return coefficent;
    }

    String getId() {
        return id;
    }

    //Allows adding a key binding plus action
    //TODO figure out how to store an action

    boolean addKeyBinding(KeyEvent key, String action) {
        for(KeyEvent e :keybindings.keySet()){

            if(e.getCode().getName().equals(key.getCode().getName()) && e.getEventType().getName().equals(key.getEventType().getName()) ){
                System.out.println("Keybinding exists!");
                return false;
           }

        }
        keybindings.put(key,action);
        return true;
    }

    boolean removeKeyBinding(KeyEvent key){

        boolean deleted = false;

            //Adding binding to be removed in a set to avoid concurrent modification exception
            Set<KeyEvent> toBeRemoved = new HashSet<>();
            for(KeyEvent e :keybindings.keySet()){
                if(e.getCode().getName().equals(key.getCode().getName()) )
                {

                  toBeRemoved.add(e);
                    deleted = true;
                }
            }
            keybindings.keySet().removeAll(toBeRemoved);
           return deleted;
        }



    //Getter for keybindings
    public HashMap<KeyEvent, String> getKeybindings() {
        return keybindings;
    }

    //Add gizmo to gizmo connection
    boolean addGizmoConnection(Gizmo gizmo) {
       return connections.add(gizmo);
    }

    //Remove gizmo to gizmo connection
    boolean removeGizmoConnection(Gizmo gizmo) {
        return connections.remove(gizmo);
    }

    //Get all id's of all connected gizmo's
    Set<String> getGizmoConnectionIds() {
        Set<String> ids = new HashSet<>();
        for (Gizmo gizmos : connections) {
            ids.add(gizmos.getId());
        }
        return ids;
    }

    public abstract void move(int newxPos, int newyPos);
}
