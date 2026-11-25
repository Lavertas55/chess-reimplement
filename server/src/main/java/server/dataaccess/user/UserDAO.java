package server.dataaccess.user;

import datamodel.UserData;
import server.dataaccess.DataAccessException;

public interface UserDAO {
    public UserData getUser(int id);
    public int addUser(UserData user);
    public void addUser(int id, UserData user) throws DataAccessException;
}
