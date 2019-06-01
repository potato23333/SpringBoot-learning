package com.example.springbootchapter3.repository;

import com.example.springbootchapter3.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lzc
 * 2019/6/1 14:48
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 通过studentName查
    Student findByStudentName(String studentName);

    Student findByStudentNameAndCityName(String studentName, String cityName);

    // 通过className查找并实现分页排序
    Page<Student> findByClassName(String className, Pageable pageable);

    // 使用Spring Data JPA提供的SQL规则
    @Query("select s from Student s where s.studentName = ?1")
    Student getByStudentName1(String studentName);

    // 使用原生SQL
    @Query(value = "select * from student where student_name = ?1", nativeQuery = true)
    Student getByStudentName2(String studentName);

    // 这里需要加上事物
    @Transactional
    @Modifying
    @Query("update Student s set s.age = ?2 where s.id = ?1")
    Integer updateAgeById(Long id, Integer count);
}
