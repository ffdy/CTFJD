package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "challengeContainers")
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class ChallengeContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flag;
    private String username;
    private Date time;
    private String containerId;
    @Column(name = "challenge_id")
    private Integer challengeId;
    private String status;
}
