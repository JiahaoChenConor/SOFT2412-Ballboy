package ballboy.model.entities.utilities;

/**
 * Utility object for 2D coordinates.
 * <p>
 * All state is immutable.
 */
public class Vector2D {
    public static final Vector2D ZERO = new Vector2D(0, 0);

    private final double x;
    private final double y;

    public Vector2D(
            double x,
            double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Set the x
     * @param x x
     * @return the new Vector contains new x
     */
    public Vector2D setX(double x) {
        return new Vector2D(x, this.y);
    }

    /**
     * Get the y
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set the y
     * @param y y
     * @return the new Vector contains new y
     */
    public Vector2D setY(double y) {
        return new Vector2D(this.x, y);
    }

    /**
     * change y into inverse number
     * @return new Vector contains -y
     */
    public Vector2D reflectY() {
        return new Vector2D(this.x, -this.y);
    }

    /**
     * change x into inverse number
     * @return new Vector contains -x
     */
    public Vector2D reflectX() {
        return new Vector2D(-this.x, this.y);
    }

    /**
     * Add two vectors
     * @param v the one be added
     * @return the new vector
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.getX(), this.y + v.getY());
    }

    /**
     * Add current vector in x direction
     * @param x the x be added
     * @return the new vector
     */
    public Vector2D addX(double x) {
        return new Vector2D(this.x + x, this.y);
    }

    /**
     * Add current vector in y direction
     * @param y the y be added
     * @return the new vector
     */
    public Vector2D addY(double y) {
        return new Vector2D(this.x, this.y + y);
    }

    /**
     * Scale the current vector
     * @param scale the scale rate
     * @return new vector after scaling
     */
    public Vector2D scale(double scale) {
        return new Vector2D(this.x * scale, this.y * scale);
    }

    /**
     * check the x of vector is less than new x or not
     * @param x new x
     * @return if the x is less than new x, return true. Otherwise, return false
     */
    public boolean isLeftOf(double x) {
        return this.x < x;
    }

    /**
     * check the x of vector is greater than new x or not
     * @param x new x
     * @return if the x is greater than new x, return true. Otherwise, return false
     */
    public boolean isRightOf(double x) {
        return this.x > x;
    }

    /**
     * check the y of vector is less than new y or not
     * @param y new y
     * @return if the y is less than new y, return true. Otherwise, return false
     */
    public boolean isAbove(double y) {
        return this.y < y;
    }

    /**
     * check the y of vector is greater than new y or not
     * @param y new y
     * @return if the y is greater than new y, return true. Otherwise, return false
     */
    public boolean isBelow(double y) {
        return this.y > y;
    }
}
