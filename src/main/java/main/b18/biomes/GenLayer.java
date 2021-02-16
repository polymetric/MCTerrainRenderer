// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

package main.b18.biomes;


// Referenced classes of package net.minecraft.src:
//            LayerIsland, GenLayerZoomFuzzy, GenLayerIsland, GenLayerZoom,
//            GenLayerRiverInit, GenLayerRiver, GenLayerSmooth, GenLayerVillageLandscape,
//            GenLayerTemperature, GenLayerDownfall, GenLayerSmoothZoom, GenLayerTemperatureMix,
//            GenLayerDownfallMix, GenLayerRiverMix, GenLayerZoomVoronoi

public abstract class GenLayer
{
    public static GenLayer[] func_35497_a(long seed)
    {
        GenLayer genLayer1 = new LayerIsland(1L);
        genLayer1 = new GenLayerZoomFuzzy(2000L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerIsland(1L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerZoom(2001L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerIsland(2L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerZoom(2002L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerIsland(3L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerZoom(2003L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerIsland(3L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerZoom(2004L, ((GenLayer) (genLayer1)));
        genLayer1 = new GenLayerIsland(3L, ((GenLayer) (genLayer1)));
        byte byte0 = 4;
        GenLayer genLayer2 = genLayer1;
        genLayer2 = GenLayerZoom.func_35515_a(1000L, ((GenLayer) (genLayer2)), 0);
        genLayer2 = new GenLayerRiverInit(100L, ((GenLayer) (genLayer2)));
        genLayer2 = GenLayerZoom.func_35515_a(1000L, ((GenLayer) (genLayer2)), byte0 + 2);
        genLayer2 = new GenLayerRiver(1L, ((GenLayer) (genLayer2)));
        genLayer2 = new GenLayerSmooth(1000L, ((GenLayer) (genLayer2)));
        GenLayer genLayerZoom = genLayer1;
        genLayerZoom = GenLayerZoom.func_35515_a(1000L, ((GenLayer) (genLayerZoom)), 0);
        genLayerZoom = new GenLayerVillageLandscape(200L, ((GenLayer) (genLayerZoom)));
        genLayerZoom = GenLayerZoom.func_35515_a(1000L, ((GenLayer) (genLayerZoom)), 2);
        GenLayer genLayerSmoothZoom1 = new GenLayerTemperature(((GenLayer) (genLayerZoom)));
        GenLayer genLayerSmoothZoom2 = new GenLayerDownfall(((GenLayer) (genLayerZoom)));
        for(int i = 0; i < byte0; i++)
        {
            genLayerZoom = new GenLayerZoom(1000 + i, ((GenLayer) (genLayerZoom)));
            if(i == 0)
            {
                genLayerZoom = new GenLayerIsland(3L, ((GenLayer) (genLayerZoom)));
            }
            genLayerSmoothZoom1 = new GenLayerSmoothZoom(1000 + i, ((GenLayer) (genLayerSmoothZoom1)));
            genLayerSmoothZoom1 = new GenLayerTemperatureMix(((GenLayer) (genLayerSmoothZoom1)), ((GenLayer) (genLayerZoom)), i);
            genLayerSmoothZoom2 = new GenLayerSmoothZoom(1000 + i, ((GenLayer) (genLayerSmoothZoom2)));
            genLayerSmoothZoom2 = new GenLayerDownfallMix(((GenLayer) (genLayerSmoothZoom2)), ((GenLayer) (genLayerZoom)), i);
        }

        genLayerZoom = new GenLayerSmooth(1000L, ((GenLayer) (genLayerZoom)));
        genLayerZoom = new GenLayerRiverMix(100L, ((GenLayer) (genLayerZoom)), ((GenLayer) (genLayer2)));
        genLayerSmoothZoom1 = GenLayerSmoothZoom.func_35517_a(1000L, ((GenLayer) (genLayerSmoothZoom1)), 2);
        genLayerSmoothZoom2 = GenLayerSmoothZoom.func_35517_a(1000L, ((GenLayer) (genLayerSmoothZoom2)), 2);
        GenLayerZoomVoronoi getLayerZoomVoronoi = new GenLayerZoomVoronoi(10L, ((GenLayer) (genLayerZoom)));
        ((GenLayer) (genLayerZoom)).setSeed(seed);
        ((GenLayer) (genLayerSmoothZoom1)).setSeed(seed);
        ((GenLayer) (genLayerSmoothZoom2)).setSeed(seed);
        getLayerZoomVoronoi.setSeed(seed);
        return (new GenLayer[] {
                genLayerZoom, getLayerZoomVoronoi, genLayerSmoothZoom1, genLayerSmoothZoom2
        });
    }

    public GenLayer(long l)
    {
        field_35501_d = l;
        field_35501_d *= field_35501_d * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35501_d += l;
        field_35501_d *= field_35501_d * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35501_d += l;
        field_35501_d *= field_35501_d * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35501_d += l;
    }

    public void setSeed(long l)
    {
        seed = l;
        if(field_35504_a != null)
        {
            field_35504_a.setSeed(l);
        }
        seed *= seed * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        seed += field_35501_d;
        seed *= seed * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        seed += field_35501_d;
        seed *= seed * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        seed += field_35501_d;
    }

    public void func_35499_a(long l, long l1)
    {
        field_35503_c = seed;
        field_35503_c *= field_35503_c * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35503_c += l;
        field_35503_c *= field_35503_c * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35503_c += l1;
        field_35503_c *= field_35503_c * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35503_c += l;
        field_35503_c *= field_35503_c * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35503_c += l1;
    }

    protected int func_35498_a(int i)
    {
        int j = (int)((field_35503_c >> 24) % (long)i);
        if(j < 0)
        {
            j += i;
        }
        field_35503_c *= field_35503_c * 0x5851f42d4c957f2dL + 0x14057b7ef767814fL;
        field_35503_c += seed;
        return j;
    }

    public abstract int[] func_35500_a(int i, int j, int k, int l);

    private long seed;
    protected GenLayer field_35504_a;
    private long field_35503_c;
    private long field_35501_d;
}
