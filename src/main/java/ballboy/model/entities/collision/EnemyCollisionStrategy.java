package ballboy.model.entities.collision;

import ballboy.model.Entity;
import ballboy.model.GameEngine;
import ballboy.model.Level;

/**
 * Collision logic for enemies.
 */
public class EnemyCollisionStrategy implements CollisionStrategy {
    private Level level;


    public EnemyCollisionStrategy(Level level) {
        this.level = level;
    }

    @Override
    public void collideWith(
            Entity enemy,
            Entity hitEntity) {
        if (!enemy.isDelete()){
            if (level.isHero(hitEntity)) {
                level.resetHero();
            }
            // CHANGE
            if (level.isSquareCat(hitEntity)){
                enemy.delete();
                // Observer
                level.notifyObservers(enemy.getColor());
                System.out.println(level.getScores(enemy.getColor()));
            }


        }

    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }
}
