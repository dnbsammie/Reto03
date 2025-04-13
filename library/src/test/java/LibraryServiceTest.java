import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.Book;
import com.devsenior.library.model.Loan;
import com.devsenior.library.model.LoanState;
import com.devsenior.library.model.User;
import com.devsenior.library.repository.BookRepository;
import com.devsenior.library.repository.LoanRepository;
import com.devsenior.library.repository.UserRepository;
import com.devsenior.library.service.LibraryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

public class LibraryServiceTest {

    @Mock private BookRepository bookRepository;
    @Mock private LoanRepository loanRepository;
    @Mock private UserRepository userRepository;

    private LibraryService libraryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        libraryService = new LibraryService(bookRepository, loanRepository, userRepository);
    }

    @Test
    public void testLoanBookSuccess() throws NotFoundException {
        User user = new User("U1", "Ana", "ana@mail.com");
        Book book = new Book("B1", "Libro", "Autor", false);

        when(userRepository.findById("U1")).thenReturn(user);
        when(bookRepository.findById("B1")).thenReturn(book);
        when(loanRepository.findByUserId("U1")).thenReturn(List.of());

        libraryService.loanBook("U1", "B1");

        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    public void testLoanBookThrowsIfAlreadyLoaned() {
        User user = new User("U1", "Ana", "ana@mail.com");
        Book book = new Book("B1", "Libro", "Autor", false);
        Loan loan = new Loan(user, book);

        when(userRepository.findById("U1")).thenReturn(user);
        when(bookRepository.findById("B1")).thenReturn(book);
        when(loanRepository.findByUserId("U1")).thenReturn(List.of(loan));

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            libraryService.loanBook("U1", "B1");
        });

        assertTrue(ex.getMessage().contains("ya está prestado"));
    }

    @Test
    public void testReturnBookSuccess() throws NotFoundException {
        User user = new User("U1", "Ana", "ana@mail.com");
        Book book = new Book("B1", "Libro", "Autor", false);
        Loan loan = new Loan(user, book);

        when(loanRepository.findByUserId("U1")).thenReturn(List.of(loan));

        libraryService.returnBook("U1", "B1");

        assertEquals(LoanState.FINISHED, loan.getState());
        verify(loanRepository).save(loan);
    }

    @Test
    public void testAddBookCallsRepository() {
        libraryService.addBook("1", "Clean Code", "Robert Martin");
        // Verifica que el método save fue llamado una vez con cualquier Book
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testAddUserCallsLoanRepository() {
        libraryService.addUser("U1", "Ana", "ana@mail.com");
        // Verifica que el método save fue llamado una vez con cualquier Loan (aunque esto está raro...)
        verify(loanRepository, times(1)).save(any(Loan.class));
    }
}
