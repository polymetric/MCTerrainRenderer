// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode
//
package main.b18;

// Referenced classes of package net.minecraft.src:
//            BiomeCache, BiomeGenBase, World, GenLayer,
//            ChunkCoordIntPair, IntCache, ChunkPosition
//

import main.b18.biomes.BiomeGenBase;
import main.b18.biomes.GenLayer;
import main.b18.biomes.IntCache;

import java.util.ArrayList;
import java.util.List;

public class WorldChunkManager {

    protected WorldChunkManager() {
//        biomeCache = new BiomeCache(this);
//        biomes = new ArrayList();
//        biomes.add(BiomeGenBase.forest);
//        biomes.add(BiomeGenBase.swampland);
//        biomes.add(BiomeGenBase.taiga);
    }

    public WorldChunkManager(long seed) {
        this();
        GenLayer agenlayer[] = GenLayer.func_35497_a(seed);
        field_34903_b = agenlayer[0];
        field_34902_c = agenlayer[1];
        field_34901_d = agenlayer[2];
        field_35565_e = agenlayer[3];
    }

//    public List func_35559_a() {
//        return biomes;
//    }

//    public BiomeGenBase getBiomeGenAtChunkCoord(int chunkX, int chunkZ) {
//        return getBiomeGenAt(chunkX << 4, chunkZ << 4);
//    }

//    public BiomeGenBase getBiomeGenAt(int i, int j) {
//        return biomeCache.func_35725_a(i, j);
//    }

//    public float func_35558_c(int i, int j) {
//        return biomeCache.func_35727_c(i, j);
//    }

//    public float[] func_35560_b(float af[], int i, int j, int k, int l) {
////        IntCache.func_35268_a();
//        if (af == null || af.length < k * l) {
//            af = new float[k * l];
//        }
//        int ai[] = field_35565_e.func_35500_a(i, j, k, l);
//        for (int i1 = 0; i1 < k * l; i1++) {
//            float f = (float) ai[i1] / 65536F;
//            if (f > 1.0F) {
//                f = 1.0F;
//            }
//            af[i1] = f;
//        }
//
//        return af;
//    }

//    public float func_35554_b(int i, int j) {
//        return biomeCache.func_35722_b(i, j);
//    }

//    public float[] getTemperatures(float temperatures[], int i, int j, int k, int l) {
////        IntCache.func_35268_a();
//        if (temperatures == null || temperatures.length < k * l) {
//            temperatures = new float[k * l];
//        }
//        int ai[] = field_34901_d.func_35500_a(i, j, k, l);
//        for (int i1 = 0; i1 < k * l; i1++) {
//            float f = (float) ai[i1] / 65536F;
//            if (f > 1.0F) {
//                f = 1.0F;
//            }
//            temperatures[i1] = f;
//        }
//
//        return temperatures;
//    }

    public BiomeGenBase[] getBiomes(BiomeGenBase abiomegenbase[], int chunkX, int chunkZ, int sizeX, int sizeZ) {
        IntCache.func_35268_a();
        if (abiomegenbase == null || abiomegenbase.length < sizeX * sizeZ) {
            abiomegenbase = new BiomeGenBase[sizeX * sizeZ];
        }
        int ai[] = field_34903_b.func_35500_a(chunkX, chunkZ, sizeX, sizeZ);
        for (int i = 0; i < sizeX * sizeZ; i++) {
            abiomegenbase[i] = BiomeGenBase.biomeBase[ai[i]];
        }

        return abiomegenbase;
    }

//    public BiomeGenBase[] func_4069_a(int i, int j, int k, int l) {
//        if (k == 16 && l == 16 && (i & 0xf) == 0 && (j & 0xf) == 0) {
//            return biomeCache.func_35723_d(i, j);
//        } else {
//            biomeGenBase = loadBlockGeneratorData(biomeGenBase, i, j, k, l);
//            return biomeGenBase;
//        }
//    }

//    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase abiomegenbase[], int i, int j, int k, int l) {
//        return func_35555_a(abiomegenbase, i, j, k, l, true);
//    }

//    public BiomeGenBase[] func_35555_a(BiomeGenBase abiomegenbase[], int i, int j, int k, int l, boolean flag) {
//        if (abiomegenbase == null || abiomegenbase.length < k * l) {
//            abiomegenbase = new BiomeGenBase[k * l];
//        }
//        if (flag && k == 16 && l == 16 && (i & 0xf) == 0 && (j & 0xf) == 0) {
//            BiomeGenBase abiomegenbase1[] = biomeCache.func_35723_d(i, j);
//            System.arraycopy(abiomegenbase1, 0, abiomegenbase, 0, k * l);
//            return abiomegenbase;
//        }
//        int ai[] = field_34902_c.func_35500_a(i, j, k, l);
//        for (int i1 = 0; i1 < k * l; i1++) {
//            abiomegenbase[i1] = BiomeGenBase.field_35486_a[ai[i1]];
//        }
//
//        return abiomegenbase;
//    }

//    public boolean func_35562_a(int i, int j, int k, List list) {
//        int l = i - k >> 2;
//        int i1 = j - k >> 2;
//        int j1 = i + k >> 2;
//        int k1 = j + k >> 2;
//        int l1 = (j1 - l) + 1;
//        int i2 = (k1 - i1) + 1;
//        int ai[] = field_34903_b.func_35500_a(l, i1, l1, i2);
//        for (int j2 = 0; j2 < l1 * i2; j2++) {
//            BiomeGenBase biomegenbase = BiomeGenBase.field_35486_a[ai[j2]];
//            if (!list.contains(biomegenbase)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

//    public ChunkPosition func_35556_a(int i, int j, int k, List list, RandomOverride random) {
//        int l = i - k >> 2;
//        int i1 = j - k >> 2;
//        int j1 = i + k >> 2;
//        int k1 = j + k >> 2;
//        int l1 = (j1 - l) + 1;
//        int i2 = (k1 - i1) + 1;
//        int ai[] = field_34903_b.func_35500_a(l, i1, l1, i2);
//        ChunkPosition chunkposition = null;
//        int j2 = 0;
//        for (int k2 = 0; k2 < ai.length; k2++) {
//            int l2 = l + k2 % l1 << 2;
//            int i3 = i1 + k2 / l1 << 2;
//            BiomeGenBase biomegenbase = BiomeGenBase.field_35486_a[ai[k2]];
//            if (list.contains(biomegenbase) && (chunkposition == null || random.nextInt(j2 + 1) == 0)) {
//                chunkposition = new ChunkPosition(l2, 0, i3);
//                j2++;
//            }
//        }
//
//        return chunkposition;
//    }

    private GenLayer field_34903_b;
    private GenLayer field_34902_c;
    private GenLayer field_34901_d;
    private GenLayer field_35565_e;
//    private BiomeCache biomeCache;
//    private List<BiomeGenBase> biomes;
//    public BiomeGenBase biomeGenBase[];
}
