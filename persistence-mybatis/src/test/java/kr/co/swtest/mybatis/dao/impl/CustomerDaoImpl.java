/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.mybatis.dao.impl;

import java.util.List;

import kr.co.swtest.mybatis.dao.CustomerDao;
import kr.co.swtest.mybatis.dto.CustomerDto;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * CustomerDaoImpl (MyBatis example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">√÷øµ∏Ò</a>
 * @since 2012. 6. 6.
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SqlSession sqlSession;

    // -------------------------------------------------------------------------
    // Public Method
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCustomer(CustomerDto customer) {
        this.sqlSession.insert("customer.createCustomer", customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDto readCustomerById(int customerId) {
        return (CustomerDto) this.sqlSession.selectOne("customer.readCustomerById", customerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerDto> readCustomersByCondition(CustomerDto customer) {
        return this.sqlSession.selectList("customer.readCustomersByCondition", customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomer(CustomerDto customer) {
        this.sqlSession.update("customer.updateCustomer", customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCustomerById(int customerId) {
        this.sqlSession.delete("customer.deleteCustomerById", customerId);
    }

}
