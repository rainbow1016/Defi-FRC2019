package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import badlog.lib.BadLog;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lanceur extends Subsystem {
    
    private VictorSP lanceur;

    public Lanceur() {
        lanceur = new VictorSP(K.Ports.MOTEUR_LANCEUR);
        addChild("Moteur Lanceur", lanceur);
        BadLog.createTopic("Lanceur/Puissance moteur lanceur", "%", () -> lanceur.get());
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        lanceur.set(K.Lanceur.VITESSE_PRENDRE_BALLON);
    }

    public void lancerBallon() {
        lanceur.set(K.Lanceur.VITESSE_LANCER_BALLON);
    }

    public void arreter() {
        lanceur.set(0.0);
    }

    public boolean ballonPresent() {
        return false;

    }

}