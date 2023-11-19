package com.smithster.gr8plugin.gamemodes;

import org.bukkit.scoreboard.Objective;

public class CTF extends Gamemode{
    
    Objective objective;

    public CTF() {
        this.setType("CTF");
        this.setMaxScore(3);
    }

    public void handleCapture(){
        
    }

}
