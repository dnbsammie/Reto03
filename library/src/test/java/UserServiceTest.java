import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.User;
import com.devsenior.library.repository.UserRepository;
import com.devsenior.library.service.UserService;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  private UserService userService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = new UserService(userRepository);
  }

  @Test
  public void testAddAndGetUserById() throws NotFoundException {
    userService.addUser("U1", "Carlos", "carlos@mail.com");

    User user = userService.getUserById("U1");

    assertNotNull(user);
    assertEquals("Carlos", user.getName());
  }

  @Test
  public void testUpdateUserEmail() throws NotFoundException {
    User user = new User("U1", "Luis", "old@mail.com");
    when(userRepository.findById("U1")).thenReturn(user);

    userService.updateUserEmail("U1", "new@mail.com");

    assertEquals("new@mail.com", user.getEmail());
    verify(userRepository).save(user);
  }

  @Test
  public void testDeleteUser() throws NotFoundException {
    userService.addUser("U1", "Luis", "luis@mail.com");
    userService.deleteUser("U1");

    assertThrows(NotFoundException.class, () -> {
      userService.getUserById("U1");
    });
  }

  @Test
  public void testAddUserCallsSave() {
    userService.addUser("U1", "Maria", "maria@mail.com");
    verify(userRepository).save(any(User.class));
  }

  @Test
  public void testGetUserByIdThrowsIfNotFound() {
    when(userRepository.findById("U1")).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      userService.getUserById("U1");
    });
  }

}
