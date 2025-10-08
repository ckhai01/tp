package seedu.address.commons.core.index;

import java.util.Optional;

import seedu.address.model.person.StudentID;

/**
 * Represents either an Index or a StudentID for identifying a person. Used in
 * commands that can accept either format.
 */
public class IndexOrStudentId {
    private final Index index;
    private final StudentID studentID;

    /**
     * Creates an IndexOrStudentId with an Index.
     */
    private IndexOrStudentId(Index index) {
        this.index = index;
        this.studentID = null;
    }

    /**
     * Creates an IndexOrStudentId with a StudentID.
     */
    private IndexOrStudentId(StudentID studentID) {
        this.index = null;
        this.studentID = studentID;
    }

    /**
     * Creates an IndexOrStudentId from an Index.
     */
    public static IndexOrStudentId fromIndex(Index index) {
        return new IndexOrStudentId(index);
    }

    /**
     * Creates an IndexOrStudentId from a StudentID.
     */
    public static IndexOrStudentId fromStudentId(StudentID studentID) {
        return new IndexOrStudentId(studentID);
    }

    /**
     * Returns true if this contains an Index.
     */
    public boolean isIndex() {
        return index != null;
    }

    /**
     * Returns true if this contains a StudentID.
     */
    public boolean isStudentId() {
        return studentID != null;
    }

    /**
     * Returns the Index if present.
     */
    public Optional<Index> getIndex() {
        return Optional.ofNullable(index);
    }

    /**
     * Returns the StudentID if present.
     */
    public Optional<StudentID> getStudentId() {
        return Optional.ofNullable(studentID);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IndexOrStudentId)) {
            return false;
        }

        IndexOrStudentId otherIdentifier = (IndexOrStudentId) other;
        if (index != null) {
            return index.equals(otherIdentifier.index);
        } else {
            return studentID.equals(otherIdentifier.studentID);
        }
    }

    @Override
    public String toString() {
        if (index != null) {
            return "Index: " + index.toString();
        } else {
            return "StudentID: " + studentID.toString();
        }
    }
}
