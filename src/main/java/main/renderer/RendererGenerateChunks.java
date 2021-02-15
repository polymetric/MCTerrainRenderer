package main.renderer;

import main.BiomeGeneration;
import main.BiomesBase;
import main.GenerateChunk;

import java.util.ArrayList;

public class RendererGenerateChunks {
    public static void genChunksForRenderer(ArrayList<Chunk> chunks, long seed) {
        final int RADIUS = 8;
        // GENERATE BLOCKS
        for (int chunkX = -RADIUS; chunkX <= RADIUS; chunkX++) {
            for (int chunkZ = -RADIUS; chunkZ <= RADIUS; chunkZ++) {

                BiomesBase[] biomesForGeneration = new BiomesBase[256];
                BiomeGeneration biomeGeneration = new BiomeGeneration(seed);
                biomeGeneration.loadBiomes(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);

                GenerateChunk generateChunk = new GenerateChunk(seed);
                byte[] blocks = generateChunk.provideChunk(chunkX, chunkZ, false, biomeGeneration, biomesForGeneration);

                System.out.printf("generating chunk %4d %4d\n", chunkX, chunkZ);
                Chunk chunkObj = new Chunk(chunkX, chunkZ);
                chunkObj.blocks = blocks;
                chunkObj.rebuild();
                chunks.add(chunkObj);
            }
        }
        System.out.println("done generating chunks");
    }
}
