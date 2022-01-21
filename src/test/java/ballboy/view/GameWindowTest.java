package ballboy.view;
import ballboy.ConfigurationParseException;
import ballboy.ConfigurationParser;
import ballboy.model.Entity;
import ballboy.model.GameEngine;
import ballboy.model.GameEngineImpl;
import ballboy.model.Level;
import ballboy.model.entities.Observers.LevelGreenObserver;
import ballboy.model.factories.*;
import ballboy.model.levels.LevelImpl;
import ballboy.model.levels.PhysicsEngine;
import ballboy.model.levels.PhysicsEngineImpl;
import javafx.embed.swing.JFXPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameWindowTest {
    static GameWindow gameWindow;
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

        Level curLevel = firstLevel;

        for (int i = 0; i < levelIndex; i++){
            curLevel = curLevel.getNextLevel();
        }
        // ###################
        GameEngine gameEngine = new GameEngineImpl(curLevel);

        gameWindow = new GameWindow(gameEngine, 640, 400, frameDurationMilli);

    }

    @Test
    public void test(){
        gameWindow.run();
    }

    @Test
    public void testGetScene(){
        assertNotNull(gameWindow.getScene());
    }
}
