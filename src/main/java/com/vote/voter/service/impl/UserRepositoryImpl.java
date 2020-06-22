package com.vote.voter.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vote.voter.service.UserRepositoryCustom;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public long getUserIdByName(String username) {
        try {
            String sql = "select id from User u where u.username=?1";
            return (long) entityManager.createQuery(sql).setParameter(1, username).getSingleResult();
        } catch (Exception e) {
            return 0;
        }

    }
    // public List<Item> findRecent() {
    // return findRecent(10);
    // }

    // @SuppressWarnings("unchecked")
    // public List<Item> findRecent(int count) {
    // return (List<Item>) entityManager.createQuery("select s from Item
    // s").setMaxResults(count).getResultList();
    // }

}
