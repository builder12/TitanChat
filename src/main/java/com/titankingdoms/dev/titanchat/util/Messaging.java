/*
 *     Copyright (C) 2013  Nodin Chan <nodinchan@live.com>
 *     
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.titankingdoms.dev.titanchat.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.titankingdoms.dev.titanchat.TitanChat;
import com.titankingdoms.dev.titanchat.core.channel.Channel;
import com.titankingdoms.dev.titanchat.core.participant.Participant;
import com.titankingdoms.dev.titanchat.format.ChatUtils;
import com.titankingdoms.dev.titanchat.format.Format;

public final class Messaging {
	
	public static void broadcast(String... messages) {
		List<String> lines = new ArrayList<String>();
		
		for (String message : messages)
			lines.addAll(Arrays.asList(ChatUtils.wordWrap(Format.colourise(message), 119)));
		
		for (String line : lines)
			TitanChat.getInstance().getServer().broadcastMessage(line);
	}
	
	public static void broadcast(World world, String... messages) {
		if (world == null)
			return;
		
		List<String> lines = new ArrayList<String>();
		
		for (String message : messages)
			lines.addAll(Arrays.asList(ChatUtils.wordWrap(Format.colourise(message), 119)));
		
		for (Player player : world.getPlayers())
			player.sendMessage(lines.toArray(new String[0]));
	}
	
	public static void broadcast(Channel channel, String... messages) {
		if (channel == null)
			return;
		
		List<String> lines = new ArrayList<String>();
		
		for (String message : messages)
			lines.addAll(Arrays.asList(ChatUtils.wordWrap(Format.colourise(message), 119)));
		
		for (Participant participant : channel.getParticipants())
			participant.sendMessage(lines.toArray(new String[0]));
	}
	
	public static void broadcast(CommandSender sender, double radius, String... messages) {
		if (sender == null)
			return;
		
		if (!(sender instanceof Player))
			sendMessage(sender, messages);
		
		for (Entity entity : ((Player) sender).getNearbyEntities(radius, radius, radius))
			if (entity instanceof Player)
				sendMessage((Player) entity, messages);
	}
	
	public static void sendMessage(CommandSender sender, String... messages) {
		if (sender == null)
			return;
		
		List<String> lines = new ArrayList<String>();
		
		for (String message : messages)
			lines.addAll(Arrays.asList(ChatUtils.wordWrap(Format.colourise(message), 119)));
		
		sender.sendMessage(lines.toArray(new String[0]));
	}
}