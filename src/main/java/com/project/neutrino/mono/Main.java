package com.project.neutrino.mono;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Browser browser;

    private TrayHandler handler;

    private Scene scene;

    private Property.SaveHandler saveHandler = new Property.SaveHandler();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Mono");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icons/cloud.png")));

        browser  = new Browser();

        Platform.setImplicitExit(false);

        handler = new TrayHandler(primaryStage);
        // sets up the tray icon (using awt code run on the swing thread).
        javax.swing.SwingUtilities.invokeLater(this::addToTray);

        Property size = saveHandler.restore();

        scene = new Scene(browser.getWebView(), size.getWidth(), size.getHeight());

        HotKeyListener hotKeyListener = new HotKeyListener(scene, browser);
        hotKeyListener.setListener();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (browser != null) {
            browser.saveCookie();
        }
        saveHandler.save(new Property(scene.getWidth(), scene.getHeight()));
        super.stop();
    }

    private void addToTray() {
        handler.addAppToTray();
    }
}
