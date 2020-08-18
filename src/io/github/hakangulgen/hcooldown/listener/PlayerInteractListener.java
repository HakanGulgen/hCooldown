package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.hCooldownPlugin;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    private final hCooldownPlugin plugin;

    public static Map<Player, Long> interactCooldown = new HashMap<>();

    public PlayerInteractListener(hCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        ConfigurationVariables variables = plugin.getVariables();
        if (variables.isInteractEnabled()) {
            Player player = event.getPlayer();
            ItemStack item = player.getItemInHand();
            if (item.getType() != Material.AIR) {
                if (variables.isInteractItemMetaEnabled() && !item.hasItemMeta()) {
                    return;
                }
                Action action = event.getAction();
                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    if (interactCooldown.containsKey(player)) {
                        int cooldownSeconds = variables.getInteractCooldown();
                        long secondsLeft = ((interactCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);
                        if (secondsLeft > 0L) {
                            event.setCancelled(true);
                            String warningMessage = variables.getInteractWarnMessage().replace("%seconds%", secondsLeft + "");
                            if (variables.getWarningType().equals("chat")) {
                                player.sendMessage(warningMessage);
                            } else if (plugin.checkHamster) {
                                plugin.getHamsterAPI().get(player).sendActionbar(warningMessage);
                            } else {
                                player.sendMessage(warningMessage);
                            }
                            return;
                        }
                    }
                    interactCooldown.put(player, System.currentTimeMillis());
                }
            }
        }
    }
}
