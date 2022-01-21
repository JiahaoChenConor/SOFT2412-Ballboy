package ballboy.model.entities.behaviour;

import ballboy.model.Level;
import ballboy.model.entities.DynamicEntity;


public class SquareCatBehaviour implements BehaviourStrategy{
    private Level level;

    public SquareCatBehaviour(Level level){
        this.level = level;
    }
    @Override
    public void behave(DynamicEntity entity, double frameDurationMilli) {
        entity.orbited((DynamicEntity) level.getBallboy());
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }
}
