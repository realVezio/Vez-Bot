package net.voiasis.commands.prefix;

import net.dv8tion.jda.api.entities.Message;

public class prefixTest {
    public static void test(Message message, String[] args) {
        if (message.getAuthor().getId().equals("472899069136601099")) {
            message.reply(String.valueOf(message.getJDA().getGuilds().size())).queue();
        }
    }
}
