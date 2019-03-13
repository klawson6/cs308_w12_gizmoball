package ModelPackage;

import Physics.Vect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBallTest {

    IBall ball;

    @BeforeEach
    void setUp() {
        ball = new Ball(0, 0, 5, 5);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getXPosition(){
        assertEquals(ball.getXPosition(), 0.0);
    }

    @Test
    void getYPosition(){
        assertEquals(ball.getYPosition(), 0.0);
    }

    @Test
    void getVelocity(){
        Vect vector = ball.getVelocity();
        assertNotNull(vector);
        assertEquals(vector.y(), 5);
        assertEquals(vector.x(), 5);
    }

}