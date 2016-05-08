package cc.isotopestudio.MoonKits.commandblocker;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import cc.isotopestudio.MoonKits.MoonKits;
import net.md_5.bungee.api.ChatColor;

public class CommandListener implements Listener {

	private final MoonKits plugin;

	public CommandListener(MoonKits moonKits) {
		this.plugin = moonKits;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (event.getPlayer().isOp())
			return;
		String command = event.getMessage();
		String worldName = event.getPlayer().getWorld().getName();
		List<String> banList = plugin.getConfig().getStringList("commandblocker." + worldName);
		if (banList.size() < 1)
			return;
		boolean needBan = false;
		for (String tempCmd : banList) {
			int index = tempCmd.length() + 1;
			if (command.startsWith("/" + tempCmd)) {
				if (command.length() == index) {
					needBan = true;
					break;
				}
				if (command.charAt(index) == ' ') {
					needBan = true;
					break;
				}
			}
		}
		if (needBan) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					new StringBuilder(MoonKits.prefix).append(ChatColor.RED).append("您无法在该世界使用这个指令!").toString());
			return;
		}
	}
}
