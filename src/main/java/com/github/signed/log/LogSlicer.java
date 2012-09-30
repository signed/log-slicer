package com.github.signed.log;

import com.github.signed.log.compare.SideBySideLogPresenter;
import com.github.signed.log.compare.SideBySideLogView;
import com.github.signed.log.core.RawLog;
import com.github.signed.log.core.ui.Presenter;
import com.github.signed.log.list.SimpleLogModel;
import com.google.common.collect.Lists;
import javafx.HBoxControlledOrphanage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

    private final SimpleLogModel logModel = new SimpleLogModel();


    private LogPanel left = LogPanel.PositionFilterLeft(logModel);
    private LogPanel right = LogPanel.PositionFilterRight(logModel);

    private final SideBySideLogView logView = new SideBySideLogView(left.completeView, right.completeView);
    private final SideBySideLogPresenter sideBySideLogPresenter = new SideBySideLogPresenter(logModel, logView, left.logPresenter, right.logPresenter);

    @Override
    public void init() throws Exception {
        presenters.addAll(left.allPresenters);
        presenters.addAll(right.allPresenters);
        presenters.add(sideBySideLogPresenter);

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
        HBox hBox = new HBox();
        HBoxControlledOrphanage orphanage = new HBoxControlledOrphanage(hBox);
        orphanage.nextGrabHorizontalSpace(Priority.ALWAYS);
        logView.addTo(orphanage);

        Scene scene = new Scene(hBox);
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/hide-scroll-bar.css").toExternalForm());
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/filter-predicate.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
