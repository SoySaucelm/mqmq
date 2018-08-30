package com.mq.letter.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TUser implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码     md5(password+salt)
     */
    private String pwd;

    /**
     * salt
     */
    private String salt;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 账号类型     cf, 建企，监理i，区域公司， 房产集团， 资金方
            0~5
            引用 企业类型表
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 企业id
     */
    @Column(name = "corp_id")
    private Long corpId;

    /**
     * 用户身份     reserved
     */
    @Column(name = "user_level")
    private Integer userLevel;

    /**
     * 角色     用逗号分隔
     */
    private String roles;

    /**
     * 状态     0：正常， -1：停用
     */
    private Integer status;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Long createUser;

    private static final long serialVersionUID = 1L;
}