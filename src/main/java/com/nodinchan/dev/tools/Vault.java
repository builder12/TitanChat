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

package com.nodinchan.dev.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicesManager;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import net.milkbowl.vault.permission.Permission;

public final class Vault {
	
	private static Chat chat;
	private static Economy econ;
	private static Permission perm;
	
	private Vault() {}
	
	public static EconomyResponse depositBank(String name, double amount) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		if (name == null || name.isEmpty() || amount < 0)
			return new EconomyResponse(0.0D, 0.0D, ResponseType.FAILURE, "Cannot deposit");
		
		return econ.bankDeposit(name, amount);
	}
	
	public static EconomyResponse depositPlayer(Player player, double amount) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		if (player == null || amount < 0)
			return new EconomyResponse(0.0D, 0.0D, ResponseType.FAILURE, "Cannot deposit");
		
		return econ.depositPlayer(player.getName(), player.getWorld().getName(), amount);
	}
	
	public static double getBalance(Player player) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		if (player == null)
			return 0.0D;
		
		return econ.getBalance(player.getName(), player.getWorld().getName());
	}
	
	public static EconomyResponse getBankBalance(String name) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		if (name == null || name.isEmpty())
			return new EconomyResponse(0.0D, 0.0D, ResponseType.FAILURE, "Cannot find balance");
		
		return econ.bankBalance(name);
	}
	
	public static List<String> getBanks() {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		List<String> banks = econ.getBanks();
		return (banks != null) ? banks : new ArrayList<String>();
	}
	
	public static Chat getChatBridge() {
		return chat;
	}
	
	public static Economy getEconomyBridge() {
		return econ;
	}
	
	public static String getGroupInfo(Player player, String node, String def) {
		if (chat == null)
			throw new IllegalStateException("Chat bridge not found");
		
		if (player == null || node == null || node.isEmpty())
			return def;
		
		String group = chat.getPrimaryGroup(player.getWorld(), player.getName());
		
		if (group == null || group.isEmpty())
			return def;
		
		return chat.getGroupInfoString(player.getWorld(), group, node, def);
	}
	
	public static String getGroup(Player player) {
		if (perm == null)
			throw new IllegalStateException("Permission bridge not found");
		
		if (player == null)
			return "";
		
		String group = perm.getPrimaryGroup(player.getWorld(), player.getName());
		return (group != null) ? group : "";
	}
	
	public static String getGroupPrefix(Player player) {
		if (chat == null)
			throw new IllegalStateException("Chat bridge not found");
		
		if (player == null)
			return "";
		
		String group = chat.getPrimaryGroup(player.getWorld(), player.getName());
		
		if (group == null || group.isEmpty())
			return "";
		
		String prefix = chat.getGroupPrefix(player.getWorld(), group);
		return (prefix != null) ? prefix : "";
	}
	
	public static String[] getGroups(Player player) {
		if (perm == null)
			throw new IllegalStateException("Permission bridge not found");
		
		if (player == null)
			return new String[0];
		
		String[] groups = perm.getPlayerGroups(player.getWorld(), player.getName());
		return (groups != null) ? groups : new String[0];
	}
	
	public static String[] getGroups() {
		if (perm == null)
			throw new IllegalStateException("Permission bridge not found");
		
		String[] groups = perm.getGroups();
		return (groups != null) ? groups : new String[0];
	}
	
	public static String getGroupSuffix(Player player) {
		if (chat == null)
			throw new IllegalStateException("Chat bridge not found");
		
		if (player == null)
			return "";
		
		String group = chat.getPrimaryGroup(player.getWorld(), player.getName());
		
		if (group == null || group.isEmpty())
			return "";
		
		String suffix = chat.getGroupSuffix(player.getWorld(), group);
		return (suffix != null) ? suffix : "";
	}
	
	public static Permission getPermissionBridge() {
		return perm;
	}
	
	public static String getPlayerInfo(Player player, String node, String def) {
		if (chat == null)
			throw new IllegalStateException("Chat bridge not found");
		
		if (player == null || node == null || node.isEmpty())
			return def;
		
		return chat.getPlayerInfoString(player.getWorld(), player.getName(), node, def);
	}
	
	public static String getPlayerPrefix(Player player) {
		if (chat == null)
			throw new IllegalStateException("Chat bridge not found");
		
		if (player == null)
			return "";
		
		String prefix = chat.getPlayerPrefix(player.getWorld(), player.getName());
		return (prefix != null) ? prefix : "";
	}
	
	public static String getPlayerSuffix(Player player) {
		if (chat == null)
			throw new IllegalStateException("Chat bridge not found");
		
		if (player == null)
			return "";
		
		String suffix = chat.getPlayerSuffix(player.getWorld(), player.getName());
		return (suffix != null) ? suffix : "";
	}
	
	public static boolean hasAccount(Player player) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		return player != null && econ.hasAccount(player.getName(), player.getWorld().getName());
	}
	
	public static boolean hasPermission(CommandSender sender, String node) {
		if (perm == null)
			throw new IllegalStateException("Permission bridge not found");
		
		if (sender == null || node == null || node.isEmpty())
			return false;
		
		if (Player.class.isInstance(sender))
			return hasPermission((Player) sender, node);
		
		return perm.has(sender, node);
	}
	
	public static boolean hasPermission(Player player, String node) {
		if (perm == null)
			throw new IllegalStateException("Permission bridge not found");
		
		if (player == null || node == null || node.isEmpty())
			return false;
		
		return perm.playerHas(player.getWorld(), player.getName(), node);
	}
	
	public static boolean initialise(Server server) {
		if (server == null || server.getPluginManager().getPlugin("Vault") == null)
			return false;
		
		ServicesManager services = server.getServicesManager();
		
		chat = services.load(Chat.class);
		econ = services.load(Economy.class);
		perm = services.load(Permission.class);
		return true;
	}
	
	public static boolean isChatSetup() {
		return chat != null;
	}
	
	public static boolean isEconomySetup() {
		return econ != null;
	}
	
	public static boolean isPermissionSetup() {
		return perm != null;
	}
	
	public static EconomyResponse withdrawBank(String name, double amount) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		if (name == null || name.isEmpty() || amount < 0)
			return new EconomyResponse(0.0D, 0.0D, ResponseType.FAILURE, "Cannot withdraw");
		
		return econ.bankWithdraw(name, amount);
	}
	
	public static EconomyResponse withdrawPlayer(Player player, double amount) {
		if (econ == null)
			throw new IllegalStateException("Economy bridge not found");
		
		if (player == null || amount < 0)
			return new EconomyResponse(0.0D, 0.0D, ResponseType.FAILURE, "Cannot withdraw");
		
		return econ.withdrawPlayer(player.getName(), player.getWorld().getName(), amount);
	}
}