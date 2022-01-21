package ballboy.model.entities;
import ballboy.model.Entity;
import ballboy.model.Memento.EntityState;
import ballboy.model.entities.utilities.AxisAlignedBoundingBox;
import ballboy.model.entities.utilities.AxisAlignedBoundingBoxImpl;
import ballboy.model.entities.utilities.Vector2D;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


public class TestStaticEntityImpl {
    private AxisAlignedBoundingBox volume;
    private StaticEntity staticEntity;

    @BeforeEach
    void constructObject(){
        volume = new AxisAlignedBoundingBoxImpl(new Vector2D(10, 20), 40, 50);
        staticEntity = new StaticEntityImpl(volume, Entity.Layer.FOREGROUND, null);
    }

    @Test
    public void testConstructor(){
        StaticEntity staticEntity = new StaticEntityImpl(null, Entity.Layer.FOREGROUND, null);
        assertNotNull(staticEntity);
    }

    @Test
    public void testSetCount(){
        staticEntity.setCount(10);
    }

    @Test
    public void testCloneState(){
        EntityState entityState = staticEntity.cloneState();
        assertEquals(0, entityState.getX());
        assertEquals(0, entityState.getY());
        assertFalse(entityState.isDelete());
    }

    @Test
    public void testGetColor(){
        assertNull(staticEntity.getColor());
    }


    @Test
    public void testSetColor(){
        staticEntity.setColor("red");
        assertNull(staticEntity.getColor());
    }


    @Test
    public void testDelete(){
        staticEntity.delete();
        assertTrue(staticEntity.isDelete());
    }

    @Test
    public void testIsDelete(){
        assertFalse(staticEntity.isDelete());
    }

    @Test
    public void testGetImage(){
        assertNull(staticEntity.getImage());
    }

    @Test
    public void testGetPosition(){
        Vector2D pos = staticEntity.getPosition();
        assertEquals(10, pos.getX());
        assertEquals(20, pos.getY());
    }

    @Test
    public void testGetHeight(){
        assertEquals(40, staticEntity.getVolume().getHeight());

    }

    @Test
    public void testGetWidth(){
        assertEquals(50, staticEntity.getVolume().getWidth());
    }


    @Test
    public void testGetLayer(){
        assertEquals(Entity.Layer.FOREGROUND, staticEntity.getLayer());
    }


    @Test
    public void testGetVolume(){
        assertEquals(volume, staticEntity.getVolume());
    }

    @Test
    public void testSetPosition(){
        staticEntity.setPosition(10, 10);
    }

    @Test
    public void testRestoreDelete(){
        staticEntity.restoreDelete();
    }

    @Test
    public void testGetCollisionStrategy(){
        staticEntity.getCollisionStrategy();
    }

    @Test
    public void testGetBehaviourStrategy(){
        staticEntity.getBehaviourStrategy();
    }









}
