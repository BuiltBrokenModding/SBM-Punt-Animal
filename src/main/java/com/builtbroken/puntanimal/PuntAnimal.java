package com.builtbroken.puntanimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
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
            if (source instanceof PlayerEntity)
            {
                PlayerEntity attacker = (PlayerEntity) source;
                LivingEntity victim = event.getEntityLiving();

                if (attacker.isCrouching() && victim instanceof AnimalEntity) //TODO set allow/block list for victim
                {
                    event.setCanceled(true);

                    victim.knockback(0.5F,
                            MathHelper.sin(attacker.yRot * ((float)Math.PI / 180F)),
                            -MathHelper.cos(attacker.yRot * ((float)Math.PI / 180F)));
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Unexpected error while pushing animal", e);
        }
    }
}
