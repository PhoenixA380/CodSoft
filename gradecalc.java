package GradeCalculator;
import java.util.Scanner;

public class gradecalc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Grade Calculator!");
        System.out.print("Enter the number of subjects: ");
        int numSubjects = sc.nextInt();

        int totalMarks = 0;

        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks for Subject " + i + ": ");
            int marks = sc.nextInt();

            if (marks < 0 || marks > 100) {
                System.out.println("Invalid marks.");
                return;
            }

            totalMarks += marks;
        }

        System.out.println("Total Marks: " + totalMarks);

        double averagePercentage = (double) totalMarks / numSubjects;
        System.out.println("Average Percentage: " + averagePercentage + "%");

        String grade = calculateGrade(averagePercentage);
        System.out.println("Grade: " + grade);
        sc.close();
    }

    private static String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 95) {
            return "S";
        } else if (averagePercentage >= 90) {
            return "O";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50){
            return "D";
        } else {
            return "F";
        }
    }
}
