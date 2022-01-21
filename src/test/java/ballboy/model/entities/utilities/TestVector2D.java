package ballboy.model.entities.utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestVector2D {

    @Test
    public void testGetX(){
        Vector2D vector2D = new Vector2D(10, 11);
        assertEquals(10, vector2D.getX());
    }

    @Test
    public void testSetX(){
        Vector2D vector2D = new Vector2D(10, 11);
        Vector2D newVector = vector2D.setX(20);
        assertEquals(20, newVector.getX());
    }

    @Test
    public void testGetY(){
        Vector2D vector2D = new Vector2D(10, 11);
        assertEquals(11, vector2D.getY());
    }

    @Test
    public void testSetY(){
        Vector2D vector2D = new Vector2D(10, 11);
        Vector2D newVector = vector2D.setY(20);
        assertEquals(20, newVector.getY());
    }

    @Test
    public void testIsRightOf(){
        Vector2D vector2D = new Vector2D(10, 11);
        assertTrue(vector2D.isRightOf(1));
    }

    @Test
    public void testIsAbove(){
        Vector2D vector2D = new Vector2D(10, 11);
        assertTrue(vector2D.isAbove(20));
    }
}
