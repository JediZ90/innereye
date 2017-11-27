package org.innereye.sso.server.common;

import java.io.Serializable;

/**
 * 登录成功用户对象
 *
 * @author zhangbaohao
 * @date 2017年8月27日
 * @version 1.0
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -8890558329629336558L;

    private Integer userId; // 登录成功ID

    private String account; // 登录成功用户名

    public LoginUser(Integer userId, String account){
        super();
        this.userId = userId;
        this.account = account;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
