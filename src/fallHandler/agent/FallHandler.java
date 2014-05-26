package fallHandler.agent;

//import net.rim.blackberry.api.invoke.Invoke;
//import net.rim.blackberry.api.invoke.PhoneArguments;

import fallHandler.datastore.DataStore;
import fallHandler.datastore.FallSensorRecord;
import fallHandler.datastore.Observable;
//import fallHandler.datastore.Options;
import fallHandler.datastore.Records;
import fallHandler.datastore.SourceList;
import fallHandler.datastore.Observable.Observer;
import fallHandler.datastore.SourceList.SourceListEntry;

/**
 * Fall Handler dials a preset phone number to alert the dialled party of a fall
 * event.
 * 
 * @author Hugh O'Brien
 */
public class FallHandler implements Observer {

	/**
	 * Create a new FallHandler which automatically begins observing.
	 */
	private FallHandler() {

		/* Register on the SourceList, all new devices will be monitored. */
		DataStore.getSourceList().addObserver(this);

		/* Trigger a fake update as an initialiser. */
		update(DataStore.getSourceList());

	}

	/**
	 * Update takes different actions depending on whether the update was
	 * triggered by a FallSensorRecord or by the SourceList. In the latter case
	 * the new Source is observed, in the former, a fall must have occurred so
	 * action is taken.
	 * 
	 * @param updatedObject The Observable object that has triggered the update.
	 */
	public void update(Observable updatedObject) {

		/*
		 * We're observing both FallSensorRecords and a SourceList so some
		 * disambiguation is necessary.
		 */
		if (updatedObject instanceof SourceList) {

			SourceListEntry[] knownSources =
				((SourceList) updatedObject).getSources();

			/*
			 * For each of the known sources, register as an observer of the
			 * status.falldetected record. Duplicate observers are ignored.
			 */
			for (int i = 0; i < knownSources.length; i++) {
				DataStore.getRecord(knownSources[i].sourceID
					+ Records.prependsrc.status.falldetected).addObserver(this);
			}
		}
		/* FallSensorRecord branch, therefore a fall has been detected. */
		else {

			System.out.println("DEBUG: FallHandler notified by "
				+ ((FallSensorRecord) updatedObject).getName());
			// TODO find out why this kept making calls.
			
//			String phNumber = DataStore.getOption(Records.options.numbertodial);

			/*
			 * If this doesn't work it's likely the application permissions need
			 * to be set on the BlackBerry OS
			 */
			
//			Invoke.invokeApplication(Invoke.APP_TYPE_PHONE, new PhoneArguments(
//				PhoneArguments.ARG_CALL, phNumber));

		}

	}

//	/**
//	 * Start the agent, if already started this does nothing.
//	 */
//	public static void startAgent() {
//
//		if (thisAgent == null) {
//			thisAgent = new FallHandler(); // TODO maybe thread this?
//		}
//	}
//
//	/**
//	 * Stop the agent, this deregisters any observables and closes any open
//	 * resources.
//	 */
//	public static void stopAgent() {
//
//		if (thisAgent != null) {
//			stopObserving();
//			// TODO do shutdown stuff.
//			thisAgent = null;
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
//		if (thisAgent == null) {
//			return false;
//		}
//		return true;
//	}
}
