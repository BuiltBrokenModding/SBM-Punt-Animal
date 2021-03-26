package com.builtbroken.puntanimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("sbmpuntanimal")
@Mod.EventBusSubscriber
public final class PuntAnimal
{
    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onDamage(LivingAttackEvent event)
    {
        try
        {
            final Entity source = event.getSource().getTrueSource();
            if (source instanceof EntityPlayer)
            {
                EntityPlayer attacker = (EntityPlayer) source;
                EntityLivingBase victim = event.getEntityLiving();

                if (attacker.isSneaking() && victim instanceof EntityAnimal) //TODO set allow/block list for victim
                {
                    event.setCanceled(true);

                    int xr = (int) -(victim.posX - attacker.posX);
                    int zr = (int) -(victim.posZ - attacker.posZ);

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
