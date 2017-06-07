package com.project.neutrino.mono;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.CookieStore;
import java.net.URI;
import java.net.URISyntaxException;

public class Browser {

    private String url = "https://irens.bitrix24.ua";

    private WebView webView = new WebView();

    private CookieHelper cookieHelper = new CookieHelper();

    private java.net.CookieManager manager = new java.net.CookieManager();

    {
        webView.getEngine().load(url);
        createPopupHandler();
        java.net.CookieHandler.setDefault(manager);
        loadCookie();
    }

    private void createPopupHandler() {
        webView.getEngine().setCreatePopupHandler(p -> {
            Stage stage = new Stage(StageStyle.UTILITY);
            WebView wv2 = new WebView();
            stage.setScene(new Scene(wv2));
            stage.show();
            return wv2.getEngine();
        });
    }

    public void saveCookie() {
        cookieHelper.save(manager.getCookieStore().getCookies());
    }

    private void loadCookie() {
        CookieStore store = manager.getCookieStore();
        cookieHelper.restore().forEach(c -> {
            try {
                store.add(new URI(url), c);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public String goBack() {
        final WebHistory history = webView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

        Platform.runLater(() -> history.go(-1));
        return entryList.get(currentIndex > 0 ? currentIndex - 1 : currentIndex).getUrl();
    }

    public String goForward() {
        final WebHistory history = webView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

        Platform.runLater(() -> history.go(1));
        return entryList.get(currentIndex < entryList.size() - 1 ? currentIndex + 1 : currentIndex).getUrl();
    }

    public void reload() {
        webView.getEngine().reload();
    }

    public WebView getWebView() {
        return webView;
    }
}
