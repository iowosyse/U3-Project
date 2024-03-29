package Project;

import Repositories.BookRepositories;

import java.util.Date;

public class Token extends Transaction{
    private boolean validated;

    public Token(String tID, Date dateOfTransaction, Client transactingClient, Book transactedBook){
        super(tID, "Borrow", dateOfTransaction, transactingClient, transactedBook);
    }

    public Token() {
        validated = false;
    }

    public void validate() {
        validated = true;
        transactedBook.isAvailable = false;
        BookRepositories.availableBooks.remove(transactedBook);
        BookRepositories.notAvailableBooks.add(transactedBook);
        dateOfTransaction = new Date();
        setTID();
    }

    public String tokenToString() {
        return String.format("%s: %s. %s: %s", "The client is", transactingClient.getUsername(),
                "The book they're trying to borrow is", transactedBook.getTitle());
    }
}
