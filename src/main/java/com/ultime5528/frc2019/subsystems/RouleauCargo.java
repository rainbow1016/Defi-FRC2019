package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RouleauCargo extends Subsystem {

    private VictorSP moteurRouleauHaut;
    private VictorSP moteurRouleauBas;
    private VictorSP moteurPrendreBallon;
    private VictorSP moteurPorte;
    private AnalogInput ultraSons;

    public RouleauCargo() {

        moteurRouleauHaut = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_HAUT);
        addChild("Moteur du rouleau haut", moteurRouleauHaut);
        
        moteurRouleauBas = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_BAS);
        addChild("Moteur du rouleau bas", moteurRouleauBas);
        
        moteurPrendreBallon = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_PRENDRE_BALLON);
        addChild("Moteur pour prendre le ballon", moteurPrendreBallon);
        
        moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
        addChild("Porte moteur", moteurPorte);
        
        ultraSons = new AnalogInput(K.Ports.ULTRA_SONS);
        addChild("UltraSons", ultraSons);

        BadLog.createTopic("RouleauCargo/Puissance moteur rouleau haut", "%", () -> moteurRouleauHaut.get());
        BadLog.createTopic("RouleauCargo/Puissance moteur rouleau bas", "%", () -> moteurRouleauBas.get());
        BadLog.createTopic("RouleauCargo/Puissance moteur porte", "%", () -> moteurPorte.get());
        BadLog.createTopic("RouleauCargo/Valeur ultrasons", "V", () -> ultraSons.getAverageVoltage());

        // TODO Ajouter BadLog pour les nouveaux moteurs et capteurs

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
        moteurPorte.set(K.RouleauCargon.MOTEUR_PORTE_OUVRIR);
    }

    public void fermerPorte() {
        moteurPorte.set(K.RouleauCargon.MOTEUR_PORTE_FERMER);
    }

    public void arreterMoteurPorte() {
        moteurPorte.set(0.0);
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