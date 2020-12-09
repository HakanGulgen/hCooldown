package io.github.hakangulgen.hcooldown.listener;

import dev._2lstudios.hamsterapi.enums.PacketType;
import dev._2lstudios.hamsterapi.events.PacketReceiveEvent;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayer;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayerManager;
import dev._2lstudios.hamsterapi.wrappers.PacketWrapper;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PacketReceiveListener implements Listener {

    private final ConfigurationVariables variables;
    private final HamsterPlayerManager playerManager;

    public PacketReceiveListener(ConfigurationVariables variables, HamsterPlayerManager playerManager) {
        this.variables = variables;
        this.playerManager = playerManager;
    }

    public static Map<Player, Long> interactCooldown = new HashMap<>();
    public static Map<Player, Long> clickCooldown = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onPacketReceive(final PacketReceiveEvent event) {
        final PacketWrapper packetWrapper = event.getPacket();
        final PacketType packetType = packetWrapper.getType();

        if (packetType == PacketType.PacketPlayInBlockPlace && variables.isInteractEnabled()) {
            final HamsterPlayer hamsterPlayer = event.getHamsterPlayer();
            final Player player = hamsterPlayer.getPlayer();

            final ItemStack item = player.getItemInHand();

            if (item.getType() == Material.AIR) return;

            if (variables.isInteractItemMetaEnabled() && !item.hasItemMeta()) return;

            if (interactCooldown.containsKey(player)) {
                final int cooldownSeconds = variables.getInteractCooldown();
                final long secondsLeft = ((interactCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

                if (secondsLeft > 0L) {
                    event.setCancelled(true);

                    if (variables.isInteractWarnEnabled()) {
                        final String warningMessage = variables.getInteractWarnMessage().replace("%seconds%", secondsLeft + "");

                        if (variables.getWarningType() == 1) {
                            playerManager.get(player).sendActionbar(warningMessage);
                        } else {
                            player.sendMessage(warningMessage);
                        }
                    }

                    return;
                }
            }

            interactCooldown.put(player, System.currentTimeMillis());
        } else if (packetType == PacketType.PacketPlayInWindowClick && variables.isInventoryEnabled()) {
            final ItemStack item = packetWrapper.getItem("item");

            if (item == null) return;

            if (variables.isInventoryItemMetaEnabled() && !item.hasItemMeta()) return;

            final HamsterPlayer hamsterPlayer = event.getHamsterPlayer();
            final Player player = hamsterPlayer.getPlayer();

            if (clickCooldown.containsKey(player)) {
                final int cooldownSeconds = variables.getInventoryCooldown();
                final long secondsLeft = ((clickCooldown.get(player) / 1000) + cooldownSeconds) - (System.currentTimeMillis() / 1000);

                if (secondsLeft > 0L) {
                    event.setCancelled(true);

                    if (variables.isInventoryWarnEnabled()) {
                        final String warningMessage = variables.getInventoryWarnMessage().replace("%seconds%", secondsLeft + "");

                        if (variables.getWarningType() == 1) {
                            playerManager.get(player).sendActionbar(warningMessage);
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
}
