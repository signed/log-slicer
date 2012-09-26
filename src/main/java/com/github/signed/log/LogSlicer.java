package com.github.signed.log;

import com.github.signed.log.compare.SideBySideLogPresenter;
import com.github.signed.log.compare.SideBySideLogView;
import com.github.signed.log.core.RawLog;
import com.github.signed.log.list.LogModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
    private final SideBySideLogPresenter logPresenter = new SideBySideLogPresenter(logModel, logView);


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
        HBox hBox = new HBox();
        logView.addTo(new HboxControledOrphanage(hBox));

        Scene scene = new Scene(hBox);
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/hide-scroll-bar.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
