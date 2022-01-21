package ballboy.model.entities.Observers;

import ballboy.model.Level;


// for the level observer, the subject is Level
public interface LevelObserver {
    /**
     * Increase the score by 1
     */
    void increase();

    /**
     * Get the score in this Level observer
     * @return the score in this level obserber
     */
    int getScore();

    /**
     * Clone the observer
     * @return the cloned observer
     */
    LevelObserver cloneObserver();

    /**
     * Reset the score
     */
    void reset();
}
