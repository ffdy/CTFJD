package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "flags")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Flag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer challenge_id;
    private String content;

}
