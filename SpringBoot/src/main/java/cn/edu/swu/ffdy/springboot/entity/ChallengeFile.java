package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "files")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ChallengeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String location;
    private Integer challenge_id;
}
