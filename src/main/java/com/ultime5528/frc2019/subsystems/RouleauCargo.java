package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RouleauCargo extends Subsystem {

    private VictorSP moteurRouleauHaut;
    private VictorSP moteurRouleauBas;
    private VictorSP moteurPrendreBallon;
    private VictorSP porteMoteur;
    private AnalogInput ultraSons;

    public RouleauCargo() {
        moteurRouleauHaut = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_HAUT);
        addChild("Moteur du rouleau haut", moteurRouleauHaut);
        moteurRouleauBas = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_BAS);
        addChild("moteur du rouleau bas", moteurRouleauBas);
        moteurPrendreBallon = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_PRENDRE_BALLON);
        addChild("moteur pour prendre le ballon", moteurPrendreBallon);
        porteMoteur = new VictorSP(K.Ports.PORTE_MOTEUR);
        addChild("porte moteur", porteMoteur);
        ultraSons = new AnalogInput(K.Ports.ULTRA_SONS);
        addChild("ultraSons", ultraSons);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        moteurRouleauHaut.set(K.RouleauCargon.MOTEUR_ROUE_HAUT);
        moteurRouleauBas.set(K.RouleauCargon.MOTEUR_ROUE_BAS);
    }

    public void arreterMoteur() {
        moteurRouleauHaut.set(0.0);
        moteurRouleauBas.set(0.0);
    }

    public void ouvrirPorte() {
        porteMoteur.set(K.RouleauCargon.MOTEUR_PORTE_OUVRIR);
    }

    public void fermerPorte() {
        porteMoteur.set(K.RouleauCargon.MOTEUR_PORTE_FERMER);
    }

    public void arreterMoteurPorte() {
        porteMoteur.set(0.0);
    }

    public void arreterMoteurPrendreBalle() {
        moteurPrendreBallon.set(0.0);
    }

    public boolean ballonPresent() {
        return ultraSons.getAverageVoltage() < 3;
    }

    public void descendre() {
        moteurPrendreBallon.set(K.RouleauCargon.MOTEUR_DECENDRE);
    }

    public void monter() {
        moteurPrendreBallon.set(K.RouleauCargon.MOTEUR_MONTER);
    }

    public void maintien() {
        moteurPrendreBallon.set(K.RouleauCargon.MAINTIEN);
    }
}