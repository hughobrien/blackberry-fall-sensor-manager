package fallHandler.gui;
//package fallHandler.gui;
//
//import fallHandler.datastore.DataStore;
//import fallHandler.datastore.Records;
//import fallHandler.datastore.SourceList;
//import fallHandler.datastore.SourceList.SourceListEntry;
//import net.rim.device.api.ui.MenuItem;
//import net.rim.device.api.ui.UiApplication;
//import net.rim.device.api.ui.component.Dialog;
//import net.rim.device.api.ui.component.LabelField;
//import net.rim.device.api.ui.component.RichTextField;
//import net.rim.device.api.ui.container.MainScreen;
//
///**
// * GeneralScreen gives an overview of the current state as reported by the
// * device.
// * 
// * @author Hugh O'Brien obrien.hugh@gmail.com
// */
//public class GeneralScreen extends MainScreen {
//
//	/**
//	 * Constructor should be passed the name of the target device.
//	 * 
//	 * @param targetName Friendly name of the target device. See
//	 *            Modules.setTargetName()
//	 */
//	public GeneralScreen() {
//
//		// Set the screen name
//		setTitle(new LabelField("General Info: Unknown", FIELD_HCENTER
//			| NON_FOCUSABLE));
//
//		add(new RichTextField("\n\r\n\r"
//			+ "No information has yet been received."
//			+ "\n\r\n\rIf this message is present for a "
//			+ "long period of time it is likely that "
//			+ "the connection has failed to initialise."
//			+ "\n\r\n\rWatch for the blinking blue light "
//			+ "which indicates Bluetooth activity.", NON_FOCUSABLE));
//
//		addMenuItem(pictograph);
//		addMenuItem(dataStore);
//		addMenuItem(closeLink);
//
//	}
//
//	/**
//	 * List of MenuItems used to launch sub-screens. The first int is the menu
//	 * order, smallest items at the top and the second is the thread priority
//	 * (not used). Menu positions > 32 cause a divider to be drawn.
//	 */
//	private final MenuItem pictograph =
//		new MenuItem("Open Pictograph", 10002, 100) {
//
//			public void run() {
//
//				UiApplication.getUiApplication().pushScreen(new PictoScreen());
//			}
//		};
//
//	private final MenuItem dataStore =
//		new MenuItem("View DataStore", 10003, 100) {
//
//			public void run() {
//
//				UiApplication.getUiApplication().pushScreen(
//					new DataStoreDumpScreen());
//			}
//		};
//
//	private final MenuItem closeLink = new MenuItem("Close Link", 20005, 100) {
//
//		public void run() {
//
//			SourceListEntry[] knownSources =
//				((SourceList) DataStore.getSourceList())
//					.getSources();
//
//			for (int i = 0; i < knownSources.length; i++) {
//				knownSources[i].byteSource.disconnect();
//			}
//		}
//	};
//
//	/**
//	 * Override default close command to get user confirmation, this is in
//	 * GeneralScreen as GeneralScreen should always be the screen on the bottom
//	 * of the stack.
//	 */
//	public boolean onClose() {
//
//		int response =
//			Dialog.ask(Dialog.D_YES_NO, "Are you sure you want exit?\n\r\n\r"
//				+ "Use the menu to switch views.");
//
//		if (response == -1)
//			return false;
//		else {
//			close();
//			return true;
//		}
//	}
//}
