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

package com.titankingdoms.dev.titanchat.api.command.guide;

import com.titankingdoms.dev.titanchat.api.command.Command;
import com.titankingdoms.dev.titanchat.api.guide.Index;

public abstract class Assistance extends Index {
	
	protected final Command command;
	
	public Assistance(Command command) {
		super((command != null) ? command.getLabel() : "");
		this.command = command;
	}
	
	@Override
	public abstract String getContent(int page);
	
	@Override
	public final String getDescription() {
		return command.getDescription();
	}
	
	@Override
	public final void setDescription(String description) {}
}