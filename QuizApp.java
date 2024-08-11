package Quiz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizApp extends JFrame {
    private final String[][] questions = {
            {"Which part of an aircraft controls the pitch?", "Elevators", "Ailerons", "Rudder", "Flaps", "Elevators"},
            {"What is the primary purpose of the ailerons on an aircraft?", "Control roll", "Control pitch", "Control yaw", "Increase speed", "Control roll"},
            {"Which flight instrument shows the aircraft's altitude?", "Altimeter", "Airspeed Indicator", "Heading Indicator", "Vertical Speed Indicator", "Altimeter"},
            {"What does the rudder control on an aircraft?", "Yaw", "Roll", "Pitch", "Altitude", "Yaw"},
            {"Which component is used to adjust the aircraft's speed?", "Throttle", "Elevators", "Ailerons", "Rudder", "Throttle"},
            {"What is the purpose of the aircraft's flaps?", "Increase lift", "Decrease drag", "Control pitch", "Increase speed", "Increase lift"},
            {"Which instrument helps the pilot maintain straight and level flight?", "Artificial Horizon", "Airspeed Indicator", "Altimeter", "Vertical Speed Indicator", "Artificial Horizon"},
            {"What does the Heading Indicator show?", "Aircraft's direction", "Altitude", "Speed", "Pitch", "Aircraft's direction"},
            {"Which part of the aircraft is primarily responsible for controlling its roll?", "Ailerons", "Elevators", "Rudder", "Flaps", "Ailerons"},
            {"What is the primary function of the aircraft's autopilot?", "Maintain flight path", "Control speed", "Control pitch", "Monitor fuel", "Maintain flight path"}
    };

    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel, questionNumberLabel, remainingQuestionsLabel;
    private JRadioButton option1, option2, option3, option4;
    private JButton submitButton;
    private JPanel optionsPanel;
    private String correctAnswer;

    public QuizApp() {
        setTitle("Aviation Quiz Application");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colors and Fonts
        Color backgroundColor = new Color(240, 248, 255); // Alice Blue
        Color headerColor = new Color(70, 130, 180); // Steel Blue
        Color optionPanelColor = new Color(255, 255, 255); // White
        Color buttonColor = new Color(50, 205, 50); // Lime Green
        Color buttonTextColor = Color.WHITE;

        Font questionFont = new Font("Arial", Font.BOLD, 18);
        Font optionFont = new Font("Arial", Font.PLAIN, 16);
        Font scoreFont = new Font("Arial", Font.BOLD, 14);

        getContentPane().setBackground(backgroundColor);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(headerColor);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.setLayout(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setFont(questionFont);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(questionLabel, BorderLayout.CENTER);

        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(scoreFont);
        questionNumberLabel.setForeground(Color.WHITE);
        questionNumberLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(questionNumberLabel, BorderLayout.NORTH);

        remainingQuestionsLabel = new JLabel();
        remainingQuestionsLabel.setFont(scoreFont);
        remainingQuestionsLabel.setForeground(Color.WHITE);
        remainingQuestionsLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(remainingQuestionsLabel, BorderLayout.SOUTH);

        // Options Panel
        optionsPanel = new JPanel();
        optionsPanel.setBackground(optionPanelColor);
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        optionsPanel.setLayout(new GridLayout(4, 1, 5, 5));

        ButtonGroup optionsGroup = new ButtonGroup();
        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();
        option1.setFont(optionFont);
        option2.setFont(optionFont);
        option3.setFont(optionFont);
        option4.setFont(optionFont);
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);
        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);
        optionsPanel.add(option4);

        // Submit Panel
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(headerColor);
        submitPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        submitButton = new JButton("Submit");
        submitButton.setFont(optionFont);
        submitButton.setBackground(buttonColor);
        submitButton.setForeground(buttonTextColor);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitPanel.add(submitButton);

        add(headerPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        displayQuestion(currentQuestionIndex);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAnswer = getSelectedAnswer();
                if (selectedAnswer != null) {
                    checkAnswer(selectedAnswer);
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        displayQuestion(currentQuestionIndex);
                    } else {
                        showFinalScore();
                    }
                } else {
                    JOptionPane.showMessageDialog(QuizApp.this,
                            "Please select an option.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void displayQuestion(int index) {
        String[] options = {
                questions[index][1],
                questions[index][2],
                questions[index][3],
                questions[index][4]
        };

        // Shuffle options
        List<String> optionList = new ArrayList<>();
        for (String option : options) {
            optionList.add(option);
        }
        Collections.shuffle(optionList);

        option1.setText(optionList.get(0));
        option2.setText(optionList.get(1));
        option3.setText(optionList.get(2));
        option4.setText(optionList.get(3));

        // Determine the correct answer based on shuffled options
        correctAnswer = questions[index][5];

        // Ensure correct answer is properly matched to the shuffled options
        if (correctAnswer.equals(option1.getText())) {
            option1.setActionCommand(correctAnswer);
        } else if (correctAnswer.equals(option2.getText())) {
            option2.setActionCommand(correctAnswer);
        } else if (correctAnswer.equals(option3.getText())) {
            option3.setActionCommand(correctAnswer);
        } else {
            option4.setActionCommand(correctAnswer);
        }

        questionLabel.setText(questions[index][0]);
        questionNumberLabel.setText("Question " + (index + 1) + " of " + questions.length);
    }

    private String getSelectedAnswer() {
        if (option1.isSelected()) {
            return option1.getActionCommand();
        } else if (option2.isSelected()) {
            return option2.getActionCommand();
        } else if (option3.isSelected()) {
            return option3.getActionCommand();
        } else if (option4.isSelected()) {
            return option4.getActionCommand();
        }
        return null;
    }

    private void checkAnswer(String answer) {
        if (answer.equals(correctAnswer)) {
            score++;
        }
    }

    private void showFinalScore() {
        JOptionPane.showMessageDialog(QuizApp.this,
                "Quiz complete! Your score: " + score + "/" + questions.length,
                "Final Score", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp();
            app.setLocationRelativeTo(null); // Center the window
            app.setVisible(true);
        });
    }
}
