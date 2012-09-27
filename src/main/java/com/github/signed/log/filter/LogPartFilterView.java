package com.github.signed.log.filter;

import com.github.signed.log.ViewOrphanage;
import com.github.signed.log.thread.LoggedThread;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lang.Announcer;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterView {
    private final VBox vbox = new VBox();
    private ComboBox<LoggedThread> availableThreads;
    private final VBox selectedFilterContainer = new VBox();
    private final Announcer<ArgumentClosure> discardFilterListeners = new Announcer<>(ArgumentClosure.class);
    private final Announcer<ArgumentClosure> selectionListener = new Announcer<>(ArgumentClosure.class);

    public LogPartFilterView() {
        createAvailableThreadsCombobox();
        vbox.getChildren().addAll(availableThreads, selectedFilterContainer);
    }

    public void onSelectedThreadChanges(final ArgumentClosure<LoggedThread> closure) {
        selectionListener.addListener(closure);
    }

    public void displayAvailableThreads(List<LoggedThread> threads) {
        reCreateComboBox();
        availableThreads.getSelectionModel().clearSelection();
        availableThreads.setItems(new ObservableListWrapper<>(threads));
    }

    public void reCreateComboBox(){
        createAvailableThreadsCombobox();
        vbox.getChildren().remove(0);
        vbox.getChildren().add(0, availableThreads);
    }

    public void createAvailableThreadsCombobox() {
        availableThreads = new ComboBox<>();
        availableThreads.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LoggedThread>() {
            @Override
            public void changed(ObservableValue<? extends LoggedThread> observableValue, LoggedThread loggedThread, LoggedThread loggedThread1) {
                selectionListener.announce().excecute(loggedThread1);
            }
        });

        availableThreads.setPromptText("threads");
        availableThreads.setCellFactory(new Callback<ListView<LoggedThread>, ListCell<LoggedThread>>() {
            @Override
            public ListCell<LoggedThread> call(ListView<LoggedThread> loggedThreadListView) {
                return new ListCell<LoggedThread>() {
                    @Override
                    protected void updateItem(LoggedThread loggedThread, boolean b) {
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

    public void addTo(ViewOrphanage viewOrphanage) {
        viewOrphanage.add(vbox);
    }

    public void displaySelectedFilter(final List<LoggedThread> whiteListedThreads) {
        selectedFilterContainer.getChildren().clear();

        for (final LoggedThread thread : whiteListedThreads) {
            Label label = createLabelFor(thread);
            selectedFilterContainer.getChildren().add(label);
        }
    }

    private Label createLabelFor(final LoggedThread thread) {
        Label label = new Label(thread.toString());
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                discardFilterListeners.announce().excecute(thread);
            }
        });
        return label;
    }

    public void onDiscardFilter(ArgumentClosure<LoggedThread> argumentClosure) {
        discardFilterListeners.addListener(argumentClosure);
    }
}
