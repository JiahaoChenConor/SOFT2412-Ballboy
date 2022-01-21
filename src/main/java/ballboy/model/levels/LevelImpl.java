package ballboy.model.levels;

import ballboy.ConfigurationParseException;
import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.ControllableDynamicEntity;
import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.Observers.LevelBlueObserver;
import ballboy.model.entities.Observers.LevelGreenObserver;
import ballboy.model.entities.Observers.LevelObserver;
import ballboy.model.entities.Observers.LevelRedObserver;
import ballboy.model.entities.StaticEntity;
import ballboy.model.entities.utilities.Vector2D;
import ballboy.model.factories.EntityFactory;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.charset.CoderResult;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Level logic, with abstract factor methods.
 */
public class LevelImpl implements Level {

    private final List<Entity> entities = new ArrayList<>();
    private final PhysicsEngine engine;
    private final EntityFactory entityFactory;
    private ControllableDynamicEntity<DynamicEntity> hero;
    private Entity finish;
    private double levelHeight;
    private double levelWidth;
    private double levelGravity;
    private double floorHeight;
    private Color floorColor;
    // CHANGE
    private Level nextLevel;
    private boolean isFinish;
    private DynamicEntity squareCat;

    @Override
    public DynamicEntity getSquareCat(){
        return squareCat;
    }
    @Override
    public Entity getBallboy(){
        return hero;
    }

    @Override
    public Level getNextLevel(){
        return nextLevel;
    }

    @Override
    public void setNextLevel(Level level){
        nextLevel = level;
    }

    @Override
    public boolean getIsFinish() {
        return isFinish;
    }

    // Observer
    // When init the level, we can know how many different types of enemies in this level and add observers
    private Map<String, LevelObserver> levelObserverMap = new HashMap<>();
    @Override
    public Map<String, LevelObserver> getLevelObserverMap(){
        return levelObserverMap;
    }

    @Override
    public void setLevelObserverMap(Map<String, LevelObserver> map){
        this.levelObserverMap = map;
    }

    @Override
    public Map<String, LevelObserver> cloneLevelObserverMap(){
        Map<String, LevelObserver> clonedMap = new HashMap<>();
        if (levelObserverMap.containsKey("red")){
            clonedMap.put("red", levelObserverMap.get("red").cloneObserver());
        }

        if (levelObserverMap.containsKey("green")){
            clonedMap.put("green", levelObserverMap.get("green").cloneObserver());
        }

        if (levelObserverMap.containsKey("blue")){
            clonedMap.put("blue", levelObserverMap.get("blue").cloneObserver());
        }


        return clonedMap;
    }

    @Override
    public void attach(String color, LevelObserver o) {
        levelObserverMap.put(color, o);
    }

    @Override
    public void detach(String color, LevelObserver o) {
        levelObserverMap.remove(color, o);
    }

    @Override
    public void notifyObservers(String color) {
        levelObserverMap.get(color).increase();
    }

    @Override
    public int getScores(String color) {

        return levelObserverMap.get(color).getScore();
    }


    // Observer

    @Override
    public boolean isSquareCat(Entity entity) {
        return this.squareCat == entity;
    }

    // CHANGE

    private final double frameDurationMilli;

    /**
     * A callback queue for post-update jobs. This is specifically useful for scheduling jobs mid-update
     * that require the level to be in a valid state.
     */
    private final Queue<Runnable> afterUpdateJobQueue = new ArrayDeque<>();

    public LevelImpl(
            JSONObject levelConfiguration,
            PhysicsEngine engine,
            EntityFactory entityFactory,
            double frameDurationMilli) {
        this.engine = engine;
        this.entityFactory = entityFactory;
        this.frameDurationMilli = frameDurationMilli;
        initLevel(levelConfiguration);
    }

    /**
     * Instantiates a level from the level configuration.
     *
     * @param levelConfiguration The configuration for the level.
     */
    private void initLevel(JSONObject levelConfiguration) {
        if (levelConfiguration == null){
            return;
        }

        // Observer###
        JSONArray colors = (JSONArray) levelConfiguration.get("scoreColors");
        for (Object color: colors){
            String colorString = (String) color;
            if (colorString.equalsIgnoreCase("red")){
                this.attach(colorString, new LevelRedObserver(0));
            }else if (colorString.equalsIgnoreCase("blue")){
                this.attach(colorString, new LevelBlueObserver(0));
            }else if (colorString.equalsIgnoreCase("green")){
                this.attach(colorString, new LevelGreenObserver(0));
            }
            // if there are some other colors, we need to edit this
        }


        this.levelWidth = ((Number) levelConfiguration.get("levelWidth")).doubleValue();
        this.levelHeight = ((Number) levelConfiguration.get("levelHeight")).doubleValue();
        this.levelGravity = ((Number) levelConfiguration.get("levelGravity")).doubleValue();

        JSONObject floorJson = (JSONObject) levelConfiguration.get("floor");
        this.floorHeight = ((Number) floorJson.get("height")).doubleValue();
        String floorColorWeb = (String) floorJson.get("color");
        this.floorColor = Color.web(floorColorWeb);

        JSONArray generalEntities = (JSONArray) levelConfiguration.get("genericEntities");
        for (Object o : generalEntities) {
            Entity newEntity = entityFactory.createEntity(this, (JSONObject) o);
            this.entities.add(newEntity);
            JSONObject configObject = (JSONObject) o;
            String type = (String) configObject.get("type");
            if (type != null && type.equalsIgnoreCase("enemy")){
                String image = (String) configObject.get("image");
                String color = null;
                if (image == null || image.equals("slimeRa.png") || image.equals("slimeRb.png")){
                    color = "red";
                } else if (image.equals("slimeBa.png") || image.equals("slimeBb.png")){
                    color = "blue";
                } else if (image.equals("slimeGa.png") || image.equals("slimeGb.png")){
                    color = "green";
                }
                newEntity.setColor(color);
            }
        }

        JSONObject heroConfig = (JSONObject) levelConfiguration.get("hero");
        double maxVelX = ((Number) levelConfiguration.get("maxHeroVelocityX")).doubleValue();

        Object hero = entityFactory.createEntity(this, heroConfig);
        if (!(hero instanceof DynamicEntity)) {
            throw new ConfigurationParseException("hero must be a dynamic entity");
        }
        DynamicEntity dynamicHero = (DynamicEntity) hero;
        Vector2D heroStartingPosition = dynamicHero.getPosition();
        this.hero = new ControllableDynamicEntity<>(dynamicHero, heroStartingPosition, maxVelX, floorHeight,
                levelGravity);
        this.entities.add(this.hero);

        JSONObject finishConfig = (JSONObject) levelConfiguration.get("finish");
        this.finish = entityFactory.createEntity(this, finishConfig);
        this.entities.add(finish);

        // Square Cat
        JSONObject squareCatConfig = (JSONObject) levelConfiguration.get("squareCat");
        Entity squareCat = entityFactory.createEntity(this, squareCatConfig);
        if (!(squareCat instanceof DynamicEntity)){
            throw new ConfigurationParseException("square cat must be a dynamic entity");
        }

        this.squareCat = (DynamicEntity) squareCat;

        this.entities.add(squareCat);




        //#######3
    }

    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    private List<DynamicEntity> getDynamicEntities() {
        return entities.stream().filter(e -> e instanceof DynamicEntity).map(e -> (DynamicEntity) e).collect(
                Collectors.toList());
    }

    private List<StaticEntity> getStaticEntities() {
        return entities.stream().filter(e -> e instanceof StaticEntity).map(e -> (StaticEntity) e).collect(
                Collectors.toList());
    }

    @Override
    public double getLevelHeight() {
        return this.levelHeight;
    }

    @Override
    public double getLevelWidth() {
        return this.levelWidth;
    }

    @Override
    public double getHeroHeight() {
        return hero.getHeight();
    }

    @Override
    public double getHeroWidth() {
        return hero.getWidth();
    }

    @Override
    public double getFloorHeight() {
        return floorHeight;
    }

    @Override
    public Color getFloorColor() {
        return floorColor;
    }

    @Override
    public double getGravity() {
        return levelGravity;
    }

    @Override
    public void update() {
        List<DynamicEntity> dynamicEntities = getDynamicEntities();

        dynamicEntities.stream().forEach(e -> {
            e.update(frameDurationMilli, levelGravity);
        });

        for (int i = 0; i < dynamicEntities.size(); ++i) {
            DynamicEntity dynamicEntityA = dynamicEntities.get(i);

            for (int j = i + 1; j < dynamicEntities.size(); ++j) {
                DynamicEntity dynamicEntityB = dynamicEntities.get(j);

                if (dynamicEntityA.collidesWith(dynamicEntityB)) {
                    dynamicEntityA.collideWith(dynamicEntityB);
                    dynamicEntityB.collideWith(dynamicEntityA);
                    if (!isHero(dynamicEntityA) && !isHero(dynamicEntityB)) {
                        engine.resolveCollision(dynamicEntityA, dynamicEntityB);
                    }
                }
            }

            for (StaticEntity staticEntity : getStaticEntities()) {
                if (dynamicEntityA.collidesWith(staticEntity)) {
                    dynamicEntityA.collideWith(staticEntity);
                    engine.resolveCollision(dynamicEntityA, staticEntity, this);
                }
            }
        }

        dynamicEntities.stream().forEach(e -> engine.enforceWorldLimits(e, this));

        afterUpdateJobQueue.forEach(j -> j.run());
        afterUpdateJobQueue.clear();

    }

    @Override
    public double getHeroX() {
        return hero.getPosition().getX();
    }

    @Override
    public double getHeroY() {
        return hero.getPosition().getY();
    }

    @Override
    public boolean boostHeight() {
        return hero.boostHeight();
    }

    @Override
    public boolean dropHeight() {
        return hero.dropHeight();
    }

    @Override
    public boolean moveLeft() {
        return hero.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return hero.moveRight();
    }

    @Override
    public boolean isHero(Entity entity) {
        return entity == hero;
    }

    @Override
    public boolean isFinish(Entity entity) {
        return this.finish == entity;
    }

    @Override
    public void resetHero() {
        afterUpdateJobQueue.add(() -> this.hero.reset());
    }

    @Override
    public void finish() {
        isFinish = true;
    }

    @Override
    public void restoreFinish(){
        isFinish = false;
    }


}
