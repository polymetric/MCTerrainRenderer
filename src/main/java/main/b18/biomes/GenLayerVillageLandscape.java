// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package main.b18.biomes;


// Referenced classes of package net.minecraft.src:
//            GenLayer, BiomeGenBase, IntCache

public class GenLayerVillageLandscape extends GenLayer
{

    public GenLayerVillageLandscape(long l, GenLayer genlayer)
    {
        super(l);
        field_35509_b = (new BiomeGenBase[] {
            BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga
        });
        field_35504_a = genlayer;
    }

    public int[] func_35500_a(int i, int j, int k, int l)
    {
        int ai[] = field_35504_a.func_35500_a(i, j, k, l);
        int ai1[] = IntCache.func_35267_a(k * l);
        for(int i1 = 0; i1 < l; i1++)
        {
            for(int j1 = 0; j1 < k; j1++)
            {
                func_35499_a(j1 + i, i1 + j);
                ai1[j1 + i1 * k] = ai[j1 + i1 * k] <= 0 ? 0 : field_35509_b[func_35498_a(field_35509_b.length)].biomeID;
            }

        }

        return ai1;
    }

    private BiomeGenBase field_35509_b[];
}
