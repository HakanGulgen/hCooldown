package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.hCooldownPlugin;
import io.github.hakangulgen.hcooldown.util.ActionbarUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    private final hCooldownPlugin plugin;

    public static Map<Player, Long> interactCooldown = new HashMap<>();

    public PlayerInteractListener(hCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent event) {
        if (!event.hasItem()) return;

        if (event.getItem() == null) return;

        final Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        final ConfigurationVariables variables = plugin.getVariables();

        if (!variables.isInteractEnabled()) return;

        if (variables.isInteractItemMetaEnabled() && !event.getItem().hasItemMeta()) return;

        final Player player = event.getPlayer();

        if (interactCooldown.containsKey(player)) {
            final int cooldownSeconds = variables.getInteractCooldown();
            final long secondsLeft = ((interactCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

            if (secondsLeft > 0L) {
                event.setCancelled(true);

                if (variables.isInteractWarnEnabled()) {
                    final String warningMessage = variables.getInteractWarnMessage().replace("%seconds%", secondsLeft + "");

                    if (variables.getWarningType().equals("chat")) {
                        player.sendMessage(warningMessage);
                    } else {
                        ActionbarUtil.sendActionbar(player, warningMessage);
                    }
                }

                return;
            }
        }

        interactCooldown.put(player, System.currentTimeMillis());
    }
}
