package com.github.signed.log;

import com.github.signed.log.core.LogEntryView;
import com.github.signed.log.core.RawLog;
import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.list.SimpleLogModel;
import com.github.signed.log.sidebyside.SideBySide;
import com.google.common.collect.Lists;
import javafx.CenterOnScreenTwo;
import javafx.EnsureWindowComesOnTop;
import javafx.ViewOrphanage;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

public class LogSlicer extends Application{

    public static void main(String [] args){
        launch(args);
    }

    private final List<Presenter> presenters = Lists.newArrayList();
    private final List<LogEntryView> logEntryViews = Lists.newArrayList();
    private final SimpleLogModel logModel = new SimpleLogModel();

    @Override
    public void init() throws Exception {
        SideBySide sideBySide = new SideBySide(logModel);
        logEntryViews.add(sideBySide);

        for (LogEntryView logEntryView : logEntryViews) {
            logEntryView.contributeTo(presenters);
        }

        for (Presenter presenter : presenters) {
            presenter.initialize();
        }

        Parameters parameters = getParameters();
        String path = parameters.getUnnamed().get(0);
        String logAsPlainString = FileUtils.readFileToString(Paths.get(path).toFile(), Charset.forName("UTF-8"));
        logModel.addEntriesFrom(new RawLog(logAsPlainString));
    }

    @Override
    public void start(Stage stage) throws Exception {
        TabPane tabPane = new TabPane();
        ViewOrphanage orphanage = new TabPaneOrphanage(tabPane);
        for (LogEntryView logEntryView : logEntryViews) {
            logEntryView.addTo(orphanage);
        }

        Scene scene = new Scene(tabPane);
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/hide-scroll-bar.css").toExternalForm());
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/filter-predicate.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnShowing(new CenterOnScreenTwo());
        stage.setOnShown(new EnsureWindowComesOnTop());
        stage.show();
    }

    private static class TabPaneOrphanage implements ViewOrphanage {

        private final TabPane tabPane;

        public TabPaneOrphanage(TabPane tabPane) {
            this.tabPane = tabPane;
        }

        @Override
        public void add(Node node) {
            Tab tab = new Tab("Tab");
            tab.setContent(node);
            tabPane.getTabs().add(tab);
        }
    }
}
