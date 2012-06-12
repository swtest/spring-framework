/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.mybatis.dto;

import java.io.Serializable;

/**
 * CustomerDto (MyBatis example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">최영목</a>
 * @since 2012. 6. 6.
 */
public class CustomerDto implements Serializable {

    /** UID */
    private static final long serialVersionUID = 7163216786886274057L;

    /** ID */
    private Integer id;

    /** 고객명 */
    private String name;

    /** 이메일 */
    private String email;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * 생성자
     */
    public CustomerDto() {
        // 아무일도 하지 않음
    }

    /**
     * 생성자
     *
     * @param id ID
     * @param name 고객명
     * @param email 이메일
     */
    public CustomerDto(int id, String name, String email) {
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
