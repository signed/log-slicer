package com.github.signed.log.filter;

import com.github.signed.log.core.LogPart;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.OrphanView;
import javafx.ViewOrphanage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterView implements OrphanView {
    private final VBox vbox = new VBox();
    private ComboBox<LogPart> availableLogParts;
    private final FlowPane selectedFilterContainer = new FlowPane();
    private final Announcer<ArgumentClosure> discardFilterListeners = new Announcer<>(ArgumentClosure.class);
    private final Announcer<ArgumentClosure> selectionListener = new Announcer<>(ArgumentClosure.class);
    private final String promptText;

    public LogPartFilterView(String name) {
        this.promptText = name;
        vbox.getChildren().addAll(new Label("just a placeholder"), selectedFilterContainer);
        vbox.setMaxWidth(250);
    }


    public void displayAvailableLogParts(List<LogPart> parts) {
        reCreateComboBox(this.promptText);
        availableLogParts.setItems(new ObservableListWrapper<>(parts));
    }

    public void onSelection(final ArgumentClosure<LogPart> closure) {
        selectionListener.addListener(closure);
    }

    public void displaySelectedFilter(final List<LogPart> whiteListedParts) {
        selectedFilterContainer.getChildren().clear();

        for (final LogPart logPart : whiteListedParts) {
            Label label = createLabelFor(logPart);
            selectedFilterContainer.getChildren().add(label);
        }
    }

    private void reCreateComboBox(String promptText){
        createComboBox(promptText);
        vbox.getChildren().remove(0);
        vbox.getChildren().add(0, availableLogParts);
    }

    public void onDiscardFilter(ArgumentClosure<LogPart> argumentClosure) {
        discardFilterListeners.addListener(argumentClosure);
    }

    private void createComboBox(String promptText) {
        availableLogParts = new ComboBox<>();
        availableLogParts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LogPart>() {
            @Override
            public void changed(ObservableValue<? extends LogPart> observableValue, LogPart oldLogPart, LogPart newLogPart) {
                selectionListener.announce().excecute(newLogPart);
            }
        });

        availableLogParts.setPromptText(promptText);
        availableLogParts.setCellFactory(new Callback<ListView<LogPart>, ListCell<LogPart>>() {
            @Override
            public ListCell<LogPart> call(ListView<LogPart> logPartListView) {
                return new ListCell<LogPart>() {
                    @Override
                    protected void updateItem(LogPart part, boolean b) {
                        super.updateItem(part, b);
                        if (null == part) {
                            setText("null");
                            return;
                        }
                        StringBuilder builder = new StringBuilder();
                        part.dumpInto(builder);
                        setText(builder.toString());
                    }
                };
            }
        });
    }

    @Override
    public void addTo(ViewOrphanage viewOrphanage) {
        viewOrphanage.add(vbox);
    }

    private Label createLabelFor(final LogPart part) {
        Label label = new Label(part.toString());
        label.getStyleClass().add("filter-predicate");
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                discardFilterListeners.announce().excecute(part);
            }
        });
        return label;
    }
}
