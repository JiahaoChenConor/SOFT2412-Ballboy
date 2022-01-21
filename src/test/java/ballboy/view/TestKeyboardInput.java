package ballboy.view;
import ballboy.ConfigurationParseException;
import ballboy.ConfigurationParser;
import ballboy.model.Entity;
import ballboy.model.GameEngine;
import ballboy.model.GameEngineImpl;
import ballboy.model.Level;
import ballboy.model.factories.*;
import ballboy.model.levels.LevelImpl;
import ballboy.model.levels.PhysicsEngine;
import ballboy.model.levels.PhysicsEngineImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
public class TestKeyboardInput {

    static GameEngine gameEngine;
    static KeyboardInputHandler keyboardInputHandler;
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

        // ###################
        gameEngine = new GameEngineImpl(firstLevel);

        keyboardInputHandler = new KeyboardInputHandler(gameEngine);
    }

    @Test
    public void testHandlePressed(){
        keyboardInputHandler.handlePressed(new KeyEvent(null, null, null
                , KeyCode.UP, false, false, false, false));

        keyboardInputHandler.handlePressed(new KeyEvent(null, null, null
                , KeyCode.DOWN, false, false, false, false));

        keyboardInputHandler.handlePressed(new KeyEvent(null, null, null
                , KeyCode.LEFT, false, false, false, false));

        keyboardInputHandler.handlePressed(new KeyEvent(null, null, null
                , KeyCode.RIGHT, false, false, false, false));
    }

    @Test
    public void testHandleReleased(){
        keyboardInputHandler.handleReleased(new KeyEvent(null, null, null
                , KeyCode.UP, false, false, false, false));

        keyboardInputHandler.handleReleased(new KeyEvent(null, null, null
                , KeyCode.DOWN, false, false, false, false));

        keyboardInputHandler.handleReleased(new KeyEvent(null, null, null
                , KeyCode.LEFT, false, false, false, false));

        keyboardInputHandler.handleReleased(new KeyEvent(null, null, null
                , KeyCode.RIGHT, false, false, false, false));
    }
}
