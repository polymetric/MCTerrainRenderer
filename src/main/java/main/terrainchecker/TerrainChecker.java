package main.terrainchecker;

import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.mc.MCVersion;
import main.Utils;
import main.b18.Blocks;
import main.b18.ChunkProviderGenerate;
import main.b18.WorldChunkManager;
import main.renderer.Chunk;
import mjtb49.hashreversals.ChunkRandomReverser;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TerrainChecker {
    public static void main(String[] args) throws Exception {
        final int THREADS = 8;

        final int chunkX = 7;
        final int chunkZ = 13;

        final LCG rev1 = LCG.JAVA.combine(-1);

        ArrayList<Thread> threads = new ArrayList<>();
        String[] treeSeedsIn = Utils.readFileToString("treeseeds_shotp_3.txt").split("\n");
        AtomicInteger results = new AtomicInteger();
        AtomicInteger seedsChecked = new AtomicInteger();

        System.out.printf("loaded %d tree seeds\n", treeSeedsIn.length);

        for (int i = 0; i < treeSeedsIn.length; i++) {
            final long initialSeed = LCG.JAVA.combine(-3760).nextSeed(Long.parseLong(treeSeedsIn[i].trim()));
            final int threadId = i;
            Thread thread = new Thread(() -> {
                System.out.printf("started thread %4d with seed %15d\n", threadId, initialSeed);
                for (int dfz = 0; dfz < 5500 - 3760; dfz++) {
                    long seed = rev1.nextSeed(initialSeed);
                    for (long worldSeed : ChunkRandomReverser.reversePopulationSeed(seed, chunkX, chunkZ, MCVersion.v1_8)) {
                        WorldChunkManager worldChunkManager = new WorldChunkManager(worldSeed);

                        ChunkProviderGenerate chunkProvider = new ChunkProviderGenerate(worldSeed, worldChunkManager);
                        byte[] blocks = chunkProvider.provideChunk(chunkX, chunkZ);

                        int matches = 0;
                        for (int j = 0; j < targetTerrain.length; j++) {
                            int x = targetTerrain[j][0];
                            int y = targetTerrain[j][1];
                            int z = targetTerrain[j][2];
                            if (blocks[Chunk.getIndexOf(x, y, z)] == Blocks.GRASS.blockID()) {
                                matches++;
                            }
                        }
                        if (matches > targetTerrain.length * 0.9) {
                            System.out.println(worldSeed);
                            results.getAndIncrement();
                        }

                        seedsChecked.getAndIncrement();
                    }
                }
            });
            System.out.printf("created thread %4d with seed %15d\n", threadId, initialSeed);
            threads.add(thread);
        }

        ArrayList<Thread> threadsToRemove = new ArrayList<>();
        long now = System.currentTimeMillis();
        long lastTime = now;
        int threadCount = 0;
        boolean[] threadsStarted = new boolean[threads.size()];

        while (threads.size() > 0) {
            now = System.currentTimeMillis();
            if (now - lastTime > 250) {
                threadsToRemove.clear();
                for (int i = 0; i < threads.size(); i++) {
                    Thread t = threads.get(i);
                    if (threadCount < THREADS && !threadsStarted[i]) {
                        t.start();
                        threadCount++;
                        threadsStarted[i] = true;
                    }
                    if (!t.isAlive() && threadsStarted[i]) {
                        threadsToRemove.add(t);
                    }
                }
                for (Thread t : threadsToRemove) {
                    threads.remove(t);
                    threadCount--;
                }

                lastTime = now;
                System.out.printf("threads: %4d\n", threads.size());
                System.out.printf("progress: %6d / %6d, %6.2f%% results %6d\n", seedsChecked.get(), treeSeedsIn.length * (5500 - 3760), ((double) seedsChecked.get() / (treeSeedsIn.length * (5500 - 3760))) * 100D, results.get());
            }
        }

        System.out.println("done");
    }

    static final int[][] targetTerrain = {
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
    };
}
