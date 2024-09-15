package TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> tasks = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(tasks);
    private JTextField taskInput = new JTextField(20);
    private JButton addButton = new JButton("Add Task");
    private JButton updateButton = new JButton("Update Task");
    private JButton deleteButton = new JButton("Delete Task");

    public ToDoListApp() {
        setTitle("To-Do List Application");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window

        // Define colors and fonts
        Color primaryColor = new Color(33, 150, 243); // Blue
        Color white = Color.WHITE; // White
        Color addButtonColor = new Color(76, 175, 80); // Green for Add
        Color updateButtonColor = new Color(255, 193, 7); // Yellow for Update
        Color deleteButtonColor = new Color(244, 67, 54); // Red for Delete
        Color buttonTextColor = Color.WHITE;
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        // Create and set up the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(white);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel taskLabel = new JLabel("Task:");
        taskLabel.setFont(inputFont);
        inputPanel.add(taskLabel, gbc);

        // Input Field
        gbc.gridx = 1;
        gbc.gridy = 0;
        taskInput.setFont(inputFont);
        inputPanel.add(taskInput, gbc);

        // Add Button
        addButton.setFont(buttonFont);
        addButton.setBackground(addButtonColor);
        addButton.setForeground(buttonTextColor);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setToolTipText("Add a new task");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(addButton, gbc);

        // Update Button
        updateButton.setFont(buttonFont);
        updateButton.setBackground(updateButtonColor);
        updateButton.setForeground(buttonTextColor);
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.setToolTipText("Update the selected task");
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(updateButton, gbc);

        // Delete Button
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(deleteButtonColor);
        deleteButton.setForeground(buttonTextColor);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setToolTipText("Delete the selected task");
        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(deleteButton, gbc);

        // Task List
        taskList.setFont(inputFont);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primaryColor), "Tasks"));

        // Add components to the main frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add button action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskInput.getText().trim();
                if (!task.isEmpty()) {
                    tasks.addElement(task);
                    taskInput.setText("");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String updatedTask = taskInput.getText().trim();
                    if (!updatedTask.isEmpty()) {
                        tasks.set(selectedIndex, updatedTask);
                        taskInput.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(ToDoListApp.this, "Please select a task to update");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    tasks.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(ToDoListApp.this, "Please select a task to delete");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoListApp app = new ToDoListApp();
            app.setVisible(true);
        });
    }
}