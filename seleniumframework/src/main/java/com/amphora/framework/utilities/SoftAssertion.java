package com.amphora.framework.utilities;

import org.testng.asserts.SoftAssert;

public class SoftAssertion {
	private static SoftAssert softAssert;

	public SoftAssertion() {
		softAssert = new SoftAssert();
	}

	/**
	 * If expected and actual values are not matched then register soft failure,
	 * else nothing
	 * 
	 * @param expected
	 *            - expected value
	 * @param actual
	 *            - actual value
	 * @param message
	 *            - message to display if expected and actual values are not matched
	 */
	public static void assertEquals(String expected, String actual, String message) {
		softAssert.assertEquals(actual, expected, message);
	}

	/**
	 * If boolean state is false then register soft failure, else nothing
	 * 
	 * @param state
	 *            - actual value
	 * @param message
	 *            - message to display boolean value is false
	 */
	public static void assertTrue(boolean state, String message) {
		softAssert.assertTrue(state, message);
	}

	/**
	 * If boolean state is true then register soft failure, else nothing
	 * 
	 * @param state
	 *            - actual value
	 * @param message
	 *            - message to display boolean value is true
	 */
	public static void assertFalse(boolean state, String message) {
		softAssert.assertFalse(state, message);
	}

	/**
	 * If actual does not contains expected string then register soft failure, else
	 * nothing
	 * 
	 * @param expected
	 *            - expected value
	 * @param actual
	 *            - actual value
	 * @param message
	 *            - message to display if actual value does not contain expected
	 *            value
	 */
	public static void assertContains(String expected, String actual, String message) {
		softAssert.assertTrue(actual.contains(expected), message);
	}

	/**
	 * Throw all failed soft assertions and stops program execution
	 */
	public static void assertAllSoftAssertions() {
		softAssert.assertAll();
	}

}
