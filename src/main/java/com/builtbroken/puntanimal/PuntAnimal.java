package com.builtbroken.puntanimal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("sbmpuntanimal")
@Mod.EventBusSubscriber
public class PuntAnimal
{
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onDamage(LivingAttackEvent event)
    {
        try
        {
            final Entity source = event.getSource().getDirectEntity();
            if (source instanceof Player)
            {
                final Player attacker = (Player) source;
                final LivingEntity victim = event.getEntityLiving();

                if (attacker.isCrouching() && victim instanceof Animal) //TODO set allow/block list for victim
                {
                    event.setCanceled(true);

                    victim.knockback(0.5F,
                            Mth.sin(attacker.yHeadRot * ((float)Math.PI / 180F)),
                            -Mth.cos(attacker.yHeadRot * ((float)Math.PI / 180F)));
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Unexpected error while pushing animal", e);
        }
    }
}
