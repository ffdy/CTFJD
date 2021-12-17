package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "solves")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Solve {
    @Id
    private Integer id;

    @Column(name="challenge_id")
    private Integer challengeId;
    @Column(name="user_id")
    private Integer userId;
}
