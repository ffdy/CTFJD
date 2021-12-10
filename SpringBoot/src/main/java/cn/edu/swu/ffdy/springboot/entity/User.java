package cn.edu.swu.ffdy.springboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Users")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String type;
    private Date created;
}
