package ballboy.model.entities.collision;

import ballboy.model.Entity;
import ballboy.model.Level;


/**
 * Collision strategy injected into all concrete dynamic entities.
 * It should contain the non-physical entity-specific behaviour for collisions.
 */
public interface CollisionStrategy {

    /**
     * The collision strategy for entities
     * @param currentEntity current entity
     * @param hitEntity teh entity be hit by current entity
     */
    void collideWith(
            Entity currentEntity,
            Entity hitEntity);

    void setLevel(Level level);

}
