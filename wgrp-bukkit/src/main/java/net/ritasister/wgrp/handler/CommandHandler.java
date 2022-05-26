package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.extend.CommandWGRP;

public record CommandHandler(WorldGuardRegionProtect wgRegionProtect) {

	public void commandHandler() {
		try{
			new CommandWGRP(wgRegionProtect);

			wgRegionProtect.getRsApi().getLogger().info("&2All commands registered successfully!");
		}catch(NullPointerException e) {
			wgRegionProtect.getRsApi().getLogger().error("&cCommand cannot be &4null.");
			wgRegionProtect.getRsApi().getLogger().error("&cPossible for reason:");
			wgRegionProtect.getRsApi().getLogger().error("&c- command not set in &4getCommand(ucl.cmd_name).");
			wgRegionProtect.getRsApi().getLogger().error("&c- command not set in &4UtilCommandList.");
			wgRegionProtect.getRsApi().getLogger().error("&c- command not set in &4plugin.yml.");
		}
	}
}