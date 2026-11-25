package passoff.server.dataaccess.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import datamodel.UserData;
import server.dataaccess.DataAccessException;
import server.dataaccess.user.UserDAO;

abstract class AbstractUserDAOTest {
    protected UserDAO userDao;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDao = createUserDAO();
        seedData();
    }

    protected abstract UserDAO createUserDAO();

    protected void seedData() throws DataAccessException {
        userDao.addUser(new UserData("u1", "u1", "u1"));
        userDao.addUser(new UserData("u2", "u2", "u2"));
    }

    @Test
    @DisplayName("Get User with Match")
    public void getUser() {
        UserData user = userDao.getUser(1);
        assertNotNull(user);
        assertEquals(user.userName(), "u1");
    }

    @Test 
    @DisplayName("Get User - Returns Null on Missing")
    public void getMissingUser() {
        assertNull(userDao.getUser(3));
    }

    @Test 
    @DisplayName("Add User")
    public void addUser() {
        int id = userDao.addUser(new UserData("u3", "u3", "u3"));
        UserData user = userDao.getUser(id);
        assertNotNull(user);
        assertEquals(user.userName(), "u3");
    }
}
