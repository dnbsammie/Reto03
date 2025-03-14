package com.devsenior.library.service;

import java.util.ArrayList;

import com.devsenior.library.model.User;
import com.devsenior.library.model.Book;
import com.devsenior.library.model.Loan;
import com.devsenior.library.repository.BookRepository;
import com.devsenior.library.repository.LoanRepository;

public class LibraryService {
    private BookRepository bookRepository; // mock
    private LoanRepository loanRepository; // mock
    private ArrayList<User> users;

    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository, ArrayList<User> users) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        users = new ArrayList<>();
    }

    public void addBook(String id, String title, String author){
        bookRepository.save(new Book(id, title, author, false));
    }

    public void addUser(String id, String name, String email){
        loanRepository.save(new Loan(null, null));
    }
}
