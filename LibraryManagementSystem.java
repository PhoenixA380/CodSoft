package Library;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class LibraryManagementSystem extends JFrame {
    private ArrayList<Book> books = new ArrayList<>();
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, authorField, isbnField, searchField;
    private JTextArea outputArea;

    private Color primaryColor = new Color(33, 150, 243); // Blue
    private Color secondaryColor = new Color(76, 175, 80); // Green
    private Color dangerColor = new Color(244, 67, 54); // Red
    private Color backgroundColor = new Color(240, 248, 255); // Alice Blue

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window
        getContentPane().setBackground(backgroundColor);

        // Create panels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(backgroundColor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(backgroundColor);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        outputPanel.setBackground(backgroundColor);

        // Input fields
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        isbnField = new JTextField(20);
        searchField = new JTextField(20);
        JButton addButton = new JButton("Add Book");
        JButton searchButton = new JButton("Search Book");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton deleteButton = new JButton("Delete Book"); // New Delete Button

        // Style buttons
        styleButton(addButton, secondaryColor);
        styleButton(searchButton, primaryColor);
        styleButton(borrowButton, primaryColor);
        styleButton(returnButton, dangerColor);
        styleButton(deleteButton, dangerColor); // Style Delete Button

        // Table setup
        String[] columns = {"Title", "Author", "ISBN", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Add components to input panel
        addInputLabel(inputPanel, "Title:", titleField);
        addInputLabel(inputPanel, "Author:", authorField);
        addInputLabel(inputPanel, "ISBN:", isbnField);
        addInputLabel(inputPanel, "Search by Title or Author:", searchField);
        inputPanel.add(addButton, getConstraints(0, 4, 2, 1));

        // Add components to button panel
        buttonPanel.add(searchButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(deleteButton); // Add Delete Button

        // Add components to output panel
        outputPanel.add(new JLabel("Output:"), BorderLayout.NORTH);
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        // Add panels to main frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(outputPanel, BorderLayout.EAST);

        // Load initial data from file
        loadBooks();

        // Button actions
        addButton.addActionListener(e -> addBook());
        searchButton.addActionListener(e -> searchBook());
        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());
        deleteButton.addActionListener(e -> deleteBook()); // Add ActionListener for Delete Button

        // Save data to file on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveBooks();
            }
        });
    }

    // Helper method to style buttons
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setToolTipText("Click to " + button.getText());
    }

    // Helper method to create input labels with fields
    private void addInputLabel(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(primaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = panel.getComponentCount() / 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(label, gbc);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // Helper method to get GridBagConstraints
    private GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    // Method to add a new book
    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.");
            return;
        }

        Book newBook = new Book(title, author, isbn, true);
        books.add(newBook);
        tableModel.addRow(new Object[]{newBook.getTitle(), newBook.getAuthor(), newBook.getIsbn(), "Available"});
        outputArea.append("Book added: " + newBook + "\n");

        // Clear input fields
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
    }

    // Method to search for a book by title or author
    private void searchBook() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
            return;
        }

        outputArea.setText("");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchText) || book.getAuthor().toLowerCase().contains(searchText)) {
                outputArea.append(book + "\n");
                found = true;
            }
        }

        if (!found) {
            outputArea.append("No books found matching the search criteria.\n");
        }

        // Clear search field
        searchField.setText("");
    }

    // Method to borrow a book
    private void borrowBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to borrow.");
            return;
        }

        Book selectedBook = books.get(selectedRow);
        if (!selectedBook.isAvailable()) {
            JOptionPane.showMessageDialog(this, "This book is already borrowed.");
            return;
        }

        selectedBook.setAvailable(false);
        tableModel.setValueAt("Borrowed", selectedRow, 3);
        outputArea.append("Book borrowed: " + selectedBook + "\n");
    }

    // Method to return a book
    private void returnBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to return.");
            return;
        }

        Book selectedBook = books.get(selectedRow);
        if (selectedBook.isAvailable()) {
            JOptionPane.showMessageDialog(this, "This book is already available.");
            return;
        }

        selectedBook.setAvailable(true);
        tableModel.setValueAt("Available", selectedRow, 3);
        outputArea.append("Book returned: " + selectedBook + "\n");
    }

    // Method to delete a book
    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            books.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            outputArea.append("Book deleted.\n");
        }
    }

    // Method to load books from file
    private void loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("books.dat"))) {
            books = (ArrayList<Book>) ois.readObject();
            updateTable();
            outputArea.append("Books loaded from file.\n");
        } catch (FileNotFoundException e) {
            outputArea.append("No existing library data found.\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            outputArea.append("Error loading books from file.\n");
        }
    }

    // Method to save books to file
    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.dat"))) {
            oos.writeObject(books);
            outputArea.append("Books saved to file.\n");
        } catch (IOException e) {
            e.printStackTrace();
            outputArea.append("Error saving books to file.\n");
        }
    }

    // Method to update the JTable with current book data
    private void updateTable() {
        tableModel.setRowCount(0);
        for (Book book : books) {
            String status = book.isAvailable() ? "Available" : "Borrowed";
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getIsbn(), status});
        }
    }

    // Book class representing a book
    private static class Book implements Serializable {
        private String title;
        private String author;
        private String isbn;
        private boolean available;

        public Book(String title, String author, String isbn, boolean available) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.available = available;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public String toString() {
            return title + " by " + author + " (ISBN: " + isbn + ")";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManagementSystem app = new LibraryManagementSystem();
            app.setVisible(true);
        });
    }
}
