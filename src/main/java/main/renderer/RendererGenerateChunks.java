package main.renderer;

import main.b18.WorldChunkManager;
import main.b18.biomes.BiomeGenBase;
import main.b18.ChunkProviderGenerate;

public class RendererGenerateChunks {
    public static void genChunksForRenderer(Level level, long seed, int radius, int centerX, int centerZ) {
        // GENERATE BLOCKS
        int chunkCount = 0;
        long timeStart = System.nanoTime();
        for (int chunkX = centerX-radius; chunkX <= centerX+radius; chunkX++) {
            for (int chunkZ = centerZ-radius; chunkZ <= centerZ+radius; chunkZ++) {
                BiomeGenBase[] biomesForGeneration = new BiomeGenBase[256];
//                biomeGeneration.loadBiomes(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
                WorldChunkManager worldChunkManager = new WorldChunkManager(seed);

                ChunkProviderGenerate chunkProvider = new ChunkProviderGenerate(seed, worldChunkManager);
                byte[] blocks = chunkProvider.provideChunk(chunkX, chunkZ);

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
