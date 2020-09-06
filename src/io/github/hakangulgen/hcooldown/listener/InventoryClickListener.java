package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.hCooldownPlugin;
import io.github.hakangulgen.hcooldown.util.ActionbarUtil;
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
    public void onClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        final ConfigurationVariables variables = plugin.getVariables();

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

                    if (variables.getWarningType().equals("chat")) {
                        player.sendMessage(warningMessage);
                    } else {
                        ActionbarUtil.sendActionbar(player, warningMessage);
                    }
                }

                return;
            }
        }

        clickCooldown.put(player, System.currentTimeMillis());
    }
}
