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

package com.titankingdoms.dev.titanchat.api.meta;

import java.util.*;

import org.apache.commons.lang.Validate;

import com.titankingdoms.dev.titanchat.api.Manager;

public final class AdapterHandler implements Manager<MetaAdapter> {
	
	private final Map<String, MetaAdapter> adapters;
	
	private final Set<String> dependencies = Collections.unmodifiableSet(new HashSet<String>());
	
	public AdapterHandler() {
		this.adapters = new HashMap<String, MetaAdapter>();
	}
	
	@Override
	public MetaAdapter get(String key) {
		return (has(key)) ? adapters.get(key) : new CommonAdapter();
	}
	
	@Override
	public Set<MetaAdapter> getAll() {
		return null;
	}
	
	@Override
	public Set<String> getDependencies() {
		return dependencies;
	}
	
	@Override
	public String getName() {
		return "MetaHandler";
	}
	
	@Override
	public boolean has(String key) {
		return key != null && !key.isEmpty() && adapters.containsKey(key);
	}
	
	@Override
	public boolean has(MetaAdapter adapter) {
		return adapter != null && has(adapter.getKey()) && get(adapter.getKey()).equals(adapter);
	}
	
	@Override
	public void load() {}
	
	@Override
	public List<String> match(String key) {
		if (key == null || key.isEmpty())
			return new ArrayList<String>(adapters.keySet());
		
		List<String> matches = new ArrayList<String>();
		
		for (String adapter : adapters.keySet()) {
			if (!adapter.startsWith(key.toLowerCase()))
				continue;
			
			matches.add(adapter);
		}
		
		Collections.sort(matches);
		
		return matches;
	}
	
	@Override
	public void register(MetaAdapter adapter) {
		Validate.notNull(adapter, "Adapter cannot be null");
		Validate.isTrue(!has(adapter.getKey()), "Adapter already registered");
		
		this.adapters.put(adapter.getKey(), adapter);
	}
	
	@Override
	public void reload() {}
	
	@Override
	public void unload() {}
	
	@Override
	public void unregister(MetaAdapter adapter) {
		Validate.notNull(adapter, "Adapter cannot be null");
		Validate.isTrue(has(adapter.getKey()), "Adapter not registered");
		
		this.adapters.remove(adapter.getKey());
	}
}