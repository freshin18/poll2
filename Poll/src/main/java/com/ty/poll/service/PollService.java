package com.ty.poll.service;

import com.ty.poll.model.*;
import com.ty.poll.repository.*; // Ensure your package name is fixed!
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class PollService {

    private final VoteRepository voteRepository;
    private final OptionRepository optionRepository;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    public PollService(VoteRepository voteRepository, OptionRepository optionRepository,
                       PollRepository pollRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.optionRepository = optionRepository;
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void castVote(Long userId, Long pollId, Long optionId) {
        if (voteRepository.existsByUserIdAndPollId(userId, pollId)) {
            throw new RuntimeException("User has already voted in this poll.");
        }

        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new RuntimeException("Poll not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Option option = optionRepository.findById(optionId).orElseThrow(() -> new RuntimeException("Option not found"));

        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(user);
        vote.setOption(option);
        voteRepository.save(vote);

        optionRepository.incrementVoteCount(optionId);
    }
}