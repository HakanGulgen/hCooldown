package io.github.hakangulgen.hcooldown.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        PlayerInteractListener.interactCooldown.remove(player);
        InventoryClickListener.clickCooldown.remove(player);
        NPCRightClickListener.rightClickCooldown.remove(player);
    }
}
