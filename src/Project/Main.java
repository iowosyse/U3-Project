package Project;

import Controllers.Menus.TitleScreen;
import Controllers.TransactionController;
import Repositories.Seeder;
import Repositories.TransactionRepositories;

public class Main {
    public static void main(String[] args) {
        Seeder.initialize();
        TitleScreen.titleScreen();
    }
}
