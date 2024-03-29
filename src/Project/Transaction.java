package Project;

import java.util.*;
public class Transaction {
    static Random ran = new Random();
    protected String tID = "";
    public String typeOfTransaction;
    protected Date dateOfTransaction;
    protected Client transactingClient;
    protected Book transactedBook;

    public  Transaction() {

    }

    public Transaction(String tID, String typeOfTransaction, Date dateOfTransaction, Client transactingClient, Book transactedBook) {
        this.tID = tID;
        this.typeOfTransaction = typeOfTransaction;
        this.dateOfTransaction = dateOfTransaction;
        this.transactingClient = transactingClient;
        this.setTransactedBook(transactedBook);
    }

    public void setTID() {
        int number = ran.nextInt(996) + 3;
        String base = "T|";

        tID += base + number;
    }

    public String gettID() {
        return tID;
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public Client getTransactingClient() {
        return transactingClient;
    }

    public void setTransactingClient(Client transactingClient) {
        this.transactingClient = transactingClient;
    }

    public Book getTransactedBook() {
        return transactedBook;
    }

    public void setTransactedBook(Book transactedBook) {
        this.transactedBook = transactedBook;
    }

    @Override
    public String toString() {
        return String.format("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s |", gettID(),
                typeOfTransaction, getDateOfTransaction().getDate() + "-" +  (getDateOfTransaction().getMonth() + 1) + "-" +
                        getDateOfTransaction().getYear(), getTransactingClient().getProfile().getName() +
                        " " + getTransactingClient().getProfile().getLastName(), getTransactedBook().getBookID(),
                        getTransactedBook().getTitle());
    }
}
