package io.github.hakangulgen.hcooldown.command;

import io.github.hakangulgen.hcooldown.listener.InventoryClickListener;
import io.github.hakangulgen.hcooldown.listener.NPCRightClickListener;
import io.github.hakangulgen.hcooldown.listener.PacketReceiveListener;
import io.github.hakangulgen.hcooldown.listener.PlayerInteractListener;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class hCooldownCommand implements CommandExecutor {

    private final ConfigurationVariables variables;

    public hCooldownCommand(ConfigurationVariables variables) { this.variables = variables; }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (!player.hasPermission("hcooldown.usage")) {
                player.sendMessage(variables.getNoPermission());
                return true;
            }
        }
        if (args.length != 1) {
            this.help(sender);
        } else if (args[0].equalsIgnoreCase("reload")) {
            NPCRightClickListener.rightClickCooldown.clear();

            if (variables.isUseHamsterAPIEnabled()) {
                PacketReceiveListener.interactCooldown.clear();
                PacketReceiveListener.clickCooldown.clear();
            } else {
                InventoryClickListener.clickCooldown.clear();
                PlayerInteractListener.interactCooldown.clear();
            }

            variables.reloadConfig();

            sender.sendMessage(variables.getPluginReloaded());
        } else {
            this.help(sender);
        }
        return false;
    }

    private void help(CommandSender sender) {
        final String prefix = variables.getPrefix();

        sender.sendMessage(prefix + " §bAuthor: §7HKNGLGN (hknn)");
        sender.sendMessage(prefix + " §7Plugin uses: §e" + (variables.isUseHamsterAPIEnabled() ? "Packets §8(§eHamsterAPI§8)" : "Bukkit events"));
        sender.sendMessage(prefix + " §b/hcooldown reload §8- §7Reload configuration.");
    }
}
