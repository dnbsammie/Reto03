import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devsenior.library.repository.BookRepository;
import com.devsenior.library.repository.LoanRepository;
import com.devsenior.library.service.LibraryService;


public class LibraryServiceTest {

    @Mock private BookRepository bookRepository;
    @Mock private LoanRepository loanRepository;
    private LibraryService libraryService;
}
