package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class LobbyVote {

    private ArrayList<Arena> options = new ArrayList<Arena>();
    private HashMap<Player, Arena> votes = new HashMap<Player, Arena>();

    public LobbyVote(ArrayList<Arena> options) {
        this.options = options;
    }

    public void vote(Player player, Arena arena) {
        votes.put(player, arena);
    }

    public Arena getWinner() {
        HashMap<Arena, Integer> voteCount = new HashMap<Arena, Integer>();
        Integer winningScore = 0;
        Arena winner = new Arena();
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
}