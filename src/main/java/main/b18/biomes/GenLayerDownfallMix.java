// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package main.b18.biomes;


// Referenced classes of package net.minecraft.src:
//            GenLayer, IntCache, BiomeGenBase

public class GenLayerDownfallMix extends GenLayer
{

    public GenLayerDownfallMix(GenLayer genlayer, GenLayer genlayer1, int i)
    {
        super(0L);
        field_35504_a = genlayer1;
        field_35507_b = genlayer;
        field_35508_c = i;
    }

    public int[] func_35500_a(int i, int j, int k, int l)
    {
        int ai[] = field_35504_a.func_35500_a(i, j, k, l);
        int ai1[] = field_35507_b.func_35500_a(i, j, k, l);
        int ai2[] = IntCache.func_35267_a(k * l);
        for(int i1 = 0; i1 < k * l; i1++)
        {
            ai2[i1] = ai1[i1] + (BiomeGenBase.biomeBase[ai[i1]].func_35476_e() - ai1[i1]) / (field_35508_c + 1);
        }

        return ai2;
    }

    private GenLayer field_35507_b;
    private int field_35508_c;
}
