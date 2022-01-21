package ballboy.model.Memento;

import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.Observers.LevelObserver;
import ballboy.model.entities.Observers.OverallObserver;
import ballboy.model.entities.utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameEngineMemento {
    // record the level pointer
    // record the ballboy position
    // record the enemy position, deleted or not and Id

    // how to reset the following level ?
    // change the linked list into config
    // init the level when go to next level
    private Level level;
    private EntityState ballboyState;
    private EntityState squareCatState;
    private List<EntityState> entityStates = new ArrayList<>();
    private Map<String, OverallObserver> overallObserverMap;
    private Map<String, LevelObserver> curLevelObserverMap;
    // Memento pattern, We treat game engine as Originator


    public GameEngineMemento(Level level,
                             Map<String, OverallObserver> overallObserverMap,
                             Map<String, LevelObserver> curLevelObserverMap) {
        this.level = level;
        this.overallObserverMap = overallObserverMap;
        this.curLevelObserverMap = curLevelObserverMap;
        this.ballboyState = level.getBallboy().cloneState();
        this.squareCatState = level.getSquareCat().cloneState();
        for (Entity e: level.getEntities()){
            entityStates.add(e.cloneState());
        }
    }

    public Level getLevel(){
        return level;
    }

    public EntityState getBallboyState(){
        return ballboyState;
    }

    public List<EntityState> getEntityStates(){
        return entityStates;
    }

    public Map<String, OverallObserver> getOverallObserverMap() {
        return overallObserverMap;
    }

    public Map<String, LevelObserver> getCurLevelObserverMap() {
        return curLevelObserverMap;
    }

    public EntityState getSquareCatState() {
        return squareCatState;
    }
}
