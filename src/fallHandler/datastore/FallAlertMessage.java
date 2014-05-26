package fallHandler.datastore;

/**
 * FallAlertMessage handles fall alert messages and stores the sequence number
 * of the alerting message in the datastore for the fall handling agent to
 * access.
 * 
 * @author Hugh O'Brien
 */
public final class FallAlertMessage {

	/* Storage for values extracted from the message. */
	/** Source ID of originating Fall Sensor. */
	final int source;
	/** Sequence number of the alerting message */
	final int sequenceNumber;
	/** Type of fall observed */
	final int fallType;

	/**
	 * Construct a fall alert message.
	 * 
	 * @param header Reference the processed header of the message.
	 * @param messageBytes Bytes of the message without the header.
	 */
	FallAlertMessage(FallSensorHeader header, int[] messageBytes) {

		if (messageBytes.length != FALLALERTMSG_LENGTH) {
			throw new IndexOutOfBoundsException(
				"Fall Alert Message: unexpected length.");
		}

		source = header.source;
		sequenceNumber = header.sequenceNumber;
		fallType = messageBytes[BYTEINDEX_FALLTYPE];

		updateDataStore();

	}

	/**
	 * Commit the processed data to the DataStore.
	 */
	private void updateDataStore() {

		DataStore.store(source + Records.prependsrc.status.falldetected, 1);
		DataStore.store(source + Records.prependsrc.fall.falltype, fallType);
		DataStore.store(source + Records.prependsrc.fall.alertseqnum,
			sequenceNumber);

	}

	/** Length of a fall alert message */
	public static final int FALLALERTMSG_LENGTH = 1;
	/** Message ID corresponding to a fall alert message. */
	public static final int FALLALERTMSG_TYPE = 0x03;

	/** Byte index of fall type data in a fall alert message. */
	public static final int BYTEINDEX_FALLTYPE = 0;

	/* Fall type information decoding. */
	/** A generic or otherwise indiscernible fall. */
	public static final int GENERIC_FALL = 0;
	/** A sliding fall. */
	public static final int SLIDING_FALL = 1;
}
