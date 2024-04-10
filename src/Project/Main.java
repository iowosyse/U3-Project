package Project;

import Controllers.Menus.TitleScreen;
import Repositories.Seeder;

/**@author Cándido Ortega Martínez */
public class Main {
    public static void main(String[] args) {
        Seeder.initialize();
        TitleScreen.titleScreen();
    }
}
