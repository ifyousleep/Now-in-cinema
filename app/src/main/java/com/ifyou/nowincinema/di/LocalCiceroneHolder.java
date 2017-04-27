package com.ifyou.nowincinema.di;

import android.util.SparseArray;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

/**
 * Created by Baranov on 12.04.2017.
 **/

public class LocalCiceroneHolder {
    private SparseArray<Cicerone<Router>> containers;

    public LocalCiceroneHolder() {
        containers = new SparseArray<>();
    }

    public Cicerone<Router> getCicerone(Integer containerTag) {
        if (containers.indexOfKey(containerTag) < 0) {
            containers.put(containerTag, Cicerone.create());
        }
        return containers.get(containerTag);
    }
}
