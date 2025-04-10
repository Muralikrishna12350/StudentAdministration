package com.org.StudentAdministration.service;

import com.org.StudentAdministration.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private StudentService studentService;

    @BeforeAll
   public static void beforeAll(){
        System.out.println("this is beforeAll");
    }

    @BeforeEach
    public void beforEach(){
        System.out.println("beforeEach");
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
