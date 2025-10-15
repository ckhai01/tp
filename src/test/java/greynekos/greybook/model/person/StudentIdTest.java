package greynekos.greybook.model.person;

import static greynekos.greybook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StudentIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentID(null));
    }

    @Test
    public void constructor_invalidStudentID_throwsIllegalArgumentException() {
        String invalidStudentID = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentID(invalidStudentID));
    }

    @Test
    public void isValidStudentID() {
        // null student ID
        assertThrows(NullPointerException.class, () -> StudentID.isValidStudentID(null));

        // invalid student IDs
        assertFalse(StudentID.isValidStudentID("")); // empty string
        assertFalse(StudentID.isValidStudentID(" ")); // spaces only
        assertFalse(StudentID.isValidStudentID("B1234567A")); // first letter not 'A'
        assertFalse(StudentID.isValidStudentID("a1234567A")); // lowercase 'a' at start
        assertFalse(StudentID.isValidStudentID("A123456A")); // only 6 digits
        assertFalse(StudentID.isValidStudentID("A12345678A")); // 8 digits
        assertFalse(StudentID.isValidStudentID("A1234567")); // missing last letter
        assertFalse(StudentID.isValidStudentID("A12345671")); // last character is digit
        assertFalse(StudentID.isValidStudentID("A123456AA")); // two letters at end
        assertFalse(StudentID.isValidStudentID("1234567A")); // missing 'A' at start
        assertFalse(StudentID.isValidStudentID("A12A4567B")); // letter in middle digits
        assertFalse(StudentID.isValidStudentID("A 1234567B")); // space in middle
        assertFalse(StudentID.isValidStudentID("A1234567 B")); // space before last letter

        // valid student IDs
        assertTrue(StudentID.isValidStudentID("A1234567A")); // uppercase last letter
        assertTrue(StudentID.isValidStudentID("A1234567a")); // lowercase last letter
        assertTrue(StudentID.isValidStudentID("A0000000Z")); // all zeros
        assertTrue(StudentID.isValidStudentID("A9999999z")); // all nines
        assertTrue(StudentID.isValidStudentID("A1111111B"));
        assertTrue(StudentID.isValidStudentID("A2222222C"));
        assertTrue(StudentID.isValidStudentID("A1234567X"));
    }

    @Test
    public void equals() {
        StudentID studentID = new StudentID("A1234567A");

        // same values -> returns true
        assertTrue(studentID.equals(new StudentID("A1234567A")));

        // same object -> returns true
        assertTrue(studentID.equals(studentID));

        // null -> returns false
        assertFalse(studentID.equals(null));

        // different types -> returns false
        assertFalse(studentID.equals(5.0f));

        // different values -> returns false
        assertFalse(studentID.equals(new StudentID("A2345678B")));
    }
}
