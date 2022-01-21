package ballboy.view;

import ballboy.model.GameEngine;
import javafx.scene.layout.Pane;

public interface BackgroundDrawer {
    /**
     * Draw the background
     * @param model the object of GameEngine
     * @param pane the object of Pane
     */
    void draw(
            GameEngine model,
            Pane pane);

    /**
     * Update the background according to the position of ballBoy
     * @param xViewportOffset x offset
     * @param yViewportOffset y offset
     */
    void update(
            double xViewportOffset,
            double yViewportOffset);
}
