package com.blogservice.domain;

import com.blogservice.common.DataHandler;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
@Data
@Entity
@DynamicUpdate
@Table(name = "post")
public class PostEntity extends DataHandler{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    private String password;

    private String content;
}
