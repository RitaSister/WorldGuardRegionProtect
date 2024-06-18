package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class MessageCheckVersion implements CheckVersion<WorldGuardRegionProtectBukkitPlugin> {

    private final ParamsVersionCheck<YamlConfiguration> paramsVersionCheck;

    public MessageCheckVersion(ParamsVersionCheck<YamlConfiguration> paramsVersionCheck) {
        this.paramsVersionCheck = paramsVersionCheck;
    }

    @Override
    public void checkVersion(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin, final @NotNull Config config) {
        wgrpBukkitPlugin.getPluginLogger().info("Start checking for new versions for language file...");
        String lang = config.getLang();
        File currentLangFile = new File(wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(), "lang/" + lang + ".yml");
        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(
                wgrpBukkitPlugin.getWgrpBukkitBase().getResource("lang/" + lang + ".yml")));
        YamlConfiguration currentVersion = YamlConfiguration.loadConfiguration(currentLangFile);
        YamlConfiguration newVersion = YamlConfiguration.loadConfiguration(inputStreamReader);
        if (currentLangFile.exists() && !paramsVersionCheck.getCurrentVersion(currentVersion).equals(Objects.requireNonNull(paramsVersionCheck.getNewVersion(newVersion)))) {
            wgrpBukkitPlugin.getPluginLogger().info("Found new version of lang file, updating this now...");
            Path renameOldLang = new File(
                    wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(),
                    "lang/" + lang + "-old-" + paramsVersionCheck.getSimpleDateFormat() + ".yml").toPath();
            try {
                Files.move(currentLangFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpBukkitPlugin.getWgrpBukkitBase().saveResource("lang/" + lang + ".yml", true);
        } else {
            wgrpBukkitPlugin.getPluginLogger().info("No update is required for the lang file");
        }
    }

}
