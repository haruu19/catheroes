// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.blog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// @JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY	)
    private long catid;
    
    private String nickname;

    @Column(name="age")
    private int age;    
    @Column(name="breed")
    private String breed;
    @Column(name="location")
    private String location;
    @Column(name="attr")
    private String attr;
    @Column(name="food")
    private String food;
    @Column(name="hospital")
    private String hospital;
    @Column(name="family")
    private String family;
    @Column(name="neutered")
    private String neutered;
    @Column(name="conditions")
    private String conditions;
    @Column(name="image")
    private String image;
    @Column(name="lat")
    private String lat;
    @Column(name="lng")
    private String lng;

}
