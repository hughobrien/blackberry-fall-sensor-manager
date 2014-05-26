package fallHandler.gui;
//package fallHandler.gui;
//
//import fallHandler.agent.FallAlertAcknowledger;
//import fallHandler.agent.FallHandler;
//import fallHandler.datastore.DataStore;
//import fallHandler.datastore.Options;
//import fallHandler.datastore.Records;
//import fallHandler.test.RunTests;
//import net.rim.device.api.ui.UiApplication;
//
///**
// * Displays a number of options to the user, System settings, patient view,
// * clinician view...
// * 
// * @author Hugh O'Brien
// */
//public class OpeningScreen extends UiApplication {
//
//	public OpeningScreen() {
//
//		loadAgents();
//		pushScreen(new SensorSelectionScreen());
//	}
//
//	/**
//	 * Standard Blackberry initialisation.
//	 * 
//	 * @param args Ignored.
//	 */
//	public static void main(String[] args) {
//
//		if (args.length > 0 && args[0].equalsIgnoreCase("test")) {
//			RunTests.runAll();
//			System.exit(0);
//		}
//
//		OpeningScreen app = new OpeningScreen();
//		app.enterEventDispatcher();
//	}
//
//	/**
//	 * Scan the options db to determine which agents to load at run time and run
//	 * them.
//	 */
//	private void loadAgents() {
//
//		/*
//		 * For each agent determine if their option value is set to load on
//		 * default by comparing it against 'yes' and if it is, start the agent.
//		 */
//		if (DataStore.getOption(Records.options.loadfallhandler).equalsIgnoreCase("yes")) {
//			FallHandler.startAgent();
//		}
//		
//		if (DataStore.getOption(Records.options.loadfallalertacknowledger).equalsIgnoreCase("yes")){
//			FallAlertAcknowledger.startAgent();
//		}
//
//		if (DataStore.getOption(Records.options.loadactivitylogger).equalsIgnoreCase("yes")){
//			// new activity logger
//		}
//	}
//
//}
