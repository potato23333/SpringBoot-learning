package com.example.springbootchapter3;

import com.example.springbootchapter3.entity.Student;
import com.example.springbootchapter3.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringbootChapter3ApplicationTests {

	@Autowired
	private StudentRepository studentRepository;

	// 增加
	@Test
	public void test1() {
		Student student = new Student();
		student.setStudentName("lisi");
		student.setAge(20);
		student.setCityName("桂林");
		student.setClassName("计科153");
		student.setGender("女");
		studentRepository.save(student);
	}

	// 删除
	@Test
	public void test2() {
		// 通过id删除，如果id不存在，将会抛异常
		// 删除之前可以通过studentRepository.existsById(1L)来判断id是否存在，如果存在，则删除
		studentRepository.deleteById(1L);
	}

	// 修改
	@Test
	public void test3() {
		// 通过id查询
		Student result = studentRepository.findById(6L).orElse(null); // 当查询结果不存在时则返回null
		result.setAge(10);
		studentRepository.save(result);
	}

	// 简单查询：查询所有记录
	@Test
	public void test4() {
		List<Student> studentList = studentRepository.findAll();
		studentList.stream().forEach(s -> log.info(s.toString()));
	}

	// 简单查询：分页查询
	@Test
	public void test5() {
		Page<Student> studentPage = studentRepository.findAll(PageRequest.of(1,2));

		List<Student> studentList = studentPage.getContent();
		studentList.stream().forEach(s -> log.info(s.toString()));

        log.info("【TotalPages】"  + studentPage.getTotalPages());
        log.info("【totalElements】"  + studentPage.getTotalElements());
        log.info("【Number】"  + studentPage.getNumber());
        log.info("【Size】"  + studentPage.getSize());
        log.info("【NumberOfElements】"  + studentPage.getNumberOfElements());
	}

	// 简单查询：分页查询+排序（要么升序，要么降序）
	@Test
	public void test6() {

		Page<Student> studentPage = studentRepository.findAll(PageRequest.of(0,10,Sort.Direction.ASC,"age"));

		List<Student> studentList = studentPage.getContent();
		studentList.stream().forEach(s -> log.info(s.toString()));

		log.info("【TotalPages】"  + studentPage.getTotalPages());
		log.info("【totalElements】"  + studentPage.getTotalElements());
		log.info("【Number】"  + studentPage.getNumber());
		log.info("【Size】"  + studentPage.getSize());
		log.info("【NumberOfElements】"  + studentPage.getNumberOfElements());
	}

	// 简单查询：分页查询+排序（既有升序，又有降序）
	@Test
	public void test7() {

		Sort sort = new Sort(Sort.Direction.DESC,"age"); // 年龄降序
		sort = sort.and(new Sort(Sort.Direction.ASC,"className")); // 班级升序

		Page<Student> studentPage = studentRepository.findAll(PageRequest.of(0,10,sort));

		List<Student> studentList = studentPage.getContent();
		studentList.stream().forEach(s -> log.info(s.toString()));

		log.info("【TotalPages】"  + studentPage.getTotalPages());
		log.info("【totalElements】"  + studentPage.getTotalElements());
		log.info("【Number】"  + studentPage.getNumber());
		log.info("【Size】"  + studentPage.getSize());
		log.info("【NumberOfElements】"  + studentPage.getNumberOfElements());
	}

	// 前面的操作都是使用 JpaRepository 为我们实现好的方法，下面介绍在 StudentRepository 进行扩展

	@Test
	public void test8() {
		// 相当于 select * from student where student_name = 'lzc'
		log.info(studentRepository.findByStudentName("lzc").toString());

		// 相当于 select * from student where student_name = 'lzc' and city_name = '南宁'
		log.info(studentRepository.findByStudentNameAndCityName("lzc","南宁").toString());
	}

	@Test
	public void test9() {

		//相当于 select * from student where class_name = '计科152' order by age asc limit 0,10
		Page<Student> studentPage = studentRepository.findByClassName("计科152",PageRequest.of(0,10,Sort.Direction.ASC,"age"));

		List<Student> studentList = studentPage.getContent();
		studentList.stream().forEach(s -> log.info(s.toString()));

		log.info("【TotalPages】"  + studentPage.getTotalPages());
		log.info("【totalElements】"  + studentPage.getTotalElements());
		log.info("【Number】"  + studentPage.getNumber());
		log.info("【Size】"  + studentPage.getSize());
		log.info("【NumberOfElements】"  + studentPage.getNumberOfElements());
	}

	// 使用 sql 语句查询
	@Test
	public void test10() {
		// log.info(studentRepository.getByStudentName1("zhangsan").toString());
		// log.info(studentRepository.getByStudentName2("zhangsan").toString());
		log.info(studentRepository.updateAgeById(2L,16) + "");
	}

	// JPA动态查询
	@Test
	public void test11() {
		Student student = new Student();
		student.setCityName("南宁");
		student.setClassName("计科152");

		Example<Student> example = Example.of(student);
		// SQL语句 select * from student where city_name = '南宁' and class_name = '计科152'
		List<Student> studentList = studentRepository.findAll(example);
		studentList.forEach(s -> log.info(s.toString()));
	}
	@Test
	public void test12() {
		Student student = new Student();
		student.setCityName("南宁");
		student.setClassName("计科152");

		Example<Student> example = Example.of(student);
		// SQL语句 select * from student where city_name = '南宁' and class_name = '计科152' order by age desc limit 0,2
		Page<Student> studentPage = studentRepository.findAll(example, PageRequest.of(0,2, Sort.Direction.DESC,"age"));

		List<Student> studentList = studentPage.getContent();
		studentList.stream().forEach(s -> log.info(s.toString()));

		log.info("【TotalPages】"  + studentPage.getTotalPages());
		log.info("【totalElements】"  + studentPage.getTotalElements());
		log.info("【Number】"  + studentPage.getNumber());
		log.info("【Size】"  + studentPage.getSize());
		log.info("【NumberOfElements】"  + studentPage.getNumberOfElements());
	}
	@Test
	public void test13() {
		Student student = new Student();
		student.setCityName("南宁");
		student.setClassName("计科");

		// 设置属性的查询规则，
		// 有ignoreCase()，caseSensitive()，contains()，endsWith()，startsWith()，exact()，storeDefaultMatching()，regex()
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("className",startsWith());

		Example<Student> example = Example.of(student, matcher);

		List<Student> studentList = studentRepository.findAll(example);
		studentList.forEach(s -> log.info(s.toString()));
	}

	@Test
	public void test0() {

	}

}
