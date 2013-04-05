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

package com.titankingdoms.dev.titanchat;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.titankingdoms.dev.titanchat.core.channel.Channel;
import com.titankingdoms.dev.titanchat.core.participant.Participant;
import com.titankingdoms.dev.titanchat.core.participant.PlayerParticipant;
import com.titankingdoms.dev.titanchat.format.Format;
import com.titankingdoms.dev.titanchat.util.Messaging;

/**
 * {@link TitanChatListener} - The listener of TitanChat
 * 
 * @author NodinChan
 *
 */
public final class TitanChatListener implements Listener {
	
	private final TitanChat plugin;
	
	private final String site = "http://dev.bukkit.org/server-mods/titanchat/";
	private final double currentVer = 4.0;
	private double newVer;
	
	public TitanChatListener() {
		this.plugin = TitanChat.getInstance();
	}
	
	/**
	 * Processes chat
	 * 
	 * @param event {@link AsyncPlayerChatEvent}
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		
		Participant participant = plugin.getParticipantManager().getParticipant(event.getPlayer());
		String message = event.getMessage();
		
		if (message.startsWith("@") && message.split(" ").length > 1) {
			Channel channel = plugin.getChannelManager().getChannel(message.split(" ")[0].substring(1));
			
			if (channel == null) {
				Messaging.sendMessage(event.getPlayer(), "&4Channel does not exist");
				return;
			}
			
			participant.chat(channel, message.substring(message.indexOf(" ") + 1, message.length()));
			
		} else { participant.chat(message); }
	}
	
	/**
	 * Registers {@link Player}s on join
	 * 
	 * @param event {@link PlayerJoinEvent}
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!plugin.getParticipantManager().hasParticipant(event.getPlayer().getName()))
			plugin.getParticipantManager().registerParticipants(new PlayerParticipant(event.getPlayer()));
		
		Participant participant = plugin.getParticipantManager().getParticipant(event.getPlayer());
		
		if (participant.hasPermission("TitanChat.update")) {
			if (updateCheck() > currentVer) {
				participant.sendMessage("&6" + newVer + " &5is out!");
				participant.sendMessage("&5You are running &6" + currentVer);
				participant.sendMessage("&5Update at &9" + site);
			}
		}
	}
	
	/**
	 * Colourises text on signs
	 * 
	 * @param event {@link SignChangeEvent}
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		for (int line = 0; line < 4; line++)
			event.setLine(line, Format.colourise(event.getLine(line)));
	}
	
	/**
	 * Checks for updates
	 * 
	 * @return The latest version
	 */
	private double updateCheck() {
		try {
			URL url = new URL("http://dev.bukkit.org/server-mods/titanchat/files.rss");
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
			doc.getDocumentElement().normalize();
			
			Node node = doc.getElementsByTagName("item").item(0);
			
			if (node.getNodeType() == 1) {
				Node name = ((Element) node).getElementsByTagName("title").item(0);
				Node version = name.getChildNodes().item(0);
				this.newVer = Double.valueOf(version.getNodeValue().split(" ")[1].trim().substring(1));
			}
			
		} catch (Exception e) { this.newVer = currentVer; }
		
		return this.newVer;
	}
}