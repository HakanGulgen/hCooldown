package io.github.hakangulgen.hcooldown.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        PacketReceiveListener.interactCooldown.remove(player);
        PacketReceiveListener.clickCooldown.remove(player);
        NPCRightClickListener.rightClickCooldown.remove(player);
    }

    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();

        PacketReceiveListener.interactCooldown.remove(player);
        PacketReceiveListener.clickCooldown.remove(player);
        NPCRightClickListener.rightClickCooldown.remove(player);
    }
}
