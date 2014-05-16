/*
 *     Copyright (C) 2014  Nodin Chan
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

package com.nodinchan.dev.titanchat.api.event;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.nodinchan.dev.titanchat.api.command.Command;

public class CommandEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private final Command command;
	
	private final String type;
	
	public CommandEvent(Command command, String type) {
		super(!Bukkit.isPrimaryThread());
		Validate.notNull(command, "Command cannot be null");
		Validate.notEmpty(type, "Type cannot be empty");
		
		this.command = command;
		this.type = type;
	}
	
	public final Command getCommand() {
		return command;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public final String getType() {
		return type;
	}
}