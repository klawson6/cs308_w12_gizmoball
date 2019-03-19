package ModelPackage;

import Physics.Vect;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IGizmoAbsorberTest {

    IGizmo gizmo;

    @BeforeEach
    void setUp() {
        gizmo = new GAbsorber(0, 0, 1, 1, "TestAbsorber");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGizmoType(){
        assertEquals(gizmo.getGizmoType(), GizmoType.ABSORBER);
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
        assertEquals(gizmo.getEndxPosition(), 1);
    }

    @Test
    void getEndyPosition(){
        assertEquals(gizmo.getEndyPosition(), 1);
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
        GAbsorber castedAbsorber = (GAbsorber) gizmo;
        Ball ball = new Ball(1,1, 1, 1);
        castedAbsorber.addAbsorbedBall(ball);
        castedAbsorber.activate();
        assertEquals(new Vect(0, -50), ball.getVelocity());
    }

    @Test
    void deactivate(){
        GAbsorber castedAbsorber = (GAbsorber) gizmo;
        Ball ball = new Ball(1,1, 1, 1);
        castedAbsorber.addAbsorbedBall(ball);
        castedAbsorber.deactivate();
        assertEquals(new Vect(0, -50), ball.getVelocity());
    }

    @Test
    void getKeybindings(){

    }


}