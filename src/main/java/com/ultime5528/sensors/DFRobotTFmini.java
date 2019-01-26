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
        serialPort = new SerialPort(115200, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        serialPort.reset();

        notifier = new Notifier(()->measure());
        notifier.startPeriodic(0.02);
    }

    private boolean measure() {

        try {
            
            byte[] tableau = new byte[9];
            long checksum = 0;
    
            tableau[0] = serialPort.read(1)[0];
            checksum += tableau[0];
    
            if (tableau[0] == 'Y') {
    
                tableau[1] = serialPort.read(1)[0];
                checksum += tableau[1];
    
                if (tableau[1] == 'Y') {
    
                    for (int i = 2; i < 8; i++) {
                        tableau[i] = serialPort.read(1)[0];
                        checksum += tableau[i];
                    }
    
                    tableau[8] = serialPort.read(1)[0];
                    checksum &= 0xff;
    
                    if (checksum == tableau[8]) {
    
                        synchronized(this){
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
    public synchronized int getDistance(){
        return distance;
    }

    public synchronized int getStrength() {
        return strength;
    }
    
}