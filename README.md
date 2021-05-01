# BungeePMs

**BungeePMs** allows your players to use a **private messaging system accross your bungeecord** network flawlessly.


## Installation:
To install this plugin please put the file **BungeePMsBungee-X.X-SNAPSHOT.jar** in the **plugins folder** of your **BungeeCord server**.

In order to **override Minecraft autocompletion** on **/msg**, **/tell** and **/w** commands and have a **bungeecord wise autocompletion of players**, it is **advised but not mandatory** to put **BungeePMsBukkit-X.X-SNAPSHOT.jar** in the **plugins folder** of **all your Spigot servers**.


## Commands:

- **/msg, /m, /w, /message \<player> \<message>** : Send a private message to another player.
- **/r, /reply \<message>**  : Send a private message to the last player that sent you a private message.
- **/socialspy (\<player>)**  : Enable or disable message spying for you or another player.


## Permissions (They are all BUNGEECORD permissions):

- **bungeepm.message** : Allow the use of /**msg, /m, /message, /w**
- **bungeepm.reply** : Allow the use of **/r, /reply**
- **bungeepm.socialspy** :  Allow the use of /**socialspy**
- **bungeepm.socialspy.others** : Allow the use of **/socialspy \<player>**
- **bungeepm.chatcolor** : Allow the use of **colors in privates messages**


## FAQ:

- **Are the socialspy players saved when the server restart ?** : **Yes they are.** BungeePMs uses a SQLite DB File to saves all the players with socialspy enabled.
- **Why the jar file is so big ?** : It's simply **because SQLite is shaded into the jar** at compilation because bungeecord does not provide this one by default. Actually the plugin in itself is not more than 30kb and is extremely lightweight.


## Configuration & Default language file:

### config.yml:
```yaml
bungeePM:
  #This value is used to name the language file. You need to use a VALID BCP 47 Language Code.
  #You can get a list of BCP 47 Language Codes at: https://appmakers.dev/bcp-47-language-codes-list/
  #Languaqges already translated by default: en-US, fr-FR
  languageTag: 'en-US'
  messageSenderFormat: '&6You &8-> &6{receiver} &8» &r{message}'
  messageReceiverFormat: '&6{sender} &8-> &6You &8» &r{message}'
  socialSpyFormat: '&8[&c&lSPY&8] &6{sender} &8-> &6{receiver} &8» &r{message}'
```


### messages_en.properties
```
#Note that properties files doesn't directly support accented characters.
#Please use this tool: https://www.branah.com/unicode-converter if you want to use them.
#You put your accented character in "Unicode Text" then you copy the "UTF-16" result, it's the one you will past in the language file.

commands.targetNotFound=&cThe player &6{0} &cis not online.
commands.noPermission=&cYou do not have access to this command.
commands.noConsole=&cOnly a player can use this command.
commands.invalidSyntax=&cIncorrect usage, use the command as follows: &6/{0}&c.
commands.cannotYourself=&cYou can''t talk to yourself.
commands.emptyMessageContent=&cThe content of your message is empty.
commands.message.usage=message <player> <message>
commands.reply.usage=reply <message>
commands.reply.nobodyToReply=&cYou have no one to answer to.
commands.socialspy.enabled=&eYou have &6enabled &eSocialSpy.
commands.socialspy.disabled=&eYou have &6disabled &eSocialSpy.
commands.socialspy.others.enabled=&eYou have &6enabled &eSocialSpy for &6{0}&e.
commands.socialspy.others.disabled=&eYou have &6disabled &eSocialSpy for &6{0}&e.[/code]
```


