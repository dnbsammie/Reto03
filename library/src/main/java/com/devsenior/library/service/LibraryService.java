package com.devsenior.library.service;

import java.util.List;

import com.devsenior.library.model.User;
import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.Book;
import com.devsenior.library.model.Loan;
import com.devsenior.library.model.LoanState;
import com.devsenior.library.repository.BookRepository;
import com.devsenior.library.repository.LoanRepository;
import com.devsenior.library.repository.UserRepository;

public class LibraryService {
    // Mocked in tests
    private BookRepository bookRepository;
    private LoanRepository loanRepository;
    private UserRepository userRepository;

    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    public void addBook(String id, String title, String author) {
        bookRepository.save(new Book(id, title, author, false));
    }

    public void addUser(String id, String name, String email) {
        userRepository.save(new User(id, name, email));
    }

    public void loanBook(String userId, String bookId) throws NotFoundException {
        User user = userRepository.findById(userId);
        Book book = bookRepository.findById(bookId);

        if (user == null)
            throw new NotFoundException("Usuario no encontrado: " + userId);
        if (book == null)
            throw new NotFoundException("Libro no encontrado: " + bookId);

        List<Loan> userLoans = loanRepository.findByUserId(userId);

        for (Loan loan : userLoans) {
            if (loan.getBook().getId().equals(bookId) && loan.getState() == LoanState.STARTED) {
                throw new NotFoundException("El libro ya está prestado: " + bookId);
            }
        }

        loanRepository.save(new Loan(user, book));
    }

    public void returnBook(String userId, String bookId) throws NotFoundException {
        List<Loan> userLoans = loanRepository.findByUserId(userId);

        for (Loan loan : userLoans) {
            if (loan.getBook().getId().equals(bookId) && loan.getState() == LoanState.STARTED) {
                loan.setState(LoanState.FINISHED);
                loanRepository.save(loan);
                return;
            }
        }

        throw new NotFoundException("No existe un préstamo activo del libro " + bookId + " para el usuario " + userId);
    }
}
