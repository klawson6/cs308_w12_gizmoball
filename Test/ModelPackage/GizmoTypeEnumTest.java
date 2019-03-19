package ModelPackage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GizmoTypeEnumTest {

    @Test
    void fromStringTest(){
        assertEquals(GizmoType.CIRCLE, GizmoType.fromString("Circle"));
        assertEquals(GizmoType.SQUARE, GizmoType.fromString("Square"));
        assertEquals(GizmoType.TRIANGLE, GizmoType.fromString("Triangle"));
        assertEquals(GizmoType.ABSORBER, GizmoType.fromString("Absorber"));
        assertEquals(GizmoType.LEFTFLIPPER, GizmoType.fromString("Left Flipper"));
        assertEquals(GizmoType.RIGHTFLIPPER, GizmoType.fromString("Right Flipper"));
    }
}
