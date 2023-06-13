package com.smithster.gr8plugin.classes;

import org.bukkit.entity.Player;

public class playerState {

    public boolean isSettingTrigger = false;

    public void toggleIsSettingTrigger() {
        this.isSettingTrigger = !(this.isSettingTrigger);
    }
}
