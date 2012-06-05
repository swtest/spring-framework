/*******************************************************************************
 * Copyright(c) 2012 SWTEST. All rights reserved.
 * This software is the proprietary information of SWTEST.
 *******************************************************************************/
package kr.co.swtest.jpa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import kr.co.swtest.jpa.dao.CustomerDao;
import kr.co.swtest.jpa.type.Customer;

import org.springframework.stereotype.Repository;

/**
 * CustomerLogic (JPA example)
 *
 * @author <a href="mailto:scroogy@swtest.co.kr">최영목</a>
 * @since 2012. 5. 19.
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCustomer(Customer customer) {
        try {
            this.em.persist(customer);
        } catch (Exception e) {
            // 예외처리 필요
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer readCustomerById(int customerId) {
        try {
            return this.em.find(Customer.class, customerId);
        } catch (Exception e) {
            // 예외처리 필요
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> readCustomersByCondition(Customer customer) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT c FROM Customer c WHERE c.name like :name ORDER BY c.name, c.id");

        TypedQuery<Customer> query = this.em.createQuery(queryString.toString(), Customer.class);
        query.setParameter("name", customer.getName() + "%");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomer(Customer customer) {
        try {
            this.em.merge(customer);
        } catch (Exception e) {
            // 예외처리 필요
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCustomerById(int customerId) {
        try {
            Customer customer = readCustomerById(customerId);
            if (customer == null) return;

            this.em.remove(customer);
        } catch (Exception e) {
            // 예외처리 필요
        }
    }

}
