package com.vote.voter.service;

import com.vote.voter.model.Quest;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface QuestRepository extends CrudRepository<Quest, Long> {

}