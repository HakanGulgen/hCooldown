package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.util.ActionbarUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class NPCRightClickListener implements Listener {

    private final ConfigurationVariables variables;

    public NPCRightClickListener(ConfigurationVariables variables) {
        this.variables = variables;
    }

    public static Map<Player, Long> rightClickCooldown = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onRightClick(final NPCRightClickEvent event) {
        if (!variables.isCitizensEnabled()) return;

        final Player player = event.getClicker();

        if (rightClickCooldown.containsKey(player)) {
            final int cooldownSeconds = variables.getCitizensCooldown();
            final long secondsLeft = ((rightClickCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

            if (secondsLeft > 0L) {
                event.setCancelled(true);

                if (variables.isCitizensWarnEnabled()) {
                    final String warningMessage = variables.getCitizensWarnMessage().replace("%seconds%", secondsLeft + "");

                    if (variables.getWarningType() == 1) {
                        ActionbarUtil.sendActionbar(player, warningMessage);
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
