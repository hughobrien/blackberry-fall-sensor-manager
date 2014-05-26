package fallHandler.datastore;

import fallHandler.bytesource.ByteSource;

/**
 * Message Verifier reads in a raw byte stream from a SensorData provider,
 * processes the header and ensures that the message is fully valid. Message
 * Verifier then acts as a source of FallSensorMessages.
 * 
 * @author Hugh O'Brien
 */
public final class MessageVerifier {

	/** Class modelling of an enum for constants used in the state machine. */
	private static final class States {

		/** State indicating that we are awaiting the start of a message. */
		private static final int W4_START_MSG = 0;
		/** State indicating that we are awaiting the message length field. */
		private static final int W4_LENGTH = 1;
		/** State indicating that we are building the message. */
		private static final int BUILD_MSG = 2;
		/** State indicating that we are awaiting the end of message signal. */
		private static final int W4_END_MSG = 3;
	}

	/** Message tracking values */
	private final class MessageValues {

		/** Initial state is to wait for the start of a message. */
		private int state = States.W4_START_MSG;
		/** Storage for the message (protocol data unit). */
		private int[] pdu;
		/** Track our position in the array. */
		private int pduPosition = 0;
		/** The length of the message we are building. */
		private int length = 0;

	}

	/** Create an instance of MessageValues to work with. */
	private final MessageValues msg = new MessageValues();

	/** Reference to the ByteSource that is providing data. */
	private final ByteSource byteSource;

	/**
	 * Construct the messageVerifier by passing it a reference to the ByteSource
	 * that is feeding it.
	 * 
	 * @param byteSource Source of data.
	 */
	public MessageVerifier(ByteSource byteSource) {

		this.byteSource = byteSource;
	}

	/**
	 * Simple wrapper for processByte(int).
	 * 
	 * @param rxByte Byte to process.
	 */
	public void processByte(byte rxByte) {

		processByte(rxByte & 0x000000FF);
	}

	/**
	 * Build a complete message from the received bytes. The state machine has
	 * built in error tolerance if start and end of message markers are used.
	 * (John Nelson)
	 * 
	 * @param rxByte The value received
	 */
	public void processByte(int rxByte) {

		switch (msg.state) {

			/*
			 * If waiting for the start of a message, ignore all packets until a
			 * start of message marker is seen
			 */
			case States.W4_START_MSG:
				if (rxByte == MSG_MARK_START) {
					msg.state = States.W4_LENGTH;
				}
				else {
					DataStore.increment(Records.errors.discards);
				}
				break;

			/*
			 * The next byte is the length of the message, store it for
			 * comparison and also save it as the first byte of the extracted
			 * message.
			 */
			case States.W4_LENGTH:
				msg.length = rxByte; // this byte is the length
				msg.pdu = new int[msg.length]; // create byte buffer for msg
				msg.pduPosition = 0;
				msg.pdu[msg.pduPosition++] = rxByte; // start building

				msg.state = States.BUILD_MSG; // TODO bit stuffing? not needed.

				/*
				 * If the message length violates the protocol then log the
				 * error and go back to waiting for the start of a new message.
				 */
				if (msg.length < MSG_LENGTH_MIN || msg.length > MSG_LENGTH_MAX) {
					DataStore.increment(Records.errors.length);
					DataStore.increment(Records.errors.discards);
					msg.state = States.W4_START_MSG;
				}
				break;

			/*
			 * Keep adding bytes to the extracted message until message length
			 * bytes have been added
			 */
			case States.BUILD_MSG:
				msg.pdu[msg.pduPosition++] = rxByte; // add to the pdu
				if (msg.pduPosition == msg.length) { // test if we're at msg end
					msg.state = States.W4_END_MSG;
				}
				break;

			/*
			 * All announced bytes have now been received so the end of message
			 * marker is expected. If it is received, ensure the checksum of the
			 * message is valid and then dispatch it. Log errors if anything is
			 * not as expected but either way reset the state machine to expect
			 * a start of message marker to begin a new run.
			 */
			case States.W4_END_MSG:
				/*
				 * If the next byte is not the end marker log the error and wait
				 * for a new message otherwise dispatch it.
				 */
				if (rxByte == MSG_MARK_END) {
					if (checksumIsGood(msg.pdu)) {
						new FallSensorMessage(msg.pdu, byteSource);
					}
					else {
						DataStore.increment(Records.errors.checksum);
					}

				}
				else {
					DataStore.increment(Records.errors.discards);
					DataStore.increment(Records.errors.framing);

				}

				/* go back to the beginning */
				msg.state = States.W4_START_MSG;

				/*
				 * unless we already have the start marker, i.e. last message
				 * didn't use a closing marker. (Modification from original
				 * state machine)
				 */
				if (rxByte == MSG_MARK_START)
					msg.state = States.W4_LENGTH;

				break;

			default:
				DataStore.increment(Records.errors.state);
				break;
		}
	}

	/**
	 * Determine if the checksum is correct
	 * 
	 * @param message input message bytes
	 * @return true if the message is valid
	 */
	private static boolean checksumIsGood(int[] message) {

		int checksum = 0;

		for (int i = 0; i < message.length; i++) {
			checksum ^= message[i];
		}
		return (checksum == 0);
	}

	/* Message start and end markers */
	/** Bit sequence representing the start of a message. */
	public static final int MSG_MARK_START = 0xfc;
	/** Bit sequence representing the end of a message. */
	public static final int MSG_MARK_END = 0xfd;

	/* Message length information */
	/** Preset maximum message length in the defined protocol. */
	public static final int MSG_LENGTH_MAX = 240;
	/** Preset minimum message length (one header). */
	public static final int MSG_LENGTH_MIN = FallSensorHeader.HEADERMSG_LENGTH;
}
