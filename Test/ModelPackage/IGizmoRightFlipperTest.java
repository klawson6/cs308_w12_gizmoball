package ModelPackage;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IGizmoRightFlipperTest {

    IGizmo gizmo;

    @BeforeEach
    void setUp() {
        gizmo = new GFlipper(0, 0, false, "TestRightFlipper");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGizmoType(){
        assertEquals(gizmo.getGizmoType(), GizmoType.RIGHTFLIPPER);
    }

    @Test
    void getStartxPosition(){
        assertEquals(gizmo.getStartxPosition(), 0);
    }

    @Test
    void getStartyPosition(){
        assertEquals(gizmo.getStartyPosition(), 0);
    }

    @Test
    void getEndxPosition(){
        assertEquals(gizmo.getEndxPosition(), 0);
    }

    @Test
    void getEndyPosition(){
        assertEquals(gizmo.getEndyPosition(), 0);
    }

    @Test
    void getRotation(){
        assertEquals(gizmo.getRotation(), 0);
    }

    @Test
    void getColour(){
        assertEquals(gizmo.getColour().getClass(), Color.class);
    }

    @Test
    void activate(){
        gizmo.activate();
        assertEquals(gizmo.getRotation(), 90);
    }

    @Test
    void deactivate(){

    }

    @Test
    void getKeybindings(){

    }


}