package javafx;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class VBoxControlledOrphanage implements ViewOrphanage {
    private final VBox vBox;

    public VBoxControlledOrphanage(VBox vBox) {
        this.vBox = vBox;
    }

    @Override
    public void add(Node node) {
        vBox.getChildren().add(node);
    }
}
