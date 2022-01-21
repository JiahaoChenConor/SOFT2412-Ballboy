package ballboy.model;

import ballboy.model.Memento.EntityState;
import ballboy.model.Memento.GameEngineMemento;
import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.Observers.LevelObserver;
import ballboy.model.entities.Observers.OverallObserver;
import ballboy.model.entities.Observers.OverallRedObserver;
import ballboy.model.entities.StaticEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the GameEngine interface.
 * This provides a common interface for the entire game.
 */
public class GameEngineImpl implements GameEngine {
    // CHANGE: this cannot be final
    private Level level;
    // Memento pattern, We treat game engine as Originator

    @Override
    public Map<String, OverallObserver> cloneOverallObserverMap( Map<String, OverallObserver> map){
        Map<String, OverallObserver> clonedMap = new HashMap<>();
        for (String color: map.keySet()){
            clonedMap.put(color, map.get(color).cloneObserver());
        }


        return clonedMap;

    }

    @Override
    public Map<String, LevelObserver> cloneLevelObserverMap( Map<String, LevelObserver> map){
        Map<String, LevelObserver> clonedMap = new HashMap<>();
        for (String color: map.keySet()){
            clonedMap.put(color, map.get(color).cloneObserver());
        }


        return clonedMap;
    }

    @Override
    public GameEngineMemento save(){

        Map<String,OverallObserver> overallObserverMapCloned = cloneOverallObserverMap(overallObserverMap);

        return new GameEngineMemento(level, overallObserverMapCloned, level.cloneLevelObserverMap());
    }

    @Override
    public void restore(GameEngineMemento gameEngineMemento){
        Level level = gameEngineMemento.getLevel();
        this.level = level;

        level.restoreFinish();
        EntityState ballboyState = gameEngineMemento.getBallboyState();
        EntityState squareCatState = gameEngineMemento.getSquareCatState();
        List<EntityState> states = gameEngineMemento.getEntityStates();
        level.getBallboy().setPosition(ballboyState.getX(), ballboyState.getY());
        level.getBallboy().setVelocity(ballboyState.getXV(), ballboyState.getYV());


        level.getSquareCat().setCount(squareCatState.getCount());
        level.getSquareCat().setPosition(squareCatState.getX(), squareCatState.getY());
        level.getSquareCat().setOffset(squareCatState.getXOffset(), squareCatState.getYOffset());

        for (int i = 0; i < states.size(); i++){

            Entity entity = level.getEntities().get(i);
            EntityState entityState = states.get(i);
            entity.setPosition(entityState.getX(), entityState.getY());

            if(entityState.isDelete()){
                entity.delete();
            }else{
                entity.restoreDelete();
            }

            if (!(entity instanceof StaticEntity)){
                entity.getBehaviourStrategy().setLevel(level);
                entity.getCollisionStrategy().setLevel(level);
            }
        }

        // Score restore
        level.setLevelObserverMap(cloneLevelObserverMap(gameEngineMemento.getCurLevelObserverMap()));
        this.overallObserverMap = cloneOverallObserverMap(gameEngineMemento.getOverallObserverMap());


    }
    // Observer
    private Map<String, OverallObserver> overallObserverMap = new HashMap<>();

    @Override
    public void attach(String color, OverallObserver o) {
        overallObserverMap.put(color, o);
    }

    @Override
    public void detach(String color, OverallObserver o) {
        overallObserverMap.remove(color, o);
    }

    @Override
    public void notifyObservers(String color, int lastLevelScore) {
        overallObserverMap.get(color).updateOverall(lastLevelScore);
    }

    @Override
    public int getScores(String color) {
        return overallObserverMap.get(color).getScore();
    }

    //
    public GameEngineImpl(Level level) {
        this.level = level;
        this.attach("red", new OverallRedObserver(0));
        this.attach("green", new OverallRedObserver(0));
        this.attach("blue", new OverallRedObserver(0));
    }

    public Level getCurrentLevel() {
        return level;
    }


    public void startLevel() {
        if (level == null){
            return;
        }

        // TODO: Handle when multiple levels has been implemented
        if (level.getIsFinish()){
            Level cur = level.getNextLevel();

            while (cur != null){
                cur.restoreFinish();

//                 Need restore everything
                for (Entity entity: cur.getEntities()){

                    if (entity instanceof DynamicEntity){
                        entity.reset();
                        entity.restoreDelete();
                    }

                }
                // reset the position
                cur.getBallboy().reset();
                cur.getSquareCat().reset();

                Map<String, LevelObserver> observersMap = cur.getLevelObserverMap();
                for (String key: observersMap.keySet()){
                    observersMap.get(key).reset();
                }

                cur = cur.getNextLevel();
            }



            if (level != null){

                // Observer for previous level score
                if (level.getLevelObserverMap().containsKey("red")){
                    notifyObservers("red", level.getScores("red"));
                }

                if (level.getLevelObserverMap().containsKey("green")){
                    notifyObservers("green", level.getScores("green"));
                }

                if (level.getLevelObserverMap().containsKey("blue")){
                    notifyObservers("blue", level.getScores("blue"));
                }

            }

            assert level != null;
            level = level.getNextLevel();
        }


    }

    public boolean boostHeight() {
        return level.boostHeight();
    }

    public boolean dropHeight() {
        return level.dropHeight();
    }

    public boolean moveLeft() {
        return level.moveLeft();
    }

    public boolean moveRight() {
        return level.moveRight();
    }

    public void tick() {
        if (level != null){
            level.update();
        }

    }
}