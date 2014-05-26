package fallHandler.datastore;

/**
 * MobilityMessage handles the specifics for messages of type 'Mobility'
 * 
 * @author Hugh O'Brien
 */
public final class MobilityMessage {

	/** Which sensor sent the message. */
	final int source;

	/** Current Cadence. */
	final int cadence;
	/** Steps since last message. */
	final int steps;
	/** GaitPhase. */
	final int gaitPhase;
	/** Current limb support. */
	final int support;
	/** Time spent in SwingPhase. */
	final int swingPhaseTime;
	/** Time spent in StancePhase. */
	final int stancePhaseTime;
	/** Time spent with DoubleSupport. */
	final int doubleSupportTime;
	/** Average StepLength. */
	final int StepLength;

	/**
	 * Construct a mobility message.
	 * 
	 * @param header Reference to processed header of the message.
	 * @param messageBytes Bytes of the message without the header.
	 */
	MobilityMessage(FallSensorHeader header, int[] messageBytes) {

		if (messageBytes.length != MOBILITYMSG_LENGTH) {
			throw new IndexOutOfBoundsException(
				"Mobility Message: unexpected length.");
		}

		source = header.source;

		cadence = messageBytes[BYTEINDEX_CADENCE];
		steps = messageBytes[BYTEINDEX_STEPS];
		gaitPhase = messageBytes[BYTEINDEX_GAITPHASE];
		support = messageBytes[BYTEINDEX_SUPPORT];

		swingPhaseTime = messageBytes[BYTEINDEX_SWINGPHASETIME];
		stancePhaseTime = messageBytes[BYTEINDEX_STANCEPHASETIME];
		doubleSupportTime = messageBytes[BYTEINDEX_DOUBLESUPPORTTIME];
		StepLength = messageBytes[BYTEINDEX_STEPLENGTH];

		updateDataStore();

	}

	/**
	 * Commit the parsed data to the DataStore.
	 */
	private void updateDataStore() {

		DataStore.store(source + Records.prependsrc.mobility.cadence, cadence);

		/* note: Uses addTo */
		DataStore.addTo(source + Records.prependsrc.mobility.steps, steps);

		DataStore.store(source + Records.prependsrc.mobility.gaitphase,
			gaitPhase);

		DataStore.store(source + Records.prependsrc.mobility.support, support);

		DataStore.store(source + Records.prependsrc.mobility.swingphasetime,
			swingPhaseTime);

		DataStore.store(source + Records.prependsrc.mobility.stancephasetime,
			stancePhaseTime);

		DataStore.store(source + Records.prependsrc.mobility.doublesupporttime,
			doubleSupportTime);

		DataStore.store(source + Records.prependsrc.mobility.steplength,
			StepLength);

	}

	/** Mobility message length. */
	public static final int MOBILITYMSG_LENGTH = 8;
	/** Message ID corresponding to a mobility message. */
	public static final int MOBILITYMSG_TYPE = 0x02;

	/** Byte index of cadence data in a mobility message. */
	public static final int BYTEINDEX_CADENCE = 0;
	/** Byte index of the number of steps taken in a mobility message. */
	public static final int BYTEINDEX_STEPS = 1;
	/** Byte index of gaitPhase data in a mobility message. */
	public static final int BYTEINDEX_GAITPHASE = 2;
	/** Byte index of support data in a mobility message. */
	public static final int BYTEINDEX_SUPPORT = 3;
	/** Byte index of swingPhaseTime data in a mobility message. */
	public static final int BYTEINDEX_SWINGPHASETIME = 4;
	/** Byte index of stancePhaseTime data in a mobility message. */
	public static final int BYTEINDEX_STANCEPHASETIME = 5;
	/** Byte index of doubleSupportTime data in a mobility message. */
	public static final int BYTEINDEX_DOUBLESUPPORTTIME = 6;
	/** Byte index of stepLength data in a mobility message. */
	public static final int BYTEINDEX_STEPLENGTH = 7;

	/* Gait phase decoding */
	/** Value indicating the swing gaitphase */
	public static final int GAITPHASE_SWING = 0;
	/** Value indicating the stance gaitphase. */
	public static final int GAITPHASE_STANCE = 1;

	/* Limb support decoding */
	/** Value indicating single limb support. */
	public static final int SUPPORT_SINGLE = 1;
	/** Value indicating double limb support. */
	public static final int SUPPORT_DOUBLE = 2;
	/** Value indicating sitting limb support. */
	public static final int SUPPORT_SITTING = 3;
	/** Value indicating no support. */
	public static final int SUPPORT_NONE = 4;
}
