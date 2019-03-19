package ModelPackage;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IGizmoTriangleTest {

    IGizmo gizmo;

    @BeforeEach
    void setUp() {
        gizmo = new GTriangle(0, 0, "TestTriangle");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGizmoType(){
        assertEquals(gizmo.getGizmoType(), GizmoType.TRIANGLE);
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

    @Test
    void rotationTest(){
        GTriangle triangle = (GTriangle) gizmo;
        triangle.rotate();
        assertEquals(90, triangle.getRotation());
        triangle.rotate();
        assertEquals(180, triangle.getRotation());
        triangle.rotate();
        assertEquals(270, triangle.getRotation());
        triangle.rotate();
        assertEquals(0, triangle.getRotation());
    }

    @Test
    void rotationLeftTest(){
        GTriangle triangle = (GTriangle) gizmo;
        triangle.rotateLeft();
        assertEquals(270, triangle.getRotation());
        triangle.rotateLeft();
        assertEquals(180, triangle.getRotation());
        triangle.rotateLeft();
        assertEquals(90, triangle.getRotation());
        triangle.rotateLeft();
        assertEquals(0, triangle.getRotation());
    }


}