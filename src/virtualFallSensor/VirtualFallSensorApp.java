/*
 * VirtualFallSensorApp.java
 */
package virtualFallSensor;

import fallHandler.agent.BootStrap;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import java.io.InputStream;
import java.io.OutputStream;

//import javax.bluetooth.*;
//import javax.microedition.io.*;

/**
 * The main class of the application.
 */
public class VirtualFallSensorApp extends SingleFrameApplication {

//    private StreamConnectionNotifier streamConNotifier;
//    private StreamConnection connection;
    public static InputStream inStream;
    public static OutputStream outStream;
    private VirtualFallSensorView gui;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        gui = new VirtualFallSensorView(this);
        show(gui);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of VirtualFallSensorApp
     */
    public static VirtualFallSensorApp getApplication() {
        return Application.getInstance(VirtualFallSensorApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        BootStrap.main(null);
        launch(VirtualFallSensorApp.class, args);
    }

//    /** Start BT SPP Server */
//    public void startSPPServer() {
//
//        new Thread() {
//
//            public void run() {
//                int[] buf1 = new int[1024];
//                int i=0;
//
//                try {
//                    UUID uuid = new UUID("1101", true);
//                    String connectionString = "btspp://localhost:" + uuid + ";name=Virtual FallSensor";
//
//                    streamConNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
//                    connection = streamConNotifier.acceptAndOpen();
//                    gui.mainStatus.setText("Connected");
//
//                    inStream = connection.openInputStream();
//                    outStream = connection.openOutputStream();
//
//                    for(;;){
//                    buf1[i] = inStream.read();
//
//                    if(buf1[i] == 0xfc){
//                        while(buf1[i] != 0xfd) {
//                            buf1[++i] = inStream.read();
//                        }
//                        gui.lastReceived.setText(VirtualFallSensorView.toHexString(buf1, true));
//                    }
//
//
//                    }
//                } catch (IOException e) {
//                    System.out.println("startSPPServer IOE");
//                    gui.mainStatus.setText("Exception");
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//
//
//
//    }

    /** Stop Server */
//    public void stopSPPServer() {
//        try {
//            inStream.close();
//            outStream.close();
////            connection.close();
////            streamConNotifier.close();
//           gui.mainStatus.setText("Disconnected");
//        } catch (IOException e) {
//            gui.mainStatus.setText("Exception");
//            e.printStackTrace();
//            System.out.println("stopSPPServer IOE");
//        }
//    }
}