package main.renderer;

import main.BiomeGeneration;
import main.BiomesBase;
import main.GenerateChunk;

import java.util.ArrayList;

public class RendererGenerateChunks {
    public static void genChunksForRenderer(Level level, long seed) {
        final int RADIUS = 8;
        // GENERATE BLOCKS
        for (int chunkX = -RADIUS; chunkX <= RADIUS; chunkX++) {
            for (int chunkZ = -RADIUS; chunkZ <= RADIUS; chunkZ++) {
                BiomesBase[] biomesForGeneration = new BiomesBase[256];
                BiomeGeneration biomeGeneration = new BiomeGeneration(seed);
                biomeGeneration.loadBiomes(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);

                GenerateChunk generateChunk = new GenerateChunk(seed);
                byte[] blocks = generateChunk.provideChunk(chunkX, chunkZ, false, biomeGeneration, biomesForGeneration);

                Chunk chunkObj = new Chunk(level, chunkX, chunkZ);
                level.addChunk(chunkObj);
                chunkObj.blocks = blocks;
            }
        }
        for (int chunkX = -RADIUS; chunkX <= RADIUS; chunkX++) {
            for (int chunkZ = -RADIUS; chunkZ <= RADIUS; chunkZ++) {
                level.chunks[Level.chunkIndex(chunkX, chunkZ)].rebuild();
            }
        }
        System.out.println("done generating chunks");
    }
}
