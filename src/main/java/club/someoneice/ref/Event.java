package club.someoneice.ref;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import makamys.dmod.entity.EntityFox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import java.util.Random;

public class Event {
    @SubscribeEvent
    public void entityDropEvent(LivingDropsEvent event) {
        Entity entity = event.entityLiving;
        Random random = new Random();

        if (entity instanceof EntitySheep) {
            if (entity.isBurning()) {
                entity.worldObj.spawnEntityInWorld(
                        new EntityItem(entity.worldObj, entity.posX, entity.posY + 0.5F, entity.posZ,
                                new ItemStack(REFMain.ITEM_COOK_MUTTON, random.nextInt(3) + 1)));
            } else {
                entity.worldObj.spawnEntityInWorld(
                        new EntityItem(entity.worldObj, entity.posX, entity.posY + 0.5F, entity.posZ,
                                new ItemStack(REFMain.ITEM_MUTTON, random.nextInt(3) + 1)));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUse(EntityInteractEvent event) {
        EntityPlayer player = event.entityPlayer;
        EntityLivingBase entity = event.entityLiving;
        ItemStack item = player.getHeldItem();

        if (Tags.FOX_FOOD.has(item.getItem()) && entity instanceof EntityFox) {
            entity.heal(4);
            item.splitStack(1);

            event.setCanceled(true);
        } else if (Tags.DOG_FOOD.has(item.getItem()) && entity instanceof EntityWolf) {
            entity.heal(4);
            item.splitStack(1);

            event.setCanceled(true);
        }

    }
}
