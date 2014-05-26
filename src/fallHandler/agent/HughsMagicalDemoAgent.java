/* Pay close attention to this Class, it demonstrates almost all of what
 * you'll need to program. Also take a good look at the DataStore and FallSensorRecord
 * code. There are a few different calls you can make to the DataStore, if in doubt
 * just use the getRecord call to get the direct handle to the FallSensorRecord.
 * The other methods are for convenience but are NOT necessary.
 * 
 * The values for the various states (like CasualMessage.STATE_RUNNING) are defined
 * inside of the handlers for the various message types, e.g. that one was defined
 * in the CasualMessage class, which is the handler for the messages of type
 * "Casual Message". Take a poke around that to see if you need anything in there,
 * also look at some of the other message handlers to see if you need anything they
 * define.
 * 
 * Due to problems arising from half-implemented features, we're no longer extending
 * the Agent class (in fact I've removed it). This means you need to directly specify
 * that you're implementing the Observer interface.
 * 
 * It's best if there is only one agent of each type running at any one time.
 * (SINGLETON PATTERN ALERT! SINGLETON PATTERN ALERT!)
 * 
 * For this reason I've made the constructor of this agent private, which means
 * you can't call "new HughsMagicalDemoAgent()", you've to ask it to start itself.
 * 
 * Don't forget to add your agent to the BootStrap class, or else it will never run!
 *
 * P.S. The FallSensorRecords only alert you if the value they have stored CHANGES.
 * Unfortunately the default value for each empty FSR is zero, and the Enumerated values
 * for the status message includes 0 (for 'Running'). This means that if you observe
 * the status record, and send a message saying that you're running, your update() won't fire.
 * Send a message that you're doing anything else, then send a running message to workaround this.
 */

package fallHandler.agent;

import fallHandler.datastore.CasualMessage;
import fallHandler.datastore.DataStore;
import fallHandler.datastore.FallSensorRecord;
import fallHandler.datastore.Observable;
import fallHandler.datastore.Records;
import fallHandler.datastore.Observable.Observer;

public class HughsMagicalDemoAgent implements Observer {
	
	
	int oldState = 0;
	static HughsMagicalDemoAgent instance = null;
	
	public static HughsMagicalDemoAgent startAgent() {
		
		if (instance == null) {
			instance = new HughsMagicalDemoAgent();
		}
		
		return instance;
		
	}	
	
	/* The constructor is as good a place as any to register on observerables.
	 * Let's keep things simple by always dealing with sensor '0'. You can write
	 * code for more sensors if you'd like, but wouldn't you rather be outside? */
	private HughsMagicalDemoAgent() {
                System.out.println(this.toString() + "started\n");
		DataStore.getRecord("0" + Records.prependsrc.mobility.state).addObserver(this);
	}
	
	/* update is what the observables call when they've changed */
	public void update(Observable updatedObject) {
		System.out.println("\n\n\t\t******\n\t\tHughsMagicalDemoAgent Called!");
		
		/* Remember that update gives us an Observable object, which is not 
		 * necessarily a FallSensorRecord. So we have to test if it is before we
		 * can treat it like it is.
		 */
		if (updatedObject instanceof FallSensorRecord) {
			FallSensorRecord updatedRecord = (FallSensorRecord)updatedObject;
			
			System.out.println("\t\tRecord Details:\n" +
					"\t\tName: " + updatedRecord.getName() +
					"\n\t\tValue: " + updatedRecord.getVal() + "\n"
					);
			
			/* This is a state machine right here. No, really, it is. */
			
			int currentState = updatedRecord.getVal();
			
			if (oldState == CasualMessage.STATE_RUNNING && currentState == CasualMessage.STATE_LYING) {
				System.out.println("\t\tFALL FALL FALL");
				DataStore.getRecord("0" + Records.prependsrc.status.falldetected).setVal(1);
			}
			
				oldState = currentState;
			
			System.out.println("\n\t\t******\n");
			
		}
	}
	
	

}
