/*
 *     Copyright (C) 2013  Nodin Chan
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
 *     along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */

package com.titankingdoms.dev.titanchat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.titankingdoms.dev.titanchat.TitanChat;
import com.titankingdoms.dev.titanchat.api.conversation.Conversation;
import com.titankingdoms.dev.titanchat.api.conversation.Node;
import com.titankingdoms.dev.titanchat.api.user.User;
import com.titankingdoms.dev.titanchat.api.user.UserManager;
import com.titankingdoms.dev.titanchat.user.Participant;
import com.titankingdoms.dev.titanchat.utility.FormatUtils.Format;

public final class TitanChatListener implements Listener {
	
	private final TitanChat plugin;
	
	public TitanChatListener() {
		this.plugin = TitanChat.getInstance();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		UserManager manager = plugin.getManager(UserManager.class);
		
		if (manager == null)
			return;
		
		User user = manager.getUser(event.getPlayer());
		Node viewing = user.getViewing();
		
		if (viewing == null) {
			user.sendRawLine(Format.RED + "Please join a Node to converse");
			return;
		}
		
		viewing.sendConversation(new Conversation(user, viewing, event.getFormat(), event.getMessage()));
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		UserManager manager = plugin.getManager(UserManager.class);
		
		if (manager == null)
			return;
		
		User user = new Participant(event.getPlayer());
		manager.register(user);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		UserManager manager = plugin.getManager(UserManager.class);
		
		if (manager == null)
			return;
		
		User user = manager.get(event.getPlayer().getName());
		manager.unregister(user);
	}
}