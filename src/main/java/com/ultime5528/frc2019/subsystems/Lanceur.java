package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lanceur extends Subsystem {
    private VictorSP Lanceur;
    private AnalogInput Ultra;

    public Lanceur() {
        Lanceur = new VictorSP(K.Port.MOTEUR_LANCEUR);
        addChild("Lanceur", Lanceur);

        Ultra = new AnalogInput(K.Port.ULTRA_LANCEUR);
        addChild("Ultra", Ultra);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void LancerBallon() {
        Lanceur.set(0.5);
    }

    public void StopLancerBallon() {
        Lanceur.set(0.0);
    }

    public boolean DetecterBallon(){
        boolean Ballon = false;
         if( Ultra.getAverageVoltage() <= 3){
              Ballon = true;

         } 
          return Ballon;
    }

}