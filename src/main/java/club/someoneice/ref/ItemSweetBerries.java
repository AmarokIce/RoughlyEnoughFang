package club.someoneice.ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSweetBerries extends ItemBlock {
    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public ItemSweetBerries(Block block) {
        super(block);
        // Never registry a ItemBlock.

        this.setTextureName(REFMain.MODID + ":sweet_berries");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icon = register.registerIcon(REFMain.MODID + ":sweet_berries");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        player.getFoodStats().addStats(2, 0.1F);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        --item.stackSize;
        return item;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (player.canEat(false))
            player.setItemInUse(item, this.getMaxItemUseDuration(item));

        return item;
    }
}
