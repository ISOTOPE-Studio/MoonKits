package cc.isotopestudio.MoonKits.join;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cc.isotopestudio.MoonKits.MoonKits;

public class PlayerJoinListener implements Listener {

	private final MoonKits plugin;
	private final List<String> list;
	private final List<String> quitList;

	public PlayerJoinListener(MoonKits moonKits) {
		this.plugin = moonKits;
		list = new ArrayList<String>();
		quitList = new ArrayList<String>();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final String name = event.getPlayer().getName();
		list.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				list.remove(name);
			}
		}, 20 * plugin.getConfig().getInt("join.time", 3));
		if (quitList.remove(name)) {
			Location loc = new Location(Bukkit.getWorld(plugin.getConfig().getString("join.location.world")),
					plugin.getConfig().getInt("join.location.x", 0), plugin.getConfig().getInt("join.location.y", 0),
					plugin.getConfig().getInt("join.location.z", 0));
			event.getPlayer().teleport(loc);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		final String name = event.getPlayer().getName();
		if (list.contains(name)) {
			quitList.add(name);
		}
	}

}
