import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.Book;
import com.devsenior.library.model.Loan;
import com.devsenior.library.model.LoanState;
import com.devsenior.library.model.User;
import com.devsenior.library.service.BookService;
import com.devsenior.library.service.LoanService;
import com.devsenior.library.service.UserService;

public class LoanServiceTest {
   @Mock private BookService bookService;
    @Mock private UserService userService;

    private LoanService loanService;
    private User mockUser;
    private Book mockBook;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loanService = new LoanService(bookService, userService);

        mockUser = new User("U1", "Alice", "alice@mail.com");
        mockBook = new Book("1", "Clean Code", "Robert Martin", false);
    }

    @Test
    public void testAddLoanSuccessfully() throws NotFoundException {
        when(userService.getUserById("U1")).thenReturn(mockUser);
        when(bookService.getBookByid("1")).thenReturn(mockBook);

        loanService.addLoan("U1", "1");

        List<Loan> loans = loanService.getLoans();
        assertEquals(1, loans.size());
        assertEquals("U1", loans.get(0).getUser().getId());
        assertEquals("1", loans.get(0).getBook().getId());
        assertEquals(LoanState.STARTED, loans.get(0).getState());
    }

    @Test
    public void testLoanBookAlreadyLoaned() throws NotFoundException {
        when(userService.getUserById("U1")).thenReturn(mockUser);
        when(bookService.getBookByid("1")).thenReturn(mockBook);

        // Primer préstamo
        loanService.addLoan("U1", "1");

        // Intentar prestarlo nuevamente debe lanzar excepción
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loanService.addLoan("U1", "1");
        });

        assertTrue(exception.getMessage().contains("se encuentra prestado"));
    }

    @Test
    public void testReturnBookSuccessfully() throws NotFoundException {
        when(userService.getUserById("U1")).thenReturn(mockUser);
        when(bookService.getBookByid("1")).thenReturn(mockBook);

        loanService.addLoan("U1", "1");
        loanService.returnBook("U1", "1");

        List<Loan> loans = loanService.getLoans();
        assertEquals(1, loans.size());
        assertEquals(LoanState.FINISHED, loans.get(0).getState());
    }

    @Test
    public void testReturnBookFailsIfNotLoaned() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loanService.returnBook("U1", "1");
        });

        assertTrue(exception.getMessage().contains("No hay un prestamo"));
    }

    @Test
    public void testAddLoanFailsIfUserNotFound() throws NotFoundException {
        when(userService.getUserById("U1")).thenThrow(new NotFoundException("Usuario no encontrado"));
        when(bookService.getBookByid("1")).thenReturn(mockBook);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loanService.addLoan("U1", "1");
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    public void testAddLoanFailsIfBookNotFound() throws NotFoundException {
        when(userService.getUserById("U1")).thenReturn(mockUser);
        when(bookService.getBookByid("1")).thenThrow(new NotFoundException("Libro no encontrado"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loanService.addLoan("U1", "1");
        });

        assertEquals("Libro no encontrado", exception.getMessage());
    }
  
}
