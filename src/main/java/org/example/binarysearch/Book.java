package org.example.binarysearch;

import java.util.Objects;

class Book implements Comparable<Book> {
    String title;
    String author;
    int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public int compareTo(Book other) {
        int titleCompare = this.title.compareTo(other.title);
        if (titleCompare != 0) return titleCompare;

        int authorCompare = this.author.compareTo(other.author);
        return authorCompare != 0 ? authorCompare : Integer.compare(this.year, other.year);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return year == book.year &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

    @Override
    public String toString() {
        return "\"" + title + "\" - " + author + " (" + year + ")";
    }
}
