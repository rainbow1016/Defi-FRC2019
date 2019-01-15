package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RouleauCargo extends Subsystem {

    private VictorSP moteur;

    public RouleauCargo() {
        moteur = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR);
        addChild("Moteur", moteur);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        moteur.set(0.3);
    }

    public void arreterMoteur() {
        moteur.set(0.0);
    }

   
}