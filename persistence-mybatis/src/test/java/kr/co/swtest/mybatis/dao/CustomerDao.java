/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.mybatis.dao;

import java.util.List;

import kr.co.swtest.mybatis.dto.CustomerDto;

/**
 * CustomerDao interface (MyBatis example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">최영목</a>
 * @since 2012. 6. 6.
 */
public interface CustomerDao {

    /**
     * 고객 등록
     *
     * @param customer 고객
     */
    public void createCustomer(CustomerDto customer);

    /**
     * 고객 조회
     *
     * @param customerId 고객ID
     * @return 고객. 없을 경우 <code>null</code>을 리턴한다.
     */
    public CustomerDto readCustomerById(int customerId);

    /**
     * 고객목록 조회 : 조건검색 <br/>
     * 검색조건 : 고객명(like), 이메일(like) <br/>
     * 정렬조건 : 고객명, 고객ID
     *
     * @param customer 검색조건
     * @return 조건에 해당하는 고객목록. 없을 경우 빈목록(Empty List)를 리턴한다.
     */
    public List<CustomerDto> readCustomersByCondition(CustomerDto customer);

    /**
     * 고객 수정
     *
     * @param customer 수정할 고객
     */
    public void updateCustomer(CustomerDto customer);

    /**
     * 고객 삭제
     *
     * @param customerId 고객ID
     */
    public void deleteCustomerById(int customerId);

}
