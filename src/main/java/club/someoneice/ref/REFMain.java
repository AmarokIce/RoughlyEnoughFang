package club.someoneice.ref;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = REFMain.MODID)
public class REFMain {
    public static final String MODID = "roughly_enough_fang";
    public static final Block BLOCK_BERRIES = new BlockSweetBerries();

    public static final Item ITEM_DOG_MEAL = new Item().setUnlocalizedName("dog_meal").setTextureName(MODID + ":dog_meal").setCreativeTab(CreativeTabs.tabMisc);
    public static final Item ITEM_MUTTON = new ItemFood(4, 0.12F, true).setUnlocalizedName("raw_mutton").setTextureName(MODID + ":raw_mutton").setCreativeTab(CreativeTabs.tabFood);
    public static final Item ITEM_COOK_MUTTON = new ItemFood(6, 0.96F, true).setUnlocalizedName("cooked_mutton").setTextureName(MODID + ":cooked_mutton").setCreativeTab(CreativeTabs.tabFood);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new Tags();

        GameRegistry.registerItem(ITEM_DOG_MEAL, "dog_meal", REFMain.MODID);
        GameRegistry.registerItem(ITEM_MUTTON, "raw_mutton", REFMain.MODID);
        GameRegistry.registerItem(ITEM_COOK_MUTTON, "cooked_mutton", REFMain.MODID);
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_DOG_MEAL, 6), BLOCK_BERRIES, BLOCK_BERRIES, Items.porkchop, Items.beef, Items.wheat, Items.wheat);
        GameRegistry.addSmelting(ITEM_MUTTON, new ItemStack(ITEM_COOK_MUTTON), 0.2F);

        Tags.FOX_FOOD.put(Item.getItemFromBlock(BLOCK_BERRIES));

        Tags.FOX_FOOD.put(ITEM_DOG_MEAL);
        Tags.DOG_FOOD.put(ITEM_DOG_MEAL);

        new WorldGen();

        MinecraftForge.EVENT_BUS.register(new Event());
        FMLCommonHandler.instance().bus().register(new Event());
    }
}
