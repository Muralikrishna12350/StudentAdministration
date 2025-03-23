package com.org.StudentAdministration.service;


import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailService implements UserDetailsService {

    @Autowired
   private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Fetching user: " + username);

        Student student = studentRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            System.out.println(" Student password is null or empty in the database!");
        }

        return User.builder()
                .username(student.getName())
                .password(student.getPassword())
                .roles("USER")
                .build();
    }



}
