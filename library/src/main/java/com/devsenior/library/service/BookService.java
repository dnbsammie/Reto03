package com.devsenior.library.service;

import java.util.ArrayList;
import java.util.List;

import com.devsenior.library.exception.NotFoundException;
import com.devsenior.library.model.Book;

public class BookService {
    private List<Book> books;

    public BookService() {
        books = new ArrayList<>();
    }

    public void addBook(String id, String title, String author) {
        books.add(new Book(id, title, author, false));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookByid(String id) throws NotFoundException {
        for (var book : books) {
            if(book.getId().equals(id)) {
                return book;
            }
        }
        throw new NotFoundException("No fue encontrado el libro con id: "+id);
    }

    public void deleteBook(String id) throws NotFoundException {
        for (var book : books) {
            if (book.getId().equals(id)) {
                books.remove(book);
                return;
            }
        }
        throw new NotFoundException("No se puede eliminar el libro con id: "+id);
    }
}