package ballboy.model.levels;
import ballboy.ConfigurationParseException;
import ballboy.ConfigurationParser;
import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.Observers.LevelGreenObserver;
import ballboy.model.entities.Observers.LevelObserver;
import ballboy.model.factories.*;
import javafx.embed.swing.JFXPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLevelImpl {
    static Level level;
    static LevelGreenObserver levelGreenObserver;
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


        if (levelConfigs.size() == 0){
            return;
        }
        // set the first level as the previous level of second level
        JSONObject firstLevelConfig = (JSONObject) levelConfigs.get(0);
        level = new LevelImpl(firstLevelConfig, engine, entityFactoryRegistry, frameDurationMilli);
    }

    @Test
    public void testGetSquareCat(){
        assertNotNull(level.getSquareCat());
    }

    @Test
    public void testGetBallboy(){
        assertNotNull(level.getBallboy());
    }

    @Test
    public void testGetNextLevel(){
        assertNull(level.getNextLevel());
    }

    @Test
    public void testSetNextLevel(){
        level.setNextLevel(level);
        assertEquals(level.getNextLevel(), level);
    }

    @Test
    public void testGetIsFinish(){
        assertFalse(level.getIsFinish());
    }

    @Test
    public void testGetLevelObserverMap(){
        assertNotNull(level.getLevelObserverMap());
    }

    @Test
    public void testSetLevelObserverMap(){
        level.setLevelObserverMap(null);
        assertNull(level.getLevelObserverMap());
    }

    @Test
    public void testCloneLevelObserverMap(){
        Map<String, LevelObserver> clonedMap = level.cloneLevelObserverMap();
        assertEquals(1, level.getLevelObserverMap().get("red").getScore());

    }

    @Test
    public void testAttach(){
        levelGreenObserver = new LevelGreenObserver(1);
        level.attach("GREEN", levelGreenObserver);
        assertEquals(1, level.getLevelObserverMap().get("GREEN").getScore());
    }


    @Test
    public void testDetach(){
        level.attach("GREEN", levelGreenObserver);
    }


    @Test
    public void testNotifyObservers(){
        level.notifyObservers("red");
        assertEquals(1, level.getLevelObserverMap().get("red").getScore());

    }

    @Test
    public void testGetScores(){
        assertEquals(1, level.getLevelObserverMap().get("red").getScore());
    }


    @Test
    public void testIsSquareCat(){
        assertFalse(level.isSquareCat(null));
    }

    @Test
    public void testGetEntities(){
        assertNotNull(level.getEntities());
    }

    @Test
    public void testGetLevelHeight(){
       assertEquals(620.0, level.getLevelHeight());
    }

    @Test
    public void testGetLevelWidth(){
        assertEquals(2000.0, level.getLevelWidth());
    }

    @Test
    public void testGetHeroHeight(){
        assertEquals(50, level.getHeroHeight());
    }

    @Test
    public void testGetHeroWidth(){
        assertEquals(29.41176470588235, level.getHeroWidth());
    }

    @Test
    public void testGetFloorHeight(){
        assertEquals(600, level.getFloorHeight());
    }

    @Test
    public void testGetFloorColor(){
        assertEquals("0x001100ff", level.getFloorColor().toString());
    }

    @Test
    public void testGetGravity(){
        assertEquals(1000, level.getGravity());
    }

    @Test
    public void testUpdate(){
        level.update();
    }

    @Test
    public void testGetHeroX(){
        assertEquals(150, level.getHeroX());
    }

    @Test
    public void testGetHeroY(){
        assertEquals(300, level.getHeroY());
    }

    @Test
    public void testBoostHeight(){
        assertTrue(level.boostHeight());
    }

    @Test
    public void testDropHeight(){
        assertTrue(level.dropHeight());
    }

    @Test
    public void testMoveLeft(){
        assertTrue(level.moveLeft());
    }


    @Test
    public void testMoveRight(){
        assertTrue(level.moveRight());
    }

    @Test
    public void testIsHero(){
        assertFalse(level.isHero(null));
    }

    @Test
    public void testIsFinish(){
        assertFalse(level.isFinish(null));
    }


    @Test
    public void testResetHero(){
        level.resetHero();
        assertEquals(150, level.getHeroX());
        assertEquals(300, level.getHeroY());
    }

    @Test
    public void testFinish(){
        level.finish();
    }
}
