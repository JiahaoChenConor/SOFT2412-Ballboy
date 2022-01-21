package ballboy.model;

import ballboy.model.Memento.GameEngineMemento;
import ballboy.model.entities.Observers.LevelObserver;
import ballboy.model.entities.Observers.OverallObserver;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * The base interface for interacting with the Ballboy model
 */
public interface GameEngine {
    /**
     * Clone the Level Observer Map
     * @param map the map need to be cloned
     * @return the cloned map
     */
    Map<String, LevelObserver> cloneLevelObserverMap( Map<String, LevelObserver> map);

    /**
     * Clone the Overall Observer Map
     * @param map the map need to be cloned
     * @return the cloned map
     */
    Map<String, OverallObserver> cloneOverallObserverMap( Map<String, OverallObserver> map);

    /**
     * Save the current state
     * @return the object of GameEngineMemento which contains the saved state
     */
    GameEngineMemento save();

    /**
     * Restore the game into the previous state
     * @param gameEngineMemento the state need to be restored
     */
    void restore(GameEngineMemento gameEngineMemento);

    /**
     * add an observer for total scores into the map
     * @param color the type of color
     * @param o the observer need to be added
     */
    void attach(String color, OverallObserver o);

    /**
     * delete an observer for total scores from the map
     * @param color the type of color
     * @param o the observer need to be removed
     */
    void detach(String color, OverallObserver o);


    /**
     * Notify the observer need to be updated
     * @param color the color type
     * @param lastLevelScore the scores will be added
     */
    void notifyObservers(String color, int lastLevelScore);


    /**
     * Get the score of one color
     * @param color the type of color
     * @return the score of one color
     */
    int getScores(String color);

    /**
     * Return the currently loaded level
     *
     * @return The current level
     */
    Level getCurrentLevel();

    /**
     * Start the new level if last level is finished
     */
    void startLevel();

    /**
     * Increases the bounce height of the current hero.
     *
     * @return boolean True if the bounce height of the hero was successfully boosted.
     */
    boolean boostHeight();

    /**
     * Reduces the bounce height of the current hero.
     *
     * @return boolean True if the bounce height of the hero was successfully dropped.
     */
    boolean dropHeight();

    /**
     * Applies a left movement to the current hero.
     *
     * @return True if the hero was successfully moved left.
     */
    boolean moveLeft();

    /**
     * Applies a right movement to the current hero.
     *
     * @return True if the hero was successfully moved right.
     */
    boolean moveRight();

    /**
     * Instruct the model to progress forward in time by one increment.
     */
    void tick();
}
