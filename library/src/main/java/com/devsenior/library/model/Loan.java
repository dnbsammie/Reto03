package com.devsenior.library.model;

import java.time.LocalDate;

public class Loan {
    private final User user;
    private final Book book;
    private final LocalDate loanDate;
    private LoanState state;

    public Loan(User user, Book book) {
        this(user, book, LocalDate.now(), LoanState.STARTED);
    }

    public Loan(User user, Book book, LocalDate loanDate) {
        this(user, book, loanDate, LoanState.STARTED);
    }

    public Loan(User user, Book book, LocalDate loanDate, LoanState state) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.state = state;
    }

    public User getUser() {return user;}

    public Book getBook() {return book;}

    public LocalDate getLoanDate() {return loanDate;}

    public LoanState getState() {return state;}

    public void setState(LoanState state) {
        this.state = state;
    }
}