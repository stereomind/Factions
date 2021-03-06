package com.massivecraft.factions.commands;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

public class FCommandSafeclaim extends FBaseCommand {
	
	public FCommandSafeclaim() {
		aliases.add("safeclaim");
		aliases.add("safe");
		
		optionalParameters.add("radius");
		
		helpDescription = "Claim land for the safezone";
	}
	
	@Override
	public boolean hasPermission(CommandSender sender) {
		return Factions.hasPermManageSafeZone(sender);
	}
	
	@Override
	public void perform() {
		
		if( isLocked() ) {
			sendLockMessage();
			return;
		}
		
		// The current location of the player
		FLocation playerFlocation = new FLocation(me);
		
		// Was a radius set?
		if (parameters.size() > 0) {
			int radius = Integer.parseInt(parameters.get(0));
			
			FLocation from = playerFlocation.getRelative(radius, radius);
			FLocation to = playerFlocation.getRelative(-radius, -radius);
			
			for (FLocation locToClaim : FLocation.getArea(from, to)) {
				Board.setFactionAt(Faction.getSafeZone(), locToClaim);
			}
			
			sendMessage("You claimed "+(1+radius*2)*(1+radius*2)+" chunks for the safe zone.");
			
		} else {
			Board.setFactionAt(Faction.getSafeZone(), playerFlocation);
			sendMessage("This land is now a safe zone.");
		}
	}
	
}
