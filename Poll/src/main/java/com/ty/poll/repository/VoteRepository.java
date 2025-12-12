package com.ty.poll.repository;

import com.ty.poll.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
	public interface VoteRepository extends JpaRepository<Vote, Long> {
	    boolean existsByUserIdAndPollId(Long userId, Long pollId);
	}
	
