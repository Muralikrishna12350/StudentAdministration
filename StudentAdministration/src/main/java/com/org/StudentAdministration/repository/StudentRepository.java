package com.org.StudentAdministration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.StudentAdministration.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
