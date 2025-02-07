package net.bilaljh.fubar;

import com.fazecast.jSerialComm.SerialPort;

public class SerialReader {

    private SerialPort serialPort;
    private long marker;

    public SerialReader() {
        serialPort = SerialPort.getCommPorts()[1];

        serialPort.setBaudRate(115200);
        serialPort.openPort();

        if(!serialPort.openPort()) {
            System.out.println("Port konnte nicht geöffnet werden.");
            throw new RuntimeException("Could not open serial port");
        }

        System.out.println("Serial port: " + serialPort.getSystemPortName());
    }

    public int[] read() {
        int[] data = new int[1];
        byte[] buffer = new byte[1024];
        if(serialPort.bytesAvailable() > 0) {
            int numRead = serialPort.readBytes(buffer, buffer.length);
            String received = new String(buffer, 0, numRead);
            received = received.trim();

            if(received.startsWith("E")) {
                String[] values = received.substring(1).trim().split(" ");
                data = new int[values.length];
                for(int i = 0; i < values.length; i++) {
                    try {
                        data[i] = Integer.parseInt(values[i]);
                    } catch(NumberFormatException e) {
                        System.err.println("Fehlerhafte Daten");
                    }
                }
            }
        }
        return data;
    }

    // -- Methoden für Timer
    public void setMark() {
        marker = System.currentTimeMillis();
    }
    public long getMarker() {
        return marker;
    }

}