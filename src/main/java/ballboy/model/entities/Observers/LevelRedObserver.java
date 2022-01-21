package ballboy.model.entities.Observers;

import ballboy.model.Level;

public class LevelRedObserver implements LevelObserver {
    private int score;

    public LevelRedObserver(int score){
        this.score = score;
    }

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
        return new LevelRedObserver(score);
    }

    @Override
    public void reset() {
        score = 0;
    }
}
