package com.project.neutrino.mono;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Property {

    private double height;

    private double width;


    public Property(double width, double height) {
        this.height = height;
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public static class SaveHandler {

        private final String SAVE_DIR = System.getProperty("user.home") + "/.mono";

        private final static Type jsonType = new TypeToken<List<Property>>() {
        }.getType();

        private File file = new File(SAVE_DIR + "/properties.json");

        private SaveUtil<Property> saveUtil = new SaveUtil<>(file);

        {
            createFile();
        }

        public void save(List<Property> properties) {
            saveUtil.save(properties);
        }

        public void save(Property property) {
            saveUtil.save(Collections.singletonList(property));
        }

        public Property restore() {
           List<Property> properties = saveUtil.restore(jsonType);
           if (properties.isEmpty())
               return new Property(1200, 700);

           return properties.get(0);
        }

        private void createFile() {
            File dir = new File(SAVE_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }
}
