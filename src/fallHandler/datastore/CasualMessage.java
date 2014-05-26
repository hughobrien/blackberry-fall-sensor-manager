package fallHandler.datastore;

/**
 * Casual message contains the user state information (sitting, walking etc) as
 * well as their cadence and number of steps.
 * 
 * @author Hugh O'Brien
 */
public final class CasualMessage {

	/* Storage for values extracted from the message. */

	/** Source ID of originating Fall Sensor. */
	final int source;

	/** State of the user (falling/sitting etc) see Constants. */
	final int state;
	/** Steps per minute. */
	final int cadence;
	/** Number of steps since last message. */
	final int steps;

	/**
	 * Construct a casual message.
	 * 
	 * @param header Reference the processed header of the message.
	 * @param messageBytes Bytes of the message without the header.
	 */
	CasualMessage(FallSensorHeader header, int[] messageBytes) {

		if (messageBytes.length != CASUALMSG_LENGTH) {
			throw new IndexOutOfBoundsException(
				"Casual Message: unexpected length.");
		}

		source = header.source;

		state = messageBytes[BYTEINDEX_STATE];
		cadence = messageBytes[BYTEINDEX_CADENCE];
		steps = messageBytes[BYTEINDEX_STEPS];

		updateDataStore();

	}

	/**
	 * Commit the processed data to the DataStore.
	 */
	private void updateDataStore() {

		DataStore.store(source + Records.prependsrc.mobility.state, state);

		if (state == STATE_FALL) {
			DataStore.store(source + Records.prependsrc.status.falldetected, 1);
		}

		DataStore.store(source + Records.prependsrc.mobility.cadence, cadence);
		/* Note use of addTo */
		DataStore.addTo(source + Records.prependsrc.mobility.steps, steps);

	}

	/** Length of a casual message */
	public static final int CASUALMSG_LENGTH = 3;
	/** Message ID corresponding to a casual activity message. */
	public static final int CASUALMSG_TYPE = 1;

	/* Casual message index information */
	/** Byte index of state data in a casual message. */
	public static final int BYTEINDEX_STATE = 0;
	/** Byte index of cadence data in a casual message. */
	public static final int BYTEINDEX_CADENCE = 1;
	/** Byte index of steps data in a casual message. */
	public static final int BYTEINDEX_STEPS = 2;

	/* State information decoding */
	/** Identification code for running state. */
	public static final int STATE_RUNNING = 0;
	/** Identification code for walking state. */
	public static final int STATE_WALKING = 1;
	/** Identification code for standing state. */
	public static final int STATE_STANDING = 2;
	/** Identification code for sitting state. */
	public static final int STATE_SITTING = 3;
	/** Identification code for lying state. */
	public static final int STATE_LYING = 4;
	/** Identification code for fall state. */
	public static final int STATE_FALL = 5;

}
