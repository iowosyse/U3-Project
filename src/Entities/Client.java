package Entities;

import Repositories.TransactionRepositories;

import java.util.*;

public class Client extends User{
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Client (Permissions a, String name, String lastName, Date BD) {
        super(a);
        setProfile(name, lastName, BD);
        getPermissions()[1] = Permissions.ASK;
        setPermissionsString();
    }

    public Client() {
        super();
        getPermissions()[0] = Permissions.READ;
        getPermissions()[1] = Permissions.ASK;
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
                        + " " + showingBook.getAuthor().getProfile().getLastName(), (showingBook.getPublishDate().getMonth() + 1) + "-" +
                        showingBook.getPublishDate().getYear());
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

    public void createToken(Book tokenedBook, Client self, String type) {
        Token token = new Token();

        token.setTransactedBook(tokenedBook);
        token.setDateOfTransaction(new Date());
        token.setTransactingClient(self);
        token.setTypeOfTransaction(type);
        token.setTID();

        TransactionRepositories.tokens.add(token);
        TransactionRepositories.tokenHistory.add(token);
    }
}
