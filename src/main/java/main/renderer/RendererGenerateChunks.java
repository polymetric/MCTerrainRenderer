package main.renderer;

import main.BiomeGeneration;
import main.BiomesBase;
import main.GenerateChunk;
import org.joml.Vector3f;

import java.util.ArrayList;

import static main.renderer.Main.colorFromBlockType;

public class RendererGenerateChunks {
    public static void genChunksForRenderer(ArrayList<Block> blocks, long seed) {
        final int RADIUS = 6;
        // GENERATE BLOCKS
        for (int chunkX = -RADIUS; chunkX <= RADIUS; chunkX++) {
            for (int chunkZ = -RADIUS; chunkZ <= RADIUS; chunkZ++) {

                BiomesBase[] biomesForGeneration = new BiomesBase[256];
                BiomeGeneration biomeGeneration = new BiomeGeneration(seed);
                biomeGeneration.loadBiomes(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);

                GenerateChunk generateChunk = new GenerateChunk(seed);
//                byte[] chunk = generateChunk.provideChunk(chunkX, chunkZ, false, biomeGeneration, biomesForGeneration);
                byte[] chunk = generateChunk.provideChunk(chunkX, chunkZ, true, biomeGeneration, biomesForGeneration);

                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        int index = x << 11 | z << 7;
                        Vector3f color = colorFromBlockType((byte) 1);
                        Vector3f pos = new Vector3f(x, chunk[index >> 7], z);
                        Vector3f chunkOffset = new Vector3f(chunkX * 16, 0, chunkZ * 16);
                        pos.add(chunkOffset);
                        blocks.add(new Block(pos, color));

//                        for (int y = 0; y < 128; y++) {
//                            int index = x << 11 | z << 7 | y;
//                            if (chunk[index] != 0) {
//                                Vector3f color = colorFromBlockType(chunk[index]);
//                                Vector3f pos = new Vector3f(x, y, z);
//                                blocks.add(new Block(pos, color));
//                            }
//                        }
                    }
                }
            }
        }
    }
}
