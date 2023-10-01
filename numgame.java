package NumberGame;
import java.util.Random;
import java.util.Scanner;

public class numgame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 10;
        int rounds = 0;
        int totalAttempts = 0;
        int totalRoundsWon = 0;

        System.out.println("You need to guess the number between " + minRange + " and " + maxRange + ".");

        boolean playAgain = true;

        while (playAgain) {
            int numberToGuess = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            boolean roundWon = false;

            System.out.println("\nRound " + (rounds + 1) + ":");
            System.out.println("You have " + maxAttempts + " attempts to guess the number.");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attempts++;
                totalAttempts++;

                if (userGuess == numberToGuess) {
                    System.out.println("You guessed it right (" + numberToGuess + ") in " + attempts + " attempts.");
                    roundWon = true;
                    totalRoundsWon++;
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Nope, too low.");
                } else {
                    System.out.println("Nope, too high.");
                }
            }

            if (!roundWon) {
                System.out.println("maximum attempts reached: " + numberToGuess);
            }

            rounds++;

            System.out.print("Do you want to play again? (y/n): ");
            String playAgainChoice = sc.next().toLowerCase();
            playAgain = playAgainChoice.equals("y") || playAgainChoice.equals("n");
        }

        System.out.println("\nGame Over!");
        System.out.println("Total Rounds: " + rounds);
        System.out.println("Total Rounds Won: " + totalRoundsWon);
        System.out.println("Total Attempts: " + totalAttempts);

        sc.close();
    }
}
