package io.github.hakangulgen.hcooldown.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS {

    /*public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int displayTime, int fadeOut) {
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
        IChatBaseComponent subTitleJSON = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON);
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleJSON);
        PacketPlayOutTitle lengthPacket = new PacketPlayOutTitle(fadeIn * 20, displayTime * 20, fadeOut * 20);

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        connection.sendPacket(titlePacket);
        connection.sendPacket(subTitlePacket);
        connection.sendPacket(lengthPacket);
    }*/

    public static void sendActionbar(Player player, String message) {
        IChatBaseComponent actionJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message +"\"}");
        PacketPlayOutChat actionBarPacket = new PacketPlayOutChat(actionJSON, (byte) 2);

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(actionBarPacket);
    }
}
