package ATM;
import java.util.Scanner;

public class ATM {
    public BankAccount userAccount;
    public Scanner scanner;
    public ATM(BankAccount account) {
        this.userAccount = account;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Check Balance\n2. Deposit\n3. Withdraw\n4. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> checkBalance();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> {
                    exit();
                    return;
                }
                default -> System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    private void checkBalance() {
        double balance = userAccount.getBalance();
        System.out.println("Current Balance: ₹" + balance);
    }

    private void deposit() {
        System.out.print("Enter the deposit amount: ₹");
        double amount = scanner.nextDouble();
        userAccount.deposit(amount);
    }

    private void withdraw() {
        System.out.print("Enter the withdrawal amount: ₹");
        double amount = scanner.nextDouble();
        userAccount.withdraw(amount);
    }

    private void exit() {
        System.out.println("Thank you for using the ATM!");
        scanner.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(15000.0); // Initial balance
        ATM atm = new ATM(account);
        atm.displayMenu();
    }
}
