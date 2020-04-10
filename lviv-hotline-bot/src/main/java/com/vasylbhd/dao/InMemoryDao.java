package com.vasylbhd.dao;

import model.Action;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryDao {
    private static final Map<String, LocalDateTime> idToEndDateMap = new HashMap<>();

    public void save(Action action) {
        idToEndDateMap.put(action.getId(), action.getEstimatedEndDate());
    }

    public void remove(String id) {
        idToEndDateMap.remove(id);
    }

    public boolean contains(String id) {
        return idToEndDateMap.containsKey(id);
    }

    public LocalDateTime get(String id) {
        return idToEndDateMap.get(id);
    }

    public Map<String, LocalDateTime> getAll() {
        return idToEndDateMap;
    }


}
