import java.util.List;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This is the driver class that displays the GUI of the program.
 * @author: Zijie Huang
 * @date: 09/29/2023
 */
public class DirectoryDriver extends JFrame {
    /**
     * The directory that stores the students' info.
     */
    private Directory directory;

    /**
     * The add button that are used in the GUI.
     */
    private JButton addButton;

    /**
     * The delete button that are used in the GUI.
     */
    private JButton deleteButton;

    /**
     * The search by andrewID button that are used in the GUI.
     */
    private JButton byAndrewIDButton;

    /**
     * The search by first name button that are used in the GUI.
     */
    private JButton byFirstNameButton;

    /**
     * The search by last name button that are used in the GUI.
     */
    private JButton byLastNameButton;

    /**
     * The result area that are used in the GUI.
     */
    private JTextArea resultArea;

    /**
     * The font that are used in the GUI.
     */
    private Font textFont;

    /**
     * The andrewID add fields that are used in the GUI.
     */
    private JTextField andrewIDFieldAdd;

    /**
     * The andrewID delete fields that are used in the GUI.
     */
    private JTextField andrewIDFieldDelete;

    /**
     * The first name fields that are used in the GUI.
     */
    private JTextField firstNameField;

    /**
     * The last name fields that are used in the GUI.
     */
    private JTextField lastNameField;

    /**
     * The phone number fields that are used in the GUI.
     */
    private JTextField phoneNumberField;

    /**
     * The search fields that are used in the GUI.
     */
    private JTextField searchField;

    /**
     * Constructor take a CSV file name as a parameter and load the directory from the CSV file.
     * @param fn The CSV file name
     */
    public DirectoryDriver(String fn) {
        directory = new Directory(fn);

        // Initialize the GUI
        initGUI();
    }

    /**
     * Constructor take no parameters and create an empty directory.
     */
    public DirectoryDriver() {
        directory = new Directory();

        // Initialize the GUI
        initGUI();
    }

    /**
     * This is the GUI method that displays the GUI of the program.
     */
    private void initGUI() {

        // Set the title and size of the frame
        setTitle("CMU Student Directory");

        // Font setting
        textFont = new Font("Arial", Font.BOLD, 16);

        // Add function panel
        JPanel addStudentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Text fields initialization
        andrewIDFieldAdd = new JTextField(8);
        firstNameField = new JTextField(8);
        lastNameField = new JTextField(8);
        phoneNumberField = new JTextField(10);

        // Button initialization
        addButton = new JButton("ADD");

        // Add the labels and text fields to the addStudent panel
        addStudentPanel.add(new JLabel("First Name:", SwingConstants.LEFT));
        addStudentPanel.add(firstNameField);
        addStudentPanel.add(new JLabel("Last Name:", SwingConstants.LEFT));
        addStudentPanel.add(lastNameField);
        addStudentPanel.add(new JLabel("Andrew ID:", SwingConstants.LEFT));
        addStudentPanel.add(andrewIDFieldAdd);
        addStudentPanel.add(new JLabel("Phone Number:", SwingConstants.LEFT));
        addStudentPanel.add(phoneNumberField);
        addStudentPanel.add(addButton);

        // Add a titled border to the addStudent panel
        addStudentPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        // Delete function panel
        JPanel deleteStudentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Text fields initialization
        andrewIDFieldDelete = new JTextField(8);

        // Button initialization
        deleteButton = new JButton("DELETE");

        // Add the labels and text fields to the deleteStudent panel
        deleteStudentPanel.add(new JLabel("Andrew ID:", SwingConstants.LEFT));
        deleteStudentPanel.add(andrewIDFieldDelete);
        deleteStudentPanel.add(deleteButton);

        // Add a titled border to the deleteStudent panel
        deleteStudentPanel.setBorder(BorderFactory.createTitledBorder("Delete Student"));

        // Search function panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Search field initialization
        searchField = new JTextField(20);

        // Type enter to search by andrewID
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byAndrewIDButton.doClick();
            }
        });

        // Button initialization
        byAndrewIDButton = new JButton("By Andrew ID");
        byFirstNameButton = new JButton("By First Name");
        byLastNameButton = new JButton("By Last Name");

        // Add the labels and text fields to the search panel
        searchPanel.add(new JLabel("Search:", SwingConstants.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(byAndrewIDButton);
        searchPanel.add(byFirstNameButton);
        searchPanel.add(byLastNameButton);

        // Add a titled border to the search panel
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Student(s)"));

        // Result area panel
        resultArea = new JTextArea(50, 10);
        resultArea.setFont(textFont);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add a titled border to the result panel
        scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(addStudentPanel);
        mainPanel.add(deleteStudentPanel);
        mainPanel.add(searchPanel);

        // Add the main panel and scroll pane to the frame in the correct locations
        this.add(mainPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        // Create a new button listener
        ButtonListener listener = new ButtonListener();

        // Add listeners
        addButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        byAndrewIDButton.addActionListener(listener);
        byFirstNameButton.addActionListener(listener);
        byLastNameButton.addActionListener(listener);

        // Set the properties of the frame
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Clear the text fields.
     * @param source The source of the button
     */
    private void clearTextFields(JButton source) {
        if (source == addButton) {
            firstNameField.setText("");
            lastNameField.setText("");
            andrewIDFieldAdd.setText("");
            phoneNumberField.setText("");
            return;
        }

        if (source == deleteButton) {
            andrewIDFieldDelete.setText("");
            return;
        }

        if (source == byAndrewIDButton || source == byFirstNameButton || source == byLastNameButton) {
            searchField.setText("");
            return;
        }
    }

    /**
     * Print out the student's info in the result area.
     * @param student The student that will be printed
     * @param students The list of students that will be printed
     */
    private void printStudentInfo(Student student, List<Student> students) {

        if (student != null) {
            resultArea.append(student.toString() + "\n");
            return;
        }

        if (students != null) {

            for (Student s : students) {
                resultArea.append(s.toString() + "\n");
            }
        }
    }

    /**
     * Handle the add student error message by checking the content of the text fields.
     * @param s The student that will be added
     * @return True if the content of the text fields are valid, false otherwise
     */
    private boolean checkAddTextFields(Student s) {
        // Check if the first name is empty
        if (s.getFirstName().equals("")) {
            // pops up a error dialog box
            JOptionPane.showMessageDialog(null, "The first name cannot be empty.",
            "Error", JOptionPane.ERROR_MESSAGE);

            // clear the result area
            resultArea.setText("");

            // update the result area with the error message
            resultArea.append("The user entered an empty value for the first name.\n");

            return false;
        }

        // Check if the last name is empty
        if (s.getLastName().equals("")) {
            JOptionPane.showMessageDialog(null, "The last name cannot be empty.",
            "Error", JOptionPane.ERROR_MESSAGE);

            // clear the result area
            resultArea.setText("");

            // update the result area with the error message
            resultArea.append("The user entered an empty value for the last name.\n");

            return false;
        }

        // Check if the Andrew ID is empty
        if (s.getAndrewId().equals("")) {
            JOptionPane.showMessageDialog(null, "The Andrew ID cannot be empty.",
            "Error", JOptionPane.ERROR_MESSAGE);

            // clear the result area
            resultArea.setText("");

            // update the result area with the error message
            resultArea.append("The user entered an empty value for the Andrew ID.\n");

            return false;
        }

        // Check if the andrew ID already exists
        if (directory.searchByAndrewId(s.getAndrewId()) != null) {
            JOptionPane.showMessageDialog(null, "The Andrew ID: " + s.getAndrewId() + " already exists.",
            "Error", JOptionPane.ERROR_MESSAGE);

            // clear the result area
            resultArea.setText("");

            // update the result area with the error message
            resultArea.append("The Andrew ID: " + s.getAndrewId() + " already exists.\n");

            return false;
        }

        // Check if the digits of the phone number is 10 or empty
        if (s.getPhoneNumber() == null) {
            JOptionPane.showMessageDialog(null, "The phone number must be 10 digits.",
            "Error", JOptionPane.ERROR_MESSAGE);

            // clear the result area
            resultArea.setText("");

            // update the result area with the error message
            resultArea.append("The phone number must be 10 digits.\n");

            return false;
        }

        return true;
    }

    /**
     * Handle the delete student error message by checking the content of the text fields.
     * @param andrewID The andrew ID of the student that will be deleted
     * @return True if the content of the text fields are valid, false otherwise
     */
    private boolean checkDeleteTextFields(String andrewID) {
        // Check if the Andrew ID is empty
        if (andrewID.equals("")) {
            // pops up a error dialog box
            JOptionPane.showMessageDialog(null, "The Andrew ID cannot be empty.",
            "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the andrew ID already exists
        if (directory.searchByAndrewId(andrewID) == null) {
            JOptionPane.showMessageDialog(null, "The Andrew ID: " + andrewID + " does not exist.",
            "Error", JOptionPane.ERROR_MESSAGE);

            // update the result area with the error message
            resultArea.append("The student with Andrew ID: " + andrewID + " does not exist.\n");

            return false;
        }

        return true;
    }

    /**
     * Handle the search student error message by checking the content of the text fields.
     * @param field The search field that will be checked
     * @param source The source of the button
     * @return True if the content of the text fields are valid, false otherwise
     */
    private boolean checkSearchTextFields(String field, JButton source) {
        // Check if the search field is empty
        if (field.equals("")) {
            // pops up a error dialog box
            JOptionPane.showMessageDialog(null, "The search field cannot be empty.",
            "Error", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        // Check if the andrew ID exists
        if (source == byAndrewIDButton) {
            if (directory.searchByAndrewId(field) == null) {
                JOptionPane.showMessageDialog(null, "The student with Andrew ID: " + field
                + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);

                // update the result area with the error message
                resultArea.append("The student with Andrew ID: " + field + " does not exist.\n");

                return false;
            }
        }

        // Check if the first name exists
        if (source == byFirstNameButton) {
            if (directory.searchByFirstName(field).isEmpty()) {
                // pops up a error dialog box
                JOptionPane.showMessageDialog(null, "The student with first name: " + field
                + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);

                // update the result area with the error message
                resultArea.append("The student with first name: " + field + " does not exist.\n");

                return false;
            }
        }

        // Check if the last name exists
        if (source == byLastNameButton) {
            if (directory.searchByLastName(field).isEmpty()) {
                JOptionPane.showMessageDialog(null, "The student with last name: " + field
                + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);

                // update the result area with the error message
                resultArea.append("The student with last name: " + field + " does not exist.\n");

                return false;
            }
        }

        return true;
    }

    /**
     * Show the success message for adding and deleting students.
     * @param andrewID The andrew ID of the student that will be added or deleted
     * @param source The source of the button
     */
    private void successMessage(String andrewID, JButton source) {
        if (source == addButton) {
            // pops up a success dialog box
            JOptionPane.showMessageDialog(null, "The student with Andrew ID: " + andrewID
            + " is added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // update the result area with the success message
            resultArea.append("The student with Andrew ID: " + andrewID + " is added successfully.\n");
            return;
        }

        if (source == deleteButton) {
            JOptionPane.showMessageDialog(null, "The student with Andrew ID: " + andrewID
            + " is deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // update the result area with the success message and the student's info
            resultArea.append(directory.searchByAndrewId(andrewID).toString() + " is deleted successfully.\n");

            return;
        }
    }

    /**
     * Private nested class that handles button events.
     * @author: Zijie Huang
     * @date: 09/29/2023
     */
    private class ButtonListener implements ActionListener {

        /**
         * Override the actionPerformed method.
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the source of the button
            JButton source = (JButton) e.getSource();

            // If the source is the add button
            if (source == addButton) {
                // Get the text from the text fields
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String andrewID = andrewIDFieldAdd.getText();
                String phoneNumber = phoneNumberField.getText();

                // Create a new student
                Student student = new Student(andrewID);

                // Set the student's info
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setPhoneNumber(phoneNumber);

                // Check the content of the text fields
                if (!checkAddTextFields(student)) {
                    return;
                }

                // Clear the text fields
                clearTextFields(source);

                // pops up a success dialog box
                successMessage(andrewID, source);

                directory.addStudent(student);

                return;
            }

            // If the source is the delete button
            if (source == deleteButton) {
                // Get the text from the text fields
                String andrewID = andrewIDFieldDelete.getText();

                // Check the content of the text fields
                if (!checkDeleteTextFields(andrewID)) {
                    return;
                }

                // Clear the text fields
                clearTextFields(source);

                // pops up a success dialog box
                successMessage(andrewID, source);

                directory.deleteStudent(andrewID);

                return;
            }

            // If the source is the byAndrewID button
            if (source == byAndrewIDButton) {
                // Get the text from the text fields
                String andrewID = searchField.getText();

                // Check the content of the text fields
                if (!checkSearchTextFields(andrewID, source)) {
                    return;
                }

                Student student = directory.searchByAndrewId(andrewID);

                // Show the student's info
                printStudentInfo(student, null);

                // Clear the text fields
                clearTextFields(source);

                return;
            }

            // If the source is the byFirstName button
            if (source == byFirstNameButton) {
                // Get the text from the text fields
                String firstName = searchField.getText();

                // Check the content of the text fields
                if (!checkSearchTextFields(firstName, source)) {
                    return;
                }

                List<Student> students = directory.searchByFirstName(firstName);

                // Show the students' info
                printStudentInfo(null, students);

                // Clear the text fields
                clearTextFields(source);

                return;
            }

            // If the source is the byLastName button
            if (source == byLastNameButton) {
                // Get the text from the text fields
                String lastName = searchField.getText();

                // Check the content of the text fields
                if (!checkSearchTextFields(lastName, source)) {
                    return;
                }

                List<Student> students = directory.searchByLastName(lastName);

                // Show the students' info
                printStudentInfo(null, students);

                // Clear the text fields
                clearTextFields(source);

                return;
            }

            // If the source is unknown throw an error
            throw new AssertionError("Unknown event.");
        }
    }

    /**
     * Main method that will run the program.
     * It accepts an optional command line parameter with the name of CSV file that will contain initial data must load before
     * the GUI is displayed. If the command line parameter is not provided, the GUI will be displayed with an empty directory.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // If the command line argument is not provided, display the GUI with an empty directory
        if (args.length == 0) {
            new DirectoryDriver();
            return;
        }

        // If the command line argument is provided, display the GUI with the directory loaded from the CSV file
        if (args.length == 1) {
            new DirectoryDriver(args[0]);
            return;
        }

        // If the command line argument is invalid, throw an error
        throw new AssertionError("Invalid command line argument.");
    }
}
