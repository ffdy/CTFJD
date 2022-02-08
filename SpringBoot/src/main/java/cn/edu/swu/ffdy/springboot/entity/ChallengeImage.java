package cn.edu.swu.ffdy.springboot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "challengeImages")
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class ChallengeImage {
    @Id
    private Integer id;
    @Column(name = "docker_image")
    private String dockerImage;
    @Column(name = "redirect_type")
    private String redirectType;
    @Column(name = "redirect_port")
    private Integer redirectPort;
}
