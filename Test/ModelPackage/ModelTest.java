package ModelPackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        boolean status = m.createGizmo(GizmoType.SQUARE,0,0,0,0,"TestSquare");
        //Check to ensure its been created and is now in the list
        assertTrue(status);
        //Should only have 1 entry, a square at 0,0
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
        boolean status2 = m.createGizmo(GizmoType.TRIANGLE,0,1,0,0,"TestSquare");
        boolean status3 = m.createGizmo(GizmoType.CIRCLE,0,2,0,0,"TestSquare");

    }

    /*
    @Test
    void getModelGizmoList() {
    }
*/
    /*

    @Test
    void getGizmoList() {
    }

*/
    @Test
    void getGizmo() {
    }

    /*

    @Test
    void getBall() {
    }

    */

    @Test
    void getGizmo1() {
    }

    @Test
    void moveGizmo() {
    }

    @Test
    void deleteGizmo() {
    }

    @Test
    void rotate() {
    }

    @Test
    void createBall() {
    }

    @Test
    void getBall1() {
    }

    @Test
    void addKeyConnection() {
    }

    @Test
    void addGizmoConnection() {
    }

    @Test
    void rotateGizmo() {
    }

    @Test
    void addBall() {
    }

    /*

    @Test
    void getBalls() {
    }



    @Test
    void getModelBalls() {
    }



    @Test
    void moveBall() {
    }

*/

    @Test
    void moveBallPostion() {
    }

    @Test
    void addKeyConnection1() {
    }

    @Test
    void addGizmoConnection1() {
    }

    @Test
    void removeKeyConnection() {
    }

    @Test
    void removeGizmoConnection() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
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
    void deleteBall() {
    }

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