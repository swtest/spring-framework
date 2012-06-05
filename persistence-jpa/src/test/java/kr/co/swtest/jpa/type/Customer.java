/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.jpa.type;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Customer (JPA example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">최영목</a>
 * @since 2012. 5. 19.
 */
@Entity(name = "Customer")
@Table(name = "customer")
public class Customer implements Serializable {

    /** UID */
    private static final long serialVersionUID = -9098918044736522968L;

    /** ID */
    @Id
    @Column(name = "cust_id")
    private Integer id;

    /** 고객명 */
    @Column(name = "cust_nm", nullable = false)
    private String name;

    /** 이메일 */
    @Column(name = "cust_email")
    private String email;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * 생성자
     */
    public Customer() {
        // 아무일도 하지 않음
    }

    /**
     * 생성자
     *
     * @param id ID
     * @param name 고객명
     * @param email 이메일
     */
    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // -------------------------------------------------------------------------
    // Getter and Setter
    // -------------------------------------------------------------------------

    /**
     * @return ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 고객명
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name 고객명
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 이메일
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email 이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
