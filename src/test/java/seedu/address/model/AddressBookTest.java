package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.ALICE;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Student;
import seedu.address.model.submission.Submission;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.StudentBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2)
                        .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newStudents);

        assertThrows(IllegalStateException.class, (
        ) -> addressBook.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> addressBook.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        addressBook.addStudent(ALICE);
        assertTrue(addressBook.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withHandle(VALID_HANDLE_BOB).withTutorials(VALID_TUTORIAL_2)
                        .build();
        assertTrue(addressBook.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, (
        ) -> addressBook.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = "%s{students=%s, tutorials=%s}".formatted(AddressBook.class.getCanonicalName(),
                        addressBook.getStudentList().toString(), addressBook.getTutorialList().toString());
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose students list can violate interface
     * constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Tutorial> tutorials = FXCollections.observableArrayList();
        private final ObservableList<Attendance> attendances = FXCollections.observableArrayList();
        private final ObservableList<Submission> submissions = FXCollections.observableArrayList();

        AddressBookStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Tutorial> getTutorialList() {
            return tutorials;
        }

        @Override
        public ObservableList<Attendance> getAttendanceList() {
            return attendances;
        }

        @Override
        public ObservableList<Submission> getSubmissionList() {
            return submissions;
        }
    }

}
