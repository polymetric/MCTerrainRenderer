package main.utils;

import main.terrainchecker.TreeSeedTerrainChecker;

import static java.lang.Math.*;

public class SubtractOffset {
    public static void main(String[] args) {
        for (int i = 0; i < TreeSeedTerrainChecker.targetTerrain.length; i++) {
            TreeSeedTerrainChecker.targetTerrain[i][0] -= floorDiv(TreeSeedTerrainChecker.targetTerrain[i][0], 16) * 16;
            TreeSeedTerrainChecker.targetTerrain[i][2] -= floorDiv(TreeSeedTerrainChecker.targetTerrain[i][2], 16) * 16;
            System.out.printf("{ %4d, %4d, %4d, },\n", TreeSeedTerrainChecker.targetTerrain[i][0], TreeSeedTerrainChecker.targetTerrain[i][1], TreeSeedTerrainChecker.targetTerrain[i][2]);
        }
    }
}
