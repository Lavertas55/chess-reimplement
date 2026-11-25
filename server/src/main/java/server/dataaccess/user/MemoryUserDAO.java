package server.dataaccess.user;

import java.util.HashMap;

import datamodel.UserData;
import server.dataaccess.DataAccessException;

public class MemoryUserDAO implements UserDAO {
    private final HashMap<Integer, UserData> store = new HashMap<>();
    private int nextId = 1;

    @Override
    public UserData getUser(int id) {
        return store.get(id);
    }

    @Override 
    public int addUser(UserData user) {
        store.put(nextId, user);
        return nextId++;
    }

    @Override
    public void addUser(int id, UserData user) throws DataAccessException {
        if (store.putIfAbsent(id, user) != null) {
            throw new DataAccessException(String.format("Record conflict with %s", user));
        }
    }
}
