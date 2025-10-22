package greynekos.greybook.testutil;

import java.util.HashSet;
import java.util.Set;

import greynekos.greybook.model.person.AttendanceStatus;
import greynekos.greybook.model.person.Email;
import greynekos.greybook.model.person.Name;
import greynekos.greybook.model.person.Person;
import greynekos.greybook.model.person.Phone;
import greynekos.greybook.model.person.StudentID;
import greynekos.greybook.model.tag.Tag;
import greynekos.greybook.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_STUDENTID = "A0000000Y";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Tag> tags;
    private StudentID studentID;
    private AttendanceStatus attendanceStatus;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
        studentID = new StudentID(DEFAULT_STUDENTID);
        attendanceStatus = new AttendanceStatus();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        studentID = personToCopy.getStudentID();
        tags = new HashSet<>(personToCopy.getTags());
        attendanceStatus = personToCopy.getAttendance();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code StudentID} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentID(String studentID) {
        this.studentID = new StudentID(studentID);
        return this;
    }

    /**
     * Sets the {@code AttendanceStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withAttendanceStatus(AttendanceStatus.Status status) {
        this.attendanceStatus = new AttendanceStatus(status);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, studentID, tags, attendanceStatus);
    }

}
