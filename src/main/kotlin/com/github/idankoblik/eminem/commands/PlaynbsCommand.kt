package com.github.idankoblik.eminem.commands

import com.github.idankoblik.eminem.EminemPluginLoader
import com.github.idankoblik.jukebox.NBSFile
import com.github.idankoblik.jukebox.PaperSong
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File

class PlaynbsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender?, p1: Command?, p2: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            Bukkit.getLogger().info("You must be a player to run this command!")
            return false
        }

        val player: Player = sender

        if (args!!.size != 1) {
            player.sendMessage("Usage: /playnbs <filename>")
            return false
        }

        val filename = args[0]
        val plugin = EminemPluginLoader.getInstance()

        try {
            val nbsSong = NBSFile.readNBS(File(plugin.dataFolder, filename))
            val song: PaperSong = PaperSong(
                plugin,
                1.0f,
                nbsSong,
                Key.key("note.harp"),
                plugin.audience(player),
                null
            )

            song.toLoop()
            song.playSong()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return true
    }

}