package com.vote.voter.service;

import com.vote.voter.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface VoteRepository extends JpaRepository<Vote, Long>, VoteRepositoryCustom {

}
