package javafx;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lang.ArgumentClosure;

public class HBoxControlledOrphanage implements ViewOrphanage {

    private final HBox hBox;
    private ArgumentClosure<Node> strategy = new ArgumentClosure<Node>() {
        @Override
        public void excecute(Node node) {
            hBox.getChildren().add(node);
        }
    };

    public HBoxControlledOrphanage(HBox hBox) {
        this.hBox = hBox;
    }

    public void nextGrabHorizontalSpace(final Priority priority){
        this.strategy = new ArgumentClosure<Node>(){
            @Override
            public void excecute(Node node) {
                HBox.setHgrow(node, priority);
                hBox.getChildren().add(node);
            }
        };
    }

    @Override
    public void add(Node node) {
        this.strategy.excecute(node);
    }
}
