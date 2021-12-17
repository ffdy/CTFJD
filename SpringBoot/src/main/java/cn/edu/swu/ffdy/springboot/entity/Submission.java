package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Table(name = "submissions")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="challenge_id")
    private Integer challengeId;
    @Column(name="user_id")
    private Integer userId;
    private String ip;
    private Date date;
    private String provided;
    private String type;

}
