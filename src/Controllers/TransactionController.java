package Controllers;

import Project.*;
import Repositories.*;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;
import java.util.*;

public class TransactionController {

    /**Selects an option on deciding how to print the transactions and then shows them*/
    public static void showTransactions() {
        int option, aux;
        Date botLimit = new Date(), topLimit = new Date();

        System.out.println("===============================");
        System.out.println("How do you want to show the transactions?");
        System.out.println("1. By date range");
        System.out.println("2. By client");
        System.out.println("3. By book");
        System.out.println("4. All transactions");
        option = ConsoleReader.readInteger();
        System.out.println("===============================");

        switch (option) {
            case 1 -> {
                setDates(botLimit, topLimit);
                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                System.out.printf("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s |%n", "ID", "Type", "Date", "Client name", "BookID", "Title");
                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                for (Transaction toShow : TransactionRepositories.transactions){
                    if (toShow.getDateOfTransaction().after(botLimit) && toShow.getDateOfTransaction().before(topLimit)) {
                        System.out.println(toShow);
                        System.out.printf("-----------------------------------------------------------------------------------------------%n");
                    }
                }
            } case 2 -> {
                ClientController.showClients(false);
                System.out.println("Of whom do you want to see their transactions?");
                aux = ConsoleReader.readInteger();

                Client theClient = UserRepositories.clients.get(aux - 1);

                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                System.out.printf("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s |%n", "ID", "Type", "Date", "Client name", "BookID", "Title");
                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                for (Transaction toShow : TransactionRepositories.transactions){
                    if (toShow.getTransactingClient() == theClient) {
                        System.out.println(toShow);
                        System.out.printf("-----------------------------------------------------------------------------------------------%n");
                    }
                }
            } case 3 -> {
                BookController.showBooks(3);
                System.out.println("Of which book do you want to show its transactions?");
                aux = ConsoleReader.readInteger();

                Book theBook = BookRepositories.books.get(aux - 1);

                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                System.out.printf("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s |%n", "ID", "Type", "Date", "Client name", "BookID", "Title");
                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                for (Transaction toShow : TransactionRepositories.transactions){
                    if (toShow.getTransactedBook() == theBook) {
                        System.out.println(toShow);
                        System.out.printf("-----------------------------------------------------------------------------------------------%n");
                    }
                }
            } case 4 -> {
                System.out.printf("-----------------------------------------------------------------------------------------------%n");
                System.out.printf("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s |%n", "ID", "Type", "Date", "Client name", "BookID", "Title");
                System.out.printf("-----------------------------------------------------------------------------------------------%n");

                for (Transaction toShow : TransactionRepositories.transactions) {
                    System.out.println(toShow);
                    System.out.printf("-----------------------------------------------------------------------------------------------%n");
                }
            } default -> System.out.println(Colors.yellow + "Not an option" + Colors.reset);
        }
    }

    /**Auxiliary method to set the limits while sorting transactions by date*/
    private static void setDates(Date botLimit, Date topLimit) {
        int aux;

        System.out.println("Since when do you want to show the transactions?");
        System.out.println("\tDay");
        aux = ConsoleReader.readInteger();
        botLimit.setDate(aux);

        System.out.println("\tMonth (numeric)");
        aux = ConsoleReader.readInteger();
        botLimit.setMonth(aux - 1);

        System.out.print("\tYear");
        aux = ConsoleReader.readInteger();
        botLimit.setYear(aux);

        botLimit.setHours(00);
        botLimit.setMinutes(00);
        botLimit.setSeconds(00);

        System.out.println("Until when do you want to show the transactions?");
        System.out.print("\tDay");
        aux = ConsoleReader.readInteger();
        topLimit.setDate(aux);

        System.out.print("\tMonth (numeric)");
        aux = ConsoleReader.readInteger();
        topLimit.setMonth(aux);

        System.out.print("\tYear");
        aux = ConsoleReader.readInteger();
        topLimit.setYear(aux);

        topLimit.setHours(23);
        topLimit.setMinutes(59);
        topLimit.setSeconds(59);

    }

    private static void showTokens() {
        int i = 1;

        System.out.printf("| %-3s | %-18s %n", "No.", "Token data");
        for (Token toShow : TransactionRepositories.tokens) {
            System.out.printf("| %-3s | %-18s %n", i, toShow.tokenToString());
            System.out.printf("----------------------------------------------------------------------%n");
            i ++;
        }
    }

    public static void validateToken() {
        int aux;
        Token toValidate;

        showTokens();
        do  {
            System.out.println("Which token do you want to validate?");
            System.out.println("Enter 0 to go back.");
            System.out.print(">> ");
            aux = ConsoleReader.readInteger();
        } while (aux - 1 > TransactionRepositories.tokens.size() || aux < 0);

        if (aux != 0) {
            toValidate = TransactionRepositories.tokens.get(aux - 1);

            if (toValidate.getTransactedBook().isAvailable() && toValidate.getTypeOfTransaction().equalsIgnoreCase("Borrow")) {
                System.out.println(toValidate.tokenToString());
                makeSureAndFinish(toValidate);
            } else if(toValidate.getTypeOfTransaction().equalsIgnoreCase("Return")) {
                makeSureAndFinish(toValidate);
            } else
                System.out.println(Colors.yellow + "This book is not available." + Colors.reset);
        } else
            System.out.println("Going back...");
    }

    public static void makeSureAndFinish(Token toValidate) {
        String verification;
        do {
            System.out.println(Colors.yellow + "Are you sure you want to validate this token?" + Colors.reset);
            System.out.print("Yes or no >> ");
            verification = ConsoleReader.readString();
        } while (!verification.equalsIgnoreCase("yes") && !verification.equalsIgnoreCase("no"));

        if (verification.equalsIgnoreCase("yes")) {
            toValidate.validate();
            TransactionRepositories.tokens.remove(toValidate);
            TransactionRepositories.transactions.add(toValidate);
            System.out.println(Colors.green + "Token validated successfully!" + Colors.reset);
        } else
            System.out.println("Token not validated.");
    }

    public static void showTokenHistory() {
        if (TransactionRepositories.tokenHistory.isEmpty()) {
            System.out.println("No token has been created.");
        } else {
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-9s | %-6s | %-10s | %-20s | %-9s | %-22s | %-9s |%n", "ID", "Type", "Date", "Client name",
                    "BookID", "Title", "Validated");
            System.out.println("-----------------------------------------------------------------------------------------------------------");

            for (Token toShow : TransactionRepositories.tokenHistory) {
                System.out.println(toShow.dataToString());
                System.out.println("-----------------------------------------------------------------------------------------------------------");
            }
        }
    }

    public static void createTransaction() {
        int option, aux;
        Transaction newTransaction = new Transaction();
        Client transactingClient;
        Date dateOfTransaction = new Date();

        System.out.println("==============================="); //ñ
        System.out.println("1. Lend a book.");
        System.out.println("2. Return a book.");
        System.out.println("0. Go back.");
        option = ConsoleReader.readInteger();
        System.out.println("===============================");

        if (option == 1) {
            Book toLend;

            newTransaction.setTID();

            System.out.println("Transaction date set to today.");
            System.out.println("------------------------------");
            dateOfTransaction.setYear(2024);

            BookRepositories.showAvailableBooks();
            do {
                System.out.println("Which book do you want to lend?");
                aux = ConsoleReader.readInteger();
            } while (aux - 1 > BookRepositories.availableBooks.size());

            toLend = BookRepositories.availableBooks.get(aux - 1);

            ClientController.showClients(false);
            do {
                System.out.println("To whom do you want to lend it?");
                aux = ConsoleReader.readInteger();
            } while (aux - 1 > UserRepositories.clients.size());

            transactingClient = UserRepositories.clients.get(aux - 1);

            if (aux - 1 > BookRepositories.availableBooks.size() || aux < 0)
                System.out.println("That book does not exists.");
            else {
                if (transactingClient.getNumberOfBorrowedBooks() == 3) {
                    System.out.println(Colors.yellow + "---This client must return a book before borrowing another one.---" + Colors.reset);
                } else {
                    transactingClient.getBorrowedBooks().add(toLend);

                    newTransaction.setTransactingClient(transactingClient);
                    newTransaction.setDateOfTransaction(dateOfTransaction);
                    newTransaction.setTransactedBook(toLend);
                    newTransaction.setTypeOfTransaction("Borrow");
                    toLend.setAvailable(false);

                    BookRepositories.notAvailableBooks.add(toLend);
                    BookRepositories.availableBooks.remove(toLend);
                    TransactionRepositories.transactions.add(newTransaction);

                    System.out.println(Colors.green + "Book lent successfully!" + Colors.reset);
                }
            }
        } else if (option == 2) {
            Book toReturn;

            newTransaction.setTID();
            newTransaction.setTypeOfTransaction("Return");

            System.out.println("Transaction date set to today.");
            System.out.println("------------------------------");
            dateOfTransaction.setYear(2024);

            ClientController.showClients(true);
            do {
                System.out.println("Who wants to return a book?");
                aux = ConsoleReader.readInteger();
            } while (aux - 1 > UserRepositories.clients.size());
            transactingClient = UserRepositories.clients.get(aux - 1);

            if (transactingClient.getBorrowedBooks().isEmpty())
                System.out.println(Colors.yellow + "This client has no books." + Colors.reset);
            else {
                transactingClient.showBorrowedBooks();
                do {
                    System.out.println("What book do they want to return?");
                    aux = ConsoleReader.readInteger();
                } while (aux - 1 > transactingClient.getBorrowedBooks().size());

                toReturn = transactingClient.getBorrowedBooks().get(aux - 1);
                toReturn.setAvailable(true);

                transactingClient.getBorrowedBooks().remove(toReturn);
                newTransaction.setDateOfTransaction(dateOfTransaction);
                newTransaction.setTransactingClient(transactingClient);
                newTransaction.setTransactedBook(toReturn);

                BookRepositories.availableBooks.add(toReturn);
                BookRepositories.notAvailableBooks.remove(toReturn);
                TransactionRepositories.transactions.add(newTransaction);

                System.out.println(Colors.green + "Book returned successfully!" + Colors.reset);
            }

        } else if (option == 0) {
            System.out.println("Going back...");
        } else {
            System.out.println(Colors.yellow + "Not an option" + Colors.reset);
        }
        System.out.println("===============================");
    }
}
