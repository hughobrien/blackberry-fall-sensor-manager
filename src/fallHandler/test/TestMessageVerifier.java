package fallHandler.test;

import fallHandler.bytesource.SimulatedByteSource;
import fallHandler.datastore.DataStore;
import fallHandler.datastore.FallSensorHeader;
import fallHandler.datastore.MessageVerifier;
import fallHandler.datastore.Records;

/**
 * Test the MessageVerifier class for error detection and handling.
 * 
 * @author Hugh O'Brien
 */
public final class TestMessageVerifier {

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testGoodChkSum();
		testBadChkSum();
		testNoStartMarker();
		testNoEndMarker();
		testNoMarkers();
		testMsgTooLong();
		testMsgTooShort();
		testMsgAtMax();
		testLengthTooLong();
		testLengthTooShort();

	}

	/**
	 * Test that messages with good checksums are passed.
	 */
	private static void testGoodChkSum() {

		int[] msg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				0,
				0,
				0,
				0,
				FallSensorHeader.HEADERMSG_LENGTH, // checksum value
				MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();

		int expectedErrors = DataStore.retrieve(Records.errors.checksum);

		byteSource.sendMessageManual(msg);

		int actualErrors = DataStore.retrieve(Records.errors.checksum);

		System.out.println("testMessageVerifierGoodChkSum "
			+ ((expectedErrors == actualErrors) ? "passed." : "failed."));

	}

	/**
	 * Test that messages with bad checksums are discarded and logged.
	 */
	private static void testBadChkSum() {

		int[] msg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				0,
				0,
				0,
				0,
				0xff, // a bad checksum value
				MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();

		int expectedErrors = DataStore.retrieve(Records.errors.checksum) + 1;

		byteSource.sendMessageManual(msg);

		int actualErrors = DataStore.retrieve(Records.errors.checksum);

		System.out.println("testMessageVerifierBadChkSum "
			+ ((expectedErrors == actualErrors) ? "passed." : "failed."));

	}

	/**
	 * Test that if a message does not include a start marker that it does not
	 * block subsequent good messages.
	 */
	private static void testNoStartMarker() {

		int source = 40;

		int[] msg = {
		// no start marker
			FallSensorHeader.HEADERMSG_LENGTH,
			FallSensorHeader.HEADERMSG_TYPE,
			0,
			source,
			0,
			0,
			0,
			0x20,
			MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();

		byteSource.sendMessageManual(msg);

		SampleMessages.goodMobilityMsg[4] = source;

		byteSource.sendMessageAuto(SampleMessages.goodMobilityMsg);

		int expectedValue = 1; // cadence value from sample message

		int receivedValue =
			DataStore.retrieve(source + Records.prependsrc.mobility.cadence);

		System.out.println("testMessageVerifierNoStartMarker "
			+ ((expectedValue == receivedValue) ? "passed." : "failed."));

	}

	/**
	 * Test that if a message does not include an end marker that it does not
	 * block subsequent good messages.
	 */
	private static void testNoEndMarker() {

		int source = 41;

		int[] msg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				source,
				0,
				0,
				0,
				0x20
			// no end marker
			};

		SimulatedByteSource byteSource = new SimulatedByteSource();

		byteSource.sendMessageManual(msg);

		SampleMessages.goodMobilityMsg[4] = source;

		byteSource.sendMessageAuto(SampleMessages.goodMobilityMsg);

		int expectedValue = 1; // cadence value from sample message

		int receivedValue =
			DataStore.retrieve(source + Records.prependsrc.mobility.cadence);

		System.out.println("testMessageVerifierNoEndMarker "
			+ ((expectedValue == receivedValue) ? "passed." : "failed."));

	}

	/**
	 * Test that a message with no markers at all does not block future good
	 * messages.
	 */
	private static void testNoMarkers() {

		int source = 42;

		int[] msg = {
		// no start marker
			FallSensorHeader.HEADERMSG_LENGTH,
			FallSensorHeader.HEADERMSG_TYPE,
			0,
			source,
			0,
			0,
			0,
			0x20
		// no end marker
			};

		SimulatedByteSource byteSource = new SimulatedByteSource();

		byteSource.sendMessageManual(msg);

		SampleMessages.goodMobilityMsg[4] = source;

		byteSource.sendMessageAuto(SampleMessages.goodMobilityMsg);

		int expectedValue = 1; // cadence value from sample message

		int receivedValue =
			DataStore.retrieve(source + Records.prependsrc.mobility.cadence);

		System.out.println("testMessageVerifierNoMarkers "
			+ ((expectedValue == receivedValue) ? "passed." : "failed."));

	}

	/**
	 * Test detection of overly long messages.
	 */
	private static void testMsgTooLong() {

		int source = 30;
		int[] msg = new int[MessageVerifier.MSG_LENGTH_MAX + 2 + 1];
		// +2 for the markers

		for (int i = 0; i < msg.length; i++) {
			msg[i] = 0;
		}

		msg[0] = MessageVerifier.MSG_MARK_START;
		msg[1] = MessageVerifier.MSG_LENGTH_MAX + 1;
		msg[2] = 0xff; // an invalid msg type
		msg[4] = source;
		msg[8] = 0x10; // valid checksum
		msg[msg.length - 1] = MessageVerifier.MSG_MARK_END;

		int expectedErrors = DataStore.retrieve(Records.errors.length) + 1;

		SimulatedByteSource byteSource = new SimulatedByteSource();
		byteSource.sendMessageManual(msg);

		int actualErrors = DataStore.retrieve(Records.errors.length);

		System.out.println("testMessageVerifierMsgTooLong "
			+ ((expectedErrors == actualErrors) ? "passed." : "failed."));

	}

	/**
	 * Test detection of messages that are too short.
	 */
	private static void testMsgTooShort() {

		int source = 31;
		int[] msg = new int[MessageVerifier.MSG_LENGTH_MIN + 2 - 1];// +2 for
		// marks

		for (int i = 0; i < msg.length; i++) {
			msg[i] = 0;
		}

		msg[0] = MessageVerifier.MSG_MARK_START;
		msg[1] = MessageVerifier.MSG_LENGTH_MIN - 1;
		msg[2] = 0xff; // an invalid msg type
		msg[4] = source;
		// checksum field truncated
		msg[msg.length - 1] = MessageVerifier.MSG_MARK_END;

		int expectedErrors = DataStore.retrieve(Records.errors.length) + 1;

		SimulatedByteSource byteSource = new SimulatedByteSource();
		byteSource.sendMessageManual(msg);

		int actualErrors = DataStore.retrieve(Records.errors.length);

		System.out.println("testMessageVerifierMsgTooShort "
			+ ((expectedErrors == actualErrors) ? "passed." : "failed."));

	}

	/**
	 * Test handling of maximum length messages.
	 */
	private static void testMsgAtMax() {

		int source = 32;
		int[] msg = new int[MessageVerifier.MSG_LENGTH_MAX + 2]; // +2 for marks

		for (int i = 0; i < msg.length; i++) {
			msg[i] = 0;
		}

		msg[0] = MessageVerifier.MSG_MARK_START;
		msg[1] = MessageVerifier.MSG_LENGTH_MAX;
		msg[2] = 0xff; // an invalid msg type
		msg[4] = source;
		msg[8] = 0x2f; // valid checksum
		msg[msg.length - 1] = MessageVerifier.MSG_MARK_END;

		int expectedLengthErrors = DataStore.retrieve(Records.errors.length);

		/* if we get a type error then the msg made it to the handler */
		int expectedTypeErrors = DataStore.retrieve(Records.errors.type) + 1;

		SimulatedByteSource byteSource = new SimulatedByteSource();
		byteSource.sendMessageManual(msg);

		int actualLengthErrors = DataStore.retrieve(Records.errors.length);

		int actualTypeErrors = DataStore.retrieve(Records.errors.type);

		System.out.println("testMessageVerifierMsgAtMax1 "
			+ ((expectedLengthErrors == actualLengthErrors) ? "passed."
				: "failed."));

		System.out
			.println("testMessageVerifierMsgAtMax2 "
				+ ((expectedTypeErrors == actualTypeErrors) ? "passed."
					: "failed."));

	}

	/**
	 * Test the detection of a length field that is larger than in reality.
	 */
	private static void testLengthTooLong() {

		int source = 50;

		int[] badMsg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH + 1,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				source,
				FallSensorHeader.STATUSMASK_FALLDETECTED,
				0,
				0,
				0,
				MessageVerifier.MSG_MARK_END };

		int[] goodMsg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				source,
				FallSensorHeader.STATUSMASK_BATTERYLOW,
				0,
				0,
				0,
				MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();
		byteSource.sendMessageAuto(badMsg);

		int fallDetected =
			DataStore.retrieve(source + Records.prependsrc.status.falldetected);

		/* msg was bad so fall should not have been detected */
		System.out.println("testMessageVerifierLengthTooLong1 "
			+ ((fallDetected == 0) ? "passed." : "failed."));

		byteSource.sendMessageAuto(goodMsg);

		int batteryLow =
			DataStore.retrieve(source + Records.prependsrc.status.batterylow);

		/* this msg was good so should have been processed */
		System.out.println("testMessageVerifierLengthTooLong2 "
			+ ((batteryLow == 1) ? "passed." : "failed."));

	}

	/**
	 * Test the detection of a length field that is shorter than in reality.
	 */
	private static void testLengthTooShort() {

		int source = 51;

		int[] badMsg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH - 1,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				source,
				FallSensorHeader.STATUSMASK_CARDFULL,
				0,
				0,
				0,
				MessageVerifier.MSG_MARK_END };

		int[] goodMsg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH,
				FallSensorHeader.HEADERMSG_TYPE,
				0,
				source,
				FallSensorHeader.STATUSMASK_ONMAINSPOWER,
				0,
				0,
				0,
				MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();
		byteSource.sendMessageAuto(badMsg);

		int cardFull =
			DataStore.retrieve(source + Records.prependsrc.status.cardfull);

		/* msg was bad so cardFull should not have been detected */
		System.out.println("testMessageVerifierLengthTooShort1 "
			+ ((cardFull == 0) ? "passed." : "failed."));

		byteSource.sendMessageAuto(goodMsg);

		int mainsPower =
			DataStore.retrieve(source + Records.prependsrc.status.onmainspower);

		/* this msg was good so should have been processed */
		System.out.println("testMessageVerifierLengthTooShort2 "
			+ ((mainsPower == 1) ? "passed." : "failed."));
	}
}
