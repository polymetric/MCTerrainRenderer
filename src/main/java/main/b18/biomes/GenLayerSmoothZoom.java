// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package main.b18.biomes;


// Referenced classes of package net.minecraft.src:
//            GenLayer, IntCache

public class GenLayerSmoothZoom extends GenLayer
{

    public GenLayerSmoothZoom(long l, GenLayer genlayer)
    {
        super(l);
        field_35504_a = genlayer;
    }

    public int[] func_35500_a(int i, int j, int k, int l)
    {
        int i1 = i >> 1;
        int j1 = j >> 1;
        int k1 = (k >> 1) + 3;
        int l1 = (l >> 1) + 3;
        int ai[] = field_35504_a.func_35500_a(i1, j1, k1, l1);
        int ai1[] = IntCache.func_35267_a(k1 * 2 * (l1 * 2));
        int i2 = k1 << 1;
        for(int j2 = 0; j2 < l1 - 1; j2++)
        {
            int k2 = j2 << 1;
            int i3 = k2 * i2;
            int j3 = ai[0 + (j2 + 0) * k1];
            int k3 = ai[0 + (j2 + 1) * k1];
            for(int l3 = 0; l3 < k1 - 1; l3++)
            {
                func_35499_a(l3 + i1 << 1, j2 + j1 << 1);
                int i4 = ai[l3 + 1 + (j2 + 0) * k1];
                int j4 = ai[l3 + 1 + (j2 + 1) * k1];
                ai1[i3] = j3;
                ai1[i3++ + i2] = j3 + ((k3 - j3) * func_35498_a(256)) / 256;
                ai1[i3] = j3 + ((i4 - j3) * func_35498_a(256)) / 256;
                int k4 = j3 + ((i4 - j3) * func_35498_a(256)) / 256;
                int l4 = k3 + ((j4 - k3) * func_35498_a(256)) / 256;
                ai1[i3++ + i2] = k4 + ((l4 - k4) * func_35498_a(256)) / 256;
                j3 = i4;
                k3 = j4;
            }

        }

        int ai2[] = IntCache.func_35267_a(k * l);
        for(int l2 = 0; l2 < l; l2++)
        {
            System.arraycopy(ai1, (l2 + (j & 1)) * (k1 << 1) + (i & 1), ai2, l2 * k, k);
        }

        return ai2;
    }

    public static GenLayer func_35517_a(long l, GenLayer genlayer, int i)
    {
        Object obj = genlayer;
        for(int j = 0; j < i; j++)
        {
            obj = new GenLayerSmoothZoom(l + (long)j, ((GenLayer) (obj)));
        }

        return ((GenLayer) (obj));
    }
}
