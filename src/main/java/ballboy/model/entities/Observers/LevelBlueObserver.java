package ballboy.model.entities.Observers;


public class LevelBlueObserver implements LevelObserver {
    private int score;
    @Override
    public void increase() {
        score++;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public LevelObserver cloneObserver() {
        return new LevelGreenObserver(score);
    }

    @Override
    public void reset() {
        score = 0;
    }

    public LevelBlueObserver(int score){
        this.score = score;
    }

}

