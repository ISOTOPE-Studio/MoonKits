package cc.isotopestudio.MoonKits.severstart;

import java.util.List;

import org.bukkit.Bukkit;

import cc.isotopestudio.MoonKits.MoonKits;

public class DispatchCommand {
	private final List<String> cmdList;
	public static MoonKits plugin;

	public DispatchCommand(List<String> list) {
		cmdList = list;
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				dispatch(0);
			}
		}, 20);
	}

	public void dispatch(int n) {
		if (n >= cmdList.size())
			return;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdList.get(n));
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				dispatch(n + 1);
			}
		}, 10);
	}

}
