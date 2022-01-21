package ballboy.model.entities;
import ballboy.model.Entity;
import ballboy.model.Memento.EntityState;

import ballboy.model.entities.utilities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestDynamicEntityImpl {
    DynamicEntityImpl dynamicEntity;
    AxisAlignedBoundingBox volume;
    KinematicState kinematicState;


    @BeforeEach
    public void construct(){
        Vector2D pos = new Vector2D(100, 50);
        Vector2D velocity = new Vector2D(5, 5);
        volume = new AxisAlignedBoundingBoxImpl(pos, 10, 20);
        kinematicState = new KinematicStateImpl(pos, velocity, 1);
        dynamicEntity = new DynamicEntityImpl(kinematicState, volume, Entity.Layer.FOREGROUND, null, null, null);

    }


    @Test
    public void testConstructor(){
        Entity entity = new DynamicEntityImpl(new KinematicStateImpl(new Vector2D(10, 10), new
                Vector2D(10, 10), 100), null, Entity.Layer.FOREGROUND, null, null, null);
        assertNotNull(entity);
    }

    @Test
    public void testCloneState(){
        EntityState entityState = dynamicEntity.cloneState();
        assertEquals(100, entityState.getX());
        assertEquals(50, entityState.getY());
        assertFalse(entityState.isDelete());
    }

    @Test
    public void testSetCount(){
        dynamicEntity.setCount(100);
    }

    @Test
    public void testSetColor(){
        dynamicEntity.setColor("red");
        assertEquals("red", dynamicEntity.getColor());
    }

    @Test
    public void testGetColor(){
        assertNull(dynamicEntity.getColor());
        dynamicEntity.setColor("red");
        assertEquals("red", dynamicEntity.getColor());
    }

    @Test
    public void testDelete(){
        dynamicEntity.delete();
        assertTrue(dynamicEntity.isDelete());
    }

    @Test
    public void testGetKinematicState(){
        assertEquals(kinematicState, dynamicEntity.getKinematicState());
    }

    @Test
    public void testGetImage(){
        assertNull(dynamicEntity.getImage());
    }

    @Test
    public void testGetPos(){

        assertEquals(100, dynamicEntity.getPosition().getX());
        assertEquals(50, dynamicEntity.getPosition().getY());
    }

    @Test
    public void testSetPos(){
        dynamicEntity.setPosition(new Vector2D(30, 70));
        assertEquals(30, dynamicEntity.getPosition().getX());
        assertEquals(70, dynamicEntity.getPosition().getY());
    }

    @Test
    public void testPrevPos(){
        assertEquals(100, dynamicEntity.getPositionBeforeLastUpdate().getX());
        assertEquals(50, dynamicEntity.getPositionBeforeLastUpdate().getY());
    }

    @Test
    public void testGetVelocity(){
        assertEquals(5, dynamicEntity.getVelocity().getX());
        assertEquals(5, dynamicEntity.getVelocity().getY());
    }

    @Test
    public void testSetVelocity(){
        dynamicEntity.setVelocity(new Vector2D(10, 10));
        assertEquals(10, dynamicEntity.getVelocity().getX());
        assertEquals(10, dynamicEntity.getVelocity().getY());
    }

    @Test
    public void testGetHorizontalAcceleration(){
        assertEquals(1, dynamicEntity.getHorizontalAcceleration());
    }

    @Test
    public void testSetHorizontalAcceleration(){
        dynamicEntity.setHorizontalAcceleration(2);
        assertEquals(2, dynamicEntity.getHorizontalAcceleration());
    }

    @Test
    public void testGetHeight(){
        assertEquals(10, dynamicEntity.getHeight());
    }

    @Test
    public void testGetWidth(){
        assertEquals(20, dynamicEntity.getWidth());
    }

    @Test
    public void testGetLayer(){
        assertEquals(Entity.Layer.FOREGROUND, dynamicEntity.getLayer());
    }

    @Test
    public void testGetVolume(){
        assertEquals(volume, dynamicEntity.getVolume());
    }

    @Test
    public void testRestoreDelete(){
        dynamicEntity.restoreDelete();
        assertFalse(dynamicEntity.isDelete());
    }

    @Test
    public void testGetCollisionStrategy(){

        assertNull(dynamicEntity.getCollisionStrategy());
    }

    @Test
    public void testGetBehaviourStrategy(){
        assertNull(dynamicEntity.getBehaviourStrategy());
    }

    @Test
    public void testCollideWith(){

//        dynamicEntity.collidesWith()
    }


    @Test
    public void testOrbited(){

        dynamicEntity.setCount(10);
        dynamicEntity.orbited(dynamicEntity);

        dynamicEntity.setCount(150);
        dynamicEntity.orbited(dynamicEntity);

        dynamicEntity.setCount(250);
        dynamicEntity.orbited(dynamicEntity);

        dynamicEntity.setCount(350);
        dynamicEntity.orbited(dynamicEntity);
    }
}
