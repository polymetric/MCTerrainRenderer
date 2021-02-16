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
    public static final int CHUNK_A_X = 7;
    public static final int CHUNK_A_Z = 13;
    public static final int CHUNK_B_X = 8;
    public static final int CHUNK_B_Z = 13;
//    public static final int CHUNK_A_X = 0;
//    public static final int CHUNK_A_Z = 0;
//    public static final int CHUNK_B_X = 1;
//    public static final int CHUNK_B_Z = -1;

    public static void main(String[] args) throws Exception {
        final int THREADS = 8;

        final String treeSeedsInPath = "treeseeds_shotp_2.txt";
        final String seedsOutPath = "worldseeds_shotp_terrainfiltered.txt";

        final LCG rev1 = LCG.JAVA.combine(-1);

        ArrayList<Thread> threads = new ArrayList<>();
        String[] treeSeedsIn = Utils.readFileToString(treeSeedsInPath).split("\n");
        new File(seedsOutPath).createNewFile();
        FileWriter seedsOut = new FileWriter(seedsOutPath);
        AtomicInteger results = new AtomicInteger();
        AtomicInteger seedsChecked = new AtomicInteger();

        System.out.printf("loaded %d tree seeds\n", treeSeedsIn.length);
        System.out.println(5500 - 3760 + 220);

        for (int i = 0; i < treeSeedsIn.length; i++) {
            final long initialSeed = LCG.JAVA.combine(-3760 - 220).nextSeed(Long.parseLong(treeSeedsIn[i].trim()));
            final int threadId = i;
            Thread thread = new Thread(() -> {
                System.out.printf("started thread %4d with seed %15d\n", threadId, initialSeed);
                long seed = initialSeed;
                for (int dfz = 0; dfz < 5500 - 3760 + 220; dfz++) {
                    seed = LCG.JAVA.nextSeed(seed);
                    for (long worldSeed : ChunkRandomReverser.reversePopulationSeed(seed ^ LCG.JAVA.multiplier, CHUNK_A_X, CHUNK_A_Z, MCVersion.v1_8)) {
                        WorldChunkManager worldChunkManager = new WorldChunkManager(worldSeed);
                        ChunkProviderGenerate chunkProvider = new ChunkProviderGenerate(worldSeed, worldChunkManager);
                        byte[] blocks = chunkProvider.provideChunk(CHUNK_B_X, CHUNK_B_Z);

                        int matches = 0;
                        for (int j = 0; j < targetTerrain.length; j++) {
                            int x = targetTerrain[j][0];
                            int y = targetTerrain[j][1];
                            int z = targetTerrain[j][2];
                            if (blocks[Chunk.getIndexOf(x, y, z)] != 0
                                    && blocks[Chunk.getIndexOf(x, y+1, z)] == 0
                            ) {
                                matches++;
                            }
                        }
                        if (matches >= targetTerrain.length * 0.9) {
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
                System.out.printf("progress: %6d / %6d, %6.2f%% results %6d\n", seedsChecked.get(), treeSeedsIn.length * (5500 - 3760), ((double) seedsChecked.get() / (treeSeedsIn.length * (5500 - 3760))) * 100D, results.get());
            }
        }

        System.out.println("done");
    }

    public static final int[][] targetTerrain = {
            {    7,   67,    9, },
            {    7,   67,   10, },
            {    7,   67,   11, },
            {    7,   67,   12, },
            {    7,   67,   13, },
            {    7,   68,   14, },
            {    7,   68,   15, },
            {    8,   67,    9, },
            {    8,   67,   10, },
            {    8,   67,   11, },
            {    8,   67,   12, },
            {    8,   68,   13, },
            {    8,   68,   14, },
            {    8,   68,   15, },
            {    9,   67,    9, },
            {    9,   67,   10, },
            {    9,   67,   11, },
            {    9,   68,   12, },
            {    9,   68,   13, },
            {    9,   68,   14, },
            {    9,   68,   15, },
            {   10,   68,    8, },
            {   10,   68,    9, },
            {   10,   68,   10, },
            {   10,   68,   11, },
            {   10,   68,   12, },
            {   10,   68,   13, },
            {   10,   68,   14, },
            {   10,   68,   15, },
            {   11,   68,    8, },
            {   11,   68,    9, },
            {   11,   68,   10, },
            {   11,   68,   11, },
            {   11,   68,   12, },
            {   11,   68,   13, },
            {   11,   68,   14, },
            {   11,   69,   15, },
            {   12,   69,    5, },
            {   12,   69,    6, },
            {   12,   69,    7, },
            {   12,   69,    8, },
            {   12,   69,    9, },
            {   12,   68,   10, },
            {   12,   68,   11, },
            {   12,   68,   12, },
            {   12,   68,   13, },
            {   12,   69,   14, },
            {   12,   69,   15, },
            {   13,   70,    4, },
            {   13,   69,    5, },
            {   13,   69,    6, },
            {   13,   69,    7, },
            {   13,   69,    8, },
            {   13,   69,    9, },
            {   13,   69,   10, },
            {   13,   69,   11, },
            {   13,   69,   12, },
            {   13,   69,   13, },
            {   13,   69,   14, },
            {   14,   70,    4, },
            {   14,   70,    5, },
            {   14,   70,    6, },
            {   14,   69,    7, },
            {   14,   69,    8, },
            {   14,   69,    9, },
            {   14,   69,   10, },
            {   14,   69,   11, },
            {   14,   69,   12, },
            {   15,   71,    4, },
            {   15,   70,    5, },
            {   15,   70,    6, },
            {   15,   70,    7, },
            {   15,   70,    8, },
            {   15,   70,    9, },
            {   15,   70,   10, },
//            {6, 63, 13,},
//            {6, 63, 14,},
//            {6, 63, 15,},
//            {7, 63, 11,},
//            {7, 63, 12,},
//            {7, 63, 13,},
//            {7, 63, 14,},
//            {7, 63, 15,},
//            {8, 63, 10,},
//            {8, 63, 11,},
//            {8, 63, 12,},
//            {8, 63, 13,},
//            {8, 64, 14,},
//            {8, 64, 15,},
//            {9, 63, 9,},
//            {9, 63, 10,},
//            {9, 63, 11,},
//            {9, 63, 12,},
//            {9, 64, 13,},
//            {9, 64, 14,},
//            {9, 65, 15,},
//            {10, 63, 7,},
//            {10, 63, 8,},
//            {10, 63, 9,},
//            {10, 63, 10,},
//            {10, 64, 11,},
//            {10, 64, 12,},
//            {10, 64, 13,},
//            {10, 65, 14,},
//            {10, 65, 15,},
//            {11, 64, 7,},
//            {11, 64, 8,},
//            {11, 64, 9,},
//            {11, 64, 10,},
//            {11, 64, 11,},
//            {11, 65, 12,},
//            {11, 65, 13,},
//            {11, 65, 14,},
//            {11, 65, 15,},
//            {12, 64, 7,},
//            {12, 65, 8,},
//            {12, 65, 9,},
//            {12, 65, 10,},
//            {12, 65, 11,},
//            {12, 65, 12,},
//            {12, 65, 13,},
//            {12, 66, 14,},
//            {12, 66, 15,},
//            {13, 65, 7,},
//            {13, 65, 8,},
//            {13, 65, 9,},
//            {13, 65, 10,},
//            {13, 65, 11,},
//            {13, 66, 12,},
//            {13, 66, 13,},
//            {13, 66, 14,},
//            {13, 66, 15,},
//            {14, 65, 7,},
//            {14, 65, 8,},
//            {14, 65, 9,},
//            {14, 65, 10,},
//            {14, 66, 11,},
//            {14, 66, 12,},
//            {14, 66, 13,},
//            {14, 66, 14,},
//            {14, 66, 15,},
//            {15, 65, 7,},
//            {15, 65, 8,},
//            {15, 66, 9,},
//            {15, 66, 10,},
//            {15, 66, 11,},
//            {15, 66, 12,},
//            {15, 66, 13,},
//            {15, 66, 14,},
//            {15, 66, 15,},
    };
}
