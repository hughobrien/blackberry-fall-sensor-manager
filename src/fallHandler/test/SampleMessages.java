package fallHandler.test;

import fallHandler.datastore.FallSensorHeader;
import fallHandler.datastore.MessageVerifier;
import fallHandler.datastore.MobilityMessage;

/**
 * Some sample messages, both error free and error riddled to use when testing.
 * 
 * @author Hugh O'Brien
 */
public final class SampleMessages {

	/**
	 * Valid mobility message with the following status bits high: sensorInUse
	 * signalLow onMainsPower.
	 */
	public static int[] goodMobilityMsg =
		{
			MessageVerifier.MSG_MARK_START,
			FallSensorHeader.HEADERMSG_LENGTH
				+ MobilityMessage.MOBILITYMSG_LENGTH,
			MobilityMessage.MOBILITYMSG_TYPE,
			0x00, // destination
			0x00, // source
			0x4a, // status
			0x00, // tsHigh
			0x00, // tsLow
			0x00, // checksum
			// end of header
			0x01, // cadence
			0x02, // steps
			0x03, // gaitphase
			MobilityMessage.SUPPORT_NONE,
			0x05, // swing phase time
			0x06, // stance phase time
			0x07, // double support time
			0x08, // step length
			MessageVerifier.MSG_MARK_END // end of message
		};

	/**
	 * Valid mobility message with the following status bits high: sensorInUse
	 * signalLow onMainsPower, byte form without markers.
	 */
	public static int[] altMobilityMsg =
		{
			FallSensorHeader.HEADERMSG_LENGTH
				+ MobilityMessage.MOBILITYMSG_LENGTH,
			MobilityMessage.MOBILITYMSG_TYPE,
			0x00, // destination
			0x00, // source
			0x4a, // status
			0x00, // tsHigh
			0x00, // tsLow
			0x00, // checksum
			// end of header
			0x01, // cadence
			0x02, // steps
			0x03, // gaitphase
			MobilityMessage.SUPPORT_DOUBLE,
			0x05, // swing phase time
			0x06, // stance phase time
			0x07, // double support time
			0x08, // step length
		};

}
