package cc.isotopestudio.MoonKits.severstart;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import cc.isotopestudio.MoonKits.MoonKits;

public class PreventLogin implements Listener {
	final private String kickMsg;

	public PreventLogin(MoonKits plugin) {
		kickMsg = plugin.getConfig().getString("serverstart.kickmsg");
	}

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event) {
		event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, kickMsg);
	}

}
