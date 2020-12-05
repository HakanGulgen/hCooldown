package io.github.hakangulgen.hcooldown.listener;

import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final ConfigurationVariables variables;

    public PlayerQuitListener(ConfigurationVariables variables) {
        this.variables = variables;
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        NPCRightClickListener.rightClickCooldown.remove(player);

        if (variables.isUseHamsterAPIEnabled()) {
            PacketReceiveListener.interactCooldown.remove(player);
            PacketReceiveListener.clickCooldown.remove(player);
        } else {
            InventoryClickListener.clickCooldown.remove(player);
            PlayerInteractListener.interactCooldown.remove(player);
        }
    }

    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();

        NPCRightClickListener.rightClickCooldown.remove(player);

        if (variables.isUseHamsterAPIEnabled()) {
            PacketReceiveListener.interactCooldown.remove(player);
            PacketReceiveListener.clickCooldown.remove(player);
        } else {
            InventoryClickListener.clickCooldown.remove(player);
            PlayerInteractListener.interactCooldown.remove(player);
        }
    }
}
