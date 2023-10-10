/**
 * This is the Student class that stores the information of a student.
 * @author: Zijie Huang
 * @date: 09/25/2023
 */
public class Student {

    /**
     * The student's Andrew ID.
     */
    private String andrewID;

    /**
     * The student's first name.
     */
    private String firstName;

    /**
     * The student's last name.
     */
    private String lastName;

    /**
     * The student's phone number.
     */
    private String phoneNumber;

    public Student(Student other) {
        this.andrewID = other.andrewID;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.phoneNumber = other.phoneNumber;
    }

    public Student(String pandrewID) {
        this.andrewID = pandrewID;
        this.firstName = null;
        this.lastName = null;
        this.phoneNumber = null;
    }

    public String getAndrewId() {
        return andrewID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String pfirstName) {
        this.firstName = pfirstName;
    }

    public void setLastName(String plastName) {
        this.lastName = plastName;
    }

    public void setPhoneNumber(String pphoneNumber) {
        this.phoneNumber = formatPhoneNumber(pphoneNumber);
    }

    /**
     * This method formats the phone number to the format xxx-xxx-xxxx.
     * @param input
     * @return The formatted phone number
     */
    private String formatPhoneNumber(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Phone number cannot be null.");
        }

        String cleaned = input.replaceAll("[^0-9]", "");

        if (cleaned.length() == 0) {
            return "";
        }

        if (cleaned.length() == 10) {
            return String.format("%s-%s-%s", cleaned.substring(0, 3), cleaned.substring(3, 6), cleaned.substring(6, 10));
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(firstName).append(" ").append(lastName).append(" ");
        result.append("(Andrew ID: ").append(andrewID).append(", ");
        result.append("Phone Number: ").append(phoneNumber);
        result.append(")");
        return result.toString();
    }
}
