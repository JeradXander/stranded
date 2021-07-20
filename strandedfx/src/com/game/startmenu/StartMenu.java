package com.game.startmenu;

import java.util.Scanner;

public class StartMenu {
    public StartMenu() {

            System.out.println("Are you ready to be stranded in the Space?");
            System.out.println("                ___        ");
            System.out.println("         |      | |        ");
            System.out.println("        / \\     | |        ");
            System.out.println("      |--o--|===|-|        ");
            System.out.println("      |-----|   |B|        ");
            System.out.println("     /      \\   |C|        ");
            System.out.println("    |STRANDED|  |D|        ");
            System.out.println("   |          |=| |        ");
            System.out.println("   |          | | |        ");
            System.out.println("   |__________| |_|        ");
            System.out.println("      |@| |@|  | |         ");
            System.out.println("__________|_|_             ");
        }

    public static void entCont() {
        Scanner s = new Scanner(System.in);
        System.out.println("Press Enter to Continue");
        s.nextLine();
    }
}
