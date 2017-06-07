package com.project.neutrino.mono;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class HotKeyListener {

    private Scene scene;
    private Browser browser;

    private final KeyCombination goBackHistory = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
    private final KeyCombination goForwardHistory = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
    private final KeyCombination reload = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);

    public HotKeyListener(Scene scene, Browser browser) {
        this.scene = scene;
        this.browser = browser;
    }

    public void setListener() {
        scene.addEventHandler(
                KeyEvent.KEY_RELEASED, event -> {
                    if (reload.match(event)) {
                        browser.reload();
                    } else if (goBackHistory.match(event)) {
                        browser.goBack();
                    } else if (goForwardHistory.match(event)) {
                        browser.goForward();
                    }
                }
        );
    }
}
