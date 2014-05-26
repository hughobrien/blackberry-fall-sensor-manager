package fallHandler.bytesource;
//package fallHandler.bytesource;
//
//import java.io.IOException;
//import net.rim.device.api.bluetooth.*;
//
//import fallHandler.datastore.MessageVerifier;
//
///**
// * Class to open a bluetooth client socket to the specified bluetooth target and
// * pass all received data to a message verifier.
// * 
// * @author Hugh O'Brien
// */
//public final class RIMBTByteSource extends ByteSource implements
//	BluetoothSerialPortListener {
//
//	/** Size of the receive Bluetooth buffer. */
//	public static final int RX_BUFFERSIZE = 1024;
//	/** Size of the transmit Bluetooth buffer */
//	public static final int TX_BUFFERSIZE = 1024;
//
//	/** Reference to the serial port */
//	private BluetoothSerialPort btPort;
//
//	/** Indicates if the port is currently operational */
//	private boolean isConnected = false;
//
//	/** Personal MessageVerifier */
//	private MessageVerifier verifier = new MessageVerifier(this);
//
//	/** Buffer for received bytes. */
//	private byte[] rxBuffer = new byte[RX_BUFFERSIZE];
//
//	/**
//	 * Construct a new socket and automatically begin processing received data.
//	 * 
//	 * @param target Target bluetooth device to connect to.
//	 * @throws IOException Indicates a bad connection.
//	 */
//
//	public RIMBTByteSource(BluetoothSerialPortInfo target) throws IOException {
//
//		/* create the connection with a standard 8N1 protocol setup */
//		btPort =
//			new BluetoothSerialPort(target, BluetoothSerialPort.BAUD_230400,
//				BluetoothSerialPort.DATA_FORMAT_DATA_BITS_8
//					| BluetoothSerialPort.DATA_FORMAT_PARITY_NONE
//					| BluetoothSerialPort.DATA_FORMAT_STOP_BITS_1,
//				BluetoothSerialPort.FLOW_CONTROL_NONE, RX_BUFFERSIZE,
//				TX_BUFFERSIZE, this);
//	}
//
//	/**
//	 * System call back that indicates that data is ready to be read.
//	 * 
//	 * @param length length of received data (useless metric)
//	 */
//	public void dataReceived(int length) {
//
//		try {
//			int byteCount = btPort.read(rxBuffer);
//
//			for (int i = 0; i < byteCount; i++) {
//				verifier.processByte(rxBuffer[i]);
//			}
//		}
//		/* Bomb out if there's an IOe */
//		catch (IOException e) {
//			System.out.println("Exception Encountered:");
//			e.printStackTrace();
//			System.exit(1);
//		}
//
//	}
//
//	/** System callback to indicate that any data in the TX buffer has been sent */
//	public void dataSent() {
//
//	// TODO handle dataSent call back.
//
//	}
//
//	/**
//	 * Log the device connection in the ByteSourceList
//	 * 
//	 * @param success Status of the connection.
//	 */
//	public void deviceConnected(boolean success) {
//
//		if (success)
//			isConnected = true;
//		else
//			deviceDisconnected();
//
//	}
//
//	/** Called when contact has been lost with the device. */
//	public void deviceDisconnected() {
//
//		isConnected = false;
//
//	}
//
//	/**
//	 * Causes the port to close.
//	 */
//	public void disconnect() {
//
//		deviceDisconnected();
//		btPort.disconnect();
//	}
//
//	/**
//	 * Not used
//	 * 
//	 * @param high Not Used
//	 */
//	public void dtrStateChange(boolean high) {
//
//	/* this is not needed */
//	}
//
//	/**
//	 * Write the passed int[] representing a message to the fall sensor that is
//	 * currently connected. Checksum is automatically calculated.
//	 * 
//	 * @param message Message data to send.
//	 */
//	public void sendToFallSensor(int[] message) {
//
//		if (isConnected) {
//
//			verifyBytes(message);
//			writeChecksum(message);
//
//			byte[] messageBytes = new byte[message.length];
//
//			for (int i = 0; i < messageBytes.length; i++) {
//				messageBytes[i] = (byte) message[i];
//			}
//
//			try {
//				btPort.write(messageBytes);
//			}
//			catch (IOException e) {
//				System.out.println("sendToFallSensor IOe");
//				e.printStackTrace();
//				System.exit(1);
//			}
//
//		}
//		else {
//			System.out
//				.println("sendToFallSensor ignored, device is not connected.");
//		}
//	}
//}
