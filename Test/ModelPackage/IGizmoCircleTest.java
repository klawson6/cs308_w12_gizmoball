package ModelPackage;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IGizmoCircleTest {

    IGizmo gizmo;

    @BeforeEach
    void setUp() {
        gizmo = new GCircle(0, 0, "TestCircle");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGizmoType(){
        assertEquals(gizmo.getGizmoType(), GizmoType.CIRCLE);
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
        for(int i = 0; i < 3; i++){
            Color previousColour = gizmo.getColour();
            gizmo.activate();
            assertNotEquals(gizmo.getColour(), previousColour);
        }
    }

    @Test
    void deactivate(){
        gizmo.deactivate();
    }

    @Test
    void getKeybindings(){

    }


}