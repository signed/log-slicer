package com.github.signed.log.ui;

import com.github.signed.log.core.RawLog;
import com.github.signed.log.ui.compare.SideBySideLogView;
import com.github.signed.log.ui.list.LogModel;
import com.github.signed.log.ui.list.LogPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.nio.charset.Charset;
import java.nio.file.Paths;

public class LogSlicer extends Application{

    public static void main(String [] args){
        launch(args);
    }

    private final LogModel logModel = new LogModel();
    private final SideBySideLogView logView = new SideBySideLogView();
    private final LogPresenter logPresenter = new LogPresenter(logModel, logView);


    @Override
    public void init() throws Exception {
        Parameters parameters = getParameters();
        String path = parameters.getUnnamed().get(0);
        String s = FileUtils.readFileToString(Paths.get(path).toFile(), Charset.forName("UTF-8"));
        logPresenter.initialize();
        logModel.addEntriesFrom(new RawLog(s));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new HBox();
        logView.addTo(new ViewOrphanage(pane));


        Scene scene = new Scene(pane);
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/hide-scroll-bar.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
