package com.org.StudentAdministration.service;

import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @BeforeAll
   public static void beforeAll(){
        System.out.println("this is beforeAll");
    }

    @BeforeEach
    public void beforEach(){
        System.out.println("beforeEach");
    }

    @Test
     void addStudentSuccessfully(){

        Student student=new Student();
        student.setId(100); // data preparations
        student.setName("mks");
        student.setEmail("mks@gmail.com");
        student.setMobileNumber("345456");
        student.setPassword("mks@123");

        Mockito.when(studentRepository.save(student)).thenReturn(student); // mocking our calls if any

        Student addedStudent= studentService.saveStudent(student);  // Calling our actual methods

        Assertions.assertNotNull(student); // Assertions
        Assertions.assertEquals(student.getId(), addedStudent.getId());
        Assertions.assertEquals(student.getName(), addedStudent.getName());
        Assertions.assertEquals(student.getEmail(), addedStudent.getEmail());
        Assertions.assertEquals(student.getMobileNumber(), addedStudent.getMobileNumber());
        Assertions.assertEquals(student.getPassword(), addedStudent.getPassword());
        Assertions.assertTrue(student.getId()==100);

        System.out.println("this is my first test");
    }

    @Test
    void deleteStudentSuccessfully(){
        Mockito.doNothing().when(studentRepository).deleteById(1);
        studentService.deleteStudent(1);
        Mockito.verify(studentRepository,Mockito.times(1)).deleteById(1);
    }

    @Test
    void testprivatemethodandvalidatename() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
     Method validateStudentName= StudentService.class.getDeclaredMethod("validateStudentName",String.class);
     validateStudentName.setAccessible(true);
     boolean name= (boolean) validateStudentName.invoke(studentService,"mmks ");
     Assertions.assertTrue(name);
    }

    @Test
    public void dummyTest(){
        System.out.println("this is dummy test");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("this is for aftereach");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("this is for afterAll");
    }
}
