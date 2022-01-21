package ballboy.model.levels;
import ballboy.ConfigurationParseException;
import ballboy.ConfigurationParser;
import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.DynamicEntityImpl;
import ballboy.model.entities.Observers.LevelGreenObserver;
import ballboy.model.entities.StaticEntity;
import ballboy.model.entities.StaticEntityImpl;
import ballboy.model.entities.behaviour.PassiveEntityBehaviourStrategy;
import ballboy.model.entities.collision.BallboyCollisionStrategy;
import ballboy.model.entities.utilities.AxisAlignedBoundingBoxImpl;
import ballboy.model.entities.utilities.KinematicStateImpl;
import ballboy.model.entities.utilities.Vector2D;
import ballboy.model.factories.*;
import javafx.embed.swing.JFXPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestPhysicsEngineImp {

    static Level level;
    static LevelGreenObserver levelGreenObserver;
    static DynamicEntity dynamicEntity1;
    static DynamicEntity dynamicEntity2;
    static DynamicEntity dynamicEntity3;
    static StaticEntity staticEntity;
    static PhysicsEngine physicsEngine;
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
        physicsEngine = new PhysicsEngineImpl(frameDurationMilli);

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
        level = new LevelImpl(firstLevelConfig, physicsEngine, entityFactoryRegistry, frameDurationMilli);
    }

    @Test
    public void testResolveCollisionBetweenDynamicAndStatic(){
        dynamicEntity1 = new DynamicEntityImpl(new KinematicStateImpl(new Vector2D(20, 20), new Vector2D(20, 20), 20),
                new AxisAlignedBoundingBoxImpl(new Vector2D(30, 30), 40, 40),
                Entity.Layer.FOREGROUND,
                null,
                new BallboyCollisionStrategy(level),
                new PassiveEntityBehaviourStrategy());

        dynamicEntity2 = new DynamicEntityImpl(new KinematicStateImpl(new Vector2D(120, 120), new Vector2D(120, 120), 20),
                new AxisAlignedBoundingBoxImpl(new Vector2D(60, 60), 40, 40),
                Entity.Layer.FOREGROUND,
                null,
                new BallboyCollisionStrategy(level),
                new PassiveEntityBehaviourStrategy());

        staticEntity = new StaticEntityImpl(new AxisAlignedBoundingBoxImpl(new Vector2D(30, 30), 50 , 50),
                Entity.Layer.FOREGROUND,
                null
                );

        physicsEngine.resolveCollision(dynamicEntity1, staticEntity, level);
        physicsEngine.resolveCollision(dynamicEntity2, staticEntity, level);
    }

    @Test
    public void testResolveCollisionBetweenDynamics(){
        dynamicEntity1 = new DynamicEntityImpl(new KinematicStateImpl(new Vector2D(20, 20), new Vector2D(20, 20), 20),
                new AxisAlignedBoundingBoxImpl(new Vector2D(30, 30), 40, 40),
                Entity.Layer.FOREGROUND,
                null,
                new BallboyCollisionStrategy(level),
                new PassiveEntityBehaviourStrategy());

        dynamicEntity2 = new DynamicEntityImpl(new KinematicStateImpl(new Vector2D(20, 20), new Vector2D(20, 20), 20),
                new AxisAlignedBoundingBoxImpl(new Vector2D(30, 30), 40, 40),
                Entity.Layer.FOREGROUND,
                null,
                new BallboyCollisionStrategy(level),
                new PassiveEntityBehaviourStrategy());

        dynamicEntity3 = new DynamicEntityImpl(new KinematicStateImpl(new Vector2D(120, 120), new Vector2D(120, 120), 20),
                new AxisAlignedBoundingBoxImpl(new Vector2D(30, 30), 40, 40),
                Entity.Layer.FOREGROUND,
                null,
                new BallboyCollisionStrategy(level),
                new PassiveEntityBehaviourStrategy());

        physicsEngine.resolveCollision(dynamicEntity1, dynamicEntity2);
        physicsEngine.resolveCollision(dynamicEntity1, dynamicEntity3);
    }

    @Test
    public void testEnforceWorldLimits(){

    }

}
