package Project;

import Controllers.BookController;
import Controllers.Permissions;
import Repositories.BookRepositories;
import Repositories.TransactionRepositories;
import UtilityClasses.Colors;

import java.util.*;

public class Client extends User{
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Client (Permissions a, String name, String lastName, Date BD) {
        super(a);
        setProfile(name, lastName, BD);
        userType = "Client";
        permissions[1] = Permissions.ASK;
        setPermissionsString();
    }

    public Client() {
        super();
        userType = "Client";
        permissions[0] = Permissions.READ;
        permissions[1] = Permissions.ASK;
        setPermissionsString();
    }

    public int getNumberOfBorrowedBooks() { //this because of phantom books
        return borrowedBooks.size();
    }

    public boolean hasBooks(){
        return !borrowedBooks.isEmpty(); //gracias profe
    }

    public void showTransactions(Client self) {
        System.out.printf("-----------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s |%n", "ID", "Type", "Date", "Client name", "BookID", "Title");
        System.out.printf("-----------------------------------------------------------------------------------------------%n");
        for (Transaction toShow : TransactionRepositories.transactions){
            if (toShow.getTransactingClient() == self) {
                System.out.println(toShow);
                System.out.printf("-----------------------------------------------------------------------------------------------%n");
            }
        }
    }

    public void showBorrowedBooks() {
        int count = 1;
        for (Book showingBook : borrowedBooks) {
            if (!showingBook.getTitle().isEmpty()) {
                System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", count, showingBook.getTitle(), showingBook.getAuthor().getProfile().getName()
                        + " " + showingBook.getAuthor().getProfile().getLastName(), (showingBook.publishDate.getMonth() + 1) + "-" +
                        showingBook.publishDate.getYear());
                System.out.println("-------------------------------------------------------------------");
                count++;
            }
        }
    }

    public void setProfile(String name, String lastName, Date dateOfBirth) {
        profile = new Profile(name, lastName, dateOfBirth);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void createToken(Book askingForThisBook, Client self) {
        Token token = new Token();

        token.setTransactedBook(askingForThisBook);
        token.setDateOfTransaction(new Date());
        token.setTransactingClient(self);
        token.typeOfTransaction = "Borrow";
        token.setTID();

        TransactionRepositories.tokens.add(token);

        System.out.println(Colors.green + "Token created successfully!" + Colors.reset);
    }
}
