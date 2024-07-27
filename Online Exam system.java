import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineExamSystem {
    // HashMap to store user credentials
    static Map<String, String> users = new HashMap<>();
    // HashMap to store user profiles
    static Map<String, String> profiles = new HashMap<>();
    // Boolean to check if user is logged in
    static boolean isLoggedIn = false;
    // Store the currently logged-in user
    static String currentUser = null;

    public static void main(String[] args) {
        // Adding a test user for demonstration
        users.put("testUser", "password");
        profiles.put("testUser", "Profile details of testUser");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!isLoggedIn) {
                System.out.println("\nWelcome to the Online Exam System");
                System.out.println("1. Login");
                System.out.println("2. Create User");
                System.out.println("3. Exit");
                System.out.print("Please enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        createUser(scanner);
                        break;
                    case 3:
                        System.out.println("Thank you for using the Online Exam System. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("\nWelcome, " + currentUser);
                System.out.println("1. Update Profile");
                System.out.println("2. Change Password");
                System.out.println("3. Take Exam");
                System.out.println("4. Logout");
                System.out.print("Please enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        updateProfile(scanner);
                        break;
                    case 2:
                        changePassword(scanner);
                        break;
                    case 3:
                        takeExam(scanner);
                        break;
                    case 4:
                        logout();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }

    // Method for user login
    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            isLoggedIn = true;
            currentUser = username;
            System.out.println("Login successful! Welcome, " + username);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    // Method for creating a new user
    private static void createUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please try a different username.");
            return;
        }
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        users.put(username, password);
        profiles.put(username, "Profile details of " + username);
        System.out.println("User created successfully! You can now login with your new credentials.");
    }

    // Method for updating user profile
    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter new profile details: ");
        String profile = scanner.nextLine();
        profiles.put(currentUser, profile);
        System.out.println("Profile updated successfully!");
    }

    // Method for changing user password
    private static void changePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        users.put(currentUser, newPassword);
        System.out.println("Password changed successfully!");
    }

    // Method for taking the exam
    private static void takeExam(Scanner scanner) {
        String[] questions = {
            "Question 1: What is 2 + 2?",
            "Question 2: What is the capital of France?"
        };
        String[][] options = {
            {"1. 3", "2. 4", "3. 5", "4. 6"},
            {"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}
        };
        int[] correctAnswers = {2, 3};

        int[] userAnswers = new int[questions.length];
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 60000; // 1 minute timer

        System.out.println("\nStarting the exam. You have 1 minute to complete it.");

        for (int i = 0; i < questions.length; i++) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("Time is up! Auto-submitting the exam...");
                break;
            }

            long timeLeft = endTime - System.currentTimeMillis();
            long minutesLeft = timeLeft / 60000;
            long secondsLeft = (timeLeft % 60000) / 1000;
            System.out.printf("Time left: %02d:%02d\n", minutesLeft, secondsLeft);

            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }

            System.out.print("Select your answer (1-4): ");
            userAnswers[i] = scanner.nextInt();
        }

        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }

        System.out.println("\nExam completed! Your score: " + score + "/" + questions.length);
        congratulateUser(score, questions.length);
    }

    // Method to congratulate the user based on their score
    private static void congratulateUser(int score, int totalQuestions) {
        double percentage = (double) score / totalQuestions * 100;

        if (percentage == 100) {
            System.out.println("Excellent work! You got a perfect score!");
        } else if (percentage >= 80) {
            System.out.println("Great job! You scored " + percentage + "%.");
        } else if (percentage >= 50) {
            System.out.println("Good effort! You scored " + percentage + "%.");
        } else {
            System.out.println("You scored " + percentage + "%. Keep trying to improve!");
        }
    }

    // Method for logging out
    private static void logout() {
        System.out.println("Logging out...");
        isLoggedIn = false;
        currentUser = null;
        System.out.println("Logged out successfully!");
    }
}
