/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.mybatis.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import kr.co.swtest.mybatis.dao.CustomerDao;
import kr.co.swtest.mybatis.dto.CustomerDto;
import kr.co.swtest.test.util.SpringDbUnitTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

/**
 * CustomerDaoImplTest (MyBatis test example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">최영목</a>
 * @since 2012. 6. 10.
 */
@ContextConfiguration(locations = { "classpath:context-mybatis.xml" })
@SuppressWarnings("deprecation")
public class CustomerDaoImplTest extends SpringDbUnitTestCase {

    /** 고객DAO 인터페이스 : 테스트대상(SUT) */
    @Autowired
    private CustomerDao customerDao;

    @Override
    protected List<String> addDataFiles() {
        return super.addDataFiles();
    }

    @Override
    protected String addDataFile() {
        return "kr/co/swtest/mybatis/dao/impl/CustomerDaoImplTestDataSet.xml";
    }

    /**
     * Oracle처럼 스키마명이 필요한 경우 오버라이딩해서 사용한다.
     *
     * <pre>
     * <code>
     * protected String getSchemaName() {
     *     return "test";
     * }
     * </code>
     * </pre>
     */
    @Override
    protected String getSchemaName() {
        return super.getSchemaName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createTable() {
        SimpleJdbcTemplate template = new SimpleJdbcTemplate(getDataSource());

        Resource resource = new ClassPathResource("/script/test-script.sql");
        SimpleJdbcTestUtils.executeSqlScript(template, resource, true);
    }

    // -------------------------------------------------------------------------
    // Test Method
    // -------------------------------------------------------------------------

    /** Customer 등록 테스트 */
    @Test
    public void testCreateCustomer() {
        int customerId = 3;

        // 1. 등록 전 확인
        CustomerDto customer = this.customerDao.readCustomerById(customerId);
        assertNull(customer);

        // 2. 등록
        customer = new CustomerDto(customerId, "최영목3", "scroogy3@swtest.co.kr");
        this.customerDao.createCustomer(customer);

        // 3. 검증
        CustomerDto result = this.customerDao.readCustomerById(customerId);
        assertCustomer(customer, result);
    }

    /** Customer 조회 Test */
    @Test
    public void testReadCustomerById() {
        int customerId = 1;
        CustomerDto result = this.customerDao.readCustomerById(customerId);

        assertCustomer(new CustomerDto(customerId, "최영목", "scroogy@swtest.co.kr"), result);
    }

    /** Customer 조건조회 Test */
    @Test
    public void testReadCustomersByCondition() {
        CustomerDto condition = new CustomerDto();
        condition.setName("최%");

        List<CustomerDto> customers = this.customerDao.readCustomersByCondition(condition);
        assertEquals(2, customers.size());
        assertCustomer(new CustomerDto(1, "최영목", "scroogy@swtest.co.kr"), customers.get(0));
        assertCustomer(new CustomerDto(2, "최영목2", "scroogy2@swtest.co.kr"), customers.get(1));
    }

    /** Customer 수정 Test */
    @Test
    public void testUpdateCustomer() {
        // 1. 수정 전 확인
        int customerId = 2;
        CustomerDto customer = this.customerDao.readCustomerById(customerId);
        assertCustomer(new CustomerDto(customerId, "최영목2", "scroogy2@swtest.co.kr"), customer);

        // 2. 수정
        customer.setName("newName");
        customer.setEmail("newEmail@swtest.co.kr");
        this.customerDao.updateCustomer(customer);

        // 3. 검증
        CustomerDto result = this.customerDao.readCustomerById(customerId);
        assertEquals("newName", result.getName());
        assertEquals("newEmail@swtest.co.kr", result.getEmail());
    }

    /** Customer 삭제 Test */
    @Test
    public void testDeleteCustomer() {
        // 1. 삭제 전 확인
        int customerId = 1;
        CustomerDto customer = this.customerDao.readCustomerById(customerId);
        assertNotNull(customer);

        // 2. 삭제
        this.customerDao.deleteCustomerById(customerId);

        // 3. 삭제 후 확인
        assertNull(this.customerDao.readCustomerById(customerId));
    }

    // -------------------------------------------------------------------------
    // Private Method
    // -------------------------------------------------------------------------

    /**
     * 고객 검증 <br/>
     * 검증대상 : ID, 고객명, 이메일
     *
     * @param expected 기대하는 고객 정보
     * @param actual 실제 고객 정보
     */
    private void assertCustomer(CustomerDto expected, CustomerDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

}
