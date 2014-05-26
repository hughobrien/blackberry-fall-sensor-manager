package fallHandler.agent;
//package fallHandler.agent;
//
//import java.util.Vector;
//
//import fallHandler.datastore.Observable;
//import fallHandler.datastore.Observable.Observer;
//
///**
// * Agent is the superclass for the agents that will interact with FallSensor
// * data and take action based on it. It defines start and stop commands as well
// * as a mechanism for deregistering each of the observables.
// * 
// * @author Hugh O'Brien
// */
//public abstract class Agent implements Observer {
//
//	/** Static reference to the singleton instantiation of this Agent. */
//	 Agent thisAgent;
//
//	/**
//	 * Vector containing the Observable objects that are currently set to notify
//	 * this object when updated.
//	 */
//	 Vector beingObserved;
//
//	/**
//	 * Register as an observer of the method argument and log it to the Vector
//	 * containing the list of current observables.
//	 * 
//	 * @param observable The observable to register on.
//	 */
//	 void observe(Observable observable) {
//
//		/* Register on the record */
//		observable.addObserver(thisAgent);
//
//		/* Lazy initialisation for the Vector */
//		if (beingObserved == null) {
//			beingObserved = new Vector();
//		}
//
//		/* Only add unique entries */
//		if (!beingObserved.contains(observable)) {
//			beingObserved.addElement(observable);
//		}
//	}
//
//	/**
//	 * Iterate over the list of observables and unobserve each of them.
//	 */
//	 void stopObserving() {
//
//		if (beingObserved != null) {
//			for (int i = 0; i < beingObserved.size(); i++) {
//				((Observable) beingObserved.elementAt(i))
//					.deleteObserver(thisAgent);
//			}
//		}
//
//		beingObserved = null;
//	}
//
//}
