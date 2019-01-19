package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RouleauCargo extends Subsystem {

    private VictorSP moteur;
    private VictorSP porteMoteur;
    private AnalogInput ultraSons;

    public RouleauCargo() {
        moteur = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR);
        addChild("Moteur", moteur);
        porteMoteur = new VictorSP(K.Ports.PORTE_MOTEUR);
        addChild("porte moteur", porteMoteur);
        ultraSons = new AnalogInput(K.Ports.ULTRA_SONS);
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

    public void ouvrirPorte() {
        porteMoteur.set(0.5);
    }

    public void fermerPorte() {
        porteMoteur.set(-0.5);
    }

    public void arreterMoteurPorte() {
        porteMoteur.set(0.0);
    }

    public boolean ballonPresent() {
        ultraSons.getAverageVoltage();
        return ultraSons.getAverageVoltage() < 3;
    }
}