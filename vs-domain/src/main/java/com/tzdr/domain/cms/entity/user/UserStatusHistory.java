
package com.tzdr.domain.cms.entity.user;

import com.tzdr.common.domain.BaseAuthEntity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.Date;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_user_status_history")
public class UserStatusHistory extends BaseAuthEntity<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 965961486689696940L;

	/**
     * 锁定的用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private User user;

    /**
     * 备注信息
     */
    private String reason;

    /**
     * 操作的状态
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /**
     * 操作的管理员
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "op_user_id")
    private User opUser;

    /**
     * 操作时间
     */
    @Column(name = "op_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opDate;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getOpUser() {
        return opUser;
    }

    public void setOpUser(User opUser) {
        this.opUser = opUser;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}
