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

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Input fields
        titleField = new JTextField();
        authorField = new JTextField();
        isbnField = new JTextField();
        searchField = new JTextField();
        JButton addButton = new JButton("Add Book");
        JButton searchButton = new JButton("Search Book");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");

        // Table setup
        String[] columns = {"Title", "Author", "ISBN", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Add components to input panel
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(isbnField);
        inputPanel.add(new JLabel("Search by Title or Author:"));
        inputPanel.add(searchField);
        inputPanel.add(addButton);

        // Add components to button panel
        buttonPanel.add(searchButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);

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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        // Save data to file on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveBooks();
            }
        });
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
