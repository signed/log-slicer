package com.github.signed.log;

import com.github.signed.log.compare.SideBySideLogPresenter;
import com.github.signed.log.compare.SideBySideLogView;
import com.github.signed.log.core.RawLog;
import com.github.signed.log.filter.LogPartFilterModel;
import com.github.signed.log.filter.LogPartFilterPresenter;
import com.github.signed.log.filter.LogPartFilterView;
import com.github.signed.log.filteredlisting.LogEntryListingWithFilterInputView;
import com.github.signed.log.list.LogPresenter;
import com.github.signed.log.list.LogView;
import com.github.signed.log.list.Presenter;
import com.github.signed.log.list.SimpleLogModel;
import com.google.common.collect.Lists;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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


    private final LogPartFilterModel leftLogPartFilterModel = new LogPartFilterModel(logModel);
    private final LogPartFilterView  leftLogPartFilterView = new LogPartFilterView();
    private final LogPartFilterPresenter leftLogPartFilterPresenter = new LogPartFilterPresenter(leftLogPartFilterView, leftLogPartFilterModel, logModel);

    private final LogView leftLogView = new LogView();
    private final LogPresenter leftLogPresenter = new LogPresenter(leftLogPartFilterModel, leftLogView);

    private final LogPartFilterModel rightLogPartFilterModel = new LogPartFilterModel(logModel);
    private final LogPartFilterView  rightLogPartFilterView = new LogPartFilterView();
    private final LogPartFilterPresenter rightLogPartFilterPresenter = new LogPartFilterPresenter(rightLogPartFilterView, rightLogPartFilterModel, logModel);

    private final LogView rightLogView = new LogView();
    private final LogPresenter rightLogPresenter = new LogPresenter(rightLogPartFilterModel, rightLogView);

    private final LogEntryListingWithFilterInputView left = new LogEntryListingWithFilterInputView(leftLogPartFilterView, leftLogView);
    private final LogEntryListingWithFilterInputView right = new LogEntryListingWithFilterInputView(rightLogPartFilterView, rightLogView);

    private final SideBySideLogView logView = new SideBySideLogView(left, right);
    private final SideBySideLogPresenter sideBySideLogPresenter = new SideBySideLogPresenter(logModel, logView, leftLogPresenter, rightLogPresenter);

    @Override
    public void init() throws Exception {
        presenters.add(leftLogPartFilterPresenter);
        presenters.add(leftLogPresenter);
        presenters.add(rightLogPartFilterPresenter);
        presenters.add(rightLogPresenter);
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
        logView.addTo(new HBoxControlledOrphanage(hBox));

        Scene scene = new Scene(hBox);
        scene.getStylesheets().addAll(LogSlicer.class.getResource("/hide-scroll-bar.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
