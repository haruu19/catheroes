// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.blog.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleid;

//    private long userid;
//    private long catid;
    private String title;
    private String content;
    private String image;
    
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;
    
    @ManyToOne
    @JoinColumn(name ="catid")
    private Cat cat;    
    
}
