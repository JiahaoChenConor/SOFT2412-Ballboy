package ballboy.model;
import ballboy.ConfigurationParseException;
import ballboy.ConfigurationParser;
import ballboy.model.Memento.GameEngineMemento;
import ballboy.model.entities.Observers.LevelGreenObserver;
import ballboy.model.entities.Observers.LevelObserver;
import ballboy.model.entities.Observers.OverallGreenObserver;
import ballboy.model.entities.Observers.OverallObserver;
import ballboy.model.factories.*;
import ballboy.model.levels.LevelImpl;
import ballboy.model.levels.PhysicsEngine;
import ballboy.model.levels.PhysicsEngineImpl;
import javafx.embed.swing.JFXPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGameEngineImpl {
    static GameEngine gameEngine;
    @BeforeAll
    public static void setup(){
        JFXPanel jfxPanel = new JFXPanel();
        ConfigurationParser configuration = new ConfigurationParser();
        JSONObject parsedConfiguration = null;
        try {
            parsedConfiguration = configuration.parseConfig("config.json");
        } catch (ConfigurationParseException e) {
            System.out.println(e);
            System.exit(-1);
        }

        final double frameDurationMilli = 17;
        PhysicsEngine engine = new PhysicsEngineImpl(frameDurationMilli);

        EntityFactoryRegistry entityFactoryRegistry = new EntityFactoryRegistry();
        entityFactoryRegistry.registerFactory("cloud", new CloudFactory());
        entityFactoryRegistry.registerFactory("enemy", new EnemyFactory());
        entityFactoryRegistry.registerFactory("background", new StaticEntityFactory(Entity.Layer.BACKGROUND));
        entityFactoryRegistry.registerFactory("static", new StaticEntityFactory(Entity.Layer.FOREGROUND));
        entityFactoryRegistry.registerFactory("finish", new FinishFactory());
        entityFactoryRegistry.registerFactory("hero", new BallboyFactory());
        // CHANGE
        entityFactoryRegistry.registerFactory("squareCat", new SquareCatFactory());

        Integer levelIndex = ((Number) parsedConfiguration.get("currentLevelIndex")).intValue();
        JSONArray levelConfigs = (JSONArray) parsedConfiguration.get("levels");

        // #######CHANGE######
        Level prevLevel;

        if (levelConfigs.size() == 0){
            return;
        }
        // set the first level as the previous level of second level
        JSONObject firstLevelConfig = (JSONObject) levelConfigs.get(0);
        Level firstLevel = new LevelImpl(firstLevelConfig, engine, entityFactoryRegistry, frameDurationMilli);
        prevLevel = firstLevel;

        for (int i = 1; i < levelConfigs.size(); i++){
            JSONObject config = (JSONObject) levelConfigs.get(i);
            Level level = new LevelImpl(config, engine, entityFactoryRegistry, frameDurationMilli);
            // for the new level, set this level as the next level for its previous one
            prevLevel.setNextLevel(level);
            // then make it as previous
            prevLevel = level;
        }
//        // when finish, there is a empty level which show the winner
//        Level winner = new LevelImpl(null, engine, entityFactoryRegistry, frameDurationMilli);
//        winner.setLevelObserverMap(prevLevel.getLevelObserverMap());
//        prevLevel.setNextLevel(winner);

        Level curLevel = firstLevel;

        for (int i = 0; i < levelIndex; i++){
            curLevel = curLevel.getNextLevel();
        }
        // ###################
        gameEngine = new GameEngineImpl(curLevel);
    }

    @Test
    public void testCloneOverallObserverMap(){
        Map<String, OverallObserver> originalMap = new HashMap<>();
        originalMap.put("green", new OverallGreenObserver(10));

        Map<String, OverallObserver> clonedMap = gameEngine.cloneOverallObserverMap(originalMap);

        assertEquals(10, clonedMap.get("green").getScore());

    }

    @Test
    public void testCloneLevelObserverMap(){
        Map<String, LevelObserver> originalMap = new HashMap<>();
        originalMap.put("green", new LevelGreenObserver(10));
        Map<String, LevelObserver> clonedMap = gameEngine.cloneLevelObserverMap(originalMap);
        assertEquals(10, clonedMap.get("green").getScore());
    }


    @Test
    public void testGameEngineSave(){
        GameEngineMemento gameEngineMemento = gameEngine.save();
        assertEquals(11, gameEngineMemento.getOverallObserverMap().get("green").getScore());
        assertNotNull(gameEngineMemento.getLevel());
        assertEquals(0, gameEngineMemento.getCurLevelObserverMap().get("red").getScore());
        // Why not NULL ?
        assertNull(gameEngineMemento.getCurLevelObserverMap().get("blue"));
        assertEquals(150, gameEngineMemento.getBallboyState().getX());
        assertEquals(300, gameEngineMemento.getBallboyState().getY());
        assertFalse(gameEngineMemento.getBallboyState().isDelete());

        // remaining states
    }


    @Test
    public void testRestore(){
        GameEngineMemento gameEngineMemento = gameEngine.save();
        gameEngine.moveLeft();
        gameEngine.restore(gameEngineMemento);
        assertEquals(150, gameEngine.getCurrentLevel().getBallboy().getPosition().getX());

    }

    @Test
    public void testAttach(){
        gameEngine.attach("greenA", new OverallGreenObserver(1));
        assertEquals(1, gameEngine.getScores("greenA"));
    }


    @Test
    public void testDetach(){
        OverallGreenObserver o = new OverallGreenObserver(1);
        gameEngine.attach("greenA", o);
        gameEngine.detach("greenA", o);
        // assert not contains key
    }

    @Test
    public void testNotifyAll(){
        gameEngine.notifyObservers("green", 11);
        assertEquals(11, gameEngine.getScores("green"));
    }


    @Test
    public void testBoostHeight(){
        assertTrue(gameEngine.boostHeight());
    }

    @Test
    public void testDropHeight(){
        assertTrue(gameEngine.dropHeight());
    }

    @Test
    public void testMoveLeft(){
        assertTrue(gameEngine.moveLeft());
    }


    @Test
    public void testMoveRight(){
        assertTrue(gameEngine.moveRight());
    }

    @Test
    public void testStartLevel(){
        gameEngine.startLevel();
        gameEngine.getCurrentLevel().finish();
        gameEngine.startLevel();
        gameEngine.getCurrentLevel().finish();
        gameEngine.startLevel();
        gameEngine.getCurrentLevel().finish();
    }



}
