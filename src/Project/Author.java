package Project;

import java.util.*;

public class Author {
    private Profile profile;
    private ArrayList<Book> writtenBooks = new ArrayList<>();

    public Profile getProfile()  {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setProfile(String name, String lastName, Date BD) {
        profile = new Profile(name, lastName, BD);
    }

    public void addWrittenBook(Book newBook) {
        getWrittenBooks().add(newBook);
    }

    public void deleteBook(Book toDelete) {
        getWrittenBooks().remove(toDelete);
    }

    public boolean wroteBooks() {
        return !getWrittenBooks().isEmpty();
    }

    public ArrayList<Book> getWrittenBooks() {
        return writtenBooks;
    }

    public void setWrittenBooks(ArrayList<Book> writtenBooks) {
        this.writtenBooks = writtenBooks;
    }
}
