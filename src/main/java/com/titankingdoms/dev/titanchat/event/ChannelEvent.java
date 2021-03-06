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

package com.titankingdoms.dev.titanchat.event;

import org.bukkit.event.Event;

import com.titankingdoms.dev.titanchat.core.channel.Channel;

/**
 * {@link ChannelEvent} - Base for {@link ChannelEvent}s
 * 
 * @author NodinChan
 *
 */
public abstract class ChannelEvent extends Event {
	
	protected final Channel channel;
	
	public ChannelEvent(Channel channel) {
		this.channel = channel;
	}
	
	/**
	 * Gets the {@link Channel} involved
	 * 
	 * @return The {@link Channel}
	 */
	public final Channel getChannel() {
		return channel;
	}
}