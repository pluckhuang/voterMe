package com.vote.voter.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vote.voter.model.Item;
import com.vote.voter.service.ItemRepositoryCustom;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Item> GetItemsByQuestId(HashSet<Long> questIds) {
        String sql = "select s from Item s where quest_id in (:inclList)";
        return (List<Item>) entityManager.createQuery(sql).setParameter("inclList", questIds).getResultList();
    }

    // @SuppressWarnings("unchecked")
    // public List<Item> findRecent(int count) {
    // return (List<Item>) entityManager.createQuery("select s from Item
    // s").setMaxResults(count).getResultList();
    // }

}
