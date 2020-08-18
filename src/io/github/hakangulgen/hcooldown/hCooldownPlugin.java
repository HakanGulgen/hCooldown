package io.github.hakangulgen.hcooldown;

import dev._2lstudios.hamsterapi.HamsterAPI;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayerManager;
import io.github.hakangulgen.hcooldown.command.hCooldownCommand;
import io.github.hakangulgen.hcooldown.listener.InventoryClickListener;
import io.github.hakangulgen.hcooldown.listener.PlayerInteractListener;
import io.github.hakangulgen.hcooldown.listener.PlayerQuitListener;
import io.github.hakangulgen.hcooldown.util.ConfigurationUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class hCooldownPlugin extends JavaPlugin {

    private HamsterPlayerManager hamsterAPI;

    private ConfigurationVariables configurationVariables;

    public boolean checkHamster = false;

    @Override
    public void onEnable() {
        final Server server = this.getServer();
        final PluginManager pluginManager = server.getPluginManager();

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        configurationVariables = new ConfigurationVariables(configurationUtil);

        if (!configurationVariables.getWarningType().equals("chat")) {
            if (pluginManager.getPlugin("HamsterAPI") == null) {
                this.getLogger().severe("The plugin requires 'HamsterAPI' for '" + configurationVariables.getWarningType() + "' type.");
                this.getLogger().severe("Please install 'HamsterAPI' or change warningType to 'chat'.");
                pluginManager.disablePlugin(this);
                return;
            } else {
                hamsterAPI = HamsterAPI.getInstance().getHamsterPlayerManager();
                checkHamster = true;
                this.getLogger().info("Succesfully hooked with HamsterAPI.");
            }
        }

        this.registerListeners(pluginManager);

        this.getCommand("hcooldown").setExecutor(new hCooldownCommand(configurationVariables));
    }

    private void registerListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new InventoryClickListener(this), this);
        pluginManager.registerEvents(new PlayerInteractListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }

    public HamsterPlayerManager getHamsterAPI() { return hamsterAPI; }

    public ConfigurationVariables getVariables() { return configurationVariables; }

}
