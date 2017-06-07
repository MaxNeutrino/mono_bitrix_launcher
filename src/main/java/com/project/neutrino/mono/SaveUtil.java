package com.project.neutrino.mono;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SaveUtil<T> {

    private File save;

    public SaveUtil(File save) {
        this.save = save;
    }

    public void save(List<T> objects) {
        try (Writer writer = new FileWriter(save)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(objects, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<T> restore(final Type type) {
        if (save.exists()) {
            try (JsonReader reader = new JsonReader(new FileReader(save))) {
                Gson gson = new GsonBuilder().create();
                List<T> objects = gson.fromJson(reader, type);
                return objects == null ? new ArrayList<>() : objects;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
