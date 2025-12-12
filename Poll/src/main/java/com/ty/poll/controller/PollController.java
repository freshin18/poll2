package com.ty.poll.controller;


import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    // Simulating a Database in memory
    private List<VoteOption> options = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    // 1. GET RESULTS
    @GetMapping("/{pollId}/results")
    public List<VoteOption> getResults(@PathVariable int pollId) {
        return options;
    }

    // 2. ADD CANDIDATE (Used by Auto-Seed and Manual Add)
    @PostMapping("/options")
    public void addOption(@RequestBody Map<String, Object> payload) {
        String description = (String) payload.get("description");
        
        // Prevent duplicates
        for(VoteOption opt : options) {
            if(opt.getDescription().equalsIgnoreCase(description)) return;
        }

        VoteOption newOpt = new VoteOption(idCounter.getAndIncrement(), description);
        options.add(newOpt);
    }

    // 3. VOTE
    @PostMapping("/vote")
    public String vote(@RequestBody Map<String, Object> payload) {
        int optionId = (int) payload.get("optionId");
        String username = (String) payload.get("username");

        for (VoteOption opt : options) {
            if (opt.getId() == optionId) {
                opt.incrementVote();
                System.out.println("User " + username + " voted for " + opt.getDescription());
                return "Voted successfully";
            }
        }
        throw new RuntimeException("Option not found");
    }

    // --- SIMPLE DATA MODEL CLASS ---
    static class VoteOption {
        private int id;
        private String description;
        private int voteCount;

        public VoteOption(int id, String description) {
            this.id = id;
            this.description = description;
            this.voteCount = 0;
        }

        public void incrementVote() { this.voteCount++; }
        
        // Getters needed for JSON conversion
        public int getId() { return id; }
        public String getDescription() { return description; }
        public int getVoteCount() { return voteCount; }
    }
}