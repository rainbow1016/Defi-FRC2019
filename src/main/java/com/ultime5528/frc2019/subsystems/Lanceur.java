package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lanceur extends Subsystem {
    private VictorSP lanceur;
    private AnalogInput ultra;

    public Lanceur() {
        lanceur = new VictorSP(K.Ports.MOTEUR_LANCEUR);
        addChild("Lanceur", lanceur);
        BadLog.createTopic("Lanceur/Puissance moteurs", "%", () -> lanceur.get());

        ultra = new AnalogInput(K.Ports.ULTRA_LANCEUR);
        addChild("Ultra", ultra);
        BadLog.createTopic("Lanceur/Valeur ultasons", "V", () -> ultra.getAverageVoltage());
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void lancerBallon() {
        lanceur.set(0.5);
    }

    public void stopLancerBallon() {
        lanceur.set(0.0);
    }

    public boolean DetecterBallon(){
        boolean Ballon = false;
         if( ultra.getAverageVoltage() <= 3){
              Ballon = true;

         } 
          return Ballon;
    }

}