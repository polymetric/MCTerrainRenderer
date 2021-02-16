// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

package main.b18;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, MapGenCaves, MapGenStronghold, MapGenVillage,
//            MapGenMineshaft, MapGenRavine, NoiseGeneratorOctaves, World,
//            WorldChunkManager, Block, BiomeGenBase, Chunk,
//            MapGenBase, MathHelper, BlockSand, WorldGenLakes,
//            WorldGenDungeons, SpawnerAnimals, IProgressUpdate

import main.b18.biomes.BiomeGenBase;

public class ChunkProviderGenerate {

    public ChunkProviderGenerate(long seed, WorldChunkManager worldChunkManager) {
        this.worldChunkManager = worldChunkManager;
        stoneNoise = new double[256];
//        caveGenerator = new MapGenCaves();
//        field_35386_d = new MapGenStronghold();
//        field_35387_e = new MapGenVillage();
//        field_35385_f = new MapGenMineshaft();
//        field_35390_x = new MapGenRavine();
//        field_35389_t = flag;
        rand = new RandomOverride(seed);
        minLimit = new NoiseGeneratorOctaves(rand, 16);
        maxLimit = new NoiseGeneratorOctaves(rand, 16);
        mainLimit = new NoiseGeneratorOctaves(rand, 8);
        field_908_o = new NoiseGeneratorOctaves(rand, 4);
        scale = new NoiseGeneratorOctaves(rand, 10);
        depth = new NoiseGeneratorOctaves(rand, 16);
        mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
    }

    public void generateTerrain(int chunkX, int chunkZ, byte chunk[]) {
        byte samplerInterval = 4;
        int k = 128 / 8;
        byte byte1 = 63;
        int sizeX = samplerInterval + 1;
        int sizeY = 128 / 8 + 1;
        int sizeZ = samplerInterval + 1;
        biomesForGeneration = worldChunkManager.getBiomes(biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, sizeX + 5, sizeZ + 5);
        field_4180_q = fillNoiseColumn(field_4180_q, chunkX * samplerInterval, 0, chunkZ * samplerInterval, sizeX, sizeY, sizeZ);
        for (int k1 = 0; k1 < samplerInterval; k1++) {
            for (int l1 = 0; l1 < samplerInterval; l1++) {
                for (int i2 = 0; i2 < k; i2++) {
                    double d = 0.125D;
                    double d1 = field_4180_q[((k1 + 0) * sizeZ + (l1 + 0)) * sizeY + (i2 + 0)];
                    double d2 = field_4180_q[((k1 + 0) * sizeZ + (l1 + 1)) * sizeY + (i2 + 0)];
                    double d3 = field_4180_q[((k1 + 1) * sizeZ + (l1 + 0)) * sizeY + (i2 + 0)];
                    double d4 = field_4180_q[((k1 + 1) * sizeZ + (l1 + 1)) * sizeY + (i2 + 0)];
                    double d5 = (field_4180_q[((k1 + 0) * sizeZ + (l1 + 0)) * sizeY + (i2 + 1)] - d1) * d;
                    double d6 = (field_4180_q[((k1 + 0) * sizeZ + (l1 + 1)) * sizeY + (i2 + 1)] - d2) * d;
                    double d7 = (field_4180_q[((k1 + 1) * sizeZ + (l1 + 0)) * sizeY + (i2 + 1)] - d3) * d;
                    double d8 = (field_4180_q[((k1 + 1) * sizeZ + (l1 + 1)) * sizeY + (i2 + 1)] - d4) * d;
                    for (int j2 = 0; j2 < 8; j2++) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int k2 = 0; k2 < 4; k2++) {
                            int l2 = k2 + k1 * 4 << 11 | 0 + l1 * 4 << 7 | i2 * 8 + j2;
                            int i3 = 1 << 7;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int j3 = 0; j3 < 4; j3++) {
                                int k3 = 0;
                                if (i2 * 8 + j2 < byte1) {
                                    k3 = 9;
                                }
                                if (d15 > 0.0D) {
                                    k3 = 1;
                                }
                                chunk[l2] = (byte) k3;
                                l2 += i3;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }

                }

            }

        }

    }

    public void replaceBlocksForBiome(int chunkX, int chunkZ, byte chunk[], BiomeGenBase abiomegenbase[]) {
        byte oceanLevel = 63;
        double noiseFactor = 0.03125D;
        stoneNoise = field_908_o.generateNoiseOctaves(stoneNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, noiseFactor * 2D, noiseFactor * 2D, noiseFactor * 2D);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BiomeGenBase biomegenbase = abiomegenbase[z + x * 16];
                int elevation = (int) (stoneNoise[x + z * 16] / 3D + 3D + rand.nextDouble() * 0.25D);
                int state = -1;
                byte topBlock = biomegenbase.topBlock;
                byte fillerBlock = biomegenbase.fillerBlock;
                for (int y = 127; y >= 0; y--) {
                    int chunkCachePos = (z * 16 + x) * 128 + y;
                    if (y <= 0 + rand.nextInt(5)) {
                        chunk[chunkCachePos] = (byte) Blocks.BEDROCK.blockID();
                        continue;
                    }
                    byte prevBlock = chunk[chunkCachePos];
                    if (prevBlock == Blocks.AIR.blockID()) {
                        state = -1;
                        continue;
                    }
                    if (prevBlock != Blocks.STONE.blockID()) {
                        continue;
                    }
                    if (state == -1) {
                        if (elevation <= 0) {
                            topBlock = 0;
                            fillerBlock = (byte) Blocks.STONE.blockID();
                        } else if (y >= oceanLevel - 4 && y <= oceanLevel + 1) {
                            topBlock = biomegenbase.topBlock;
                            fillerBlock = biomegenbase.fillerBlock;
                        }
                        if (y < oceanLevel && topBlock == 0) {
                            topBlock = (byte) Blocks.STILL_WATER.blockID();
                        }
                        state = elevation;
                        if (y >= oceanLevel - 1) {
                            chunk[chunkCachePos] = topBlock;
                        } else {
                            chunk[chunkCachePos] = fillerBlock;
                        }
                        continue;
                    }
                    if (state <= 0) {
                        continue;
                    }
                    state--;
                    chunk[chunkCachePos] = fillerBlock;
                    if (state == 0 && fillerBlock == Blocks.SAND.blockID()) {
                        state = rand.nextInt(4);
                        fillerBlock = (byte) Blocks.SANDSTONE.blockID();
                    }
                }
            }
        }
    }

    public byte[] provideChunk(int chunkX, int chunkZ) {
        rand.setSeed((long) chunkX * 0x4f9939f508L + (long) chunkZ * 0x1ef1565bd5L);
        byte chunk[] = new byte[16 * 128 * 16];
//        Chunk chunk = new Chunk(worldObj, chunk, chunkX, chunkZ);
        generateTerrain(chunkX, chunkZ, chunk);
//        biomesForGeneration = worldChunkManager.loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
//        replaceBlocksForBiome(chunkX, chunkZ, chunk, biomesForGeneration);
//        caveGenerator.generate(this, worldObj, chunkX, chunkZ, chunk);
//        if(field_35389_t)
//        {
//            field_35386_d.generate(this, worldObj, chunkX, chunkZ, chunk);
//            field_35385_f.generate(this, worldObj, chunkX, chunkZ, chunk);
//            field_35387_e.generate(this, worldObj, chunkX, chunkZ, chunk);
//        }
//        field_35390_x.generate(this, worldObj, chunkX, chunkZ, chunk);
//        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] fillNoiseColumn(double noiseColumn[], int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
        if (noiseColumn == null) {
            noiseColumn = new double[sizeX * sizeY * sizeZ];
        }
        if (biomeNoise == null) {
            biomeNoise = new float[25];
            for (int columnX = -2; columnX <= 2; columnX++) {
                for (int columnZ = -2; columnZ <= 2; columnZ++) {
                    biomeNoise[columnX + 2 + (columnZ + 2) * 5] = 10F / (float) Math.sqrt((float) (columnX * columnX + columnZ * columnZ) + 0.2F);
                }

            }

        }
        double d = 684.412D;
        double d1 = 684.412D;
        surfaceNoise = scale.generateFixedNoise(surfaceNoise, x, z, sizeX, sizeZ, 1.121D, 1.121D);
        depthNoise = depth.generateFixedNoise(depthNoise, x, z, sizeX, sizeZ, 200D, 200D);
        mainLimitNoise = mainLimit.generateNoiseOctaves(mainLimitNoise, x, y, z, sizeX, sizeY, sizeZ, d / 80D, d1 / 160D, d / 80D);
        minLimitNoise = minLimit.generateNoiseOctaves(minLimitNoise, x, y, z, sizeX, sizeY, sizeZ, d, d1, d);
        maxLimitNoise = maxLimit.generateNoiseOctaves(maxLimitNoise, x, y, z, sizeX, sizeY, sizeZ, d, d1, d);
        x = z = 0;
        int i2 = 0;
        int j2 = 0;
        for (int xCell = 0; xCell < sizeX; xCell++) {
            for (int zCell = 0; zCell < sizeZ; zCell++) {
                float biomeScalarA = 0.0F;
                float biomeScalarB = 0.0F;
                float biomeScalarC = 0.0F;
                byte columnRadius = 2;
                BiomeGenBase biomegenbase = biomesForGeneration[xCell + 2 + (zCell + 2) * (sizeX + 5)];
                for (int columnX = -columnRadius; columnX <= columnRadius; columnX++) {
                    for (int columnZ = -columnRadius; columnZ <= columnRadius; columnZ++) {
                        BiomeGenBase biomegenbase1 = biomesForGeneration[xCell + columnX + 2 + (zCell + columnZ + 2) * (sizeX + 5)];
                        float biomeScalarD = biomeNoise[columnX + 2 + (columnZ + 2) * 5] / (biomegenbase1.biomeScalarB + 2.0F);
                        if (biomegenbase1.biomeScalarB > biomegenbase.biomeScalarB) {
                            biomeScalarD /= 2.0F;
                        }
                        biomeScalarA += biomegenbase1.biomeScalarA * biomeScalarD;
                        biomeScalarB += biomegenbase1.biomeScalarB * biomeScalarD;
                        biomeScalarC += biomeScalarD;
                    }
                }

                biomeScalarA /= biomeScalarC;
                biomeScalarB /= biomeScalarC;
                biomeScalarA = biomeScalarA * 0.9F + 0.1F;
                biomeScalarB = (biomeScalarB * 4F - 1.0F) / 8F;
                double d2 = depthNoise[j2] / 8000D;
                if (d2 < 0.0D) {
                    d2 = -d2 * 0.3D;
                }
                d2 = d2 * 3D - 2D;
                if (d2 < 0.0D) {
                    d2 /= 2D;
                    if (d2 < -1D) {
                        d2 = -1D;
                    }
                    d2 /= 1.4D;
                    d2 /= 2D;
                } else {
                    if (d2 > 1.0D) {
                        d2 = 1.0D;
                    }
                    d2 /= 8D;
                }
                j2++;
                for (int k3 = 0; k3 < sizeY; k3++) {
                    double d3 = biomeScalarB;
                    double d4 = biomeScalarA;
                    d3 += d2 * 0.2D;
                    d3 = (d3 * (double) sizeY) / 16D;
                    double d5 = (double) sizeY / 2D + d3 * 4D;
                    double d6 = 0.0D;
                    double d7 = (((double) k3 - d5) * 12D * 128D) / 128D / d4;
                    if (d7 < 0.0D) {
                        d7 *= 4D;
                    }
                    double d8 = minLimitNoise[i2] / 512D;
                    double d9 = maxLimitNoise[i2] / 512D;
                    double d10 = (mainLimitNoise[i2] / 10D + 1.0D) / 2D;
                    if (d10 < 0.0D) {
                        d6 = d8;
                    } else if (d10 > 1.0D) {
                        d6 = d9;
                    } else {
                        d6 = d8 + (d9 - d8) * d10;
                    }
                    d6 -= d7;
                    if (k3 > sizeY - 4) {
                        double d11 = (float) (k3 - (sizeY - 4)) / 3F;
                        d6 = d6 * (1.0D - d11) + -10D * d11;
                    }
                    noiseColumn[i2] = d6;
                    i2++;
                }
            }
        }
        return noiseColumn;
    }

//    public static long[][] chunkDFZs = new long[512][512];

//    public void populate(IChunkProvider ichunkprovider, int chunkX, int chunkZ)
//    {
//        BlockSand.fallInstantly = true;
//        int blockX = chunkX * 16;
//        int blockZ = chunkZ * 16;
//        BiomeGenBase biomegenbase = worldObj.getWorldChunkManager().getBiomeGenAt(blockX + 16, blockZ + 16);
//
//        // generate chunk seed
//        rand.setSeed(worldObj.getRandomOverrideSeed());
//        long chunkSeedModifierX = (rand.nextLong() / 2L) * 2L + 1L;
//        long chunkSeedModifierZ = (rand.nextLong() / 2L) * 2L + 1L;
//        rand.setSeed((long)chunkX * chunkSeedModifierX + (long)chunkZ * chunkSeedModifierZ ^ worldObj.getRandomOverrideSeed());
//
//        if (chunkX == 0 && chunkZ == 0) {
////            System.out.printf("chunk seed for chunk %12d %12d is %24d\n", chunkX, chunkZ, rand.getSeed());
//        }
//
////        if (chunkX >= 0 && chunkZ >= 0) {
////            chunkDFZs[chunkX][chunkZ] = rand.getSeed() ^ 25214903917L;
////        }
//
//        boolean flag = false;
//        if(field_35389_t)
//        {
//            field_35386_d.func_35629_a(worldObj, rand, chunkX, chunkZ);
//            field_35385_f.func_35629_a(worldObj, rand, chunkX, chunkZ);
//            flag = field_35387_e.func_35629_a(worldObj, rand, chunkX, chunkZ);
//        }
//        if(!flag && rand.nextInt(4) == 0)
//        {
//            int i1 = blockX + rand.nextInt(16) + 8;
//            worldObj.getClass();
//            int i2 = rand.nextInt(128);
//            int i3 = blockZ + rand.nextInt(16) + 8;
//            (new WorldGenLakes(Block.waterStill.blockID)).generate(worldObj, rand, i1, i2, i3);
//        }
//        if(!flag && rand.nextInt(8) == 0)
//        {
//            int j1 = blockX + rand.nextInt(16) + 8;
//            worldObj.getClass();
//            int j2 = rand.nextInt(rand.nextInt(128 - 8) + 8);
//            int j3 = blockZ + rand.nextInt(16) + 8;
//            if(j2 < 63 || rand.nextInt(10) == 0)
//            {
//                (new WorldGenLakes(Block.lavaStill.blockID)).generate(worldObj, rand, j1, j2, j3);
//            }
//        }
//        for(int k1 = 0; k1 < 8; k1++)
//        {
//            int k2 = blockX + rand.nextInt(16) + 8;
//            int k3 = rand.nextInt(128);
//            int l3 = blockZ + rand.nextInt(16) + 8;
//            if(!(new WorldGenDungeons()).generate(worldObj, rand, k2, k3, l3));
//        }
//
//        biomegenbase.func_35477_a(worldObj, rand, blockX, blockZ);
//        SpawnerAnimals.func_35957_a(worldObj, biomegenbase, blockX + 8, blockZ + 8, 16, 16, rand);
//        BlockSand.fallInstantly = false;
//    }

    private RandomOverride rand;
    private NoiseGeneratorOctaves minLimit;
    private NoiseGeneratorOctaves maxLimit;
    private NoiseGeneratorOctaves mainLimit;
    private NoiseGeneratorOctaves field_908_o;
    public NoiseGeneratorOctaves scale;
    public NoiseGeneratorOctaves depth;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private WorldChunkManager worldChunkManager;
    //    private final boolean field_35389_t;
    private double field_4180_q[];
    private double stoneNoise[];
    //    private MapGenBase caveGenerator;
//    public MapGenStronghold field_35386_d;
//    public MapGenVillage field_35387_e;
//    public MapGenMineshaft field_35385_f;
//    private MapGenBase field_35390_x;
    private BiomeGenBase biomesForGeneration[];
    double mainLimitNoise[];
    double minLimitNoise[];
    double maxLimitNoise[];
    double surfaceNoise[];
    double depthNoise[];
    float biomeNoise[];
}
