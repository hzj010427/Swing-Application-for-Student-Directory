import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class uses three Maps as follows to maintain the collection of students.
 * One Map will have the Andrew ID as the key and will map from Andrew ID to Student object.
 * The other two maps will take either the first name or the last name as the key and will map the name to a List of Student objects.
 * @author: Zijie Huang
 * @date: 09/25/2023
 */
public class Directory {
    /**
     * This is the Map that maps from Andrew ID to Student object.
     */
    private Map<String, Student> andrewIDToStudent;

    /**
     * This is the Map that maps from first name to a List of Student objects.
     */
    private Map<String, List<Student>> firstNameToStudents;

    /**
     * This is the Map that maps from last name to a List of Student objects.
     */
    private Map<String, List<Student>> lastNameToStudents;

    /**
     * This constructor takes a file name as a parameter and reads the file to initialize the three maps.
     * @param fn
     */
    public Directory(String fn) {
        this();

        try {
            CSVReader reader = new CSVReader(new java.io.FileReader(fn)); // Open the file
            String[] values = reader.readCSVLine();

            while (values != null) {
                Student student = new Student(values[2]); // Create a student object with the andrewID
                student.setFirstName(values[0]);
                student.setLastName(values[1]);
                student.setPhoneNumber(values[3]);

                addStudentToMap(student);

                values = reader.readCSVLine();
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e);
        }
    }

    /**
     * This constructor initializes the three maps to empty.
     */
    public Directory() {
        andrewIDToStudent = new HashMap<>();
        firstNameToStudents = new HashMap<>();
        lastNameToStudents = new HashMap<>();
    }

    /**
     * This method adds a student to the three maps.
     * @param student
     */
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        if (andrewIDToStudent.containsKey(student.getAndrewId())) {
            throw new IllegalArgumentException("Student already exists.");
        }

        addStudentToMap(new Student(student));
    }

    /**
     * This method deletes a student by andrewID in the three maps.
     * @param andrewID
     */
    public void deleteStudent(String andrewID) {
        if (andrewID == null) {
            throw new IllegalArgumentException("Andrew ID cannot be null.");
        }

        if (!andrewIDToStudent.containsKey(andrewID)) {
            throw new IllegalArgumentException("Student does not exist.");
        }

        removeStudentFromMap(andrewID);
    }

    /**
     * This method searches a student by andrewID in the andrewIDToStudent map.
     * @param andrewID
     * @return The object of the student and null if the student does not exist
     */
    public Student searchByAndrewId(String andrewID) {
        if (andrewID == null) {
            throw new IllegalArgumentException("Andrew ID cannot be null.");
        }

        return andrewIDToStudent.get(andrewID);
    }

    /**
     * This method searches a student by first name in the firstNameToStudents map.
     * @param firstName
     * @return The list of students with the same first name
     */
    public List<Student> searchByFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null.");
        }

        List<Student> sameFirstName = firstNameToStudents.get(firstName);
        List<Student> sameFirstNameCopy = new ArrayList<>();

        if (sameFirstName != null) {
            for (Student student : sameFirstName) {
                sameFirstNameCopy.add(new Student(student));
            }
        }

        return sameFirstNameCopy;
    }

    /**
     * This method searches a student by last name in the lastNameToStudents map.
     * @param lastName
     * @return The list of students with the same last name
     */
    public List<Student> searchByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null.");
        }

        List<Student> sameLastName = lastNameToStudents.get(lastName);
        List<Student> sameLastNameCopy = new ArrayList<>();

        if (sameLastName != null) {
            for (Student student : sameLastName) {
                sameLastNameCopy.add(new Student(student));
            }
        }

        return sameLastNameCopy;
    }

    /**
     * This method returns the number of students in the directory.
     * @return The number of students in the directory
     */
    public int size() {
       return andrewIDToStudent.size();
    }

    /**
     * This method adds a student to the three maps.
     * @param student
     */
    private void addStudentToMap(Student student) {
        // Map by Andrew ID
        andrewIDToStudent.put(student.getAndrewId(), student);

        // Map by first name
        List<Student> sameFirstName = firstNameToStudents.get(student.getFirstName());

        if (sameFirstName == null) {
            sameFirstName = new ArrayList<>();
            firstNameToStudents.put(student.getFirstName(), sameFirstName);
        }

        sameFirstName.add(student);

        // Map by last name
        List<Student> sameLastName = lastNameToStudents.get(student.getLastName());

        if (sameLastName == null) {
            sameLastName = new ArrayList<>();
            lastNameToStudents.put(student.getLastName(), sameLastName);
        }

        sameLastName.add(student);
    }

    /**
     * This method removes a student from the three maps.
     * @param andrewID
     */
    private void removeStudentFromMap(String andrewID) {
        Student student = andrewIDToStudent.remove(andrewID);

        List<Student> sameFirstName = firstNameToStudents.get(student.getFirstName());
        sameFirstName.remove(student);

        List<Student> sameLastName = lastNameToStudents.get(student.getLastName());
        sameLastName.remove(student);
    }

    /**
     * A simplified version of CSV Reader.
     *
     * Subclass of a BufferedReader to handle a character stream that consists of
     * comma separated values (CSVs)
     *
     * Provides an additional instance method, readCSVLine(), that parses lines into
     * substrings. The substrings are separated by comma in the original input
     * stream. The readCSVLine() method returns an array of references to Strings.
     * The Strings are the values from the line that were separated by commas. If a
     * value was surrounded by quotes, the quotes are removed.
     *
     * Limitations: Spaces before or after the commas are not removed. In the first
     * and last quote are removed from a value. Embedding commas in a quoted value
     * is not handled properly. (In this case, the commas will separate the values
     * and the quotes will not be removed from the ends of those values.
     *
     * @author Jeffrey Eppinger (jle@cs.cmu.com)
     * Date: October 2, 2007
     */
    private class CSVReader extends BufferedReader {
        /**
         * Initializes the class.
         * @param in the reader from which to read CSV lines
         */
        CSVReader(Reader in) {
            super(in);
        }

        /**
         * This is the only additional method. It uses readLine from the superclass
         * to get a line but returns the comma separated values as in an array of
         * strings.
         * @return an array of Strings containing the values At the end of the file,
         *         readCSVLine returns null (just as readLine does).
         * @throws IOException throws IOException
         */
        public String[] readCSVLine() throws IOException {

            // Get a line by calling the superclass's readLine method
            String line = super.readLine();

            // If we're at the end of the file, readLine returns null
            // so we return null.
            if (line == null) {
                return null;
            }

            // Count up the number of commas
            int commaCount = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ',') {
                    commaCount = commaCount + 1;
                }
            }

            // Allocate an array of the necessary size to return the strings
            String[] values = new String[commaCount + 1];

            // In a loop, set beginIndex and endIndex to the start and end
            // positions of each argument and then use the substring method
            // to create strings for each of the comma separate values

            // Start beginIndex at the beginning of the String, position 0
            int beginIndex = 0;

            for (int i = 0; i < commaCount; i++) {
                // set endIndex to the position of the (next) comma
                int endIndex = line.indexOf(',', beginIndex);

                // if the argument begins and ends with quotes, remove them
                if (line.charAt(beginIndex) == '"' && line.charAt(endIndex - 1) == '"') {

                    // If we made it here, we have quotes around our string.
                    // Add/subtract one from the start/end of the args
                    // to substring to get the value. (See else comment
                    // below for details on how this works.)
                    values[i] = line.substring(beginIndex + 1, endIndex - 1);

                } else {
                    // If we made it here, we don't have quotes around
                    // our string. Take the substring of this line
                    // from the beginIndex to the endIndex. The substring
                    // method called on a String will return the portion
                    // of the String starting with the beginIndex and up
                    // to but not including the endIndex.
                    values[i] = line.substring(beginIndex, endIndex);
                }

                // Set beginIndex to the position character after the
                // comma. (Remember, endIndex was set to the position
                // of the comma.)
                beginIndex = endIndex + 1;
            }

            // handle the value that's after the last comma
            if (line.charAt(beginIndex) == '"' && line.charAt(line.length() - 1) == '"') {
                values[commaCount] = line.substring(beginIndex + 1, line.length() - 1);
            } else {
                values[commaCount] = line.substring(beginIndex, line.length());
            }

            return values;
        }
    }
}
