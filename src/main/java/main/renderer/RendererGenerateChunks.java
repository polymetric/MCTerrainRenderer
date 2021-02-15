package main.renderer;

import main.BiomeGeneration;
import main.BiomesBase;
import main.GenerateChunk;

import java.util.ArrayList;

public class RendererGenerateChunks {
    public static void genChunksForRenderer(Level level, long seed, int radius, int centerX, int centerZ) {
        // GENERATE BLOCKS
        int chunkCount = 0;
        long timeStart = System.nanoTime();
        for (int chunkX = centerX-radius; chunkX <= centerX+radius; chunkX++) {
            for (int chunkZ = centerZ-radius; chunkZ <= centerZ+radius; chunkZ++) {
                BiomesBase[] biomesForGeneration = new BiomesBase[256];
                BiomeGeneration biomeGeneration = new BiomeGeneration(seed);
                biomeGeneration.loadBiomes(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);

                GenerateChunk generateChunk = new GenerateChunk(seed);
                byte[] blocks = generateChunk.provideChunk(chunkX, chunkZ, false, biomeGeneration, biomesForGeneration);

                Chunk chunkObj = new Chunk(level, chunkX, chunkZ);
                level.addChunk(chunkObj);
                chunkObj.blocks = blocks;
                chunkCount++;
            }
        }
        System.out.printf("generated %4d chunks in %8.3fms\n", chunkCount, (System.nanoTime() - timeStart) / 1e6);
        chunkCount = 0;
        timeStart = System.nanoTime();
        for (int chunkX = centerX-radius; chunkX <= centerX+radius; chunkX++) {
            for (int chunkZ = centerZ-radius; chunkZ <= centerZ+radius; chunkZ++) {
                level.chunks[Level.chunkIndex(chunkX, chunkZ)].rebuild();
                chunkCount++;
            }
        }
        System.out.printf("rendered %5d chunks in %8.3fms\n", chunkCount, (System.nanoTime() - timeStart) / 1e6);
    }
}
