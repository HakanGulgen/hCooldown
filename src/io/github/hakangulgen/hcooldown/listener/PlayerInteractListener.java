package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.util.ActionbarUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
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

    private final ConfigurationVariables variables;

    public PlayerInteractListener(ConfigurationVariables variables) {
        this.variables = variables;
    }

    public static Map<Player, Long> interactCooldown = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent event) {
        if (!variables.isInteractEnabled()) return;

        final ItemStack item = event.getItem();

        if (item == null) return;

        final Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        if (variables.isInteractItemMetaEnabled() && !item.hasItemMeta()) return;

        final Player player = event.getPlayer();

        if (interactCooldown.containsKey(player)) {
            final int cooldownSeconds = variables.getInteractCooldown();
            final long secondsLeft = ((interactCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

            if (secondsLeft > 0L) {
                event.setCancelled(true);

                if (variables.isInteractWarnEnabled()) {
                    final String warningMessage = variables.getInteractWarnMessage().replace("%seconds%", secondsLeft + "");

                    if (variables.getWarningType() == 1) {
                        ActionbarUtil.sendActionbar(player, warningMessage);
                    } else {
                        player.sendMessage(warningMessage);
                    }
                }

                return;
            }
        }

        interactCooldown.put(player, System.currentTimeMillis());
    }
}
