package com.github.idankoblik.eminem

import com.github.idankoblik.eminem.commands.PlaynbsCommand
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class EminemPluginLoader : JavaPlugin() {

    companion object {
        private var instance: EminemPluginLoader? = null

        fun getInstance(): EminemPluginLoader {
            return instance ?: throw IllegalStateException("Plugin not enabled")
        }
    }

    private var bukkitAudiences: BukkitAudiences? = null

    override fun onEnable() {
        instance = this
        this.bukkitAudiences = BukkitAudiences.create(this)

        getCommand("playnbs").setExecutor(PlaynbsCommand())
        logger.info("Successfully loaded `Eminem` plugin!")
    }

    override fun onDisable() {
        this.bukkitAudiences!!.close();
    }

    @Throws(Exception::class)
    public fun audience(`object`: Any): Audience {
        return when (`object`) {
            is Player -> bukkitAudiences!!.player(`object`)
            is CommandSender -> bukkitAudiences!!.sender(`object`)
            is Audience -> `object`
            else -> throw Exception("Unsupported audience object")
        }
    }
}