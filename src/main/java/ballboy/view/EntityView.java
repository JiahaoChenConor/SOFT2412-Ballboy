package ballboy.view;

import ballboy.model.Entity;
import javafx.scene.Node;

public interface EntityView {
    /**
     * Update the entities according to the BallBoy position
     * @param xViewportOffset x offset
     * @param yViewportOffset y offset
     */
    void update(
            double xViewportOffset,
            double yViewportOffset);

    /**
     * Match the entity
     * @param entity the entity need to be checked
     * @return if matching successfully, return ture. Otherwise, return false
     */
    boolean matchesEntity(Entity entity);

    /**
     * Mark the entity as deleted
     */
    void markForDelete();

    /**
     * Return the ImageView object of this entity
     * @return the ImageView object of this entity
     */
    Node getNode();

    /**
     * Judge the entity is deleted or not
     * @return If the entity is deleted return true. Otherwise, return false
     */
    boolean isMarkedForDelete();
}
