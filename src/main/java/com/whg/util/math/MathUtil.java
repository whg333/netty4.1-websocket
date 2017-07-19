package com.whg.util.math;

public class MathUtil {

	/**
	 * 以mod取整
	 * 
	 * @param src
	 * @param mod
	 * @return
	 */
	public static int round(int src, int mod) {
		return round((float) src, mod);
	}

	public static int round(float src, int mod) {
		return Math.round(src / mod) * mod;
	}

	public static long round(double src, int mod) {
		return Math.round(src / mod) * mod;
	}

	public static long round(long src, int mod) {
		return round((double) src, mod);
	}

	/**
	 * MROUND rounds up, away from zero, if the remainder of dividing number by
	 * multiple is greater than or equal to half the value of multiple.
	 * 
	 * @param number
	 *            is the value to round.
	 * @param multiple
	 *            is the multiple to which you want to round number.
	 * @return Returns a number rounded to the desired multiple.
	 */
	public static float mround(double number, float multiple) {
		if (number * multiple < 0)
			throw new IllegalArgumentException("Arguments hava different signs!");
		return Math.round(number / multiple) * multiple;
	}
	
	public static void main(String[] args) {
		float dv1 = 95;
		float dv2 = 100;
		float val = (83.24f - 83)/(85-83);
		System.out.println(lerp(dv1, dv2, val));
		System.out.println(roundLerp(dv1, dv2, val));
		//System.out.println(normalDistribution(dv1, dv2, val));
	}
	
//	public static int normalDistribution(double dv1, double dv2, double val){
//		NormalDistribution normal = new NormalDistribution(dv1, dv2);
//		double p = normal.cumulativeProbability(val*100);
//		System.out.println(p);
//		return (int)p;
//	}
	
	public static float lerp(float d1, float d2, float stage) {
		if (Float.isNaN(stage) || stage > 1) {
			return d2;
		} else if (stage < 0) {
			return d1;
		} else {
			return d1 * (1 - stage) + d2 * stage;
		}
	}
	
	public static int roundLerp(float d1, float d2, float stage) {
		return (int)Math.round(lerp(d1, d2, stage));
	}
	
}
