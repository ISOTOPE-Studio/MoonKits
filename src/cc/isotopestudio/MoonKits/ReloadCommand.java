package cc.isotopestudio.MoonKits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class ReloadCommand implements CommandExecutor {
	private final MoonKits plugin;

	public ReloadCommand(MoonKits moonKits) {
		this.plugin = moonKits;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("MoonKits")) {
			if (sender.isOp()) {
				plugin.onReload();
				sender.sendMessage(
						new StringBuilder(MoonKits.prefix).append(ChatColor.GREEN).append("成功重载!").toString());
				return true;
			}
			sender.sendMessage(
					new StringBuilder(MoonKits.prefix).append(ChatColor.RED).append("你没有权限").toString());

			return true;
		}
		return false;
	}
}
