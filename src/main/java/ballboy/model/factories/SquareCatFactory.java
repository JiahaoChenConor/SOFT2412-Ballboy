package ballboy.model.factories;

import ballboy.ConfigurationParseException;
import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.DynamicEntityImpl;
import ballboy.model.entities.behaviour.PassiveEntityBehaviourStrategy;
import ballboy.model.entities.behaviour.SquareCatBehaviour;
import ballboy.model.entities.collision.BallboyCollisionStrategy;
import ballboy.model.entities.collision.PassiveCollisionStrategy;
import ballboy.model.entities.utilities.*;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

public class SquareCatFactory implements EntityFactory{

    @Override
    public Entity createEntity(
            Level level,
            JSONObject config) {
        try {
            double startX = ((Number) config.get("startX")).doubleValue();
            double startY = ((Number) config.get("startY")).doubleValue();

            String imageName = (String) config.getOrDefault("image", "squareCat.png");

            double height = 20;

            Image image = new Image(imageName);
            // preserve image ratio
            double width = 20;

            Vector2D startingPosition = new Vector2D(startX, startY);

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(startingPosition)
                    .build();

            AxisAlignedBoundingBox volume = new AxisAlignedBoundingBoxImpl(
                    startingPosition,
                    height,
                    width
            );

            return new DynamicEntityImpl(
                    kinematicState,
                    volume,
                    Entity.Layer.EFFECT,
                    new Image(imageName),
                    new PassiveCollisionStrategy(),
                    new SquareCatBehaviour(level)
            );

        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid Square cat entity configuration | %s | %s", config, e));
        }
    }
}
