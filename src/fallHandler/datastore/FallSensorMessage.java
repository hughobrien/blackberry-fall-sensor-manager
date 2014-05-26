package fallHandler.datastore;

import fallHandler.bytesource.ByteSource;

/**
 * FallSensorMessage decomposes the message by first extracting the header and
 * then determining the message type and passing the bytes onto the
 * corresponding message type constructor.
 * 
 * @author Hugh O'Brien
 */
public final class FallSensorMessage {

	/**
	 * Reference to the processed header.
	 */
	private FallSensorHeader header;

	/**
	 * Decompose the message by first extracting the header and then determining
	 * the message type and passing the bytes onto the corresponding message
	 * type constructor.
	 * 
	 * @param rawData Input byte[].
	 * @param byteSource Object reference to the ByteSource that created these
	 *            bytes.
	 */
	FallSensorMessage(int[] rawData, ByteSource byteSource) {

		/* process the header */
		header = new FallSensorHeader(rawData);

		/* log the source ID */
		((SourceList) DataStore.getSourceList())
			.addSource(header.source, byteSource);

		/* determine the type and dispatch it to the right handler. */
		switch (header.type) {

			case FallSensorHeader.HEADERMSG_TYPE:
				break; // header only message, do nothing.

			case CasualMessage.CASUALMSG_TYPE:
				new CasualMessage(header, messageWithoutHeader(rawData));
				break;

			case MobilityMessage.MOBILITYMSG_TYPE:
				new MobilityMessage(header, messageWithoutHeader(rawData));
				break;

			case FallAlertMessage.FALLALERTMSG_TYPE:
				new FallAlertMessage(header, messageWithoutHeader(rawData));
				break;

			default:
				DataStore.increment(Records.errors.type);

		}

	}

	/**
	 * Returns the same byte array as passed but without the first
	 * HEADERMSG_LENGTH bytes
	 * 
	 * @param rawData Input Bytes
	 * @return Bytes without header
	 */
	public static int[] messageWithoutHeader(int[] rawData) {

		int[] message =
			new int[rawData.length - FallSensorHeader.HEADERMSG_LENGTH];
		int position = 0;
		for (int i = FallSensorHeader.HEADERMSG_LENGTH; i < rawData.length; i++) {
			message[position++] = rawData[i];
		}

		return message;

	}
}
