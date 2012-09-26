package com.github.signed.log.filter;

import com.github.signed.log.thread.LoggedThread;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import lang.ArgumentClosure;

import java.util.List;

public class LogPartFilterView {
    private final ComboBox<LoggedThread> availableThreads = new ComboBox<>();

    public LogPartFilterView() {
        createThreadsComboBox();
    }

    public ComboBox<LoggedThread> node() {
        return availableThreads;
    }

    public void onSelectedThreadChanges(final ArgumentClosure<LoggedThread> closure){
        node().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LoggedThread>() {
            @Override
            public void changed(ObservableValue<? extends LoggedThread> observableValue, LoggedThread loggedThread, LoggedThread loggedThread1) {
                closure.excecute(loggedThread1);
            }
        });
    }

    public void displayAvailableThreads(List<LoggedThread> threads) {
        node().setItems(new ObservableListWrapper<>(threads));
    }



    public void createThreadsComboBox() {
        node().setPromptText("threads");
        node().setCellFactory(new Callback<ListView<LoggedThread>, ListCell<LoggedThread>>() {
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
}
