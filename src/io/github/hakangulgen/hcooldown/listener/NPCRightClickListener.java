package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.hCooldownPlugin;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class NPCRightClickListener implements Listener {

    private final hCooldownPlugin plugin;

    public static Map<Player, Long> rightClickCooldown = new HashMap<>();

    public NPCRightClickListener(hCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightclick(final NPCRightClickEvent event) {
        final ConfigurationVariables variables = plugin.getVariables();
        if (!variables.isCitizensEnabled()) return;

        final Player player = event.getClicker();

        if (rightClickCooldown.containsKey(player)) {
            final int cooldownSeconds = variables.getCitizensCooldown();
            final long secondsLeft = ((rightClickCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

            if (secondsLeft > 0L) {
                event.setCancelled(true);

                if (variables.isCitizensWarnEnabled()) {
                    String warningMessage = variables.getCitizensWarnMessage().replace("%seconds%", secondsLeft + "");
                    if (variables.getWarningType().equals("chat")) {
                        player.sendMessage(warningMessage);
                    } else if (plugin.checkHamster) {
                        plugin.getHamsterAPI().get(player).sendActionbar(warningMessage);
                    } else {
                        player.sendMessage(warningMessage);
                    }
                }

                return;
            }
        }

        rightClickCooldown.put(player, System.currentTimeMillis());
    }
}
