package fr.keke142.bungeepm.bungee.configs;

import net.md_5.bungee.config.Configuration;

public class BungeePMConfig {
    private String languageTag;
    private String messageSenderFormat;
    private String messageReceiverFormat;
    private String socialSpyFormat;

    public void load(Configuration config) {
        languageTag = config.getString("bungeePM.languageTag", "en-US");
        messageSenderFormat = config.getString("bungeePM.messageSenderFormat", "&6You &8→ &6{receiver} &8» &r{message}");
        messageReceiverFormat = config.getString("bungeePM.messageReceiverFormat", "&6{sender} &8→ &6You &8» &r{message}");
        socialSpyFormat = config.getString("bungeePM.socialSpyFormat", "&8[&c&lSPY&8] &6{sender} &8-> &6{receiver} &8» &r{message}");
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public String getMessageSenderFormat() {
        return messageSenderFormat;
    }

    public String getMessageReceiverFormat() {
        return messageReceiverFormat;
    }

    public String getSocialSpyFormat() {
        return socialSpyFormat;
    }
}
