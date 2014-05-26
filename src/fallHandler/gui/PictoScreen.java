package fallHandler.gui;
//package fallHandler.gui;
//
//import fallHandler.datastore.DataStore;
//import fallHandler.datastore.Observable;
//import fallHandler.datastore.Observable.Observer;
//import fallHandler.datastore.Records;
//import fallHandler.datastore.SourceList;
//import fallHandler.datastore.SourceList.SourceListEntry;
//import net.rim.device.api.system.Bitmap;
//import net.rim.device.api.ui.component.BitmapField;
//import net.rim.device.api.ui.component.LabelField;
//import net.rim.device.api.ui.container.MainScreen;
//import net.rim.device.api.ui.container.VerticalFieldManager;
//
///**
// * PictoScreen displays the pictograph corresponding to the current state of the
// * user. The Observer pattern is used to trigger changes.
// * 
// * @author Hugh O'Brien
// */
//public class PictoScreen extends MainScreen implements Observer {
//
//	private final VerticalFieldManager fieldManager =
//		new VerticalFieldManager(FIELD_HCENTER | NO_HORIZONTAL_SCROLL);
//
//	private int sourceID;
//
//	/*
//	 * Ordering is alphabetical, i.e. falling, lying, sitting, standing,
//	 * walking. Should memory usage become a concern it should be possible to
//	 * only load one set of these images at a time; or even to only load one
//	 * image at a time.
//	 */
//
//	private Bitmap[] portraitImages =
//		{
//			Bitmap.getBitmapResource("invalidPortrait.png"),
//			Bitmap.getBitmapResource("runningPortrait.png"),
//			Bitmap.getBitmapResource("walkingPortrait.png"),
//			Bitmap.getBitmapResource("standingPortrait.png"),
//			Bitmap.getBitmapResource("sittingPortrait.png"),
//			Bitmap.getBitmapResource("lyingDownPortrait.png"),
//			Bitmap.getBitmapResource("fallingPortrait.png") };
//
//	public PictoScreen() {
//
//		this.sourceID = getSourceID();
//		
//		if (getSourceID() == 888)
//		// Name the screen
//		setTitle(new LabelField("Pictograph | NO SOURCE FOUND",
//			FIELD_HCENTER | NON_FOCUSABLE));
//		else
//			setTitle(new LabelField("Pictograph | " + getSourceID(),
//					FIELD_HCENTER | NON_FOCUSABLE));
//
//		// Register interest in Receiver
//		DataStore.getRecord(sourceID + Records.prependsrc.mobility.state)
//			.addObserver(this);
//		
//		// Register interest in Step count
//		DataStore.getRecord(sourceID + Records.prependsrc.mobility.steps).addObserver(this);
//		
//		//register interest in cadence
//		DataStore.getRecord(sourceID + Records.prependsrc.mobility.cadence).addObserver(this);
//
//		// Add our manager to the default screen manager
//		add(fieldManager);
//
//		fieldManager.add(new LabelField("\n\r\n\rAwaiting Update",
//			NON_FOCUSABLE | FIELD_HCENTER));
//	}
//
//	private int getSourceID() {
//
//		SourceListEntry[] knownsources =
//			((SourceList) DataStore.getSourceList())
//				.getSources();
//
//		if (knownsources.length >= 1) {
//			return knownsources[0].sourceID;
//		}
//		else
//			return 888;
//	}
//
//	public void update(Observable updatedObject) {
//		//not actually using updatedObject
//
//		// Clear the screen
//		fieldManager.deleteAll();
//
//		// Determine correct image to display and add it
//		fieldManager.add(new BitmapField(
//			portraitImages[DataStore.getRecord(sourceID + Records.prependsrc.mobility.state).getVal()]));
//
//		fieldManager.add(new LabelField("\n\rSteps: "
//			+ DataStore.retrieve(sourceID + Records.prependsrc.mobility.steps) + " Cadence: " + DataStore.retrieve(sourceID + Records.prependsrc.mobility.cadence),
//			FIELD_HCENTER | NON_FOCUSABLE));
//		
//
//		// Mark screen as needing an update
//		fieldManager.invalidate();
//	}
//
//}
