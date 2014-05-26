package fallHandler.gui;
//package fallHandler.gui;
//
//import net.rim.device.api.ui.Field;
//import net.rim.device.api.ui.FieldChangeListener;
//import net.rim.device.api.ui.UiApplication;
//import net.rim.device.api.ui.component.ButtonField;
//import net.rim.device.api.ui.component.LabelField;
//import net.rim.device.api.ui.container.MainScreen;
//import fallHandler.datastore.DataStore;
//import fallHandler.test.RunTests;
//
///**
// * Provide a simple way to view the entire contents of the DataStore.
// * 
// * @author Hugh O'Brien
// */
//public class DataStoreDumpScreen extends MainScreen {
//
//	/** Run Tests Button. */
//	final ButtonField runTests = new ButtonField("Run Unit Tests");
//	/** Show DataStore output button. */
//	final ButtonField showDS = new ButtonField("Show DataStore");
//
//	/**
//	 * Assign listeners to the buttons and add them to the screen.
//	 */
//	public DataStoreDumpScreen() {
//
//		setTitle(new LabelField("DataStore Contents", Field.FIELD_HCENTER));
//
//		showDS.setChangeListener(showDSAction);
//		add(showDS);
//
//		runTests.setChangeListener(runTestsAction);
//		add(runTests);
//	}
//
//	/**
//	 * Create a new anonymous screen that just shows the DataStore output.
//	 */
//	final FieldChangeListener showDSAction = new FieldChangeListener() {
//
//		public void fieldChanged(Field field, int context) {
//
//			UiApplication.getUiApplication().pushScreen(new MainScreen() {
//
//				{
//					add(new LabelField(DataStore.getEntries()));
//				}
//			});
//
//		}
//	};
//
//	/**
//	 * Run the test suite and add a message to the screen indicating completion.
//	 */
//	final FieldChangeListener runTestsAction = new FieldChangeListener() {
//
//		public void fieldChanged(Field field, int context) {
//
//			RunTests.runAll();
//			add(new LabelField("\nTests Run"));
//		}
//	};
//
//}
