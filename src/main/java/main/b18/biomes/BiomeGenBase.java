// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

package main.b18.biomes;

import main.b18.Blocks;


// Referenced classes of package net.minecraft.src:
//            Block, BlockGrass, WorldGenTrees, WorldGenBigTree,
//            WorldGenForest, WorldGenSwamp, SpawnListEntry, EntitySheep,
//            EntityPig, EntityChicken, EntityCow, EntitySpider,
//            EntityZombie, EntitySkeleton, EntityCreeper, EntitySlime,
//            EntityEnderman, EntitySquid, BiomeDecorator, EnumCreatureType,
//            BiomeGenOcean, BiomeGenPlains, BiomeGenDesert, BiomeGenHills,
//            BiomeGenForest, BiomeGenTaiga, BiomeGenSwamp, BiomeGenRiver,
//            BiomeGenHell, BiomeGenSky, WorldGenerator, World

public class BiomeGenBase {

    protected BiomeGenBase(int i) {
        topBlock = (byte) Blocks.GRASS.blockID();
        fillerBlock = (byte) Blocks.DIRT.blockID();
        field_6502_q = 0x4ee031;
        biomeScalarB = 0.1F;
        biomeScalarA = 0.3F;
        field_35490_s = 0.5F;
        field_35489_t = 0.5F;
//        enableRain = true;
//        field_35493_z = new WorldGenTrees(); // oak
//        field_35480_A = new WorldGenBigTree(); // big tree
//        field_35481_B = new WorldGenForest();
//        field_35482_C = new WorldGenSwamp();
        biomeID = i;
        biomeBase[i] = this;
//        field_35488_u = func_35475_a();
    }

//    protected BiomeDecorator func_35475_a()
//    {
//        return new BiomeDecorator(this);
//    }

    private BiomeGenBase func_35478_a(float f, float f1) {
        field_35490_s = f;
        field_35489_t = f1;
        return this;
    }

    private BiomeGenBase func_35479_b(float f, float f1) {
        biomeScalarB = f;
        biomeScalarA = f1;
        return this;
    }

//    private BiomeGenBase setDisableRain() {
//        enableRain = false;
//        return this;
//    }

//    public WorldGenerator getRandomOverrideWorldGenForTrees(RandomOverride random)
//    {
//        if(random.nextInt(10) == 0)
//        {
//            return field_35480_A;
//        } else
//        {
//            return field_35493_z;
//        }
//    }

    protected BiomeGenBase setBiomeName(String s) {
        biomeName = s;
        return this;
    }

    protected BiomeGenBase func_4124_a(int i) {
        field_6502_q = i;
        return this;
    }

    protected BiomeGenBase setColor(int i) {
        color = i;
        return this;
    }

    public int getSkyColorByTemp(float f) {
        f /= 3F;
        if (f < -1F) {
            f = -1F;
        }
        if (f > 1.0F) {
            f = 1.0F;
        }
        return java.awt.Color.getHSBColor(0.6222222F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
    }

//    public boolean getEnableSnow()
//    {
//        return enableSnow;
//    }

    public float getBiome() {
        return 0.1F;
    }

    public final int func_35476_e() {
        return (int) (field_35489_t * 65536F);
    }

    public final int func_35474_f() {
        return (int) (field_35490_s * 65536F);
    }

//    public void func_35477_a(World world, RandomOverride random, int i, int j)
//    {
//        field_35488_u.func_35881_a(world, random, i, j);
//    }

    public static final BiomeGenBase biomeBase[] = new BiomeGenBase[256];
    public static final BiomeGenBase ocean = (new BiomeGenBase(0) {
    }).setColor(112).setBiomeName("Ocean").func_35479_b(-1F, 0.5F);
    public static final BiomeGenBase plains = (new BiomeGenBase(1)).setColor(0x8db360).setBiomeName("Plains").func_35478_a(0.8F, 0.4F);
    public static final BiomeGenBase desert = (new BiomeGenBase(2)).setColor(0xfa9418).setBiomeName("Desert").func_35478_a(2.0F, 0.0F).func_35479_b(0.1F, 0.2F);
    public static final BiomeGenBase extremeHills = (new BiomeGenBase(3)).setColor(0x606060).setBiomeName("Extreme Hills").func_35479_b(0.2F, 1.8F).func_35478_a(0.2F, 0.3F);
    public static final BiomeGenBase forest = (new BiomeGenBase(4)).setColor(0x56621).setBiomeName("Forest").func_4124_a(0x4eba31).func_35478_a(0.7F, 0.8F);
    public static final BiomeGenBase taiga = (new BiomeGenBase(5)).setColor(0xb6659).setBiomeName("Taiga").func_4124_a(0x4eba31).func_35478_a(0.3F, 0.8F).func_35479_b(0.1F, 0.4F);
    public static final BiomeGenBase swampland = (new BiomeGenBase(6)).setColor(0x7f9b2).setBiomeName("Swampland").func_4124_a(0x8baf48).func_35479_b(-0.2F, 0.1F).func_35478_a(0.8F, 0.9F);
    public static final BiomeGenBase river = (new BiomeGenBase(7)).setColor(255).setBiomeName("River").func_35479_b(-0.5F, 0.0F);
    public static final BiomeGenBase hell = (new BiomeGenBase(8)).setColor(0xff0000).setBiomeName("Hell");
    public static final BiomeGenBase sky = (new BiomeGenBase(9)).setColor(0x8080ff).setBiomeName("Sky");
    public String biomeName;
    public int color;
    public byte topBlock;
    public byte fillerBlock;
    public int field_6502_q;
    public float biomeScalarB;
    public float biomeScalarA;
    public float field_35490_s;
    public float field_35489_t;
    //    public BiomeDecorator field_35488_u;
//    private boolean enableSnow;
//    private boolean enableRain;
    public final int biomeID;
//    protected WorldGenTrees field_35493_z;
//    protected WorldGenBigTree field_35480_A;
//    protected WorldGenForest field_35481_B;
//    protected WorldGenSwamp field_35482_C;
}
