package com.ifyou.nowincinema.di;

import java.util.HashMap;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

/**
 * Created by Baranov on 12.04.2017.
 **/

public class LocalCiceroneHolder {
    private HashMap<String, Cicerone<Router>> containers;

    public LocalCiceroneHolder() {
        containers = new HashMap<>();
    }

    public Cicerone<Router> getCicerone(String containerTag) {
        if (!containers.containsKey(containerTag)) {
            containers.put(containerTag, Cicerone.create());
        }
        return containers.get(containerTag);
    }
}
