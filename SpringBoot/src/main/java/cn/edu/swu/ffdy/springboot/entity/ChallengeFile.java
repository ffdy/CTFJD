package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "files")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChallengeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String location;
    @Column(name = "challenge_id")
    private Integer challengeId;
}
