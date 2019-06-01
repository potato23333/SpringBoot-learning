package com.example.springbootchapter3.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by lzc
 * 2019/6/1 11:26
 */

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // 设置数据库中id自增
    private Long id;

    // 学生姓名
    private String studentName;

    // 学生姓名
    private String gender;

    // 班级名称
    private String className;

    // 学生年龄
    private Integer age;

    // 学生所在城市
    private String cityName;

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    private Date updateTime;
}
