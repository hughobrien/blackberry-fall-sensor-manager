package fallHandler.datastore;

/**
 * 'records' defines the main DataStore records in a hierarchical manner.
 * 
 * @author Hugh O'Brien
 */
public final class Records {

	/** Keeps count of any errors that have been triggered. */
	public static final class errors {

		/** Errors where the reported length was incorrect. */
		public static final String length = "errors.length";
		/** Number of bytes that have been discarded. */
		public static final String discards = "errors.discards";
		/** Number of times in the incorrect state (should be 0). */
		public static final String state = "errors.state";
		/** Number of errors as a result of message framing. */
		public static final String framing = "errors.framing";
		/** Number of messages that have had bad checksums. */
		public static final String checksum = "errors.checksum";
		/** Number of messages received of unknown type. */
		public static final String type = "errors.type";
	}

	/**
	 * 'prependsrc' indicates that the source ID must be prepended before
	 * referring to any further data
	 */
	public static final class prependsrc {

		/** Records relating to the protocol.. */
		public static final class proto {

			/** Sequence number of next message. */
			public static final String expectedsequencenumber =
				".proto.expectedsequencenumber";
			/** Number of sequence mismatches from this fallsensor. */
			public static final String sequenceerrors = ".proto.sequenceerrors";
			/** Total number of messages seen from this sensor */
			public static final String messagecount = ".proto.messagecount";

			/** These records must have a further value appended */
			public static final class append {

				/** Keeps track of the number of different msg lengths seen. */
				public static final String lengths = ".proto.lengths.";
				/** Keeps track of the number of different msg types seen. */
				public static final String types = ".proto.types.";
			}
		}

		/** Configuration settings for various systems. */
		public static final class config {

			/** Set protocol debugging on, counts msg types/lengths. */
			public static final String debugprotocol = ".config.debugprotocol";
		}

		/** Current fallsensor status flags. */
		public static final class status {

			/** Fall detected flag. */
			public static final String falldetected = ".status.falldetected";
			/** Sensor in use flag. */
			public static final String sensorinuse = ".status.sensorinuse";
			/** Battery low flag. */
			public static final String batterylow = ".status.batterylow";
			/** Card full flag. */
			public static final String cardfull = ".status.cardfull";
			/** Signal low flag. */
			public static final String signallow = ".status.signallow";
			/** Message missed flag. */
			public static final String messagemissed = ".status.messagemissed";
			/** Mains power available flag. */
			public static final String onmainspower = ".status.onmainspower";
			/** Time synchronisation needed flag. */
			public static final String requesttime = ".status.requesttime";
		}

		/** Mobility related records. */
		public static final class mobility {

			/** State. */
			public static final String state = ".mobility.state";
			/** Cadence. */
			public static final String cadence = ".mobility.cadence";
			/** Steps taken. */
			public static final String steps = ".mobility.steps";
			/** Gaitphase. */
			public static final String gaitphase = ".mobility.gaitphase";
			/** Current support. */
			public static final String support = ".mobility.support";
			/** SwingPhase time. */
			public static final String swingphasetime =
				".mobility.swingphasetime";
			/** StancePhase time. */
			public static final String stancephasetime =
				".mobility.stancephasetime";
			/** DoubleSupport time. */
			public static final String doublesupporttime =
				".mobility.doublesupporttime";
			/** Steplength. */
			public static final String steplength = ".mobility.steplength";
		}

		/** Fall Event related records. */
		public static final class fall {

			/** Fall Type. */
			public static final String falltype = ".fall.falltype";
			/** Sequence number of the Fall Alert message for acknowledgement. */
			public static final String alertseqnum = ".fall.alertseqnum";
		}
	}

	/**
	 * Special objects used for system tasks and component configuration - can
	 * only be accessed using DataStore.getConfig
	 */
	public static final class options {


			/** The number that should be dialled. */
			public static final String numbertodial = "numbertodial";

			/** Should the FallHandler agent load at program start. */
			public static final String loadfallhandler = "loadfallhandler";
			/** Should the FallAlertAcknowledger load at program start. */
			public static final String loadfallalertacknowledger =
				"loadfallalertacknowledger";
			/** Should the ActivityLogger agent load at program start. */
			public static final String loadactivitylogger =
				"loadactivitylogger";
	}

}
