package ballboy.model.entities.behaviour;

import ballboy.model.Level;
import ballboy.model.entities.DynamicEntity;

import java.rmi.dgc.Lease;

/**
 * Behaviour of a given dynamic entity. This is to be delegated to after an entity is updated.
 */
public interface BehaviourStrategy {
    /**
     * Behave according to the type
     * @param entity the entity
     * @param frameDurationMilli the time for every frame
     */
    void behave(
            DynamicEntity entity,
            double frameDurationMilli);

    void setLevel(Level level);
}