package ballboy.model.entities;

import ballboy.model.Entity;
import ballboy.model.Memento.EntityState;
import ballboy.model.entities.behaviour.BehaviourStrategy;
import ballboy.model.entities.collision.CollisionStrategy;
import ballboy.model.entities.utilities.AxisAlignedBoundingBox;
import ballboy.model.entities.utilities.Vector2D;
import javafx.scene.image.Image;

/**
 * A static entity.
 */
public class StaticEntityImpl extends StaticEntity {
    private final AxisAlignedBoundingBox volume;
    private final Entity.Layer layer;
    private final Image image;
    private boolean deleted;

    public StaticEntityImpl(
            AxisAlignedBoundingBox volume,
            Entity.Layer layer,
            Image image
    ) {
        this.volume = volume;
        this.layer = layer;
        this.image = image;
    }

    @Override
    public boolean reset() {
        // we do not need reset for static
        return false;
    }

    @Override
    public void setVelocity(double x, double y) {

    }

    @Override
    public void setOffset(double x, double y) {

    }

    @Override
    public void setCount(int count) {

    }

    @Override
    public EntityState cloneState() {
        return new EntityState(0,0,false);
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public void setColor(String color) {

    }

    @Override
    public void delete() {
        deleted = true;
    }

    @Override
    public boolean isDelete() {
        return deleted;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(volume.getLeftX(), volume.getTopY());
    }

    @Override
    public double getHeight() {
        return volume.getHeight();
    }

    @Override
    public double getWidth() {
        return volume.getWidth();
    }

    @Override
    public Entity.Layer getLayer() {
        return this.layer;
    }

    @Override
    public AxisAlignedBoundingBox getVolume() {
        return this.volume;
    }

    @Override
    public void setPosition(double x, double y) {

    }

    @Override
    public void restoreDelete() {

    }

    @Override
    public CollisionStrategy getCollisionStrategy() {
        return null;
    }

    @Override
    public BehaviourStrategy getBehaviourStrategy() {
        return null;
    }

}
