package Quiz;
import java.util.*;

public class QuizApplication {
    private final List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex;
    private int score;
    private final Timer timer;
    private boolean timedOut;

    public QuizApplication() {
        quizQuestions = new ArrayList<>();
        quizQuestions.add(new QuizQuestion("What is 2 + 2?", List.of("1", "2", "3", "4"), 3));
        quizQuestions.add(new QuizQuestion("What is the capital of France?", List.of("Madrid", "Berlin", "Paris", "London"), 2));
        currentQuestionIndex = 0;
        score = 0;
        timer = new Timer();
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Here is the Quiz");
        System.out.println("Press Enter to start the quiz.");
        scanner.nextLine();

        for (QuizQuestion question : quizQuestions) {
            timedOut = false;
            displayQuestion(question);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Next,");
                    timedOut = true;
                }
            }, 20000);

            while (!timedOut) {
                System.out.print("Enter your choice (1-" + question.getOptions().size() + "): ");
                int userChoice = scanner.nextInt();

                if (userChoice >= 1 && userChoice <= question.getOptions().size()) {
                    if (userChoice == question.getCorrectOption() + 1) {
                        System.out.println("Correct!\n");
                        score++;
                    } else {
                        System.out.println("Incorrect.\n");
                    }
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }

            currentQuestionIndex++;
        }

        // Display results
        System.out.println("Quiz completed!");
        System.out.println("Your Score: " + score + "/" + quizQuestions.size());

        scanner.close();
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ":");
        System.out.println(question.getQuestion());

        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    public static void main(String[] args) {
        QuizApplication quizApp = new QuizApplication();
        quizApp.startQuiz();
    }
}
