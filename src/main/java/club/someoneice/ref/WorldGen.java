package club.someoneice.ref;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator {
    public WorldGen() {
        GameRegistry.registerWorldGenerator(this, 0);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.isSurfaceWorld()) {
            BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
            if (biome == BiomeGenBase.roofedForest) {
                if (random.nextDouble() > 0.70D) {
                    int y = -1;
                    for (int i = 40; i < 127; i++) {
                        if (world.getBlock(chunkX, i, chunkZ).getMaterial() == Material.air) {
                            y = i;
                            break;
                        }
                    }

                    if (y == -1) return;

                    for (int o = 0; o < 16; o++) {
                        new WorldGeneratorSweetBerries().generate(world, random, chunkX + random.nextInt(16), y, chunkZ + random.nextInt(16));
                    }
                }
            }
        }
    }

    public class WorldGeneratorSweetBerries extends WorldGenerator {
        public boolean generate(World world, Random random, int x, int y, int z) {
            if (Tags.DIRT_TAG.has(world.getBlock(x, y - 1, z)) && random.nextBoolean())
                world.setBlock(x, y, z , REFMain.BLOCK_BERRIES);

            return true;
        }
    }
}
