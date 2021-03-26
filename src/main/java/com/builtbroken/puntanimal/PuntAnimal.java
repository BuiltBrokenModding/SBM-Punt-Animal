package com.builtbroken.puntanimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            final Entity source = event.getSource().getTrueSource();
            if (source instanceof PlayerEntity)
            {
                PlayerEntity attacker = (PlayerEntity) source;
                LivingEntity victim = event.getEntityLiving();

                if (attacker.isSneaking() && victim instanceof AnimalEntity) //TODO set allow/block list for victim
                {
                    event.setCanceled(true);

                    int xr = (int) -(victim.getPosX() - attacker.getPosX());
                    int zr = (int) -(victim.getPosZ() - attacker.getPosZ());

                    //When xr & zr == 0, both are on the same block, we don't know where to push
                    if (xr != 0 || zr != 0)
                    {
                        victim.knockBack(attacker, 0.5F, xr, zr);
                    }
                    //TODO push randomly into nearest free spot for else
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Unexpected error while pushing animal", e);
        }
    }
}
