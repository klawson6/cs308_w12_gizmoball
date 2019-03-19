package ModelPackage;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IGizmoLeftFlipperTest {

    IGizmo gizmo;

    @BeforeEach
    void setUp() {
        gizmo = new GFlipper(0, 0, true, "TestLeftFlipper");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGizmoType(){
        assertEquals(gizmo.getGizmoType(), GizmoType.LEFTFLIPPER);
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
        assertEquals(1, gizmo.getEndxPosition());
    }

    @Test
    void getEndyPosition(){
        assertEquals(1, gizmo.getEndyPosition());
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
    void activateDeactivateTest(){
        assertFalse(((GFlipper) gizmo).isActivated());
        gizmo.activate();
        assertTrue(((GFlipper) gizmo).isActivated());
        gizmo.deactivate();
        assertFalse(((GFlipper) gizmo).isActivated());
    }

    @Test
    void getKeybindings(){

    }


}