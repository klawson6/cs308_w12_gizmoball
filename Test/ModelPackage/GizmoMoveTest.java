package ModelPackage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GizmoMoveTest {

    @Test
    void circleMoveTest(){
        Gizmo gizmo = new GCircle(5, 6);
        assertEquals(gizmo.getStartxPosition(), 5);
        assertEquals(gizmo.getStartyPosition(), 6);
        gizmo.move(9, 10);
        assertEquals(gizmo.getStartxPosition(), 9);
        assertEquals(gizmo.getStartyPosition(), 10);
    }

    @Test
    void squareMoveTest(){
        Gizmo gizmo = new GSquare(5, 6);
        assertEquals(gizmo.getStartxPosition(), 5);
        assertEquals(gizmo.getStartyPosition(), 6);
        gizmo.move(9, 10);
        assertEquals(gizmo.getStartxPosition(), 9);
        assertEquals(gizmo.getStartyPosition(), 10);
    }

    @Test
    void triangleMoveTest(){
        Gizmo gizmo = new GTriangle(5, 6);
        assertEquals(gizmo.getStartxPosition(), 5);
        assertEquals(gizmo.getStartyPosition(), 6);
        gizmo.move(9, 10);
        assertEquals(gizmo.getStartxPosition(), 9);
        assertEquals(gizmo.getStartyPosition(), 10);
    }

    @Test
    void leftFlipperMoveTest(){
        Gizmo gizmo = new GFlipper(5, 6, true);
        assertEquals(gizmo.getStartxPosition(), 5);
        assertEquals(gizmo.getStartyPosition(), 6);
        gizmo.move(9, 10);
        assertEquals(gizmo.getStartxPosition(), 9);
        assertEquals(gizmo.getStartyPosition(), 10);
    }

    @Test
    void rightFlipperMoveTest(){
        Gizmo gizmo = new GFlipper(5, 6, false);
        assertEquals(gizmo.getStartxPosition(), 5);
        assertEquals(gizmo.getStartyPosition(), 6);
        gizmo.move(9, 10);
        assertEquals(gizmo.getStartxPosition(), 9);
        assertEquals(gizmo.getStartyPosition(), 10);
    }

    @Test
    void absorberMoveTest(){
        Gizmo gizmo = new GAbsorber(5, 6, 7, 8);
        assertEquals(gizmo.getStartxPosition(), 5);
        assertEquals(gizmo.getStartyPosition(), 6);
        assertEquals(gizmo.getEndxPosition(), 7);
        assertEquals(gizmo.getEndyPosition(), 8);
        gizmo.move(9, 10);
        assertEquals(gizmo.getStartxPosition(), 9);
        assertEquals(gizmo.getStartyPosition(), 10);
        assertEquals(gizmo.getEndxPosition(), 11);
        assertEquals(gizmo.getEndyPosition(), 12);
    }

}
