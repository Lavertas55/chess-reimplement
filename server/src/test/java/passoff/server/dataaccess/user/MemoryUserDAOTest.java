package passoff.server.dataaccess.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import datamodel.UserData;
import server.dataaccess.DataAccessException;
import server.dataaccess.user.MemoryUserDAO;
import server.dataaccess.user.UserDAO;

public class MemoryUserDAOTest extends AbstractUserDAOTest {
    @Override 
    protected UserDAO createUserDAO() {
        return new MemoryUserDAO();
    }

    @Test 
    @DisplayName("Add User - With ID")
    public void addUserWithId() throws DataAccessException {
        userDao.addUser(10, new UserData("u10", "u10", "u10"));
        UserData user = userDao.getUser(10);
        assertNotNull(user);
        assertEquals(user.userName(), "u10");
    }

    @Test 
    @DisplayName("Add User - Conflicting ID")
    public void addUserIdConflict() {
        assertThrows(
            DataAccessException.class,
            () -> userDao.addUser(1, new UserData("i", "i", "i"))
        );
    }
}
