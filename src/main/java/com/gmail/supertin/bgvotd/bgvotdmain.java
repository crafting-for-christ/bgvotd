package com.gmail.supertin.bgvotd;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.ChatPaginator;

import static com.gmail.supertin.bgvotd.Variables.broadcasttime;
import static com.gmail.supertin.bgvotd.Variables.refreshtime;

public class bgvotdmain extends JavaPlugin {
	static bgvotdmain plugin;
	public static String verse;
	public static String reference;
	public static String pluginprefix = plugin.getConfig().getString("bgvotdprefix");

	@Override
	public void onEnable() {
		// Make sure we have a config file, then get the values we need.
		this.saveDefaultConfig();
		broadcasttime = this.getConfig().getInt("broadcast");

		// Scheduler for refreshing the verse.
        BukkitScheduler refreshScheduler = getServer().getScheduler();
        refreshScheduler.scheduleSyncRepeatingTask(this, new Runnable() {
        	@Override
            public void run() {
        		votdUpdate.main(); }
        	}, 0L, 20*60*60*refreshtime); //20 ticks * 60 seconds * 60 minutes * refreshtime (default 24 hours)
        
        // Scheduler for broadcasting the verse via chat.
        if (broadcasttime > 0) {
        	Bukkit.getLogger().info(pluginprefix + "Broadcasting verse every " + broadcasttime + " minutes.");
	        BukkitScheduler broadcastScheduler = getServer().getScheduler();
	        broadcastScheduler.scheduleSyncRepeatingTask(this, new Runnable() {
	        	@Override
	        	public void run() {
	        		// Broadcast the verse to all players.
	    			Bukkit.broadcastMessage(bgvotdmain.verse);
	    			Bukkit.broadcastMessage(bgvotdmain.reference);
	        	}
	        }, 0L, 20*60*broadcasttime);
		}
	}

	@Override
	public void onDisable() {
		Bukkit.getServer().getConsoleSender().sendMessage(pluginprefix + "bgvotd has been disabled.");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch(cmd.getName()) {
		case "votd":
			if (verse != null) {
				//Player is allowed - send them the VOTD.
				sender.sendMessage(ChatPaginator.wordWrap(verse, ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH));
				sender.sendMessage(reference);
			} else {
				sender.sendMessage(pluginprefix + "No VOTD available");
			}
			break;
		case "votdrefresh":
			//If the player has permission to refresh...
			if (sender.hasPermission("bgvotd.refresh")) {
				//Run the refresh routine
				votdUpdate.main();
			} else {
				//Player doesn't have permission to do this. Let them know. This is the default...
				sender.sendMessage(pluginprefix + "You don't have permission to manually update the VOTD.");
			}
			break;
		default:
			break;
		}
		return true;
	}
}

