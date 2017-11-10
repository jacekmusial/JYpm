package com.github.open96;

import com.github.open96.download.DownloadManager;
import com.github.open96.playlist.PlaylistManager;
import com.github.open96.settings.SettingsManager;
import com.github.open96.thread.ThreadManager;
import com.github.open96.tray.TrayIcon;
import com.github.open96.updater.Updater;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JYpm extends Application {

    //Initialize log4j logger for later use in this class
    private static Logger log = LogManager.getLogger(JYpm.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is ran before start() thus it's the best place to initialize critical components.
     */
    @Override
    public void init() {
        //Log the point where JavaFX thread will be started
        log.info("Starting JYpm...");

        //Enable hardware acceleration
        System.setProperty("sun.java2d.opengl", "true");

        //Initialize critical components
        ThreadManager.getInstance();
        SettingsManager.getInstance();
        PlaylistManager.getInstance();
        DownloadManager.getInstance();
    }

    /**
     * In start() UI components are initialized and displayed to user.
     *
     * @param stage Main stage where all UI components will be put and displayed
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Set window title
        stage.setTitle("JYpm");

        //Set window icon
        stage.getIcons().add(new Image("/icon/launcher-128-128.png"));

        //Load window layout from FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/rootPane.fxml"));

        //Create scene with loaded layout
        Scene scene = new Scene(root);

        //Show tray icon in system tray and depending on it's return value - override window buttons behaviour
        TrayIcon.setMainWindowStage(stage);
        TrayIcon.getInstance();
        if (TrayIcon.isTrayWorking()) {
            //Override main window minimize/maximize buttons behaviour
            overrideWindowButtonsBehaviour(stage);
        }

        //Draw the window and show it
        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
        stage.toFront();

        try {
            Updater.getInstance().checkForUpdate();
        } catch (IllegalStateException e) {
            log.warn("You have probably exceeded your GitHub API call limit. You won't be able to check for updates in 1 hour. Also, don't abuse GitHub API if you can :)");
        }
    }

    /**
     * stop() is called when application is closed the correct way (i.e. by clicking close button).
     * IMPORTANT: It won't run if application was killed, so it is not a reliable way to perform critical operations.
     */
    @Override
    public void stop() {
        //Tell executor service it should shut down
        log.debug("Stopping all threads on application shutdown");
        ThreadManager.getInstance().stopAllThreads();

        TrayIcon.getInstance().removeTrayIcon();

        log.info("Closing JYpm...");
        Platform.exit();
    }


    /**
     * Overrides behaviour of window buttons.
     *
     * @param stage Stage which window should be overridden
     */
    private void overrideWindowButtonsBehaviour(Stage stage) {
        //Override minimize button behaviour
        stage.iconifiedProperty().addListener((observable, oldValue, iconified) -> {
            if (iconified) {
                log.debug("Minimizing to tray.");
                //Make JavaFX thread not close on calling stage.hide()
                Platform.setImplicitExit(false);
                //Move window to background and minimize it to tray
                stage.toBack();
                stage.hide();
            }
        });

        //Override close button behaviour
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
        });
    }

}
