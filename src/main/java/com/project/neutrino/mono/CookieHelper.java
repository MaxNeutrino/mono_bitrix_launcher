package com.project.neutrino.mono;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.util.List;

public class CookieHelper {

    private final String SAVE_DIR = System.getProperty("user.home") + "/.mono/cookies";

    private final static Type jsonType = new TypeToken<List<HttpCookie>>() {
    }.getType();

    private File file = new File(SAVE_DIR + "/cookie.json");

    private SaveUtil<HttpCookie> saveUtil = new SaveUtil<>(file);

    {
        createFile();
    }

    public void save(List<HttpCookie> cookies) {
        saveUtil.save(cookies);
    }

    public List<HttpCookie> restore() {
        return saveUtil.restore(jsonType);
    }

    private void createFile() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
