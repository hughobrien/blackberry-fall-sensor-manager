package fallHandler.datastore;

import java.util.Vector;

import fallHandler.bytesource.ByteSource;

/**
 * Simple class to keep track of known FallSensor device IDs. This is not static
 * but only a single instance should be used, it is initialised during the
 * initDB section of DataStore.
 * 
 * @author Hugh O'Brien
 */
public final class SourceList extends Observable {

	/**
	 * Simple data storage class for a sourceID/Bytesource pairing.
	 * 
	 * @author Hugh O'Brien
	 */
	public class SourceListEntry {

		/** Source ID associated with the ByteSource */
		public final int sourceID;
		/** Reference to the ByteSource */
		public final ByteSource byteSource;

		/**
		 * Constructs a new SourceListEntry Object, all such objects must have
		 * the two defined fields populated.
		 * 
		 * @param sourceID Source ID associated with this object.
		 * @param byteSource Reference to the ByteSource that is responsible for
		 *            this sourceID.
		 */
		public SourceListEntry(int sourceID, ByteSource byteSource) {

			this.sourceID = sourceID;
			this.byteSource = byteSource;

		}
	}

	/** Vector is the preferred datatype for this */
	private Vector sourceList = new Vector();

	/**
	 * Empty Package Access Constructor.
	 */
	SourceList() {

	/* Empty Package Access Constructor */
	}

	/**
	 * Add the given int to the list of sources, duplicates are silently
	 * ignored.
	 * 
	 * @param newSource New source ID to add.
	 * @param byteSource Object reference to the source of this data.
	 */
	public void addSource(int newSource, ByteSource byteSource) {

		SourceListEntry newEntry = new SourceListEntry(newSource, byteSource);

		if (!entryExists(newEntry)) {
			sourceList.addElement(newEntry);
			notifyObservers();
		}

	}

	/**
	 * Scans the source list to see if an existing matching entry is already
	 * present.
	 * 
	 * @param newEntry The new entry to test.
	 * @return True if it is already present.
	 */
	private boolean entryExists(SourceListEntry newEntry) {

		for (int i = 0; i < sourceList.size(); i++) {
			if (((SourceListEntry) sourceList.elementAt(i)).sourceID == newEntry.sourceID) {
				if (((SourceListEntry) sourceList.elementAt(i)).byteSource == newEntry.byteSource) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return an int[] of known unique sources.
	 * 
	 * @return Array of FallSensor IDs/ByteSource pairings that have been seen.
	 */
	public SourceListEntry[] getSources() {

		SourceListEntry[] sourceListArray =
			new SourceListEntry[sourceList.size()];

		for (int i = 0; i < sourceListArray.length; i++) {
			sourceListArray[i] = (SourceListEntry) sourceList.elementAt(i);
		}

		return sourceListArray;

	}

}
