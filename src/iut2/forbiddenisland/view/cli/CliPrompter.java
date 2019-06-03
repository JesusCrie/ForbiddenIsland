package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.Pair;

import java.io.PrintStream;
import java.util.Scanner;

public class CliPrompter {

    private static final Scanner IN = new Scanner(System.in);
    private static final PrintStream OUT = System.out;

    @SafeVarargs
    public static void createMenu(final String prompt, final Pair<String, Runnable>... actions) {
        OUT.println(prompt);

        for (int i = 0; i < actions.length; i++) {
            OUT.println("\t" + i + " - " + actions[i].getLeft());
        }

        int res;
        do {
            res = askPositiveInt("> ", actions.length);
        } while (res == -1);

        actions[res].getRight().run();
    }

    public static int askPositiveInt(final String prompt, final int max) {
        final int res = askPositiveInt(prompt);

        if (res >= max)
            return -1;
        return res;
    }

    public static int askPositiveInt(final String prompt) {
        try {
            final int res = Integer.parseInt(askString(prompt));

            if (res < 0)
                return -1;
            return res;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String askString(final String prompt) {
        OUT.print(prompt + " ");
        return IN.nextLine();
    }
}
