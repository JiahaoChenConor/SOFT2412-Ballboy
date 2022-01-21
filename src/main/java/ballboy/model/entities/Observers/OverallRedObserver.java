package ballboy.model.entities.Observers;

public class OverallRedObserver implements OverallObserver{
    private int score;
    public OverallRedObserver(int score){
        this.score = score;
    }

    @Override
    public void updateOverall(int lastLevelScore) {
        score += lastLevelScore;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public OverallObserver cloneObserver() {
        return new OverallRedObserver(score);
    }
}
