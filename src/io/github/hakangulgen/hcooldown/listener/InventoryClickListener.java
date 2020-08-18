package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.hCooldownPlugin;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryClickListener implements Listener {

    private final hCooldownPlugin plugin;

    public static Map<Player, Long> clickCooldown = new HashMap<>();

    public InventoryClickListener(hCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            ConfigurationVariables variables = plugin.getVariables();
            if (variables.isInventoryEnabled()) {
                ItemStack item = event.getCurrentItem();
                if (item != null) {
                    if (variables.isInventoryItemMetaEnabled() && !item.hasItemMeta()) {
                        return;
                    }
                    Player player = (Player) event.getWhoClicked();
                    if (clickCooldown.containsKey(player)) {
                        int cooldownSeconds = variables.getInventoryCooldown();
                        long secondsLeft = ((clickCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);
                        if (secondsLeft > 0L) {
                            event.setCancelled(true);
                            String warningMessage = variables.getInventoryWarnMessage().replace("%seconds%", secondsLeft + "");
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
                    clickCooldown.put(player, System.currentTimeMillis());
                }
            }
        }
    }
}