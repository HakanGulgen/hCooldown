package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import io.github.hakangulgen.hcooldown.util.NMS;
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

    public static Map<Player, Long> clickCooldown = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final NPCRightClickEvent event) {
        if (event.isCancelled()) return;

        if (!variables.isCitizensEnabled()) return;

        final Player player = event.getClicker();

        if (clickCooldown.containsKey(player)) {
            final int cooldownSeconds = variables.getCitizensCooldown();
            final long secondsLeft = ((clickCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

            if (secondsLeft > 0L) {
                event.setCancelled(true);

                if (variables.isCitizensWarnEnabled()) {
                    final String warningMessage = variables.getCitizensWarnMessage().replace("%seconds%", secondsLeft + "");

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
