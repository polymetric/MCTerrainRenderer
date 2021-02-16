// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

package main.b18;


// Referenced classes of package net.minecraft.src:
//            NoiseGenerator, NoiseGeneratorPerlin, MathHelper

import java.util.Arrays;

public class NoiseGeneratorOctaves extends NoiseGenerator {

    public NoiseGeneratorOctaves(RandomOverride random, int i) {
        octaves = i;
        generatorCollection = new NoiseGeneratorPerlin[i];
        for (int j = 0; j < i; j++) {
            generatorCollection[j] = new NoiseGeneratorPerlin(random);
        }

    }

    public double[] generateNoiseOctaves(double buffer[], int x, int y, int z, int sizeX, int sizeY, int sizeZ,
                                         double offsetX, double offsetY, double offsetZ) {
        if (buffer == null) {
            buffer = new double[sizeX * sizeY * sizeZ];
        } else {
            Arrays.fill(buffer, 0);
        }
        double scale = 1.0D;
        for (int octave = 0; octave < octaves; octave++) {
            double xf = (double) x * scale * offsetX;
            double yf = (double) y * scale * offsetY;
            double zf = (double) z * scale * offsetZ;
            generatorCollection[octave].func_805_a(buffer, xf, yf, zf, sizeX, sizeY, sizeZ, offsetX * scale, offsetY * scale, offsetZ * scale, scale);
            scale /= 2D;
        }

        return buffer;
    }

    public double[] generateFixedNoise(double buffer[], int x, int z, int sizeX, int sizeZ, double offsetX,
                                       double offsetZ) {
        return generateNoiseOctaves(buffer, x, 10, z, sizeX, 1, sizeZ, offsetX, 1.0D, offsetZ);
    }

    private NoiseGeneratorPerlin generatorCollection[];
    private int octaves;
}
