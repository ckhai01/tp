package greynekos.address.model.person;

import static java.util.Objects.requireNonNull;
import static greynekos.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's student ID in the address book. Guarantees: immutable;
 * is valid as declared in {@link #isValidStudentID(String)}
 */
public class StudentID implements PersonIdentifier {

    public static final String MESSAGE_CONSTRAINTS =
            "Student IDs should be in the format A0000000L, where the first letter must be 'A', "
                    + "followed by exactly 7 digits, and ending with any English letter (A-Z or a-z)";
    public static final String VALIDATION_REGEX = "^A\\d{7}[A-Za-z]$";
    public final String value;

    /**
     * Constructs a {@code StudentID}.
     *
     * @param studentID
     *            A valid student ID.
     */
    public StudentID(String studentID) {
        requireNonNull(studentID);
        checkArgument(isValidStudentID(studentID), MESSAGE_CONSTRAINTS);
        value = studentID;
    }

    /**
     * Returns true if a given string is a valid student ID.
     */
    public static boolean isValidStudentID(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentID)) {
            return false;
        }

        StudentID otherStudentID = (StudentID) other;
        return value.equals(otherStudentID.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
