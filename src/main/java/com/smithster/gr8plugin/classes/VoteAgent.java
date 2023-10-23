package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.mongodb.internal.logging.StructuredLogMessage.Entry;

public class VoteAgent {

    private ArrayList<Arena> options = new ArrayList<Arena>();
    private HashMap<Player, Arena> votes = new HashMap<Player, Arena>();

    public VoteAgent(ArrayList<Arena> options) {
        this.options = options;
    }

    public void vote(Player player, Arena arena) {
        votes.put(player, arena);
        giveCurrentScores(player);
    }

    public void removeVote(Player player) {
        votes.remove(player);
    }

    public void giveCurrentScores(Player player) {
        HashMap<Arena, Integer> score = new HashMap<Arena, Integer>();
        for (Arena arena : votes.values()) {
            if (score.containsKey(arena)) {
                score.put(arena, score.get(arena) + 1);
            } else {
                score.put(arena, 1);
            }
        }
        for (Arena arena : score.keySet()) {
            player.sendMessage(String.format("%s: %s", arena.getName() + score.get(arena).toString()));
        }
    }

    public Arena getWinner() {
        HashMap<Arena, Integer> voteCount = new HashMap<Arena, Integer>();
        Integer winningScore = 0;
        Arena winner = null;

        for (Arena arena : this.votes.values()) {
            Integer score = 0;
            if (voteCount.containsKey(arena)) {
                score = voteCount.get(arena) + 1;

                voteCount.put(arena, score);
            } else {
                voteCount.put(arena, 1);
            }

            if (score > winningScore) {
                winningScore = score;
                winner = arena;
            }
        }

        return winner;
    }

    public void addToPool(Arena arena) {
        this.options.add(arena);
    }
}