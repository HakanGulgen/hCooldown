package io.github.hakangulgen.hcooldown;

import dev._2lstudios.hamsterapi.HamsterAPI;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayerManager;
import io.github.hakangulgen.hcooldown.command.hCooldownCommand;
import io.github.hakangulgen.hcooldown.listener.NPCRightClickListener;
import io.github.hakangulgen.hcooldown.listener.PacketReceiveListener;
import io.github.hakangulgen.hcooldown.listener.PlayerQuitListener;
import io.github.hakangulgen.hcooldown.util.ConfigurationUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class hCooldownPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final PluginManager pluginManager = this.getServer().getPluginManager();

        if (!pluginManager.isPluginEnabled("HamsterAPI")) {
            throw new NullPointerException("hCooldown requires HamsterAPI to listen packets!");
        }

        HamsterPlayerManager playerManager = HamsterAPI.getInstance().getHamsterPlayerManager();

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        final ConfigurationVariables variables = new ConfigurationVariables(configurationUtil);

        pluginManager.registerEvents(new PacketReceiveListener(variables, playerManager), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);

        this.getCommand("hcooldown").setExecutor(new hCooldownCommand(variables));

        if (variables.isCitizensEnabled() && pluginManager.getPlugin("Citizens") != null) {
            pluginManager.registerEvents(new NPCRightClickListener(variables, playerManager), this);

            this.getLogger().info("Successfully hooked with Citizens.");
        }
    }
}
