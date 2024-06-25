package Quiz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private JLabel questionLabel = new JLabel("Question: Which part of an aircraft is primarily responsible for controlling its pitch and roll during flight?");
    private JRadioButton option1 = new JRadioButton("Ailerons");
    private JRadioButton option2 = new JRadioButton("Elevators");
    private JRadioButton option3 = new JRadioButton("Rudder");
    private JButton submitButton = new JButton("Submit");

    public QuizApp() {
        setTitle("Quiz Application");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel questionPanel = new JPanel();
        questionPanel.add(questionLabel);

        JPanel optionsPanel = new JPanel();
        ButtonGroup optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);

        JPanel submitPanel = new JPanel();
        submitPanel.add(submitButton);

        add(questionPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option2.isSelected()) {
                    JOptionPane.showMessageDialog(QuizApp.this,
                            "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(QuizApp.this,
                            "Incorrect!", "Result", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp();
            app.setVisible(true);
        });
    }
}
