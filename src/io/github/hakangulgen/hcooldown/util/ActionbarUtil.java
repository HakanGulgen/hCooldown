package io.github.hakangulgen.hcooldown.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionbarUtil {

    public static void sendActionbar(Player player, String message) {
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message +"\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatBaseComponent, (byte) 2);

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}
