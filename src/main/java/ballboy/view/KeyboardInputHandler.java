package ballboy.view;

import ballboy.model.GameEngine;
import ballboy.model.Memento.GameEngineMemento;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

class KeyboardInputHandler {
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private GameEngineMemento snapshot;

//    private Map<String, MediaPlayer> sounds = new HashMap<>();

    /**
     * The constructor of the KeyboardInputHandler
     * @param model the object of the game engine
     */
    KeyboardInputHandler(GameEngine model) {
        this.model = model;

        // TODO (longGoneUser): Is there a better place for this code?
        // TODO (bobbob): Move sound choice/production into the model before alpha is released to the new devs
        // TODO (bobbob): Just commenting this out while I debug my driver - don't forget to revert this before anyone
        // else sees this
//        URL mediaUrl = getClass().getResource("/jump.wav");
//        String jumpURL = mediaUrl.toExternalForm();
//
//        Media sound = new Media(jumpURL);
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        sounds.put("jump", mediaPlayer);
    }

    /**
     * When the player presses a key on the keyboard,
     * the behavior and coordinates and background of the character are updated according to the key.
     * @param keyEvent the keyEvent from the keyboard
     */
    void handlePressed(KeyEvent keyEvent) {
        if (model.getCurrentLevel() != null){
            if (pressedKeys.contains(keyEvent.getCode())) {
                return;
            }
            pressedKeys.add(keyEvent.getCode());

            if (keyEvent.getCode().equals(KeyCode.UP)) {
                if (model.boostHeight()) {
//                MediaPlayer jumpPlayer = sounds.get("jump");
//                jumpPlayer.stop();
//                jumpPlayer.play();
                }
            }

            if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                left = true;
            } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                right = true;
            } else if (keyEvent.getCode().equals(KeyCode.Q)) {
                System.out.println("q");
                if (snapshot != null){
                    model.restore(snapshot);
                }
            }else if (keyEvent.getCode().equals(KeyCode.S)){
                System.out.println("s");
                snapshot = model.save();

            }


            if (left) {
                model.moveLeft();
            } else {
                model.moveRight();
            }
        }

    }

    /**
     * When the player releases a key on the keyboard,
     * the behavior and coordinates and background of the character are updated according to the key.
     * @param keyEvent the keyEvent from the keyboard
     */
    void handleReleased(KeyEvent keyEvent) {
        if (model.getCurrentLevel() != null){
            pressedKeys.remove(keyEvent.getCode());

            if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                left = false;
            } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                right = false;
            } else {
                return;
            }

            if (!(right || left)) {
                model.dropHeight();
            } else if (right) {
                model.moveRight();
            } else {
                model.moveLeft();
            }
        }

    }
}
