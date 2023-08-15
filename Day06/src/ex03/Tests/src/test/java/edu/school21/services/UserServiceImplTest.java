package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {
    private final String USER_LOGIN = "login";
    private final String USER_PASSWORD = "password";

    private User expectedUser;
    private UsersRepository usersRepositoryMock = Mockito.mock(UsersRepository.class);
    private UserServiceImpl userService = new UserServiceImpl(usersRepositoryMock);

    @BeforeEach
    void init() {
        expectedUser = new User(1, USER_LOGIN, USER_PASSWORD, false);
        try {
            Mockito.when(usersRepositoryMock.findByLogin(USER_LOGIN)).thenReturn(expectedUser);
            Mockito.when(usersRepositoryMock.findByLogin("wrongLogin")).thenThrow(EntityNotFoundException.class);
        } catch (EntityNotFoundException ignored) {}
    }

    @Test
    void authenticateTest() {
        try {
            Assertions.assertTrue(userService.authenticate(USER_LOGIN, USER_PASSWORD));
        } catch (EntityNotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void authenticateWrongPasswordTest() {
        try {
            Assertions.assertFalse(userService.authenticate(USER_LOGIN, "wrongPassword"));
        } catch (EntityNotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void authenticateAlreadyAuthenticatedTest() {
        expectedUser.setAuthenticated(true);
        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> userService.authenticate(USER_LOGIN, USER_PASSWORD));
    }

    @Test
    void authenticateWrongLoginTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.authenticate("wrongLogin", USER_PASSWORD));
    }

    @Test
    void authenticateWrongPasswordAndLoginTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.authenticate("wrongLogin", "wrongPassword"));
    }


}
