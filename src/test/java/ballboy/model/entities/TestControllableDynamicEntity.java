package ballboy.model.entities;
import ballboy.model.Entity;
import ballboy.model.entities.utilities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestControllableDynamicEntity {
    DynamicEntityImpl dynamicEntity;
    AxisAlignedBoundingBox volume;
    KinematicState kinematicState;
    ControllableDynamicEntity<DynamicEntityImpl> controllableDynamicEntity;

    @BeforeEach
    public void construct(){
        Vector2D pos = new Vector2D(100, 50);
        Vector2D velocity = new Vector2D(5, 5);
        volume = new AxisAlignedBoundingBoxImpl(pos, 10, 20);
        kinematicState = new KinematicStateImpl(pos, velocity, 1);
        dynamicEntity = new DynamicEntityImpl(kinematicState, volume, Entity.Layer.FOREGROUND, null, null, null);

        controllableDynamicEntity = new ControllableDynamicEntity<>(dynamicEntity, new Vector2D(40, 40), 5, 700, 20);
    }

    @Test
    public void testHashCode(){
        controllableDynamicEntity.hashCode();
    }

    @Test
    public void testBoostHeight(){
        boolean ret = controllableDynamicEntity.boostHeight();
        assertTrue(ret);
    }

    @Test
    public void testDropHeight(){
        boolean ret = controllableDynamicEntity.dropHeight();
        assertTrue(ret);
    }

    @Test
    public void testMoveLeft(){
        boolean ret = controllableDynamicEntity.moveLeft();
        assertTrue(ret);
    }

    @Test
    public void testMoveRight(){
        boolean ret = controllableDynamicEntity.moveRight();
        assertTrue(ret);
    }

    @Test
    public void testReset(){
        boolean ret = controllableDynamicEntity.reset();
        assertTrue(ret);
    }

    @Test
    public void testGetKinematicState(){
        assertEquals(kinematicState, controllableDynamicEntity.getKinematicState());
    }

    @Test
    public void testOrbited(){
        controllableDynamicEntity.orbited(dynamicEntity);
    }

    @Test
    public void testSetCount(){
        controllableDynamicEntity.setCount(100);
    }

    @Test
    public void testSetColor(){
        controllableDynamicEntity.setColor("red");
        assertEquals("red", controllableDynamicEntity.getColor());
    }

    @Test
    public void testGetColor(){
        assertNull(controllableDynamicEntity.getColor());
        controllableDynamicEntity.setColor("red");
        assertEquals("red", controllableDynamicEntity.getColor());
    }

    @Test
    public void testDelete(){
        controllableDynamicEntity.delete();
        assertTrue(controllableDynamicEntity.isDelete());
    }


    @Test
    public void testGetImage(){
        assertNull(controllableDynamicEntity.getImage());
    }

    @Test
    public void testGetPos(){

        assertEquals(100, controllableDynamicEntity.getPosition().getX());
        assertEquals(50, controllableDynamicEntity.getPosition().getY());
    }

    @Test
    public void testSetPos(){
        controllableDynamicEntity.setPosition(new Vector2D(30, 70));
        assertEquals(30, controllableDynamicEntity.getPosition().getX());
        assertEquals(70, controllableDynamicEntity.getPosition().getY());
    }

    @Test
    public void testPrevPos(){
        assertEquals(100, controllableDynamicEntity.getPositionBeforeLastUpdate().getX());
        assertEquals(50, controllableDynamicEntity.getPositionBeforeLastUpdate().getY());
    }

    @Test
    public void testGetVelocity(){
        assertEquals(5, controllableDynamicEntity.getVelocity().getX());
        assertEquals(5, controllableDynamicEntity.getVelocity().getY());
    }

    @Test
    public void testSetVelocity(){
        controllableDynamicEntity.setVelocity(new Vector2D(10, 10));
        assertEquals(10, controllableDynamicEntity.getVelocity().getX());
        assertEquals(10, controllableDynamicEntity.getVelocity().getY());
    }

    @Test
    public void testGetHorizontalAcceleration(){
        assertEquals(1, controllableDynamicEntity.getHorizontalAcceleration());
    }

    @Test
    public void testSetHorizontalAcceleration(){
        controllableDynamicEntity.setHorizontalAcceleration(2);
        assertEquals(2, controllableDynamicEntity.getHorizontalAcceleration());
    }

    @Test
    public void testGetHeight(){
        assertEquals(10, controllableDynamicEntity.getHeight());
    }

    @Test
    public void testGetWidth(){
        assertEquals(20, controllableDynamicEntity.getWidth());
    }

    @Test
    public void testGetLayer(){
        assertEquals(Entity.Layer.FOREGROUND, controllableDynamicEntity.getLayer());
    }

    @Test
    public void testGetVolume(){
        assertEquals(volume, controllableDynamicEntity.getVolume());
    }

    @Test
    public void testRestoreDelete(){
        controllableDynamicEntity.restoreDelete();
        assertFalse(controllableDynamicEntity.isDelete());
    }

    @Test
    public void testGetCollisionStrategy(){

        assertNull(controllableDynamicEntity.getCollisionStrategy());
    }

    @Test
    public void testGetBehaviourStrategy(){
        assertNull(controllableDynamicEntity.getBehaviourStrategy());
    }



}
