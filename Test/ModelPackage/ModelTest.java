package ModelPackage;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest extends Model {

    Model m;

    @BeforeEach
    void setUp() {
        m = new Model();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void createGizmo() {
        //Create Square at position 0,0
        boolean status = m.createGizmo(GizmoType.SQUARE,0,0,0,0);
        //Check to ensure its been created and is now in the list
        assertTrue(status);
        //Should only have 1 entry, a square at 0,0
        Set<Gizmo> gizmos = m.getModelGizmoList();

        for(Gizmo gizmo: gizmos){
            assertSame(gizmo.getGizmoType(), GizmoType.SQUARE);
            assertEquals(0, gizmo.getStartxPosition());
            assertEquals(0, gizmo.getStartyPosition());
        }


    }

    @Test
    void createGizmowithID() {

        //Create Square at position 1,2
        boolean status = m.createGizmo(GizmoType.SQUARE,1,2,1,2,"TestSquare");
        //Check to ensure its been created and is now in the list
        assertTrue(status);
        //Should only have 1 entry, a square at 1,2
        Set<Gizmo> gizmos = m.getModelGizmoList();

        for(Gizmo gizmo: gizmos){
            assertSame(gizmo.getGizmoType(), GizmoType.SQUARE);
            assertEquals(1, gizmo.getStartxPosition());
            assertEquals(2, gizmo.getStartyPosition());
            assertEquals("TestSquare", gizmo.getId());
        }


    }

    @Test
    void CreateMultipleGizmos(){

        boolean status1 = m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertTrue(status1);
        boolean status2 = m.createGizmo(GizmoType.TRIANGLE,0,1,0,0,"TestTriangle");
        assertTrue(status2);
        boolean status3 = m.createGizmo(GizmoType.CIRCLE,0,2,0,0,"TestCircle");
        assertTrue(status3);
        boolean status4 = m.createGizmo(GizmoType.ABSORBER,18,18,19,19,"TestAbsorber");
        assertTrue(status4);
        boolean status5 = m.createGizmo(GizmoType.LEFTFLIPPER,4,4,4,4,"TestLFlipper");
        assertTrue(status5);
        boolean status6 = m.createGizmo(GizmoType.RIGHTFLIPPER,6,6,6,6,"TestRFlipper");
        assertTrue(status6);

        assertEquals(m.getGizmoList().size(), 6);

    }


    @Test
    void getModelGizmoListTest() {

        // Should be empty at the start
        assertTrue(m.getModelGizmoList().isEmpty());
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertEquals(m.getModelGizmoList().size(), 1);

    }

    @Test
    void getGizmoListTest() {
        // Should be empty at the start
        assertTrue(m.getGizmoList().isEmpty());
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertEquals(m.getGizmoList().size(), 1);
    }

    @Test
    void getGizmo() {
        assertNull(m.getGizmo(0, 0));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        IGizmo gizmo = m.getGizmo(0, 0);
        assertEquals(gizmo.getGizmoType(), GizmoType.SQUARE);
        assertEquals(((Gizmo) gizmo).getId(), "TestSquare");
    }

    @Test
    void getBallTest() {
        assertNull(m.getBall());
    }

    @Test
    void moveGizmo() {
        assertFalse(m.moveGizmo(0, 0, 5, 5));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertTrue(m.moveGizmo(0, 0, 5, 5));
        assertFalse(m.moveGizmo(0, 0, 5, 5));
        IGizmo gizmo = m.getGizmo(0, 0);
        assertNull(gizmo);
        gizmo = m.getGizmo(5, 5);
        assertEquals(gizmo.getGizmoType(), GizmoType.SQUARE);
    }

    @Test
    void deleteGizmo() {
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertNotNull(m.getGizmo(0, 0));
        m.deleteGizmo(0, 0);
        assertNull(m.getGizmo(0, 0));
        assertTrue(m.getGizmoList().isEmpty());
    }

    @Test
    void rotate() {
        assertFalse(m.rotate(0, 0));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertNotNull(m.getGizmo(0, 0));
        assertFalse(m.rotate(0, 0));
        m.createGizmo(GizmoType.TRIANGLE,1,1,1,1);
        assertNotNull(m.getGizmo(1, 1));
        assertTrue(m.rotate(1, 1));
    }

    @Test
    void createBall() {
        assertTrue(m.createBall(2, 2, 0, 0));
        assertFalse(m.createBall(2, 2, 0, 0));
    }

    @Test
    void deleteBall() {
        assertFalse(m.deleteBall(2, 2));
        m.createBall(2, 2, 0, 0);
        assertTrue(m.deleteBall(2, 2));
    }

    @Test
    void addKeyConnection() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "E", "E", KeyCode.E, false, false, false, false);
        assertFalse(m.addKeyConnection(0,0, keyEvent));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertTrue(m.addKeyConnection(0,0, keyEvent));
    }

    @Test
    void addGizmoConnection() {
        assertFalse(m.addGizmoConnection(0, 0, 1, 1));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertFalse(m.addGizmoConnection(0, 0, 1, 1));
        m.createGizmo(GizmoType.CIRCLE,1,1,1,1,"TestCircle");
        assertTrue(m.addGizmoConnection(0, 0, 1, 1));
    }

    @Test
    void removeKeyConnection() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "E", "E", KeyCode.E, false, false, false, false);
        assertFalse(m.removeKeyConnection(0,0, keyEvent));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        m.addKeyConnection(0,0, keyEvent);
        assertTrue(m.removeKeyConnection(0,0, keyEvent));
    }

    @Test
    void removeGizmoConnection() {
        assertFalse(m.removeGizmoConnection(0, 0, 1, 1));
        m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        assertFalse(m.removeGizmoConnection(0, 0, 1, 1));
        m.createGizmo(GizmoType.CIRCLE,1,1,1,1,"TestCircle");
        m.addGizmoConnection(0, 0, 1, 1);
        assertTrue(m.removeGizmoConnection(0, 0, 1, 1));
    }

    @Test
    void rotateGizmo() {
        Gizmo gizmo = new GCircle(0, 0);
        //assertFalse(m.rotateGizmo(gizmo));
    }

    @Test
    void getBallsTest() {
    }

    @Test
    void moveBallTest() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
        File file = new File("example_file.txt");
        assertEquals(m.load(file).getClass(), Model.class);
        m = m.load(file);
        assertEquals(m.getGizmoList().size(), 36);
    }

    /*

    @Test
    void play() {
    }

    @Test
    void stop() {
    }

    @Test
    void tick() {
    }

    */

    @Test
    void applyGravity() {
    }

    @Test
    void applyFriction() {
    }

    @Test
    void moveBallForTime() {
    }

    @Test
    void timeUntilCollision() {
    }

}