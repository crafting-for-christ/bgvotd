package com.gmail.supertin.bgvotd;

public class Variables {
    static bgvotdmain plugin;

    public static String pluginprefix = plugin.getConfig().getString("bgvotdprefix");
    public static String bibleversion = plugin.getConfig().getString("bibleversion");
    public static int refreshtime = plugin.getConfig().getInt("refresh");
    public static int broadcasttime = plugin.getConfig().getInt("broadcast");
}
