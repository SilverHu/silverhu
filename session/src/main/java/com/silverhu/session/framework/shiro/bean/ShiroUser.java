package com.silverhu.session.framework.shiro.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ShiroUser implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long id;

    /** 用户名 */
    private String username;

    /** 用户姓名 */
    private String name;

    /** 密码 */
    private String password;

    /** 用户邮箱 */
    private String email;
}
