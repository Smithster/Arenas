package com.smithster.gr8plugin.classes;

import org.bukkit.entity.Player;

public class playerState {

    public boolean isSettingTrigger = false;
    private String triggerName;

    public void toggleIsSettingTrigger() {
        this.isSettingTrigger = !(this.isSettingTrigger);
    }

    public String getTriggerName() {
        return this.triggerName;
    }

    public void setTriggerName(String name) {
        this.triggerName = name;
    }
}
