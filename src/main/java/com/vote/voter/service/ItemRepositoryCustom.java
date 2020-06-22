package com.vote.voter.service;

import java.util.HashSet;
import java.util.List;

import com.vote.voter.model.Item;

public interface ItemRepositoryCustom {
    List<Item> GetItemsByQuestId(HashSet<Long> questIds);

    // public List<Item> findRecent();

    // public List<Item> findRecent(int count);
}