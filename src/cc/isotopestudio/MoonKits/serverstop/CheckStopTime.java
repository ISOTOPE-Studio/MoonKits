package cc.isotopestudio.MoonKits.serverstop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.MoonKits.MoonKits;

public class CheckStopTime extends BukkitRunnable {

	private final MoonKits plugin;
	public final static SimpleDateFormat format = new SimpleDateFormat("HH:mm");;

	public CheckStopTime(MoonKits moonKits) {
		this.plugin = moonKits;
	}

	@Override
	public void run() {
		List<String> timeList = plugin.getConfig().getStringList("serverstop.time");
		if (timeList.size() < 1)
			return;
		for (String time : timeList) {
			if (format.format(new Date()).equals(time)) {
				List<String> warnList = plugin.getConfig().getStringList("serverstop.warning");
				if (warnList.size() < 1)
					return;
				int tick = getTicks(warnList.get(0));
				plugin.getLogger().info("服务器即将重启");
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						for (Player player : Bukkit.getServer().getOnlinePlayers()) {
							player.kickPlayer(plugin.getConfig().getString("serverstop.msg.kick", "§c服务器正在重启中！"));
						}
					}
				}, tick);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						Bukkit.shutdown();
					}
				}, tick + 20);
				for (int i = 0; i < warnList.size(); i++) {
					String s = warnList.get(i);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@SuppressWarnings("deprecation")
						public void run() {
							for (Player player : Bukkit.getServer().getOnlinePlayers()) {
								player.sendTitle(
										getMsg(plugin.getConfig().getString("serverstop.msg.main", "§c服务器即将重启"), s),
										getMsg(plugin.getConfig().getString("serverstop.msg.sub", "§a还有{time}"), s));
							}
						}
					}, tick - getTicks(s));
				}
				break;
			}
		}
	}

	public static int getTicks(String s) {
		int result = -1;
		try {
			result = Integer.parseInt(s.substring(0, s.length() - 1));
		} catch (Exception e) {
			return -1;
		}
		if (s.endsWith("m")) {
			return result * 60 * 20;
		} else if (s.endsWith("s")) {
			return result * 20;
		} else
			return -1;
	}

	String getMsg(String s, String time) {
		if (s.contains("{time}")) {
			int result = -1;
			try {
				result = Integer.parseInt(time.substring(0, time.length() - 1));
			} catch (Exception e) {
				return s;
			}
			if (time.endsWith("m")) {
				return s.replace("{time}", result + "分钟");
			} else if (time.endsWith("s")) {
				return s.replace("{time}", result + "秒");
			} else
				return s;
		} else
			return s;
	}

}
