package Controllers;

import Project.*;
import Repositories.*;
import java.util.*;

public class TransactionController {
    static Scanner sc = new Scanner(System.in);

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
        System.out.print(">> ");
        option = sc.nextInt();
        sc.nextLine();
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
                System.out.print(">> ");
                aux = sc.nextInt();
                sc.nextLine();

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
                aux = sc.nextInt();
                sc.nextLine();

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
            } default -> System.out.println("Not an option");
        }
    }

    /**Auxiliary method to set the limits while sorting transactions by date*/
    private static void setDates(Date botLimit, Date topLimit) {
        int aux;

        System.out.println("Since when do you want to show the transactions?");
        System.out.print("\tDay >> ");
        aux = sc.nextInt();
        botLimit.setDate(aux);
        sc.nextLine();

        System.out.print("\tMonth(numeric) >> ");
        aux = sc.nextInt();
        botLimit.setMonth(aux);
        sc.nextLine();

        System.out.print("\tYear >> ");
        aux = sc.nextInt();
        botLimit.setYear(aux);
        sc.nextLine();

        botLimit.setHours(00);
        botLimit.setMinutes(00);
        botLimit.setSeconds(00);

        System.out.println("Until when do you want to show the transactions?");
        System.out.print("\tDay >> ");
        aux = sc.nextInt();
        topLimit.setDate(aux);
        sc.nextLine();

        System.out.print("\tMonth(numeric) >> ");
        aux = sc.nextInt();
        topLimit.setMonth(aux);
        sc.nextLine();

        System.out.print("\tYear >> ");
        aux = sc.nextInt();
        topLimit.setYear(aux);
        sc.nextLine();

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
        String verification;

        showTokens();
        try {
            System.out.println("Which token do you want to validate?");
            System.out.print(">> ");
            aux = sc.nextInt();
            sc.nextLine();

            toValidate = TransactionRepositories.tokens.get(aux - 1);

            System.out.println(toValidate.tokenToString());
            do {
                System.out.println("Are you sure you want to validate this token?");
                System.out.print("Yes or no >> ");
                verification = sc.nextLine();
            } while (!verification.equalsIgnoreCase("yes") && !verification.equalsIgnoreCase("no"));

            if (verification.equalsIgnoreCase("yes")) {
                toValidate.validate();
                TransactionRepositories.tokens.remove(toValidate);
                TransactionRepositories.transactions.add(toValidate);
                System.out.println("Token validated successfully!");
            } else
                System.out.println("Token not validated;");
        } catch (Exception e) {
            System.err.println("-- Invalid input --");
        }
    }

    @Deprecated
    public static void createTransaction() {
        int option, aux;
        Transaction newTransaction = new Transaction();
        Client transactingClient;
        Date dateOfTransaction = new Date();

        System.out.println("==============================="); //ñ
        System.out.println("1. Lend a book.");
        System.out.println("2. Return a book.");
        System.out.println("0. Go back.");
        System.out.print(">> ");
        option = sc.nextInt();
        sc.nextLine();
        System.out.println("===============================");

        if (option == 1) {
            Book toLend;

            newTransaction.setTID();

            System.out.println("Transaction date set to today.");
            System.out.println("------------------------------");
            dateOfTransaction.setYear(2024);

            BookRepositories.showAvailableBooks();
            System.out.println("Which book do you want to lend?");
            System.out.print(">> ");
            aux = sc.nextInt();
            sc.nextLine();

            toLend = BookRepositories.availableBooks.get(aux - 1);

            ClientController.showClients(false);
            System.out.println("To whom do you want to lend it?");
            System.out.print(">> ");
            aux = sc.nextInt();
            sc.nextLine();
            transactingClient = UserRepositories.clients.get(aux - 1);

            if (aux - 1 > BookRepositories.availableBooks.size() || aux < 0)
                System.out.println("That book does not exists.");
            else {
                if (transactingClient.getNumberOfBorrowedBooks() == 3) {
                    System.out.println("---This client must return a book before borrowing another one.---");
                } else {
                    transactingClient.getBorrowedBooks().add(toLend);

                    newTransaction.setTransactingClient(transactingClient);
                    newTransaction.setDateOfTransaction(dateOfTransaction);
                    newTransaction.setTransactedBook(toLend);
                    newTransaction.typeOfTransaction = "Borrow";
                    toLend.isAvailable = false;

                    BookRepositories.notAvailableBooks.add(toLend);
                    BookRepositories.availableBooks.remove(toLend);
                    //transactingClient.getBorrowedBooks().add(BookRepositories.addPhantomBook());
                    TransactionRepositories.transactions.add(newTransaction);
                }
            }
        } else if (option == 2) {
            Book toReturn;

            newTransaction.setTID();
            newTransaction.typeOfTransaction = "Return";

            System.out.println("Transaction date set to today.");
            System.out.println("------------------------------");
            dateOfTransaction.setYear(2024);

            ClientController.showClients(true);
            System.out.println("Who wants to return a book?");
            System.out.print(">> ");
            aux = sc.nextInt();
            sc.nextLine();
            transactingClient = UserRepositories.clients.get(aux - 1);

            if (transactingClient.getBorrowedBooks().isEmpty())
                System.out.println("This client has no books");
            else {
                Book template;
                int trueIndex = 0;

                transactingClient.showBorrowedBooks();
                System.out.println("What books do they want to return?");
                System.out.print(">> ");
                aux = sc.nextInt();
                sc.nextLine();

                template = transactingClient.getBorrowedBooks().get(aux - 1);

                for (int i = aux; template.getTitle().isEmpty() && trueIndex != aux; i ++) {
                    template = transactingClient.getBorrowedBooks().get(i - 1);
                    if (!template.getTitle().isEmpty()) {
                        trueIndex ++;
                    }
                } /*Since we need to create blank books, the right index will be hard to be found
                so this loop serves as the 'searcher' for the actual index */

                toReturn = template;
                toReturn.isAvailable = true;

                transactingClient.getBorrowedBooks().remove(toReturn);
                newTransaction.setDateOfTransaction(dateOfTransaction);
                newTransaction.setTransactingClient(transactingClient);
                newTransaction.setTransactedBook(toReturn);

                BookRepositories.availableBooks.add(toReturn);
                BookRepositories.notAvailableBooks.remove(toReturn);
                TransactionRepositories.transactions.add(newTransaction);
            }

        } else if (option == 0) {
            System.out.println("Going back...");
        } else {
            System.out.println("Not an option");
        }
        System.out.println("===============================");
    }
}
