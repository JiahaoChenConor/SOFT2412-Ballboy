package ballboy.view;

import ballboy.model.Entity;
import ballboy.model.GameEngine;
import ballboy.model.Level;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameWindow {
    private static final double VIEWPORT_MARGIN_X = 100;
    private static final double VIEWPORT_MARGIN_Y = 50;
    private final int width;
    private final int height;
    private final double frameDurationMilli;
    private final Scene scene;
    private final Pane pane;
    private final GameEngine model;
    private final List<EntityView> entityViews;
    private final BackgroundDrawer backgroundDrawer;
    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private int limit;
    private final GraphicsContext gc;

    /**
     * The constructor of the GameWindow
     * @param model the object of GameEngine
     * @param width the width of the game window
     * @param height the height of the game window
     */
    public GameWindow(
            GameEngine model,
            int width,
            int height,
            double frameDurationMilli) {
        this.model = model;
        this.width = width;
        this.height = height;
        this.frameDurationMilli = frameDurationMilli;
        pane = new Pane();
//        txt = new TextArea();
//        txt.setPrefHeight(50);
//        txt.setPrefWidth(width);
//        txt.setText("Current Level Score                  Red: 0, Green: 0, Blue: 0\n" +
//                    "Overall scores for previous levels   Red: 0, Green: 0, Blue: 0");
//        txt.setEditable(false);
//        txt.setMouseTransparent(true);
//        txt.setFocusTraversable(false);
//        pane.getChildren().add(txt);
        scene = new Scene(pane, width, height);
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Comic Sans MS", 15));
        pane.getChildren().add(canvas);

        gc.clearRect(0, 0, width, height);
        gc.setFill(Paint.valueOf("BLACK"));
//        gc.fillText(
//                "Current Level Score                  Red: 0, Green: 0, Blue: 0\n" +
//                        "Overall scores for previous levels   Red: 0, Green: 0, Blue: 0",
//                320,
//                50
//        );

        entityViews = new ArrayList<>();

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        backgroundDrawer = new BlockedBackground();
        backgroundDrawer.draw(model, pane);


    }

    /**
     * Return the object of Scene in game window
     * @return the object of Scene in game window
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Run the game includes play the animation indefinitely
     */
    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(frameDurationMilli),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    /**
     * Render the entity and hero in the current layer
     *
     * 1. stipulate that the hero cannot exceed the boundary
     * 2. delete some entities which may be destroyed by the player
     */
    private void draw() {
        if (limit > 40){
            System.exit(0);
        }
        if (model.getCurrentLevel() == null){
                limit ++;
                Image image = null;
                try {
                    image = new Image(new FileInputStream("src/main/resources/Winner.png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //Setting the image view
                ImageView imageView = new ImageView(image);

                //Setting the position of the image
                imageView.setX(50);
                imageView.setY(25);

                //setting the fit height and width of the image view
                imageView.setFitHeight(455);
                imageView.setFitWidth(500);

                //Setting the preserve ratio of the image view
                imageView.setPreserveRatio(true);

                pane.getChildren().add(imageView);

        }
        model.tick();
        model.startLevel();
        Level curLevel = model.getCurrentLevel();

        if (curLevel != null){
            StringBuilder sb = new StringBuilder();
            sb.append("Current Level Score:        ");

            if (curLevel.getLevelObserverMap().containsKey("red")){
                sb.append(" Red: ").append(curLevel.getScores("red"));
            }
            if (curLevel.getLevelObserverMap().containsKey("green")){
                sb.append(" Green: ").append(curLevel.getScores("green"));
            }
            if (curLevel.getLevelObserverMap().containsKey("blue")){
                sb.append(" Blue: ").append(curLevel.getScores("blue"));
            }

            sb.append("\n");

            sb.append(String.format("Overall scores for previous levels   Red: %d, Green: %d, Blue: %d",
                    model.getScores("red"), model.getScores("green"),model.getScores("blue")));


            gc.clearRect(0, 0, width, height);
            gc.setFill(Paint.valueOf("BLACK"));
            gc.fillText(
                    sb.toString(),
                    320,
                    50
            );

            List<Entity> entities = model.getCurrentLevel().getEntities();

            for (EntityView entityView : entityViews) {
                entityView.markForDelete();
            }


            double heroXPos = model.getCurrentLevel().getHeroX();
            double viewportLeftBar = xViewportOffset + VIEWPORT_MARGIN_X;
            double viewportRightBar = viewportLeftBar + (width - 2 * VIEWPORT_MARGIN_X);

            if (heroXPos < viewportLeftBar) {
                xViewportOffset -= heroXPos - viewportLeftBar;
            } else if (heroXPos + model.getCurrentLevel().getHeroWidth() > viewportRightBar) {
                xViewportOffset += heroXPos + model.getCurrentLevel().getHeroWidth() - viewportRightBar;
            }

            heroXPos -= xViewportOffset;

            if (heroXPos < VIEWPORT_MARGIN_X) {
                if (xViewportOffset >= 0) { // Don't go further left than the start of the level
                    xViewportOffset -= VIEWPORT_MARGIN_X - heroXPos;
                    if (xViewportOffset < 0) {
                        xViewportOffset = 0;
                    }
                }
            }

            double levelRight = model.getCurrentLevel().getLevelWidth();
            double screenRight = xViewportOffset + width - model.getCurrentLevel().getHeroWidth();
            if (screenRight > levelRight) {
                xViewportOffset = levelRight - width + model.getCurrentLevel().getHeroWidth();
            }


            double levelTop = 0.0;
            double levelBottom = model.getCurrentLevel().getLevelHeight();
            double heroYPos = model.getCurrentLevel().getHeroY();
            double heroHeight = model.getCurrentLevel().getHeroHeight();
            double viewportTop = yViewportOffset + VIEWPORT_MARGIN_Y;
            double viewportBottom = yViewportOffset + height - 2 * VIEWPORT_MARGIN_Y;

            if (heroYPos + heroHeight > viewportBottom) {
                // if below, shift down
                yViewportOffset += heroYPos + heroHeight - viewportBottom;
            } else if (heroYPos < viewportTop) {
                // if above, shift up
                yViewportOffset -= viewportTop - heroYPos;
            }

            double screenBottom = yViewportOffset + height;
            double screenTop = yViewportOffset;
            // shift back in the instance when we're near the boundary
            if (screenBottom > levelBottom) {
                yViewportOffset -= screenBottom - levelBottom;
            } else if (screenTop < 0.0) {
                yViewportOffset -= screenTop;
            }

            backgroundDrawer.update(xViewportOffset, yViewportOffset);


//        double viewportBottomBar = yViewportOffset + height - VIEWPORT_MARGIN_Y;
//        double viewportTopBar = yViewportOffset + VIEWPORT_MARGIN_Y;
//
//        if (heroYPos + model.getCurrentLevel().getHeroHeight() > viewportBottomBar) {
//            yViewportOffset += (heroYPos + model.getCurrentLevel().getHeroHeight()) - viewportBottomBar;
//        } else if (heroYPos < viewportTopBar) {
//            yViewportOffset -= viewportTopBar - heroYPos;
//        }
//
//        heroYPos -= yViewportOffset;
//
//        if (heroYPos > VIEWPORT_MARGIN_Y) {
//            if (yViewportOffset >= 0) { // avoid going further than bottom of the screen
//                yViewportOffset -= heroYPos - VIEWPORT_MARGIN_Y;
//                if (yViewportOffset < 0) {
//                    yViewportOffset = 0;
//                }
//            }
//        }





            for (Entity entity : entities) {
                //CHANGE
                if (entity.isDelete()){
                    continue;
                }
                //CHANGE
                boolean notFound = true;
                for (EntityView view : entityViews) {
                    if (view.matchesEntity(entity)) {
                        notFound = false;
                        view.update(xViewportOffset, yViewportOffset);
                        break;
                    }
                }
                if (notFound) {
                    EntityView entityView = new EntityViewImpl(entity);
                    entityViews.add(entityView);
                    pane.getChildren().add(entityView.getNode());
                }
            }

            for (EntityView entityView : entityViews) {
                if (entityView.isMarkedForDelete()) {
                    pane.getChildren().remove(entityView.getNode());
                }
            }
            entityViews.removeIf(EntityView::isMarkedForDelete);
        }

    }

}
