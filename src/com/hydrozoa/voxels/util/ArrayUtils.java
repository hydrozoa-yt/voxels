package com.hydrozoa.voxels.util;

public class ArrayUtils {
	
	public static byte[][][] threeDeeArrayCopier(byte[][][] src) {
		byte[][][] newArray = new byte[src.length][src[0].length][src[0][0].length];
		
		for (int x=0; x < src.length; x++) {
			for (int y=0; y < src[0].length; y++) {
				for (int z=0; z < src[0][0].length; z++) {
					newArray[x][y][z] = src[x][y][z];
				}
			}
		}
		
		return newArray;
	}

}
