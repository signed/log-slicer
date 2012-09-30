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
    private ComboBox<LogPart> availableThreads;
    private final FlowPane selectedFilterContainer = new FlowPane();
    private final Announcer<ArgumentClosure> discardFilterListeners = new Announcer<>(ArgumentClosure.class);
    private final Announcer<ArgumentClosure> selectionListener = new Announcer<>(ArgumentClosure.class);

    public LogPartFilterView() {
        vbox.getChildren().addAll(new Label("just a placeholder"), selectedFilterContainer);
        vbox.setMaxWidth(250);
    }

    public void displayAvailableThreads(List<LogPart> threads) {
        reCreateComboBox("threads");
        availableThreads.getSelectionModel().clearSelection();
        availableThreads.setItems(new ObservableListWrapper<>(threads));
    }

    public void onSelection(final ArgumentClosure<LogPart> closure) {
        selectionListener.addListener(closure);
    }

    private void reCreateComboBox(String promptText){
        createAvailableThreadsComboBox(promptText);
        vbox.getChildren().remove(0);
        vbox.getChildren().add(0, availableThreads);
    }

    private void createAvailableThreadsComboBox(String promptText) {
        availableThreads = new ComboBox<>();
        availableThreads.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LogPart>() {
            @Override
            public void changed(ObservableValue<? extends LogPart> observableValue, LogPart loggedThread, LogPart loggedThread1) {
                selectionListener.announce().excecute(loggedThread1);
            }
        });

        availableThreads.setPromptText(promptText);
        availableThreads.setCellFactory(new Callback<ListView<LogPart>, ListCell<LogPart>>() {
            @Override
            public ListCell<LogPart> call(ListView<LogPart> loggedThreadListView) {
                return new ListCell<LogPart>() {
                    @Override
                    protected void updateItem(LogPart loggedThread, boolean b) {
                        super.updateItem(loggedThread, b);
                        if (null == loggedThread) {
                            setText("null");
                            return;
                        }
                        StringBuilder builder = new StringBuilder();
                        loggedThread.dumpInto(builder);
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

    public void displaySelectedFilter(final List<LogPart> whiteListedThreads) {
        selectedFilterContainer.getChildren().clear();

        for (final LogPart thread : whiteListedThreads) {
            Label label = createLabelFor(thread);
            selectedFilterContainer.getChildren().add(label);
        }
    }

    private Label createLabelFor(final LogPart thread) {
        Label label = new Label(thread.toString());
        label.getStyleClass().add("filter-predicate");
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                discardFilterListeners.announce().excecute(thread);
            }
        });
        return label;
    }

    public void onDiscardFilter(ArgumentClosure<LogPart> argumentClosure) {
        discardFilterListeners.addListener(argumentClosure);
    }
}
