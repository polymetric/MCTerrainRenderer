// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package main.b18.biomes;


// Referenced classes of package net.minecraft.src:
//            GenLayer, IntCache, BiomeGenBase

public class GenLayerRiverMix extends GenLayer
{

    public GenLayerRiverMix(long l, GenLayer genlayer, GenLayer genlayer1)
    {
        super(l);
        field_35512_b = genlayer;
        field_35513_c = genlayer1;
    }

    public void setSeed(long l)
    {
        field_35512_b.setSeed(l);
        field_35513_c.setSeed(l);
        super.setSeed(l);
    }

    public int[] func_35500_a(int i, int j, int k, int l)
    {
        int ai[] = field_35512_b.func_35500_a(i, j, k, l);
        int ai1[] = field_35513_c.func_35500_a(i, j, k, l);
        int ai2[] = IntCache.func_35267_a(k * l);
        for(int i1 = 0; i1 < k * l; i1++)
        {
            if(ai[i1] == BiomeGenBase.ocean.biomeID)
            {
                ai2[i1] = ai[i1];
            } else
            {
                ai2[i1] = ai1[i1] < 0 ? ai[i1] : ai1[i1];
            }
        }

        return ai2;
    }

    private GenLayer field_35512_b;
    private GenLayer field_35513_c;
}
