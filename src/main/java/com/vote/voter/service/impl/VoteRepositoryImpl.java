package com.vote.voter.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vote.voter.model.Vote;
import com.vote.voter.service.VoteRepositoryCustom;

public class VoteRepositoryImpl implements VoteRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Vote> getVotes(Boolean enabled) {
        String sql = "SELECT v from Vote v where v.enabled=?1";
        return (List<Vote>) entityManager.createQuery(sql).setParameter(1, enabled).getResultList();
    }

    @Override
    public Boolean isVoted(Long userId) {
        Boolean ret = true;
        String sql = "SELECT * from t_vote v where v.enabled=true and v.user_id=? limit 1";
        try {
            entityManager.createNativeQuery(sql).setParameter(1, userId).getSingleResult();
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }
    // @SuppressWarnings("unchecked")
    // public List<Item> findRecent(int count) {
    // return (List<Item>) entityManager.createQuery("select s from Item
    // s").setMaxResults(count).getResultList();
    // }

}
