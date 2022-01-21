package ballboy.model.entities.Observers;

public class OverallBlueObserver implements OverallObserver{
    private int score;

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
        return new OverallBlueObserver(score);
    }

    public OverallBlueObserver(int score){
        this.score = score;
    }
}
