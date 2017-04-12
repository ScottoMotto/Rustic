package rustic.common.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.IWorldGenerator;
import rustic.common.Config;
import rustic.common.blocks.ModBlocks;

public class WorldGeneratorRustic implements IWorldGenerator {
	
	private WorldGenMinable slate = new WorldGenMinable(ModBlocks.SLATE.getDefaultState(), Config.SLATE_VEIN_SIZE);
	private WorldGenBeehive beehives = new WorldGenBeehive();
	private WorldGenAllTrees trees = new WorldGenAllTrees();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos chunkCenter = new BlockPos(chunkX * 16 + 8, world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);

		if (world.provider.getDimensionType() == DimensionType.OVERWORLD) {

			trees.generate(world, random, chunkCenter);

			if (!world.getBiome(chunkCenter).isSnowyBiome() && random.nextFloat() < Config.BEEHIVE_GEN_CHANCE) {
				beehives.generate(world, random, chunkCenter);
			}

			for (int i = 0; i < Config.SLATE_VEINS_PER_CHUNK; i++) {
				int x = chunkX * 16 + random.nextInt(16);
				int y = random.nextInt(80) + 4;
				int z = chunkZ * 16 + random.nextInt(16);
				slate.generate(world, random, new BlockPos(x, y, z));
			}
		}
	}

}
