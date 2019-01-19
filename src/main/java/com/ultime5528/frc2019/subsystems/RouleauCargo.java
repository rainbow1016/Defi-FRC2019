package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RouleauCargo extends Subsystem {

    private VictorSP moteurRouleau;
    private VictorSP moteurPorte;
    private AnalogInput ultraSons;

    public RouleauCargo() {
        moteurRouleau = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR);
        addChild("Moteur", moteurRouleau);
        moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
        addChild("porte moteur", moteurPorte);
        ultraSons = new AnalogInput(K.Ports.ULTRA_SONS);

        BadLog.createTopic("RouleauCargo/Puissance moteur rouleau", "%", () -> moteurRouleau.get());
        BadLog.createTopic("RouleauCargo/Puissance moteur porte", "%", () -> moteurPorte.get());
        BadLog.createTopic("RouleauCargo/Valeur ultrasons", "V", () -> ultraSons.getAverageVoltage());
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        moteurRouleau.set(0.3);
    }

    public void arreterMoteur() {
        moteurRouleau.set(0.0);
    }

    public void ouvrirPorte() {
        moteurPorte.set(0.5);
    }

    public void fermerPorte() {
        moteurPorte.set(-0.5);
    }

    public void arreterMoteurPorte() {
        moteurPorte.set(0.0);
    }

    public boolean ballonPresent() {
        ultraSons.getAverageVoltage();
        return ultraSons.getAverageVoltage() < 3;
    }
}