package fallHandler.gui;
//package fallHandler.gui;
//
//import java.io.IOException;
//
//import fallHandler.bytesource.RIMBTByteSource;
//import net.rim.blackberry.api.invoke.Invoke;
//import net.rim.device.api.bluetooth.*;
//import net.rim.device.api.ui.MenuItem;
//import net.rim.device.api.ui.UiApplication;
//import net.rim.device.api.ui.component.RichTextField;
//import net.rim.device.api.ui.container.MainScreen;
//
///**
// * SelectionScreen access the device's list of existing bluetooth pairings and
// * creates a menu option to connect to each of them. The menu object itself
// * holds the URL and passes it to a new instance of GeneralScreen which then
// * permanently becomes the bottom screen.
// * 
// * @author Hugh O'Brien
// */
//
//public class SensorSelectionScreen extends MainScreen {
//
//	public SensorSelectionScreen() {
//
//		setTitle(new RichTextField("Device Selection", FIELD_HCENTER
//			| NON_FOCUSABLE));
//
//		// Remove any default menu items
//		removeAllMenuItems();
//
//		// User instructions
//		add(new RichTextField(
//			"\n\r\n\rChoose the target device from the menu.\n\r\n\r"
//				+ "If no devices are listed, first pair the "
//				+ "target device in the Bluetooth configuration "
//				+ "screen accessbile from the menu.\n\r"
//				+ "You must restart the application before any "
//				+ "new devices will become visible\n\r"
//				+ "\n\rAlso, please ensure that Bluetooth is turned on.",
//			NON_FOCUSABLE));
//
//		// Allow user to open the Bluetooth Configuration Menu
//		addMenuItem(new ConfigBTMenu());
//
//		addConnectMenus();
//
//	}
//
//	private void addConnectMenus() {
//
//		// Get the list of device pairings
//		BluetoothSerialPortInfo[] knownDevicesList =
//			BluetoothSerialPort.getSerialPortInfo();
//
//		// Array to hold all the potential connect menu items
//		ConnectMenuItem deviceItems[] =
//			new ConnectMenuItem[knownDevicesList.length];
//
//		// Create and add a MenuItem for each Bluetooth device
//		for (int i = 0; i < knownDevicesList.length; i++) {
//			deviceItems[i] = new ConnectMenuItem(knownDevicesList[i]);
//			addMenuItem(deviceItems[i]);
//		}
//	}
//
//	/**
//	 * ConnectMenuItem is a regular MenuItem with an extra field to hold info on
//	 * the paired device associated with that menu option. Its run section
//	 * determines the friendly name of the target, passes this information to a
//	 * new GeneralScreen which is pushed to the top of the stack. It then
//	 * creates a new instance of the Receiver object which immediately opens a
//	 * connection to the passed bluetooth target. Once Receiver receives
//	 * information that causes a state change in the representation of the
//	 * sensor, the GeneralScreen is notified (along with any other observers).
//	 * Finally the SelectionScreen is closed as it is no longer needed and
//	 * should not be reachable by the user.
//	 * 
//	 * @param text text to display on button
//	 * @param portInfo details of bluetooth pairing
//	 */
//	private final class ConnectMenuItem extends MenuItem {
//
//		private final BluetoothSerialPortInfo portInfo;
//
//		ConnectMenuItem(BluetoothSerialPortInfo portInfo) {
//
//			super("Connect to " + portInfo.getDeviceName(), 1, 100);
//			this.portInfo = portInfo;
//		}
//
//		public void run() {
//
//			// Push GeneralScreen
//			UiApplication.getUiApplication().pushScreen(new GeneralScreen());
//
//			// Start the receiver
//			try {
//				new RIMBTByteSource(portInfo);
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//				System.exit(1);
//			}
//
//			// Close SelectionScreen once the user has pressed this button
//			close();
//		}
//	}
//
//	private class ConfigBTMenu extends MenuItem {
//
//		public ConfigBTMenu() {
//
//			super("Configure Bluetooth", 100, 100);
//		}
//
//		public void run() {
//
//			Invoke.invokeApplication(Invoke.APP_TYPE_BLUETOOTH_CONFIG, null);
//		}
//	}
//
//}
