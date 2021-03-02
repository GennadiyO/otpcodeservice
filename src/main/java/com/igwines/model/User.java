package com.igwines.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private Date codeGenerationDate;
    private String code;
}
