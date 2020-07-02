package com.gmail.supertin.bgvotd;

public class ConfigurationManager {
    static bgvotdmain plugin;
    public ConfigurationManager(bgvotdmain plugin) {
        this.plugin = plugin;
    }

    public void loadlocalConfiguration() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.saveConfig();
    }
}
