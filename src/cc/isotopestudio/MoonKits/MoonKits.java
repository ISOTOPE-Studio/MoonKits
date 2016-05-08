package cc.isotopestudio.MoonKits;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cc.isotopestudio.MoonKits.commandblocker.CommandListener;
import cc.isotopestudio.MoonKits.join.PlayerJoinListener;
import cc.isotopestudio.MoonKits.serverstop.CheckStopTime;
import cc.isotopestudio.MoonKits.severstart.DispatchCommand;
import cc.isotopestudio.MoonKits.severstart.PreventLogin;

public class MoonKits extends JavaPlugin {
	public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
			.append("系统提示").append("]").append(ChatColor.RESET).toString();
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
		getLogger().info("加载配置文件中");
		createFile("config");
		this.getCommand("MoonKits").setExecutor(new ReloadCommand(this));

		getLogger().info("加载 commandblocker kit");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new CommandListener(this), this);

		getLogger().info("加载 serverstart kit");
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

		getLogger().info("加载 serverstop kit");
		new CheckStopTime(this).runTaskTimer(this, 20 * 60, 20 * 60);
		System.out.println(CheckStopTime.format.format(new Date()) + " : " + getConfig().getString("serverstop.time"));
		
		getLogger().info("加载 join kit");
		pm.registerEvents(new PlayerJoinListener(this), this);
		
		getLogger().info(pluginName + "成功加载!");
		getLogger().info(pluginName + "由ISOTOPE Studio制作!");
		getLogger().info("http://isotopestudio.cc");
	}

	@Override
	public void onDisable() {
		getLogger().info(pluginName + "成功卸载!");
	}

	public void onReload() {
		this.reloadConfig();
		getLogger().info(pluginName + "成功重载!");
		getLogger().info(pluginName + "由ISOTOPE Studio制作!");
	}
}
