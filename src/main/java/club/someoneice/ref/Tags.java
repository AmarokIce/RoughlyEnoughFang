package club.someoneice.ref;

import club.someoneice.togocup.tags.Tag;
import club.someoneice.togocup.tags.TagsManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class Tags {
    public static final TagsManager manager = TagsManager.manager();

    public static final Tag<Item> FOX_FOOD = manager.registerTag("item_fox_food");
    public static final Tag<Item> DOG_FOOD = manager.registerTag("item_dog_food");

    public static final Tag<Block> DIRT_TAG = manager.registerTag("blockDirt", Blocks.farmland, Blocks.grass, Blocks.dirt);
}
