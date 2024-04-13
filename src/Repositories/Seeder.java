package Repositories;

import Controllers.*;
import Entities.*;
import java.util.Date;

/**When running the program this will pop up and start some classes and attributes*/
public class Seeder {

    /**What's up guys it's Quandle Dingle here*/
    public static void initialize(){
        Author dante = new Author();
        Date ddate = new Date();
        Date dCD = new Date();
        dCD.setDate(1);
        dCD.setMonth(0);
        dCD.setYear(1321);
        ddate.setDate(14);
        ddate.setMonth(8);
        ddate.setYear(1265);
        dante.setProfile("Dante", "Alighieri", ddate);

        Book divineComedy = new Book("what?", "The Divine Comedy", true, dCD, "B|5", dante);
        dante.getWrittenBooks().add(divineComedy);

        Author jeffKinney = new Author();
        Date jkDate = new Date();
        Date doawk = new Date();
        Date doawk2 = new Date();
        jkDate.setDate(19);
        jkDate.setMonth(1);
        jkDate.setYear(1971);
        jeffKinney.setProfile("Jeff", "Kinney", jkDate);

        doawk.setDate(1);
        doawk.setMonth(2);
        doawk.setYear(2010);
        Book diaryOfAWimpyKid1 = new Book("978-607-400-334-5", "Diary of a Wimpy Kid", true, doawk,
                "B|1", jeffKinney);

        jeffKinney.addWrittenBook(diaryOfAWimpyKid1);

        doawk2.setDate(1);
        doawk2.setMonth(0);
        doawk2.setYear(2013);
        Book diaryOfAWimpyKid2 = new Book("978-607-400-335-2", "Diary of a Wimpy Kid 2", true,
                doawk2, "B|2", jeffKinney);
        jeffKinney.addWrittenBook(diaryOfAWimpyKid2);

        Author orsonScottCard = new Author();
        Date oscDate = new Date();
        oscDate.setDate(24);
        oscDate.setMonth(7);
        oscDate.setYear(1951);
        orsonScottCard.setProfile("Orson Scott", "Card", oscDate);

        Date eg = new Date();
        eg.setDate(15);
        eg.setDate(0);
        eg.setYear(1985);
        Book endersGame = new Book("978-607-316-667-6", "Ender's Game", true, eg,
                "B|3", orsonScottCard);
        orsonScottCard.addWrittenBook(endersGame);

        Author stephenKing = new Author();
        Date skDate = new Date();
        skDate.setDate(21);
        skDate.setMonth(8);
        skDate.setYear(1947);
        stephenKing.setProfile("Stephen", "King", skDate);

        Date itd = new Date();
        itd.setDate(15);
        itd.setMonth(8);
        itd.setYear(1986);
        Book it = new Book("978-607-310-552-1", "IT", true, itd, "B|4", stephenKing);
        stephenKing.getWrittenBooks().add(it);

        BookRepositories.books.add(diaryOfAWimpyKid1);
        BookRepositories.books.add(diaryOfAWimpyKid2);
        BookRepositories.books.add(endersGame);
        BookRepositories.books.add(it);
        BookRepositories.books.add(divineComedy);

        AuthorRepositories.authors.add(jeffKinney);
        AuthorRepositories.authors.add(orsonScottCard);
        AuthorRepositories.authors.add(stephenKing);
        AuthorRepositories.authors.add(dante);

        Date candeBD = new Date();
        candeBD.setDate(11);
        candeBD.setMonth(7);
        candeBD.setYear(2005);

        Date first = new Date();
        first.setDate(20);
        first.setMonth(2);
        first.setYear(2024);

        Client cande = new Client(Permissions.READ, "Cande", "Ortega", candeBD);
        cande.setUsername("Cande chamba");
        cande.setPassword(HashPassword.hashString("c6180%"));
        UserRepositories.clients.add(cande);
        UserRepositories.users.add(cande);

        Transaction firstTransaction = new Transaction("T|0000", "Borrow", first, cande,
                endersGame);
        cande.getBorrowedBooks().add(endersGame);
        endersGame.setAvailable(false);
        firstTransaction.setTransactingClient(cande);
        TransactionRepositories.transactions.add(firstTransaction);

        Date sergioBD = new Date();
        sergioBD.setDate(8);
        sergioBD.setMonth(2);
        sergioBD.setYear(2005);

        Date second = new Date();
        second.setDate(23);
        second.setMonth(1);
        second.setYear(2024);

        Client sergio = new Client(Permissions.READ, "Sergio", "Rodriguez", sergioBD);
        sergio.setUsername("Skibidi Sergio");
        sergio.setPassword(HashPassword.hashString("s14159$"));
        UserRepositories.users.add(sergio);
        UserRepositories.clients.add(sergio);

        Transaction secondTransaction = new Transaction("T|0001", "Borrow", second, sergio, divineComedy);
        sergio.getBorrowedBooks().add(divineComedy);
        divineComedy.setAvailable(false);
        secondTransaction.setTransactingClient(sergio);
        TransactionRepositories.transactions.add(secondTransaction);

        Admin arturo = new Admin(Permissions.READ, Permissions.WRITE, Permissions.DELETE);
        Date arturoD = new Date();
        Profile arturoP = new Profile("Arturo", "Jácome", arturoD);
        arturo.setProfile(arturoP);
        arturo.setUsername(".arturogil");
        arturo.setPassword(HashPassword.hashString("a7182&"));
        arturo.setSuperAdmin(true);
        UserRepositories.users.add(arturo);
        UserRepositories.admins.add(arturo);

        Admin evilCande = new Admin(true);
        Profile evilCandeP = new Profile("Evil", "Cande", candeBD);
        evilCande.setProfile(evilCandeP);
        evilCande.setUsername("cande666");
        evilCande.setPassword(HashPassword.hashString("c110805#"));
        evilCande.setInQuarantine();
        UserRepositories.users.add(evilCande);
        UserRepositories.admins.add(evilCande);

        BookRepositories.setAvailableBooks();
        BookRepositories.setNotAvailableBooks();

        cande.createToken(it, cande, "Borrow");
    }
}
