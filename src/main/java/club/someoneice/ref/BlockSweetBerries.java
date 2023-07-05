package club.someoneice.ref;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.dmod.entity.EntityFox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraftforge.common.EnumPlantType.Plains;

public class BlockSweetBerries extends BlockCrops {
    public static final DamageSource BERRIES_HURT = new DamageSource("berries_crop").setDamageBypassesArmor();
    private IIcon[] icons;

    public BlockSweetBerries() {
        this.setBlockName("sweet_berries");
        this.setBlockTextureName(REFMain.MODID + ":sweet_berries");
        this.setCreativeTab(CreativeTabs.tabFood);
        GameRegistry.registerBlock(this, ItemSweetBerries.class, "sweet_berries");
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return Tags.DIRT_TAG.has(block);
    }

    @Override
    protected Item func_149866_i() {
        return Item.getItemFromBlock(this);
    }

    @Override
    protected Item func_149865_P() {
        return Item.getItemFromBlock(this);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return Lists.newArrayList();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > 3)
            meta = 3;

        return this.icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[4];

        for (int i = 0; i < this.icons.length; ++i) {
            this.icons[i] = register.registerIcon(this.getTextureName() + "_stage" + i);
        }
    }

    @Override
    public void func_149863_m(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);

        if (l > 3)
            l = 3;

        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);

        if (world.getBlockLightValue(x, y + 1, z) >= 9) {
            int l = world.getBlockMetadata(x, y, z);

            if (l < 3) {
                if (random.nextInt(20) == 0) {
                    ++l;
                    world.setBlockMetadataWithNotify(x, y, z, l, 2);
                }
            } else if (l > 3) {
                world.setBlockMetadataWithNotify(x, y, z, 3, 2);
            }
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return Plains;
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 2) {
            player.inventory.addItemStackToInventory(new ItemStack(this, world.rand.nextInt(2) + 1));
            world.setBlockMetadataWithNotify(x, y, z, 1, 3);

            return true;
        } else if (meta == 3) {
            player.inventory.addItemStackToInventory(new ItemStack(this, world.rand.nextInt(4) + 2));
            world.setBlockMetadataWithNotify(x, y, z, 1, 3);

            return true;
        }

        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (world.getBlockMetadata(x, y, z) == 0) return;

        if (!(entity instanceof EntityFox)) {
            entity.motionX *= 0.4D;
            entity.motionZ *= 0.4D;

            if (entity instanceof EntityLiving) {
                EntityLiving living = (EntityLiving) entity;
                living.attackEntityFrom(BERRIES_HURT, 1);
            } else if (entity instanceof EntityPlayer) {
                EntityPlayer living = (EntityPlayer) entity;
                living.attackEntityFrom(BERRIES_HURT, 1);
            }
        }
    }
}
