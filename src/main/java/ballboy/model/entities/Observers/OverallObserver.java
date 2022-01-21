package ballboy.model.entities.Observers;

public interface OverallObserver {
    /**
     * increase the score by the score in last finished level
     * @param lastLevelScore the score in last finished level
     */
    void updateOverall(int lastLevelScore);

    /**
     * Get the score in this observer
     * @return the score in this observer
     */
    int getScore();


    /**
     * Clone the observer
     * @return the cloned observer
     */
    OverallObserver cloneObserver();
}
