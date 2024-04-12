package Project;

import Repositories.BookRepositories;

import java.util.Date;

public class Token extends Transaction{
    private boolean validated = false;

    public Token(String tID, Date dateOfTransaction, Client transactingClient, Book transactedBook){
        super(tID, "Borrow", dateOfTransaction, transactingClient, transactedBook);
    }

    public Token() {
        validated = false;
    }

    public void validate() {
        validated = true;

        if (getTypeOfTransaction().equalsIgnoreCase("Borrow")) {
            transactedBook.setAvailable(false);
            BookRepositories.availableBooks.remove(transactedBook);
            BookRepositories.notAvailableBooks.add(transactedBook);
        } else {
            transactedBook.setAvailable(true);
            BookRepositories.availableBooks.add(transactedBook);
            BookRepositories.notAvailableBooks.remove(transactedBook);
        }
        dateOfTransaction = new Date();
    }

    public String tokenToString() {
        return String.format("%s: %s. %s: %s", "The client is", transactingClient.getUsername(),
                "The book they're trying to borrow is", transactedBook.getTitle());
    }

    public String dataToString() {
        return String.format("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s | %-9s |", gettID(),
                getTypeOfTransaction(), getDateOfTransaction().getDate() + "-" +  (getDateOfTransaction().getMonth() + 1) + "-" +
                        getDateOfTransaction().getYear(), getTransactingClient().getProfile().getName() +
                        " " + getTransactingClient().getProfile().getLastName(), getTransactedBook().getBookID(),
                getTransactedBook().getTitle(), validated);
    }
}
