package main.terrainchecker;

import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.mc.MCVersion;
import main.Utils;
import main.b18.Blocks;
import main.b18.ChunkProviderGenerate;
import main.b18.WorldChunkManager;
import main.renderer.Chunk;
import main.utils.DiscreteLog;
import mjtb49.hashreversals.ChunkRandomReverser;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeSeedTerrainChecker {
    public static final int CHUNK_A_X = -7;
    public static final int CHUNK_A_Z = 22;
    public static final int CHUNK_B_X = -6;
    public static final int CHUNK_B_Z = 23;
//    public static final int CHUNK_B_X = -7;
//    public static final int CHUNK_B_Z = 23;

    public static void main(String[] args) throws Exception {
        final int THREADS = 12;

        final String treeSeedsInPath = "treeseeds_shotn_16_dedodged.txt";
        final String seedsOutPath = "worldseeds_shotn_terrainfiltered_7.txt";


        final double TERRAIN_CONFIDENCE_THRESHOLD = 1.00;

        final int TREE_CALL_RANGE = 90;

        final int MAX_POP_DIST = 5500;
        final int MIN_POP_DIST = 3760;

        LCG revTCR = LCG.JAVA.combine(LCG.JAVA.combine(-MIN_POP_DIST - TREE_CALL_RANGE));

        ArrayList<Thread> threads = new ArrayList<>();
//        String[] treeSeedsIn = Utils.readFileToString(treeSeedsInPath).split("\n");
        String[] treeSeedsIn = { "237950399559203"};
        new File(seedsOutPath).createNewFile();
        FileWriter seedsOut = new FileWriter(seedsOutPath);
        AtomicInteger results = new AtomicInteger();
        AtomicInteger seedsChecked = new AtomicInteger();

        System.out.printf("loaded %d tree seeds\n", treeSeedsIn.length);

        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < treeSeedsIn.length; i++) {
            final long initialSeed = revTCR.nextSeed(Long.parseLong(treeSeedsIn[i].trim()));
            final int threadId = i;
            Thread thread = new Thread(() -> {
                System.out.printf("started thread %4d with seed %15d\n", threadId, initialSeed);
                long seed = initialSeed;
                for (int dfz = 0; dfz < MAX_POP_DIST - MIN_POP_DIST + (TREE_CALL_RANGE * 2); dfz++) {
                    for (long worldSeed48 : ChunkRandomReverser.reversePopulationSeed(seed ^ LCG.JAVA.multiplier, CHUNK_A_X, CHUNK_A_Z, MCVersion.v1_8)) {
                        for (long worldSeed : Utils.getValidSeedsAndOriginal(worldSeed48)) {
                            WorldChunkManager worldChunkManager = new WorldChunkManager(worldSeed);
                            ChunkProviderGenerate chunkProvider = new ChunkProviderGenerate(worldSeed, worldChunkManager);
                            byte[] blocks = chunkProvider.provideChunk(CHUNK_B_X, CHUNK_B_Z);

                            int matches = 0;
                            for (int j = 0; j < targetTerrain.length; j++) {
                                int x = targetTerrain[j][0];
                                int y = targetTerrain[j][1];
                                int z = targetTerrain[j][2];
                                if (blocks[Chunk.getIndexOf(x & 15, y, z & 15)] != 0
                                        && blocks[Chunk.getIndexOf(x & 15, y + 1, z & 15)] == 0
                                ) {
                                    matches++;
                                }
                            }
                            if (matches >= targetTerrain.length * TERRAIN_CONFIDENCE_THRESHOLD) {
                                try {
                                    seedsOut.write(String.format("%d\n", worldSeed));
                                    seedsOut.flush();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                results.getAndIncrement();
                            }
                            seedsChecked.getAndIncrement();
                        }
                    }
                    seed = LCG.JAVA.nextSeed(seed);
                }
            });
            threads.add(thread);
        }

        long now = System.currentTimeMillis();
        long lastTime = now;
        int threadsAlive = 0;
        boolean[] threadsStarted = new boolean[threads.size()];
        boolean[] threadsDone = new boolean[threads.size()];
        boolean done = false;

        while (!done) {
            now = System.currentTimeMillis();
            if (now - lastTime > 250) {
                for (int i = 0; i < threads.size(); i++) {
                    Thread t = threads.get(i);
                    if (threadsAlive < THREADS && !threadsStarted[i]) {
                        t.start();
                        threadsAlive++;
                        threadsStarted[i] = true;
                    }
                    if (!t.isAlive() && threadsStarted[i] && !threadsDone[i]) {
                        threadsAlive--;
                        threadsDone[i] = true;
                        if (threadsAlive <= 0) {
                            done = true;
                        }
                    }
                }

                lastTime = now;
                System.out.printf("progress: %6d / %6d, %6.2f%% results %6d\n", seedsChecked.get(), treeSeedsIn.length * (5500 - 3760), ((double) seedsChecked.get() / (treeSeedsIn.length * (5500 - 3760) * 2)) * 100D, results.get());
            }
        }

        System.out.printf("done in %ds", (System.currentTimeMillis() - timeStart) / 1000);
    }

    public static final int[][] targetTerrain = {
            // shot n chunk -6, 23
            { -96,     63,    368 },
            { -96,     63,    369 },
            { -96,     62,    371 },
            { -96,     62,    372 },
            { -96,     62,    373 },
            { -96,     62,    374 },
            { -96,     62,    375 },
            { -95,     63,    368 },
            { -95,     63,    369 },
            { -95,     62,    370 },
            { -95,     62,    371 },
            { -95,     62,    372 },
            { -95,     62,    374 },
            { -95,     62,    375 },
            { -94,     63,    368 },
            { -94,     63,    369 },
            { -94,     62,    370 },
            { -94,     62,    371 },
            { -94,     62,    372 },
            { -94,     62,    373 },
            { -94,     62,    374 },
            { -94,     62,    375 },
            { -93,     63,    368 },
            { -93,     63,    369 },
            { -93,     62,    370 },
            { -93,     62,    371 },
            { -93,     62,    372 },
            { -93,     62,    373 },
            { -93,     62,    374 },
            { -93,     62,    375 },
            { -92,     63,    368 },
            { -92,     62,    369 },
            { -92,     62,    370 },
            { -92,     62,    371 },
            { -92,     62,    372 },
            { -92,     62,    373 },
            { -92,     62,    374 },
            { -92,     62,    375 },
            { -91,     63,    368 },
            { -91,     62,    369 },
            { -91,     62,    370 },
            { -91,     62,    371 },
            { -91,     62,    372 },
            { -91,     62,    373 },
            { -91,     62,    374 },
            { -91,     62,    375 },
            { -90,     63,    368 },
            { -90,     63,    369 },
            { -90,     62,    370 },
            { -90,     62,    371 },
            { -90,     62,    372 },
            { -90,     62,    373 },
            { -90,     62,    374 },
            { -90,     62,    375 },
            { -89,     63,    368 },
            { -89,     63,    369 },
            { -89,     62,    370 },
            { -89,     62,    371 },
            { -89,     62,    372 },
            { -89,     62,    373 },
            { -89,     62,    374 },
            { -89,     62,    375 },
            { -88,     63,    368 },
            { -88,     63,    369 },
            { -88,     63,    370 },
            { -88,     62,    371 },
            { -88,     62,    372 },
            { -88,     62,    373 },
            { -88,     62,    374 },
            { -88,     62,    375 },

            // shot n chunk c

//            { -112,     65,    374 },
//            { -112,     65,    375 },
//            { -112,     65,    376 },
//            { -112,     64,    377 },
//            { -112,     64,    378 },
//            { -112,     63,    382 },
//            { -111,     63,    376 },
//            { -111,     63,    379 },
//            { -111,     63,    380 },
//            { -111,     63,    381 },
//            { -110,     63,    377 },
//            { -110,     63,    378 },
//            { -110,     63,    379 },


    };
}
