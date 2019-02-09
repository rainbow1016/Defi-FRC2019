package com.ultime5528.sensors;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

public class DFRobotTFmini {

    private int distance;
    private int strength;
    private SerialPort serialPort;
    private Notifier notifier;

    public DFRobotTFmini() {
        distance = 0;
        strength = 0;
        serialPort = new SerialPort(115200, Port.kMXP, 8, Parity.kNone, StopBits.kOne);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        serialPort.reset();

        notifier = new Notifier(() -> {
            while (true)
                measure();
        });
        notifier.startSingle(0);
    }

    private boolean measure() {

        try {

            int[] tableau = new int[9];
            long checksum = 0;

            tableau[0] = read();
            checksum += tableau[0];

            if (tableau[0] == 'Y') {

                tableau[1] = read();
                checksum += tableau[1];

                if (tableau[1] == 'Y') {

                    for (int i = 2; i < 8; i++) {
                        tableau[i] = read();
                        checksum += tableau[i];
                    }

                    tableau[8] = read();
                    checksum &= 0xff;

                    if (checksum == tableau[8]) {

                        synchronized (this) {
                            distance = tableau[2] + tableau[3] * 256;
                            strength = tableau[4] + tableau[5] * 256;
                        }
                        return true;

                    }
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private int read() {
        int out = toUnsignedByte(serialPort.read(1)[0]);
        if (out != 89)
            System.out.println(out);
        else
            System.out.println("Y");
        return out;
    }

    private int toUnsignedByte(byte b) {
        int result;

        if (b < 0)
            result = 256 + b;
        else
            result = b;

        return result;
    }

    public synchronized int getDistance() {
        return distance;
    }

    public synchronized int getStrength() {
        return strength;
    }

}