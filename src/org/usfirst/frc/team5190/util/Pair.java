package org.usfirst.frc.team5190.util;

/**
 * a storage that can store 2 values of 2 type of variable
 *
 * @author sdai
 *
 * @param <K>
 *            the type of the first variable
 * @param <T>
 *            the type of the second variable
 */
public class Pair<K, T> {
	private K valueOne;
	private T valueTwo;

	/**
	 * creates a empty pair
	 */
	public Pair() {
	}

	/**
	 * create a pair and set the values
	 *
	 * @param v1
	 *            the first value
	 * @param v2
	 *            the second value
	 */
	public Pair(K v1, T v2) {
		valueOne = v1;
		valueTwo = v2;
	}

	/**
	 * copy constructor
	 *
	 * @param src
	 *            the source
	 */
	public Pair(Pair<K, T> src) {
		valueOne = src.valueOne;
		valueTwo = src.valueTwo;
	}

	/**
	 * set the first variable
	 *
	 * @param v1
	 *            the value to set
	 */
	public void setFirst(K v1) {
		valueOne = v1;
	}

	/**
	 * set the second variable
	 *
	 * @param v2
	 *            the value to set
	 */
	public void setSecond(T v2) {
		valueTwo = v2;
	}

	/**
	 * get the first variable
	 *
	 * @return the first variable
	 */
	public K first() {
		return valueOne;
	}

	/**
	 * get the second variable
	 *
	 * @return the second variable
	 */
	public T second() {
		return valueTwo;
	}
}