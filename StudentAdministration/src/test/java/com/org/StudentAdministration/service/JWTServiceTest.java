package com.org.StudentAdministration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private JWTService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        jwtService=new JWTService();
    }

    String myToken(String userName){
        String token =jwtService.generateToken(userName);
        return token;
    }

    @Test
    void shouldGenerateTokenSuccessfully(){
      String userName= "testuser";
      String token=  myToken(userName);
     if(!token.isEmpty()) {
         log.info("token created");
     }
      assertNotNull(token);
      assertFalse(token.isEmpty());

    }

    @Test
    void shouldExtractUsernameFromToken(){
       String userName="testuser";
       String token= myToken(userName);
       String extractedName= jwtService.extractUserName(token);
       assertEquals(extractedName,userName);
    }

    @Test
    void shouldValidateTokenSuccessfully(){
        String userName="testuser";
        String token=myToken(userName);
        when(userDetails.getUsername()).thenReturn(userName);
        boolean isValid= jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }
}
