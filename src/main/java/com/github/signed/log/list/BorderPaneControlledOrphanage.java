package com.github.signed.log.list;

import com.github.signed.log.ViewOrphanage;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class BorderPaneControlledOrphanage implements ViewOrphanage {
    private final BorderPane borderPane;

    public BorderPaneControlledOrphanage(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    @Override
    public void add(Node node) {
        borderPane.setTop(node);
    }

    public void nextAtTop() {
        //To change body of created methods use File | Settings | File Templates.
    }
}
