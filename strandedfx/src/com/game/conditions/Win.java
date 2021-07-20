package com.game.conditions;

import com.game.startmenu.Status;

public class Win {

    public static void youWin() throws InterruptedException {
        System.out.println("Now with enough fuel to make it back home you can begin your journey home ");
        Thread.sleep(2000);
        Status.clearConsole();
        System.out.println("Refueling...");
        Thread.sleep(2000);
        Status.clearConsole();
        System.out.println("Refueling.........");
        Thread.sleep(2000);
        Status.clearConsole();
        System.out.println("Hyperspeed Liftoff...............");
        Thread.sleep(3000);
        Status.clearConsole();
        System.out.println("             _-o#&&*''''?d:>b\\_\n" +
                "          _o/\"`''  '',, dMF9MMMMMHo_\n" +
                "       .o&#'        `\"MbHMMMMMMMMMMMHo.\n" +
                "     .o\"\" '         vodM*$&&HMMMMMMMMMM?.\n" +
                "    ,'              $M&ood,~'`(&##MMMMMMH\\\n" +
                "   /               ,MMMMMMM#b?#bobMMMMHMMML\n" +
                "  &              ?MMMMMMMMMMMMMMMMM7MMM$R*Hk\n" +
                " ?$.            :MMMMMMMMMMMMMMMMMMM/HMMM|`*L\n" +
                "|               |MMMMMMMMMMMMMMMMMMMMbMH'   T,\n" +
                "$H#:            `*MMMMMMMMMMMMMMMMMMMMb#}'  `?\n" +
                "]MMH#             \"\"*\"\"\"\"*#MMMMMMMMMMMMM'    -\n" +
                "MMMMMb_                   |MMMMMMMMMMMP'     :\n" +
                "HMMMMMMMHo                 `MMMMMMMMMT       .\n" +
                "?MMMMMMMMP                  9MMMMMMMM}       -\n" +
                "-?MMMMMMM                  |MMMMMMMMM?,d-    '\n" +
                " :|MMMMMM-                 `MMMMMMMT .M|.   :\n" +
                "  .9MMM[                    &MMMMM*' `'    .\n" +
                "   :9MMk                    `MMM#\"        -\n" +
                "     &M}                     `          .-\n" +
                "      `&.                             .\n" +
                "        `~,   .                     ./\n" +
                "            . _                  .-\n" +
                "              '`--._,dd###pp=\"\"'");
        System.out.println("\n\nI see it now...Heading HOME!");

    }
}
