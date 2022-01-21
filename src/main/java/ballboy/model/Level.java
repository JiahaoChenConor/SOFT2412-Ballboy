package ballboy.model;

import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.Observers.LevelObserver;
import javafx.scene.paint.Color;

import java.awt.image.ColorModel;
import java.util.List;
import java.util.Map;

/**
 * The base interface for a Ballboy level.
 */
public interface Level {
    /**
     * Get the square cat
     * @return the object of square cat
     */
    DynamicEntity getSquareCat();

    /**
     * Clone the Level Observer Map
     * @return a cloned Map for level observer
     */
    Map<String, LevelObserver> cloneLevelObserverMap();

    /**
     * Get the Level Observer Map
     * @return a Map for level observer
     */
    Map<String, LevelObserver> getLevelObserverMap();

    /**
     * Set the Level Observer Map
     * @param map a new Map for level observer
     */
    void setLevelObserverMap(Map<String,LevelObserver> map);

    /**
     * Restore the deleted state if the entity is deleted
     */
    void restoreFinish();

    /**
     * Get the object of ballboy in this level
     * @return
     */
    Entity getBallboy();

    // ####Observer

    /**
     * Add a new color observer in the level
     * @param color the type of color
     * @param o corresponding observer
     */
    void attach(String color, LevelObserver o);

    /**
     * Delete a new color observer in the level
     * @param color the type of color
     * @param o corresponding observer
     */
    void detach(String color, LevelObserver o);

    /**
     * Notify the corresponding observer by color to increase score
     */
    void notifyObservers(String color);

    /**
     * Get score from the observer by color
     * @param color the color string
     * @return the scores corresponding to the color
     */
    int getScores(String color);
    // ####

    /**
     * Check the entity is square cat or not
     * @param entity the entity needs to be checked
     * @return if the entity is square cat, return true, else return false
     */
    boolean isSquareCat(Entity entity);

    /**
     * check whether this level is finished
     * @return if the level is finished, return true. Else return false
     */
    boolean getIsFinish();


    /**
     * Get next level
     * @return next level, if there is no next level return null
     */
    Level getNextLevel();

    /**
     * Set the next value
     */
    void setNextLevel(Level level);

    /**
     * Return a List of the currently existing Entities.
     *
     * @return The list of current entities for this level
     */
    List<Entity> getEntities();

    /**
     * The height of the level
     *
     * @return The height (should be in the same format as Entity sizes)
     */
    double getLevelHeight();

    /**
     * The width of the level
     *
     * @return The width (should be in the same format as Entity sizes)
     */
    double getLevelWidth();

    /**
     * @return double The height of the hero.
     */
    double getHeroHeight();

    /**
     * @return double The width of the hero.
     */
    double getHeroWidth();

    /**
     * @return double The vertical position of the floor.
     */
    double getFloorHeight();

    /**
     * @return Color The current configured color of the floor.
     */
    Color getFloorColor();

    /**
     * @return double The current level gravity.
     */
    double getGravity();

    /**
     * Instruct the level to progress forward in time by one increment.
     */
    void update();

    /**
     * The current x position of the hero. This is useful for views so they can follow the hero.
     *
     * @return The hero x position (should be in the same format as Entity sizes)
     */
    double getHeroX();

    /**
     * The current y position of the hero. This is useful for views so they can follow the hero.
     *
     * @return The hero y position (should be in the same format as Entity sizes)
     */
    double getHeroY();

    /**
     * Increase the height the bouncing hero can reach. This could be the vertical acceleration of the hero, unless
     * the current level has special behaviour.
     *
     * @return true if successful
     */
    boolean boostHeight();

    /**
     * Reduce the height the bouncing hero can reach. This could be the vertical acceleration of the hero, unless the
     * current level has special behaviour.
     *
     * @return true if successful
     */
    boolean dropHeight();

    /**
     * Move the hero left or accelerate the hero left, depending on the current level's desired behaviour
     *
     * @return true if successful
     */
    boolean moveLeft();

    /**
     * Move the hero right or accelerate the hero right, depending on the current level's desired behaviour
     *
     * @return true if successful
     */
    boolean moveRight();

    /**
     * @param entity The entity to be checked.
     * @return boolean True if the provided entity is the current hero.
     */
    boolean isHero(Entity entity);

    /**
     * @param entity The entity to be checked
     * @return boolean True if the provided entity is the finish of this level.
     */
    boolean isFinish(Entity entity);

    /**
     * Reset the hero into starting position
     */
    void resetHero();

    /**
     * Finishes the level.
     */
    void finish();
}
