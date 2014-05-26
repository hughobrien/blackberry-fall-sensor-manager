package fallHandler.agent;

import java.util.NoSuchElementException;

import fallHandler.bytesource.ByteSource;
import fallHandler.datastore.DataStore;
import fallHandler.datastore.FallSensorRecord;
import fallHandler.datastore.Observable;
import fallHandler.datastore.Records;
import fallHandler.datastore.SourceList;
import fallHandler.datastore.Observable.Observer;
import fallHandler.datastore.SourceList.SourceListEntry;

/**
 * FallAlertAcknowledger responds to a FallAlert message with a confirmation
 * message containing the sequence number of the original FallAlert message. The
 * class monitors the list of known sources and automatically begins observing
 * their fall alert records.
 * 
 * @author Hugh O'Brien
 */
public class FallAlertAcknowledger implements Observer {
	
//	private static boolean agentIsRunning = false;

	/** List of all known sourceID/bytesource pairs. */
	private SourceListEntry[] knownSources;

	/**
	 * Create a new FallAlertAcknowledger which automatically begins observing.
	 */
	private FallAlertAcknowledger() {

		/* Register on the SourceList, all new devices will be monitored. */
		DataStore.getSourceList().addObserver(this);

		/* Trigger a fake update as an initialiser. */
		update(DataStore.getSourceList());

	}

	/**
	 * Update takes different actions depending on whether the update was
	 * triggered by a FallSensorRecord or by the SourceList. In the latter case
	 * the new Source is observed, in the former, a FallAlertAcknowledgement
	 * message is created and transmitted.
	 * 
	 * @param updatedObject The Observable object that has triggered the update.
	 */
	public void update(Observable updatedObject) {

		/*
		 * We're observing both FallSensorRecords and a SourceList so some
		 * disambiguation is necessary.
		 */
		if (updatedObject instanceof SourceList) {

			knownSources = ((SourceList) updatedObject).getSources();

			/*
			 * For each of the known sources, register as an observer of the
			 * fall.alertseqnum record. This is updated by FallAlertMessage so
			 * is an indicator of when a FallAlertAck is required.
			 */
			for (int i = 0; i < knownSources.length; i++) {
				DataStore.getRecord(knownSources[i].sourceID
					+ Records.prependsrc.fall.alertseqnum).addObserver(this);
			}
		}
		/* FallSensorRecord branch */
		else {
			transmitAcknowledgement(determineSender(),
				((FallSensorRecord) updatedObject).getVal());
		}

	}

	/**
	 * Determine which of the known Sources initiated the fall alert by scanning
	 * through them and testing if their status.falldetected record is high.
	 * This must only be called if knownSources has been correctly initialised.
	 * 
	 * @return The reference to the byte source associated with the sender.
	 */
	private ByteSource determineSender() {

		for (int i = 0; i < knownSources.length; i++) {

			if (DataStore.retrieve(knownSources[i].sourceID
				+ Records.prependsrc.status.falldetected) == 1) {
				return knownSources[i].byteSource;
			}
		}

		/* If we get to here and haven't returned then something is in error. */
		throw new NoSuchElementException();
	}

	/**
	 * Write the FA-ACK message back to the ByteSource that originated the
	 * alert.
	 * 
	 * @param byteSource ByteSource to write to.
	 * @param seqNum Sequence number to include in the ACK message.
	 */
	private void transmitAcknowledgement(ByteSource byteSource, int seqNum) {

		if (seqNum > 0xffff) {
			System.out.println("Impossible sequence number.");
			throw new IndexOutOfBoundsException();
		}

		int seqNoHigh = seqNum & 0x0000FF00;
		int seqNoLow = seqNum & 0x000000FF;

		/*
		 * START_MARKER, MSG_LENGTH, MSG_TYPE, DST, SRC, STATUS, TSHI, TSLO,
		 * CHKSUM, PAYLOAD1, PAYLOAD2, END_MARKER.
		 */
		int[] ackMessage =
			{ 0xfc, (2 + 8), 0xa0, 0, 0, 0, 0, 0, 0, seqNoHigh, seqNoLow, 0xfd };

		/* The checksum will be automatically written by the ByteSource */
		byteSource.sendToFallSensor(ackMessage);

	}

//	/**
//	 * Start the agent, if already started this does nothing.
//	 */
//	public static void startAgent() {
//
//		if (agentIsRunning == false) {
//			new FallAlertAcknowledger(); // TODO maybe thread this?
//		}
//	}
//
//	/**
//	 * Stop the agent, this deregisters any observables and closes any open
//	 * resources.
//	 */
//	public static void stopAgent() {
//
//		if (agentIsRunning) {
////			stopObserving();
//			// TODO do shutdown stuff.
//			agentIsRunning = false;
//		}
//	}
//
//	/**
//	 * Returns a boolean based on whether the agent is active.
//	 * 
//	 * @return True if the agent is running.
//	 */
//	public static boolean isRunning() {
//
//		return agentIsRunning;
//	}
}
