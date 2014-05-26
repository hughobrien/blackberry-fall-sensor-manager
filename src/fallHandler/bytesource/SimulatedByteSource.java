package fallHandler.bytesource;

import fallHandler.datastore.FallSensorHeader;
import fallHandler.datastore.MessageVerifier;

/**
 * Provide a system for testing the DataStore system by passing known messages.
 * 
 * @author Hugh O'Brien
 */
public final class SimulatedByteSource extends ByteSource {

	/**
	 * Each ByteSource must be paired with a MessageVerifier for the messages to
	 * actually go anywhere.
	 */
	private MessageVerifier verifier = new MessageVerifier(this);
	/**
	 * Next expected sequence numbers are stored here.
	 */
	private int sequenceNumber;

	/**
	 * Pass an integer array representing the bytes from a message to have
	 * sendMessage dispatch it to a MessageVerifier, checksum and sequence
	 * numbers are automatically modified.
	 * 
	 * @param message Representation of message.
	 */
	public void sendMessageAuto(int[] message) {

		verifyBytes(message);
		writeSequenceNum(message);
		writeChecksum(message);
		/* verify the 'bytes' again just to be sure */
		verifyBytes(message);
		for (int i = 0; i < message.length; i++) {
			verifier.processByte(message[i]);
		}
	}

	/**
	 * Dispatch the passed message without any modifications, checksum and
	 * sequence number must be calculated manually.
	 * 
	 * @param message Manually calculated message to pass.
	 */
	public void sendMessageManual(int[] message) {

		for (int i = 0; i < message.length; i++) {
			verifier.processByte(message[i]);
		}
	}

	/**
	 * Convert the sequence number into high and low bytes and store in the
	 * message, increment when done. (This is normally done on the fall sensor)
	 * 
	 * @param message Message to overwrite sequence numbers in.
	 */
	private void writeSequenceNum(int[] message) {

		if (sequenceNumber > 0xffff) {
			sequenceNumber = 0;
		}
		int high = (sequenceNumber & 0xff00);
		int low = (sequenceNumber & 0x00ff);

		/* +1 to account for start marker */
		message[FallSensorHeader.HEADERINDEX_SEQHI + 1] = high;
		message[FallSensorHeader.HEADERINDEX_SEQLO + 1] = low;

		sequenceNumber++;
	}

	/**
	 * write allows the system to send data back to a fall sensor.
	 * 
	 * @param message The message to reply with. See: message_protocol.txt
	 */
	public void sendToFallSensor(int[] message) {

	// TODO Analyse the data written back.

	}

	public void disconnect() {

	/* do nothing */

	}

}
