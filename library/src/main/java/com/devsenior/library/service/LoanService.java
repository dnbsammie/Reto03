package com.devsenior.library.service;

import java.util.ArrayList;
import java.util.List;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.Loan;
import com.devsenior.library.model.LoanState;

public class LoanService {
    private List<Loan> loans;
    private BookService bookService;
    private UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loans = new ArrayList<>();
    }

    public void addLoan(String id, String bookID) throws NotFoundException {
        var user = userService.getUserById(id);
        var book = bookService.getBookByid(bookID);

        for (var loan : loans) {
            if (loan.getBook().getId().equals(id)
                    && loan.getState().equals(LoanState.STARTED)) {
                throw new NotFoundException("El libro con el id: "+id+" se encuentra prestado");
            }
        }

        loans.add(new Loan(user, book));
    }

    public void returnBook(String id, String bookID) throws NotFoundException {
        for (var loan : loans) {
            if (loan.getUser().getId().equals(id)
                    && loan.getBook().getId().equals(id)
                    && loan.getState().equals(LoanState.STARTED)) {
                loan.setState(LoanState.FINISHED);
                return;
            }
        }
        throw new NotFoundException("No hay un prestamo del libro: "
                + id + " para el usuario: " + id);
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
