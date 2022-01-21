package ballboy.model.entities;

import ballboy.model.Entity;
import ballboy.model.Memento.EntityState;
import ballboy.model.entities.behaviour.BehaviourStrategy;
import ballboy.model.entities.collision.CollisionStrategy;
import ballboy.model.entities.utilities.AxisAlignedBoundingBox;
import ballboy.model.entities.utilities.KinematicState;
import ballboy.model.entities.utilities.KinematicStateImpl;
import ballboy.model.entities.utilities.Vector2D;
import javafx.scene.image.Image;

public class DynamicEntityImpl extends DynamicEntity {
    private final CollisionStrategy collisionStrategy;
    private final BehaviourStrategy behaviourStrategy;
    private final AxisAlignedBoundingBox volume;
    private final Layer layer;
    private final Image image;
    private final KinematicState kinematicState;

    // CHANGE
    private final KinematicState originalKine;
    private double xOffset;
    private double yOffset;
    private int count;
    private boolean delete;
    private String color;

    @Override
    public EntityState cloneState() {
        EntityState es = new EntityState(kinematicState.getPosition().getX(),
                kinematicState.getPosition().getY(),
                delete
        );
        es.setCount(count);
        es.setXV(kinematicState.getVelocity().getX());
        es.setYV(kinematicState.getVelocity().getY());
        es.setXOffset(xOffset);
        es.setYOffset(yOffset);
        return es;

    }

    @Override
    public boolean reset() {
        this.kinematicState.setPosition(this.originalKine.getPosition());
        this.kinematicState.setVelocity(this.originalKine.getVelocity());
        this.xOffset = 0;
        this.yOffset = 0;
        this.count = 0;
        return false;
    }

    @Override
    public void setVelocity(double x, double y) {
        kinematicState.setVelocity(new Vector2D(x, y));
    }


    @Override
    public void setOffset(double x, double y) {
        this.xOffset = x;
        this.yOffset = y;
    }

    @Override
    public void setCount(int count){
        this.count = count;
    }
    // Observer
    @Override
    public String getColor(){
        return color;
    }

    @Override
    public void setColor(String color){
        this.color = color;
    }
    // ####

    @Override
    public void delete() {
        this.delete = true;
    }

    @Override
    public boolean isDelete() {
        return this.delete;
    }

    @Override
    public KinematicState getKinematicState(){
        return kinematicState;
    }



    public DynamicEntityImpl(
            KinematicState kinematicState,
            AxisAlignedBoundingBox volume,
            Layer layer,
            Image image,
            CollisionStrategy collisionStrategy,
            BehaviourStrategy behaviourStrategy
    ) {
        this.kinematicState = kinematicState;
        this.originalKine = kinematicState.cloneKine();
        this.volume = volume;
        this.layer = layer;
        this.image = image;
        this.collisionStrategy = collisionStrategy;
        this.behaviourStrategy = behaviourStrategy;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public Vector2D getPosition() {
        return kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D pos) {
        this.kinematicState.setPosition(pos);
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return this.kinematicState.getPreviousPosition();
    }

    @Override
    public Vector2D getVelocity() {
        return this.kinematicState.getVelocity();
    }

    @Override
    public void setVelocity(Vector2D vel) {
        this.kinematicState.setVelocity(vel);
    }

    @Override
    public double getHorizontalAcceleration() {
        return this.kinematicState.getHorizontalAcceleration();
    }

    @Override
    public void setHorizontalAcceleration(double horizontalAcceleration) {
        this.kinematicState.setHorizontalAcceleration(horizontalAcceleration);
    }

    @Override
    public double getHeight() {
        return volume.getHeight();
    }

    @Override
    public double getWidth() {
        return volume.getWidth();
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public boolean collidesWith(Entity entity) {
        return volume.collidesWith(entity.getVolume());
    }

    @Override
    public AxisAlignedBoundingBox getVolume() {
        return this.volume;
    }

    @Override
    public void setPosition(double x, double y) {
        setPosition(new Vector2D(x, y));
    }

    @Override
    public void restoreDelete() {
        this.delete = false;
    }

    @Override
    public CollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }

    @Override
    public BehaviourStrategy getBehaviourStrategy() {
        return behaviourStrategy;
    }

    @Override
    public void collideWith(Entity entity) {
        collisionStrategy.collideWith(this, entity);
    }

    @Override
    public void update(
            double milliSeconds,
            double levelGravity) {
        kinematicState.update(milliSeconds, levelGravity);
        behaviourStrategy.behave(this, milliSeconds);
        this.volume.setTopLeft(this.kinematicState.getPosition());
    }

    @Override
    public void orbited(DynamicEntity ballBoy){
        Vector2D ballBoyPos = ballBoy.getPosition();
        this.kinematicState.setPosition(new Vector2D(ballBoyPos.getX() - 40, ballBoyPos.getY() + 70));

        Vector2D curPos = this.kinematicState.getPosition();


        this.kinematicState.setPosition(new Vector2D(curPos.getX() + xOffset, curPos.getY() + yOffset));

        if (count % 400 < 100){
            yOffset--;
        }

        if (count % 400 >= 100 && count % 400 < 200){
            xOffset++;
        }

        if (count % 400 >= 200 && count % 400 < 300){
            yOffset++;
        }

        if (count % 400 >= 300){
            xOffset--;
        }
        count++;
    }
}
