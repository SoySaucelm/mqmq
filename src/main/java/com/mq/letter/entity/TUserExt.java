package com.mq.letter.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_user_ext")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TUserExt implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 职务
     */
    @Column(name = "job_title")
    private String jobTitle;

    /**
     * 项目部     建企
     */
    @Column(name = "dept")
    private String dept;

    /**
     * 经办人授权协议附件
     */
    @Column(name = "authority_file")
    private Long authorityFile;

    private static final long serialVersionUID = 1L;
}