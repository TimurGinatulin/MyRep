package sample.Controllers;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class Shake {
    private final TranslateTransition tt;

    public Shake(Node node) {
        tt = new TranslateTransition(Duration.millis(80), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(10);
        tt.setAutoReverse(true);
    }

    public void playAnimation() {
        tt.playFromStart();
    }
}
