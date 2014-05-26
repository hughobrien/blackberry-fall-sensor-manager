package fallHandler.test;

import fallHandler.datastore.Observable;
import fallHandler.datastore.Observable.Observer;

/**
 * Test the observable class.
 * 
 * @author Hugh O'Brien
 */
public class TestObservable {

	/**
	 * Counter for use by inner classes.
	 */
	static int updates;

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testAddObserver();
		testDeleteObserver();
	}

	/**
	 * Test that the addObserver method does not permit the same observer twice.
	 */
	private static void testAddObserver() {

		ObservableMod observable = new ObservableMod() {};
		Observer observer = new Observer() {

			public void update(Observable updatedObject) {

				updates++;

			}
		};

		updates = 10;

		observable.addObserver(observer);
		observable.addObserver(observer);

		observable.triggerUpdate();

		System.out.println("testObservableAddObserver "
			+ ((updates == 11) ? "passed." : "failed."));
	}

	/**
	 * Test that a deleted observer is no longer notified.
	 */
	private static void testDeleteObserver() {

		ObservableMod observable = new ObservableMod();
		Observer observer = new Observer() {

			public void update(Observable updatedObject) {

				updates++;

			}
		};

		updates = 20;

		observable.addObserver(observer);
		observable.deleteObserver(observer);
		observable.deleteObserver(observer);

		observable.triggerUpdate();

		System.out.println("testObservableDeleteObserver "
			+ ((updates == 20) ? "passed." : "failed."));

	}

}
