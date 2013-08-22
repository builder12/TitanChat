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

package com.titankingdoms.dev.titanchat.core.user;

import org.apache.commons.lang.Validate;

public class Metadata {
	
	private final String key;
	private final String value;
	
	public Metadata(String key, String value) {
		Validate.notEmpty(key, "Key cannot be empty");
		
		this.key = key;
		this.value = (value != null) ? value : "";
	}
	
	public final String getKey() {
		return key;
	}
	
	public final String getValue() {
		return value;
	}
}