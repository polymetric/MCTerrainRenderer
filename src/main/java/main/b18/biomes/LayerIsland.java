// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package main.b18.biomes;


// Referenced classes of package net.minecraft.src:
//            GenLayer, IntCache

public class LayerIsland extends GenLayer
{

    public LayerIsland(long l)
    {
        super(l);
    }

    public int[] func_35500_a(int i, int j, int k, int l)
    {
        int ai[] = IntCache.func_35267_a(k * l);
        for(int i1 = 0; i1 < l; i1++)
        {
            for(int j1 = 0; j1 < k; j1++)
            {
                func_35499_a(i + j1, j + i1);
                ai[j1 + i1 * k] = func_35498_a(10) != 0 ? 0 : 1;
            }

        }

        if(i > -k && i <= 0 && j > -l && j <= 0)
        {
            ai[-i + -j * k] = 1;
        }
        return ai;
    }
}
