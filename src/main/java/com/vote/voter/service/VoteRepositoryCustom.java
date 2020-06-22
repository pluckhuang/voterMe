package com.vote.voter.service;

import java.util.List;

import com.vote.voter.model.Vote;

public interface VoteRepositoryCustom {

    public List<Vote> getVotes(Boolean enabled);

    public Boolean isVoted(Long userId);
    // public List<Item> findRecent(int count);
}