package net.voiasis.auto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;

import net.dv8tion.jda.api.entities.Message;
import net.voiasis.tools.BotLog;
import net.voiasis.tools.WebhookController;

public class WordReplacer {
    public static void replacer(Message message) {
        if (!message.getAuthor().isBot()) {
            String content = message.getContentRaw();
            String spaced = " " + content.toLowerCase() + " ";
            if (!message.getChannel().getId().equals("995434413238259762")) {
                if (!spaced.contains(" ur ") && !spaced.contains(" ur: ") ) {
                    
                } else {
                    try {
                        content = (" " +  check(content) + " ")
                        .replace(" y ", " why ")
                        .replace(" Y ", " Why ")
                        .replace(" u ", " you ")
                        .replace(" U ", " You ")
                        .replace(" r ", " are ")
                        .replace(" R ", " Are ")
                        .replace(" agent ", "Voiasis")
                        .replace(" Agent ", "Voiasis");
                        String lower = content.toLowerCase();
                        if (!lower.contains(" ur ") && message.getContentRaw() != content) {
                            WebhookController.send(message, message.getMember().getEffectiveName(), message.getAuthor().getAvatarUrl(), content);
                            message.delete().queue();
                        } else if (lower.contains(" ur ")) {
                            message.delete().queue();
                        }  
                    } catch (IOException | IndexOutOfBoundsException e) {
                        BotLog.log(BotLog.getStackTraceString(e, message.getJDA()), "WordReplacer", 4);
                    }
                }
            }
        }
    }
    private static String check(String content) throws IOException {
        String replace = "";
        JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
        for (Rule rule : langTool.getAllActiveRules()) {
            if (rule instanceof SpellingCheckRule) {
                List<String> wordsToIgnore = Arrays.asList("lmao", "wdym", "idk", "wtf", "btw", "lol", "nvm", "bhai", "alr", "tf");
                ((SpellingCheckRule)rule).addIgnoreTokens(wordsToIgnore);
            }
        }
        for (Rule rule : langTool.getAllActiveRules()) {
            if (rule instanceof SpellingCheckRule) {
              ((SpellingCheckRule)rule).acceptPhrases(Arrays.asList("lmao", "wdym", "idk", "wtf", "btw", "lol", "nvm", "bhai", "alr", "tf"));
            }
          }
      
        List<RuleMatch> matches = langTool.check(content);
        for (RuleMatch match : matches) {
            replace = content.substring(match.getFromPos(), match.getToPos());
            if (replace.equals("ur")) {
                content = content.replaceFirst(replace, match.getSuggestedReplacements().get(0));
            } 
        }
        return content;
    }
}