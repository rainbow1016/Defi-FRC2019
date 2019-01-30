package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RouleauCargo extends Subsystem {
    private final static double HAUTEUR_MAX = 2.5;

    private VictorSP moteurRouleauHaut;
    private VictorSP moteurRouleauBas;
    private VictorSP moteurMonter;
    private VictorSP moteurPorte;
    private AnalogInput ultraSons;
    private AnalogPotentiometer potentiometre;

    public RouleauCargo() {

        moteurRouleauHaut = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_HAUT);
        addChild("Moteur du rouleau haut", moteurRouleauHaut);
        BadLog.createTopic("RouleauCargo/Puissance moteur rouleau haut", "%", () -> moteurRouleauHaut.get());

        moteurRouleauBas = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_BAS);
        addChild("Moteur du rouleau bas", moteurRouleauBas);
        BadLog.createTopic("RouleauCargo/Puissance moteur rouleau bas", "%", () -> moteurRouleauBas.get());

        moteurMonter = new VictorSP(K.Ports.ROULEAU_CARGO_MOTEUR_PRENDRE_BALLON);
        addChild("Moteur pour prendre le ballon", moteurMonter);
        BadLog.createTopic("RouleauCargo/Puissance moteur monter", "%", () -> moteurMonter.get());
       
        moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
        addChild("Porte moteur", moteurPorte);
        BadLog.createTopic("RouleauCargo/Puissance moteur porte", "%", () -> moteurPorte.get());
       
        ultraSons = new AnalogInput(K.Ports.ROULEAU_CARGO_ULTRA_SON);
        addChild("UltraSons", ultraSons);
        BadLog.createTopic("RouleauCargo/Valeur ultrasons", "V", () -> ultraSons.getAverageVoltage());
       
        potentiometre = new AnalogPotentiometer(K.Ports.ROULEAU_CARGO_POTENTIOMETRE);
        addChild("potentiomètre", potentiometre);
        BadLog.createTopic("RouleauCargo/Valeur potentiometre", "V", () -> potentiometre.get());
        
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        moteurRouleauHaut.set(K.RouleauCargo.MOTEUR_ROUE_HAUT);
        moteurRouleauBas.set(K.RouleauCargo.MOTEUR_ROUE_BAS);
    }

    public void arreterMoteur() {
        moteurRouleauHaut.set(0.0);
        moteurRouleauBas.set(0.0);
    }

    public void ouvrirPorte() {
        moteurPorte.set(K.RouleauCargo.MOTEUR_PORTE_OUVRIR);
    }

    public void fermerPorte() {
        moteurPorte.set(K.RouleauCargo.MOTEUR_PORTE_FERMER);
    }

    public void arreterMoteurPorte() {
        moteurPorte.set(0.0);
    }

    public void arreterMoteurPrendreBalle() {
        moteurMonter.set(0.0);
    }

    public boolean ballonPresent() {
        return ultraSons.getAverageVoltage() < 3;
    }

    public void descendre() {
        moteurMonter.set(K.RouleauCargo.MOTEUR_DECENDRE);
    }

    public void monter() {
        moteurMonter.set(K.RouleauCargo.MOTEUR_MONTER);
    }

    public void maintien() {

        if (potentiometre.get() >= K.RouleauCargo.HAUTEUR_SOMMET) {
            moteurMonter.set(K.RouleauCargo.MAINTIEN_HAUT);
        } else
            moteurMonter.set(K.RouleauCargo.MAINTIEN_BAS);
    }
    public void grimper2(){
        if (Robot.basePilotable.angleGrimpeur() > -15) {
            moteurMonter.set(0.5);
        } else {
            moteurMonter.set(0.1);
        }
    }
}