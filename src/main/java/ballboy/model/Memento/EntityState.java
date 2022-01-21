package ballboy.model.Memento;

import ballboy.model.Level;

public class EntityState {
    private double x;
    private double y;
    private double xV;
    private double yV;
    private double xOffset;
    private double yOffset;
    private boolean isDelete;
    private int count;
    private Level level;

    public EntityState(double x, double y, boolean isDelete){
        this.x = x;
        this.y = y;
        this.isDelete = isDelete;
    }

    /**
     * Set the entity x velocity
     * @param v x velocity
     */
    public void setXV(double v){
        xV = v;
    }

    /**
     * Set the entity y velocity
     * @param v y velocity
     */
    public void setYV(double v){
        yV = v;
    }

    /**
     * Get the entity x velocity
     * @return the x velocity
     */
    public double getXV(){
        return xV;
    }

    /**
     * Get the entity y velocity
     * @return the y velocity
     */
    public double getYV(){
        return yV;
    }

    /**
     * Set the entity x offset
     * @param offset x offset
     */
    public void setXOffset(double offset){
        xOffset = offset;
    }

    /**
     * Set the entity y offset
     * @param offset y offset
     */
    public void setYOffset(double offset){
        yOffset = offset;
    }

    /**
     * Get the x offset
     * @return x offset
     */
    public double getXOffset(){
        return xOffset;
    }

    /**
     * Get the y offset
     * @return y offset
     */
    public double getYOffset(){
        return yOffset;
    }


    /**
     * Set the count
     * @param count the time counter
     */
    public void setCount(int count){
        this.count = count;
    }

    /**
     * Get the count
     * @return get the time counter
     */
    public int getCount(){
        return count;
    }


    /**
     * Get x position
     * @return x position
     */
    public double getX() {
        return x;
    }

    /**
     * Set x position
     * @param x the new x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get y position
     * @return y position
     */
    public double getY() {
        return y;
    }

    /**
     * Set y position
     * @param y the new x position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Check the entity is deleted or not
     * @return if the entity is deleted, return true. Otherwise, return false
     */
    public boolean isDelete() {
        return isDelete;
    }

    /**
     * Set the state of deleted or not
     * @param delete if true, restore the state.
     */
    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    /**
     * Set the level of entity
     * @param level the new level for the entity
     */
    public void setLevel(Level level){
        this.level = level;
    }

    /**
     * Get the level of entity
     * @return the level of entity
     */
    public Level getLevel(){
        return level;
    }
}
