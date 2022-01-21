package ballboy.model;

import ballboy.model.Memento.EntityState;
import ballboy.model.entities.behaviour.BehaviourStrategy;
import ballboy.model.entities.collision.CollisionStrategy;
import ballboy.model.entities.utilities.AxisAlignedBoundingBox;
import ballboy.model.entities.utilities.KinematicState;
import ballboy.model.entities.utilities.Vector2D;
import javafx.scene.image.Image;

public interface Entity {
    /**
     * Reset the entity
     * @return whether the entity reset the state successfully
     */
    boolean reset();

    /**
     * Set velocity
     * @param x the horizontal velocity
     * @param y the vertical velocity
     */
    void setVelocity(double x, double y);

    /**
     * Set the offset of Square Cat
     * @param x the x offset of Square Cat
     * @param y the y offset of Square Cat
     */
    void setOffset(double x, double y);

    /**
     * Set the time counter
     * @param count the new count
     */
    void setCount(int count);

    /**
     * Clone the state of entity
     * @return the state of entity
     */
    EntityState cloneState();
    // Observer

    /**
     * Get the color of Entity
     * @return the color of Entity
     */
    String getColor();

    /**
     * Set the color of Entity
     * @param color the new color
     */
    void setColor(String color);
    // Observer

    /**
     * Delete this entity
     */
    void delete();

    /**
     * check the entity is deleted or not
     * @return if the entity is deleted, return true, else false
     */
    boolean isDelete();
    /**
     * Returns the current Image used by this Entity. This may change over time, such as for simple animations.
     *
     * @return An Image representing the current state of this Entity
     */
    Image getImage();

    /**
     * @return Vector2 The current position of the entity, being the top left anchor.
     */
    Vector2D getPosition();

    /**
     * Returns the current height of this Entity
     *
     * @return The height in coordinate space (e.g. number of pixels)
     */
    double getHeight();

    /**
     * Returns the current width of this Entity
     *
     * @return The width in coordinate space (e.g. number of pixels)
     */
    double getWidth();

    /**
     * Returns the current 'z' position to draw this entity. Order within each layer is undefined.
     *
     * @return The layer to draw the entity on.
     */
    Layer getLayer();

    /**
     * @return AxisAlignedBoundingBox The enclosing volume of this entity.
     */
    AxisAlignedBoundingBox getVolume();

    /**
     * The set of available layers
     */
    enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }

    /**
     * Set the position of Entity
     * @param x the x position of entity
     * @param y the y position of entity
     */
    void setPosition(double x,  double y);

    /**
     * If the entity is deleted, restore the state into undeleted
     */
    void restoreDelete();

    /**
     * Get the collision strategy
     * @return collision strategy
     */
    CollisionStrategy getCollisionStrategy();

    /**
     * et the behaviour strategy
     * @return behaviour strategy
     */
    BehaviourStrategy getBehaviourStrategy();
}
