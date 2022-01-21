package ballboy.model.entities.Observers;

public class OverallGreenObserver implements OverallObserver{

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
        return new OverallGreenObserver(score);
    }

    public OverallGreenObserver(int score){
        this.score = score;
    }
}
