package com.devsenior.library;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.service.BookService;
import com.devsenior.library.service.LoanService;
import com.devsenior.library.service.UserService;

public class Main {
    public static void main(String[] args) throws NotFoundException {
        BookService bookService = new BookService();
        UserService userService = new UserService();
        LoanService loanService = new LoanService(bookService, userService);

        bookService.addBook("1", "Java Basics", "John Doe");
        userService.addUser("U1", "Maria", "maria@example.com");
        
        loanService.addLoan("U1", "1");

        System.out.println("Préstamos activos: " + loanService.getLoans().size());
    }
}