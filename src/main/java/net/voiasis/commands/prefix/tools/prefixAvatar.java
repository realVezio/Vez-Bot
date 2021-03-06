package net.voiasis.commands.prefix.tools;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class prefixAvatar {
    public static void avatar(Message message, String[] args) {
        try {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.RED);
            if(message.getMentions().getUsers().size() == 1) {
                User mentioned = message.getMentions().getUsers().get(0);
                embed.setTitle("Avatar of " + mentioned.getAsTag());
                embed.setDescription("[Image Link]("+ mentioned.getAvatarUrl() + "?size=1024)");
                embed.setImage(mentioned.getAvatarUrl() + "?size=1024");
                message.replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
            } else if (message.getContentRaw().length() >= args[0].length() + 1) {
                User user = message.getJDA().retrieveUserById(args[1]).complete();
                embed.setTitle("Avatar of " + user.getAsTag());
                embed.setDescription("[Image Link]("+ user.getAvatarUrl() + "?size=1024)");
                embed.setImage(user.getAvatarUrl() + "?size=1024");
                message.replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
            } else {
                embed.setTitle("Avatar of " + message.getAuthor().getAsTag());
                embed.setDescription("[Image Link]("+ message.getAuthor().getAvatarUrl() + "?size=1024)");
                embed.setImage(message.getAuthor().getAvatarUrl() + "?size=1024");
                message.replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
            }
        } catch (NullPointerException e) {
            message.reply("Invalid ID or can't find that user.").mentionRepliedUser(false).queue();
        }
    }
}
