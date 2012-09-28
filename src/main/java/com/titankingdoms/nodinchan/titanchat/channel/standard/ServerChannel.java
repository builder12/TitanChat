/*     Copyright (C) 2012  Nodin Chan <nodinchan@live.com>
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

package com.titankingdoms.nodinchan.titanchat.channel.standard;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.TitanChat.MessageLevel;
import com.titankingdoms.nodinchan.titanchat.channel.Channel;
import com.titankingdoms.nodinchan.titanchat.channel.util.handler.*;
import com.titankingdoms.nodinchan.titanchat.channel.util.handler.Handler.HandlerInfo;
import com.titankingdoms.nodinchan.titanchat.channel.util.Info;

/**
 * ServerChannel - Channel when channels are disabled
 * 
 * @author NodinChan
 *
 */
public final class ServerChannel extends Channel {
	
	private static ServerChannel instance;
	
	private final Info info;
	
	private final ChannelLoader loader;
	
	public ServerChannel(ServerChannelLoader loader) {
		super("Server", Option.DEFAULT);
		ServerChannel.instance = this;
		load(null, null);
		this.info = new ServerInfo();
		this.loader = loader;
	}
	
	@Override
	public boolean access(Player player) {
		return true;
	}
	
	@Override
	public Info getInfo() {
		return info;
	}
	
	@Override
	public ChannelLoader getLoader() {
		return loader;
	}
	
	@Override
	public Channel load(String name, Option option) {
		registerCommandHandlers(
				new UnsupportedCommand("ban"),
				new UnsupportedCommand("dewhitelist"),
				new UnsupportedCommand("follow"),
				new UnsupportedCommand("force"),
				new UnsupportedCommand("invite"),
				new UnsupportedCommand("join"),
				new UnsupportedCommand("kick"),
				new UnsupportedCommand("leave"),
				new UnsupportedCommand("unban"),
				new UnsupportedCommand("unfollow"),
				new UnsupportedCommand("whitelist")
		);
		
		registerSettingHandlers(
				new ServerSettingHandlers.ChatColourSetting(),
				new ServerSettingHandlers.UnsupportedSetting("colouring"),
				new ServerSettingHandlers.UnsupportedSetting("format"),
				new ServerSettingHandlers.Help(),
				new ServerSettingHandlers.UnsupportedSetting("radius"),
				new ServerSettingHandlers.UnsupportedSetting("range"),
				new ServerSettingHandlers.TagSetting(),
				new ServerSettingHandlers.TopicSetting(),
				new ServerSettingHandlers.UnsupportedSetting("whitelist")
		);
		
		return this;
	}
	
	/**
	 * UnsupportedCommand - When channels are disabled, disabled some commands
	 * 
	 * @author NodinChan
	 *
	 */
	public static final class UnsupportedCommand extends CommandHandler {
		
		public UnsupportedCommand(String command) {
			super(ServerChannel.instance, command, new HandlerInfo("Command Disabled", command.toLowerCase()));
		}
		
		@Override
		public void onCommand(CommandSender sender, String[] args) {
			plugin.send(MessageLevel.WARNING, sender, "Channels Disabled");
		}
	}
	
	/**
	 * ServerSettingHandlers - When channels are disabled and settings can only be changed by staff
	 * 
	 * @author NodinChan
	 *
	 */
	public static final class ServerSettingHandlers {
		
		/**
		 * ChatColourSetting - For changing the chat colour setting
		 * 
		 * @author NodinChan
		 *
		 */
		public static final class ChatColourSetting extends SettingHandler {
			
			public ChatColourSetting() {
				super(ServerChannel.instance, "chat-colour", new HandlerInfo("Sets the chat display colour", "chat-colour [colour]"));
			}
			
			@Override
			public void set(CommandSender sender, String[] args) {
				try {
					if (!(sender instanceof Player) || plugin.isStaff((Player) sender)) {
						channel.getInfo().setChatColour(args[0]);
						plugin.send(MessageLevel.INFO, sender, "You have set the chat colour to " + channel.getInfo().getChatColour());
						
					} else { plugin.send(MessageLevel.WARNING, sender, "You do not have permission"); }
					
				} catch (IndexOutOfBoundsException e) { invalidArgLength(sender); }
			}
		}
		
		/**
		 * Help - Different help menu
		 * 
		 * @author NodinChan
		 *
		 */
		public static final class Help extends SettingHandler {
			
			public Help() {
				super(ServerChannel.instance, "help", new HandlerInfo("Shows the help menu", "help"));
			}
			
			@Override
			public void set(CommandSender sender, String[] args) {
				sender.sendMessage(ChatColor.AQUA + "=== " + channel.getName() + " ===");
				sender.sendMessage(ChatColor.AQUA + "CHAT-COLOUR [COLOUR] - Sets the chat display colour of the channel");
				sender.sendMessage(ChatColor.AQUA + "HELP - Shows the help menu");
				sender.sendMessage(ChatColor.AQUA + "TAG [TAG] - Sets the tag of the channel");
				sender.sendMessage(ChatColor.AQUA + "TOPIC [TOPIC] - Sets the topic of the channel");
			}
		}
		
		/**
		 * TagSetting - For changing the tag setting
		 * 
		 * @author NodinChan
		 *
		 */
		public static final class TagSetting extends SettingHandler {
			
			public TagSetting() {
				super(ServerChannel.instance, "tag", new HandlerInfo("Sets the tag", "tag [tag]"));
			}
			
			@Override
			public void set(CommandSender sender, String[] args) {
				try {
					if (!(sender instanceof Player) || plugin.isStaff((Player) sender)) {
						channel.getInfo().setTag(args[0]);
						plugin.send(MessageLevel.INFO, sender, "You have set the tag to " + channel.getInfo().getTag());
						
					} else { plugin.send(MessageLevel.WARNING, sender, "You do not have permission"); }
					
				} catch (IndexOutOfBoundsException e) { invalidArgLength(sender); }
			}
		}
		
		/**
		 * TopicSetting - For changing the topic setting
		 * 
		 * @author NodinChan
		 *
		 */
		public static final class TopicSetting extends SettingHandler {
			
			public TopicSetting() {
				super(ServerChannel.instance, "topic", new HandlerInfo("Sets the topic", "topic [topic]"));
			}
			
			@Override
			public void set(CommandSender sender, String[] args) {
				if (args.length > 0) {
					if (!(sender instanceof Player) || plugin.isStaff((Player) sender)) {
						StringBuilder str = new StringBuilder();
						
						for (String arg : args) {
							if (str.length() > 0)
								str.append(" ");
							
							str.append(arg);
						}
						
						channel.getInfo().setTopic(str.toString());
						
						if (!channel.isParticipating(sender.getName()))
							plugin.send(MessageLevel.INFO, sender, "You have changed the topic: " + channel.getInfo().getTopic());
						
						plugin.send(MessageLevel.INFO, channel, ((sender instanceof Player) ? ((Player) sender).getDisplayName() : sender.getName()) + " changed the topic: " + channel.getInfo().getTopic());
						
					} else { plugin.send(MessageLevel.WARNING, sender, "You do not have permission"); }
					
				} else { invalidArgLength(sender); }
			}
		}
		
		/**
		 * UnsupportedCommand - When channels are disabled, disabled some settings
		 * 
		 * @author NodinChan
		 *
		 */
		public static final class UnsupportedSetting extends SettingHandler {
			
			public UnsupportedSetting(String setting) {
				super(ServerChannel.instance, setting, new HandlerInfo("Unsupported Setting", setting.toLowerCase()));
			}
			
			@Override
			public void set(CommandSender sender, String[] args) {
				plugin.send(MessageLevel.WARNING, sender, "Setting Unsupported");
			}
		}
	}
	
	/**
	 * ServerInfo - To override the standard info
	 * 
	 * @author NodinChan
	 *
	 */
	public static final class ServerInfo extends Info {
		
		public ServerInfo() {
			super(null);
		}
		
		@Override
		public boolean colouring() {
			return true;
		}
		
		@Override
		public boolean enableJoinMessage() {
			return false;
		}
		
		@Override
		public boolean enableLeaveMessage() {
			return false;
		}
		
		@Override
		public String getChatColour() {
			return plugin.getConfig().getString("channels.chat-display-colour", "");
		}
		
		@Override
		public String getFormat() {
			if (plugin.getConfig().getBoolean("formatting.use-custom-format"))
				return plugin.getConfig().getString("formatting.format");
			else
				return "<%prefix%player%suffix&f> %message";
		}
		
		@Override
		public String getTag() {
			return plugin.getConfig().getString("channels.tag", "");
		}
		
		@Override
		public Range range() {
			return Range.GLOBAL;
		}
		
		@Override
		public void setChatColour(String colour) {
			plugin.getConfig().set("channels.chat-display-colour", colour);
			plugin.saveConfig();
		}
		
		@Override
		public void setTag(String tag) {
			plugin.getConfig().set("channels.tag", tag);
			plugin.saveConfig();
		}
		
		@Override
		public void setTopic(String topic) {
			plugin.getConfig().set("topic", topic);
			plugin.saveConfig();
		}
	}
}