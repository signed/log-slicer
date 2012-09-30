package javafx;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import lang.ArgumentClosure;

public class BorderPaneControlledOrphanage implements ViewOrphanage {
    private final BorderPane borderPane;
    private ArgumentClosure<Node> nextNode = new ArgumentClosure<Node>() {
        @Override
        public void excecute(Node node) {
            borderPane.getChildren().add(node);
        }
    };

    public BorderPaneControlledOrphanage(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    @Override
    public void add(Node node) {
        nextNode.excecute(node);
    }

    public void nextAtCenter() {
        nextNode = new ArgumentClosure<Node>() {
            @Override
            public void excecute(Node node) {
                borderPane.setCenter(node);
            }
        };
    }

    public void nextLeft(){
        nextNode = new ArgumentClosure<Node>() {
            @Override
            public void excecute(Node node) {
                borderPane.setLeft(node);
            }
        };
    }

    public void nextRight() {
        nextNode = new ArgumentClosure<Node>() {
            @Override
            public void excecute(Node node) {
                borderPane.setRight(node);
            }
        };
    }
}
