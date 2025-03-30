package net.bilaljh.fubar;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;

public class SerialReader {

    private SerialPort serialPort;
    private long marker;

    public SerialReader() {
        //serialPort = SerialPort.getCommPorts()[1];
        serialPort = SerialPort.getCommPorts()[1];

        serialPort.setBaudRate(115200);
        serialPort.openPort();

        if(!serialPort.openPort()) {
            System.out.println("Port konnte nicht geöffnet werden.");
            throw new RuntimeException("Could not open serial port");
        }

        System.out.println("Serial port: " + serialPort.getSystemPortName());
    }

    /**
     * Liest die Daten aus dem Serial Port und gibt sie als int[] zurück
     * @return Daten des Serial Ports
     */
    public int[] read() {
        int[] data = new int[1];  // Standardwert für uninitialisierte Daten
        byte[] buffer = new byte[1024];

        if (serialPort.bytesAvailable() > 0) {
            int numRead = serialPort.readBytes(buffer, buffer.length);
            String received = new String(buffer, 0, numRead);
            received = received.trim();

            // Verarbeite Gamepad-Daten, wenn der Empfang mit 'E' beginnt
            if (received.startsWith("E")) {
                // Entferne das 'E' und teile die Gamepad-Daten auf
                String[] values = received.substring(1).trim().split(", ");

                // Überprüfen, dass es genau 7 Werte gibt
                if (values.length == 7) {
                    data = new int[values.length];
                    for (int i = 0; i < values.length; i++) {
                        try {
                            data[i] = Integer.parseInt(values[i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Fehlerhafte Gamepad-Daten");
                            System.err.println("Empfangene Gamepad-Daten: " + received);
                        }
                    }
                } else {
                    // Wenn nicht alle Gamepad-Daten empfangen wurden, drucken wir eine Warnung aus
                    System.err.println("Unvollständige Gamepad-Daten");
                }
            }

            // Verarbeite RFID-Daten, wenn der Empfang mit 'R' beginnt
            if (received.startsWith("R")) {
                String rfidData = received.substring(1).trim();  // Entferne das 'R'
                String[] hexValues = rfidData.split(" ");

                // Wenn die RFID-Daten im erwarteten Format sind
                if (hexValues.length > 0) {
                    System.out.println("RFID-Daten empfangen: " + Arrays.toString(hexValues));

                    // Beispiel: Überprüfe, ob die RFID-Daten mit einem bestimmten Muster übereinstimmen
                    if (rfidData.equals("62 D4 F4 1B")) {
                        Main.setStartable(true);
                        System.out.println("RFID-Daten erkannt und Start wird ermöglicht.");
                    } else {
                        System.out.println("Andere RFID-Daten empfangen: " + rfidData);
                    }
                }
            }
        }

        // Debugging-Ausgabe der Gamepad-Daten
        System.out.println("Empfangene Gamepad-Daten: " + Arrays.toString(data));

        return data;
    }

    /*public int[] read() {
        int[] data = new int[1];
        byte[] buffer = new byte[1024];
        if(serialPort.bytesAvailable() > 0) {
            int numRead = serialPort.readBytes(buffer, buffer.length);
            String received = new String(buffer, 0, numRead);
            //received = received.trim();

            if(received.startsWith("E")) {
                String[] values = received.substring(1).split(" ");
                data = new int[values.length];
                for(int i = 0; i < values.length; i++) {
                    try {
                        data[i] = Integer.parseInt(values[i]);
                    } catch(NumberFormatException e) {
                        System.out.print(received);
                        System.err.println("Fehlerhafte Daten");
                    }
                }
            }

            if(received.startsWith("R")) {
                String values = Arrays.toString(received.substring(1).trim().split(" "));
                if(values.equals("[62 D4 F4 1B]")) {
                    Main.setStartable(true);
                }
            }
        }
        return data;
    } /*

    /**
     * Setzt den Marker auf die aktuelle Zeit
     */
    public void setMark() {
        marker = System.currentTimeMillis();
    }

    /**
     * Gibt den Marker zurück
     * @return Marker
     */
    public long getMarker() {
        return marker;
    }
}