package Project;

import Controllers.Menus.TitleScreen;
import Repositories.Seeder;

public class Main {
    public static void main(String[] args) {
        Seeder.initialize();
        TitleScreen.titleScreen();
    }
}
