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

        // Create and set up the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Task:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(taskInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(updateButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(deleteButton, gbc);

        // Add components to the main frame
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add button action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskInput.getText();
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
                    String updatedTask = taskInput.getText();
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
