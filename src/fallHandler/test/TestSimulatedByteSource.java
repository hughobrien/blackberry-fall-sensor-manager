package fallHandler.test;

import fallHandler.bytesource.SimulatedByteSource;
import fallHandler.datastore.MessageVerifier;

/**
 * Test the error catching features of SimulatedByteSource.
 * 
 * @author Hugh O'Brien
 */
public final class TestSimulatedByteSource {

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testSimulatedByteSourceBadMsgVals();
		testSimulatedByteSourceMsgTooShort();
		testSimulatedByteSourceGoodMsg();
	}

	/**
	 * Send a message that is too short to have it's checksum/sequence numbers
	 * auto computed.
	 */
	public static void testSimulatedByteSourceMsgTooShort() {

		SimulatedByteSource byteSource = new SimulatedByteSource();
		int[] msg =
			{
				MessageVerifier.MSG_MARK_START,
				0xff,
				0xff,
				MessageVerifier.MSG_MARK_END };
		String expectedValue = "Message shorter than a header";
		String receivedValue = "";

		try {
			byteSource.sendMessageAuto(msg);
		}
		catch (IndexOutOfBoundsException e) {
			receivedValue = e.getMessage();
		}

		System.out.println("testSimulatedByteSourceMsgTooShort "
			+ (expectedValue.equals(receivedValue) ? "passed." : "failed."));
	}

	/**
	 * Send a message with a value that exceeds the range of a byte.
	 */
	public static void testSimulatedByteSourceBadMsgVals() {

		SimulatedByteSource byteSource = new SimulatedByteSource();
		int[] msg =
			{
				MessageVerifier.MSG_MARK_START,
				0xff,
				0xf00,
				0xff,
				0xff,
				0xff,
				0xff,
				0xff,
				MessageVerifier.MSG_MARK_END };
		String expectedValue =
			"Value at index 2 is outside of the range of a ubyte";
		String receivedValue = "";

		try {
			byteSource.sendMessageAuto(msg);
		}
		catch (IndexOutOfBoundsException e) {
			receivedValue = e.getMessage();
		}

		System.out.println("testSimulatedByteSourceBadMsgVals "
			+ (expectedValue.equals(receivedValue) ? "passed." : "failed."));
	}

	/**
	 * Send a good message to ensure that the error catching is not causing
	 * problems.
	 */
	public static void testSimulatedByteSourceGoodMsg() {

		SimulatedByteSource byteSource = new SimulatedByteSource();
		boolean noExceptions = true;

		try {
			byteSource.sendMessageAuto(SampleMessages.goodMobilityMsg);
		}
		catch (Exception e) {
			noExceptions = false;
		}

		System.out.println("testSimulatedByteSourceGoodMsg "
			+ (noExceptions ? "passed." : "failed."));
	}

}
