import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JunitTestStudent {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Test
    public void testGetStudentById() {
        // Arrange
        Long studentId = 1L;
        StudentResponse expected = new StudentResponse("John Doe", "john@doe.com");

        when(studentService.getStudentById(studentId)).thenReturn(expected);

        // Act
        StudentResponse actual = studentController.getStudentById(studentId);

        // Assert
        verify(studentService).getStudentById(studentId);
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudent() {
        // Arrange
        StudentRequest request = new StudentRequest("John Doe", "john@doe.com");
        StudentResponse expected = new StudentResponse("John Doe", "john@doe.com");

        when(studentService.saveStudent(request)).thenReturn(expected);

        // Act
        StudentResponse actual = studentController.saveStudent(request);

        // Assert
        verify(studentService).saveStudent(request);
        assertEquals(expected, actual);
    }
}
