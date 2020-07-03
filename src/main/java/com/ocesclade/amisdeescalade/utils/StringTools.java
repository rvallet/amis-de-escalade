package com.ocesclade.amisdeescalade.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class StringTools {
	
	public static String randomText(int nbmaxNbWords, int maxWordSize) throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		Random ran = SecureRandom.getInstanceStrong();
		
		for (int i=0; i<ran.nextInt(nbmaxNbWords); i++) {
			int ranWordSize = ran.nextInt(maxWordSize);
			if (ranWordSize < 2) {ranWordSize=2;}
			sb.append(RandomStringUtils.random(ranWordSize, true, false)+" ");
		}
		sb.append(".");
		return sb.toString();
	}
	

}
