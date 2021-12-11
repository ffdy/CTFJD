package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "submissions")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer challenge_id;
    private Integer user_id;
    private String ip;
    private Date date;
    private String provided;
    private String type;

}
