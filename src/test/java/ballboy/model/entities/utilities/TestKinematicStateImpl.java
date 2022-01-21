package ballboy.model.entities.utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestKinematicStateImpl {
    KinematicState kinematicState;
    @BeforeEach
    public void setup(){
        kinematicState = new KinematicStateImpl(new Vector2D(10, 10), new Vector2D(20, 20), 20);
    }

    @Test
    public void testConstructor1(){
        KinematicState kinematicState1 = new KinematicStateImpl(new Vector2D(10, 10), new Vector2D(20, 20), 20);
        assertNotNull(kinematicState1);
    }


    @Test
    public void testCopy(){
        KinematicState kinematicStateCopied = kinematicState.copy();
        assertEquals(kinematicStateCopied.getPreviousPosition(), kinematicState.getPreviousPosition());
        assertEquals(kinematicStateCopied.getPosition(), kinematicState.getPosition());
        assertEquals(kinematicStateCopied.getVelocity(), kinematicState.getVelocity());
        assertEquals(kinematicStateCopied.getHorizontalAcceleration(), kinematicState.getHorizontalAcceleration());
    }
}
