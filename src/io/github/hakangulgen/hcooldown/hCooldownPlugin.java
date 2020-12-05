package io.github.hakangulgen.hcooldown;

import dev._2lstudios.hamsterapi.HamsterAPI;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayerManager;
import io.github.hakangulgen.hcooldown.command.hCooldownCommand;
import io.github.hakangulgen.hcooldown.listener.*;
import io.github.hakangulgen.hcooldown.util.ConfigurationUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class hCooldownPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Server server = this.getServer();
        final Logger logger = server.getLogger();
        final PluginManager pluginManager = server.getPluginManager();

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        final ConfigurationVariables variables = new ConfigurationVariables(configurationUtil);

        if (variables.isUseHamsterAPIEnabled() && pluginManager.isPluginEnabled("HamsterAPI")) {
            HamsterPlayerManager playerManager = HamsterAPI.getInstance().getHamsterPlayerManager();

            pluginManager.registerEvents(new PacketReceiveListener(variables, playerManager), this);

            logger.info("[hCooldown] Successfully hooked with HamsterAPI.");
            logger.info("[hCooldown] Now plugin uses packets instead of Bukkit events.");
        } else {
            variables.setUseHamsterAPIEnabled(false);

            pluginManager.registerEvents(new PlayerInteractListener(variables), this);
            pluginManager.registerEvents(new InventoryClickListener(variables), this);
        }

        pluginManager.registerEvents(new PlayerQuitListener(variables), this);

        this.getCommand("hcooldown").setExecutor(new hCooldownCommand(variables));

        if (variables.isCitizensEnabled() && pluginManager.getPlugin("Citizens") != null) {
            pluginManager.registerEvents(new NPCRightClickListener(variables), this);

            logger.info("[hCooldown] Successfully hooked with Citizens.");
        }
    }
}
