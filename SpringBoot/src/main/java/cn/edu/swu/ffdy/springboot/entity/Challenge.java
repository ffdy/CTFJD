package cn.edu.swu.ffdy.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "challenges")
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer value;
    private String category;
    private String type;
    private String state;
}
