// Implementation of the game occurs in this class

import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Game {

    /**
     * implementation of the game happens in the main class
     * @param args default parameter of main method in java
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Layout layout = new Layout(7, 6);
        long time;
        if (args.length > 0) {
            time = TimeUnit.SECONDS.toNanos(Integer.parseInt(args[0]));
        } else {
            time = TimeUnit.SECONDS.toNanos(2);
        }
        Bot bot = new Bot(layout, time);
        while (layout.GameState() == 1) { // while the game in progress
            int iterate;
            System.out.print("Please type the column you want to drop your coin into. Your color is Red (R). Bot's color is Yellow (Y). ");
            System.out.println("In every turn, please wait a bit for bot player to make its move.");
            System.out.println("Please do not type anything while bot has the turn. If you type while bot has the turn, it changes nothing.");
            System.out.println(layout);
            if (layout.getTurn() == Layout.isPlayerTurn()) {
                System.out.print("Your move: ");
                iterate = scanner.nextInt();
            } else {
                System.out.print("Bot's Move: ");
                iterate = bot.BestIndex();
                System.out.println(iterate);
            }
            while (!layout.isPlaceable(iterate)) {
                if (layout.getTurn() == Layout.isPlayerTurn()) {
                    System.out.print("Your move: ");
                    iterate = scanner.nextInt();
                } else {
                    System.out.print("Bot's Move: ");
                    iterate = bot.BestIndex();
                    System.out.println(iterate);
                }
            }
            layout.place(iterate);
            bot.updateRoot(iterate);
        }
        int result = layout.GameState();
        System.out.println(layout);
        if (result == 3) {
            System.out.println("Bot wins.");
        } else if (result == 2) {
            System.out.println("You win.");
        } else {
            System.out.println("Tie.");
        }
    }
}