package fallHandler.bytesource;

import fallHandler.datastore.FallSensorHeader;
import fallHandler.datastore.MessageVerifier;

/**
 * ByteSource defines useful assistance functions for more specific ByteSoruces
 * to inherit from - the write(int[] input) method must be implemented.
 * 
 * @author Hugh O'Brien
 */
public abstract class ByteSource {

	/**
	 * write allows the system to send data back to a fall sensor.
	 * 
	 * @param message The message to reply with. See: message_protocol.txt
	 */
	public abstract void sendToFallSensor(int[] message);

	/**
	 * Close the port.
	 */
	public abstract void disconnect();

	/**
	 * Ensure that the message array is valid.
	 * 
	 * @param message Potentially invalid message.
	 */
	public void verifyBytes(int[] message) {

		if (message.length < MessageVerifier.MSG_LENGTH_MIN) {
			throw new IndexOutOfBoundsException("Message shorter than a header");
		}
		for (int i = 0; i < message.length; i++) {
			if (message[i] > 0xff || message[i] < 0) {
				throw new IndexOutOfBoundsException("Value at index " + i
					+ " is outside of the range of a ubyte");
			}
		}
	}

	/**
	 * Calculate the checksum of the passed message and write it to the checksum
	 * field - caution: modifies the array it is passed.
	 * 
	 * @param message Message to run checksum on.
	 */
	public void writeChecksum(int[] message) {

		int checkSum = 0;
		message[FallSensorHeader.HEADERINDEX_CHKSUM + 1] = 0;

		/* First and last byte ignored as they're markers */
		for (int i = 1; i < message.length - 1; i++) {
			checkSum ^= message[i];
		}
		message[FallSensorHeader.HEADERINDEX_CHKSUM + 1] = checkSum;
	}

}
