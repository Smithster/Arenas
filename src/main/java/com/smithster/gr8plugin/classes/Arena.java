package com.smithster.gr8plugin.classes;

public class Arena extends Plot {

    private String gamemode;
    private Boolean isActive = false;

    public void toggleActiveState() {
        if (isActive) {
            this.isActive = false;
            return;
        }
        this.isActive = true;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    public String getGamemode(String gamemode) {
        return this.gamemode;
    }
}
