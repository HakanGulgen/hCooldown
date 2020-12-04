package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import io.github.hakangulgen.hcooldown.util.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryClickListener implements Listener {

    private final ConfigurationVariables variables;

    public InventoryClickListener(ConfigurationVariables variables) {
        this.variables = variables;
    }

    public static Map<Player, Long> clickCooldown = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        if (!variables.isInventoryEnabled()) return;

        final ItemStack item = event.getCurrentItem();

        if (item == null) return;

        if (variables.isInventoryItemMetaEnabled() && !item.hasItemMeta()) return;

        final Player player = (Player) event.getWhoClicked();

        if (clickCooldown.containsKey(player)) {
            final int cooldownSeconds = variables.getInventoryCooldown();
            final long secondsLeft = ((clickCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

            if (secondsLeft > 0L) {
                event.setCancelled(true);

                if (variables.isInventoryWarnEnabled()) {
                    final String warningMessage = variables.getInventoryWarnMessage().replace("%seconds%", secondsLeft + "");

                    if (variables.getWarningType() == 1) {
                        NMS.sendActionbar(player, warningMessage);
                    } else {
                        player.sendMessage(warningMessage);
                    }
                }
                return;
            }
        }

        clickCooldown.put(player, System.currentTimeMillis());
    }
}
