package com.smithster.gr8plugin.classes;

import java.util.ArrayList;

public class Team {

    private ArrayList<String> players;

    private Integer score;

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public void playerJoin(String id) {
        this.players.add(id);
    }

    public void playerLeave(String id) {
        this.players.remove(id);
    }

    public void clear() {
        this.players.clear();
    }

    public boolean hasPlayer(String id) {
        return this.players.contains(id);
    }

    public void gainPoint(Integer points) {
        this.score = +points;
    }

    public void removePoint(Integer points) {
        this.score = -points;
    }

    public Integer getScore() {
        return this.score;
    }

}
