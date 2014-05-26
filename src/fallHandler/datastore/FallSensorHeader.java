package fallHandler.datastore;

/**
 * FallSensorHeader parses the header data, storing it in the DataStore.
 * 
 * @author Hugh O'Brien
 */
public final class FallSensorHeader {

	/** byte 0 is the total message length minus the start/end markers */
	final int length;
	/** byte 1 is the message type */
	final int type;
	/** byte 2 is the destination handler (not implemented) */
	final int destination;
	/** byte 3 is the source fall sensor */
	final int source;
	/* byte 4 is the status which is handled separately */
	/** bytes 5 and 6 are the high and low segments of the sequence number */
	final int sequenceNumber;
	/** byte 7 is the checksum (stored but not used) */
	final int checkSum;

	/* status bits, DataStore only stores ints so booleans not used */
	/** bit 0 signals if a fall has been detected */
	final int fallDetected;
	/** bit 1 marks if the sensor is being used by the patient */
	final int sensorInUse;
	/** bit 2 indicates if the battery is low */
	final int batteryLow;
	/** bit 3 indicates if the card is full */
	final int cardFull;
	/** bit 4 indicates if the signal strength is low */
	final int signalLow;
	/** bit 5 indicates if a message has gone unacknowledged */
	final int msgMissed;
	/** bit 6 indicates that the sensor is on mains power */
	final int onMainsPower;
	/** bit 7 indicates that the sensor needs to reset it's clock */
	final int requestTime;

	/**
	 * Construct a FallSensorHeader object from a valid stream of bytes from a
	 * fall sensor.
	 * 
	 * @param rawData Input from fall sensor.
	 */
	FallSensorHeader(int[] rawData) {

		/* This shouldn't happen so if it does we let it go uncaught */
		if (rawData.length < HEADERMSG_LENGTH) {
			throw new IndexOutOfBoundsException(
				"FallSensorHeader: Unexpected Length");
		}

		length = rawData[HEADERINDEX_LENGTH];
		type = rawData[HEADERINDEX_TYPE];
		destination = rawData[HEADERINDEX_DST];
		source = rawData[HEADERINDEX_SRC];

		/*
		 * I'm not normally a fan of this expression but it is quite succinct
		 * here. If the bit is set then the received value after masking does
		 * not equal zero so the second parameter is returned (1), otherwise 0
		 * is returned.
		 */
		fallDetected =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_FALLDETECTED) == 0) ? 0
				: 1;
		sensorInUse =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_SENSORINUSE) == 0) ? 0
				: 1;
		batteryLow =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_BATTERYLOW) == 0) ? 0
				: 1;
		cardFull =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_CARDFULL) == 0) ? 0 : 1;
		signalLow =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_SIGNALLOW) == 0) ? 0 : 1;
		msgMissed =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_MSGMISSED) == 0) ? 0 : 1;
		onMainsPower =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_ONMAINSPOWER) == 0) ? 0
				: 1;
		requestTime =
			((rawData[HEADERINDEX_STATUS] & STATUSMASK_REQUESTTIME) == 0) ? 0
				: 1;

		/* Shift the high byte left by 8 places, then sum with the low byte. */
		sequenceNumber =
			(rawData[HEADERINDEX_SEQHI] << 8) + rawData[HEADERINDEX_SEQLO];

		checkSum = rawData[HEADERINDEX_CHKSUM];

		/* Process the data */
		checkSequenceNumbers();
		updateDataStore();

		/* Log this as a valid received message */
		DataStore.getRecord(source + Records.prependsrc.proto.messagecount)
			.increment();

		/* See if we need to log general message information */
		if (DataStore
			.retrieve(source + Records.prependsrc.config.debugprotocol) == 1) {
			logStatistics();
		}

	}

	/**
	 * Log the parsed values to the DataStore.
	 */
	private void updateDataStore() {

		DataStore.store(source + Records.prependsrc.status.falldetected,
			fallDetected);

		DataStore.store(source + Records.prependsrc.status.sensorinuse,
			sensorInUse);

		DataStore.store(source + Records.prependsrc.status.batterylow,
			batteryLow);

		DataStore.store(source + Records.prependsrc.status.cardfull, cardFull);

		DataStore
			.store(source + Records.prependsrc.status.signallow, signalLow);

		DataStore.store(source + Records.prependsrc.status.messagemissed,
			msgMissed);

		DataStore.store(source + Records.prependsrc.status.onmainspower,
			onMainsPower);

		DataStore.store(source + Records.prependsrc.status.requesttime,
			requestTime);

	}

	/**
	 * Compare the received sequence number against the expected one. Log errors
	 * and update the record.
	 */
	private void checkSequenceNumbers() {

		if (DataStore.retrieve(source
			+ Records.prependsrc.proto.expectedsequencenumber) != sequenceNumber) {

			DataStore.increment(source
				+ Records.prependsrc.proto.sequenceerrors);
		}

		/* Store the next expected seq number, with a test for rollover */
		if (sequenceNumber == 0xffff) {
			DataStore.store(source
				+ Records.prependsrc.proto.expectedsequencenumber, 0);
		}
		else {
			DataStore.store(source
				+ Records.prependsrc.proto.expectedsequencenumber,
				sequenceNumber + 1);
		}

	}

	/**
	 * Track the frequency of various message lengths and types.
	 */
	private void logStatistics() {

		DataStore.increment(source + Records.prependsrc.proto.append.lengths
			+ length);

		DataStore.increment(source + Records.prependsrc.proto.append.types
			+ type);
	}

	/** Preset length of the header in the defined protocol. */
	public static final int HEADERMSG_LENGTH = 8;
	/** Message ID corresponding to a header only message. */
	public static final int HEADERMSG_TYPE = 0x00;

	/* Header index information */
	/** Index of the length field in the message header */
	public static final int HEADERINDEX_LENGTH = 0;
	/** Index of the type field in the message header */
	public static final int HEADERINDEX_TYPE = 1;
	/** Index of the destination field in the message header */
	public static final int HEADERINDEX_DST = 2;
	/** Index of the source field in the message header */
	public static final int HEADERINDEX_SRC = 3;
	/** Index of the status field in the message header */
	public static final int HEADERINDEX_STATUS = 4;
	/** Index of the high element of the sequence number field. */
	public static final int HEADERINDEX_SEQHI = 5;
	/** Index of the low element of the sequence number field. */
	public static final int HEADERINDEX_SEQLO = 6;
	/** Index of the checksum field in the message header */
	public static final int HEADERINDEX_CHKSUM = 7;

	/* Status index bitmasks */
	/** Bitmask to determine if a fall has been detected */
	public static final int STATUSMASK_FALLDETECTED = 0x80;
	/** Bitmask to determine if a the sensor is actually in use */
	public static final int STATUSMASK_SENSORINUSE = 0x40;
	/** Bitmask to determine if the sensor's battery is low */
	public static final int STATUSMASK_BATTERYLOW = 0x20;
	/** Bitmask to determine if the sensor's memory card is full */
	public static final int STATUSMASK_CARDFULL = 0x10;
	/** Bitmask to determine if the RSSI is low */
	public static final int STATUSMASK_SIGNALLOW = 0x08;
	/** Bitmask to determine if the sensor believes a message has been missed */
	public static final int STATUSMASK_MSGMISSED = 0x04;
	/** Bitmask to determine if mains power is available */
	public static final int STATUSMASK_ONMAINSPOWER = 0x02;
	/** Bitmask to determine if the sensor requests a time synchronisation */
	public static final int STATUSMASK_REQUESTTIME = 0x01;

}
