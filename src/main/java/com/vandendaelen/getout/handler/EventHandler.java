package com.vandendaelen.getout.handler;

import com.vandendaelen.getout.util.Reference;
import net.minecraft.enchantment.EnchantmentArrowKnockback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void onDamage(LivingAttackEvent event){
        Entity source = event.getSource().getTrueSource();

        if (source instanceof EntityPlayer){

            EntityPlayer attacker = (EntityPlayer) source;
            EntityLivingBase victim = event.getEntityLiving();

            if (attacker.isSneaking() && victim instanceof EntityAnimal){
                event.setCanceled(true);

                int xr, zr;
                xr = (int) -(victim.posX - attacker.posX);
                zr = (int) -(victim.posZ - attacker.posZ);

                //When xr & zr == 0, both are on the same block, we don't know where to push
                if (xr != 0 || zr != 0)
                    victim.knockBack(attacker,0.5F,xr,zr);
            }
        }
    }


}
