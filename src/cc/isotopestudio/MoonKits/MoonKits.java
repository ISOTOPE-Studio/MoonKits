package cc.isotopestudio.MoonKits;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import cc.isotopestudio.MoonKits.commandblocker.CommandListener;
import cc.isotopestudio.MoonKits.join.PlayerJoinListener;
import cc.isotopestudio.MoonKits.serverstop.CheckStopTime;
import cc.isotopestudio.MoonKits.severstart.DispatchCommand;
import cc.isotopestudio.MoonKits.severstart.PreventLogin;

public class MoonKits extends JavaPlugin {
	public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
			.append("ϵͳ��ʾ").append("]").append(ChatColor.RESET).toString();
	public static final String pluginName = "MoonKits";

	public void createFile(String name) {
		File file;
		file = new File(getDataFolder(), name + ".yml");
		if (!file.exists()) {
			saveDefaultConfig();
		}
	}

	@Override
	public void onEnable() {
		getLogger().info("���������ļ���");
		createFile("config");
		this.getCommand("MoonKits").setExecutor(new ReloadCommand(this));
		loadKits();
		getLogger().info(pluginName + "�ɹ�����!");
		getLogger().info(pluginName + "��ISOTOPE Studio����!");
		getLogger().info("http://isotopestudio.cc");
	}

	@Override
	public void onDisable() {
		getLogger().info(pluginName + "�ɹ�ж��!");
	}

	public void onReload() {
		HandlerList.unregisterAll();
		Bukkit.getScheduler().cancelAllTasks();
		loadKits();
		getLogger().info(pluginName + "�ɹ�����!");
		getLogger().info(pluginName + "��ISOTOPE Studio����!");
		getLogger().info("http://isotopestudio.cc");
	}

	public void loadKits() {
		PluginManager pm = this.getServer().getPluginManager();

		if (getConfig().getBoolean("commandblocker.enable")) {
			getLogger().info("���� commandblocker kit");
			pm.registerEvents(new CommandListener(this), this);
		} else {
			getLogger().info("���� commandblocker kit");
		}

		if (getConfig().getBoolean("serverstart.enable")) {
			getLogger().info("���� serverstart kit");
			PreventLogin LoginListener = new PreventLogin(this);
			pm.registerEvents(LoginListener, this);
			DispatchCommand.plugin = this;
			List<String> cmdList = getConfig().getStringList("serverstart.commands");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					new DispatchCommand(cmdList);
				}
			}, 1);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					AsyncPlayerPreLoginEvent.getHandlerList().unregister(LoginListener);
				}
			}, 20 * getConfig().getInt("serverstart.wait", 60));
		} else {
			getLogger().info("���� serverstart kit");
		}
		
		if (getConfig().getBoolean("serverstop.enable")) {
			getLogger().info("���� serverstop kit");
			new CheckStopTime(this).runTaskTimer(this, 20 * 60, 20 * 60);
		} else {
			getLogger().info("���� serverstop kit");
		}

		if (getConfig().getBoolean("join.enable")) {
			getLogger().info("���� join kit");
			pm.registerEvents(new PlayerJoinListener(this), this);
		} else {
			getLogger().info("���� join kit");
		}
		
		
	}
}
