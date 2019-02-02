package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
    
    private VictorSP moteurRouleauHaut;
    private VictorSP moteurRouleauBas;
    private VictorSP moteurPorte;
    private AnalogInput ultraSons;



    public Intake() {

        moteurRouleauHaut = new VictorSP(K.Ports.INTAKE_MOTEUR_HAUT);
        addChild("Moteur du rouleau haut", moteurRouleauHaut);
        BadLog.createTopic("Intake/Puissance moteur rouleau haut", "%", () -> moteurRouleauHaut.get());

        moteurRouleauBas = new VictorSP(K.Ports.INTAKE_MOTEUR_BAS);
        addChild("Moteur du rouleau bas", moteurRouleauBas);
        BadLog.createTopic("Intake/Puissance moteur rouleau bas", "%", () -> moteurRouleauBas.get());
       
        moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
        addChild("Porte moteur", moteurPorte);
        BadLog.createTopic("Intake/Puissance moteur porte", "%", () -> moteurPorte.get());
       
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        moteurRouleauHaut.set(K.Intake.MOTEUR_ROUE_HAUT);
        moteurRouleauBas.set(K.Intake.MOTEUR_ROUE_BAS);
    }

    public void arreterMoteurs() {
        moteurRouleauHaut.set(0.0);
        moteurRouleauBas.set(0.0);
    }

    public void ouvrirPorte() {
        moteurPorte.set(K.Intake.MOTEUR_PORTE_OUVRIR);
    }

    public void fermerPorte() {
        moteurPorte.set(K.Intake.MOTEUR_PORTE_FERMER);
    }

    public void arreterMoteurPorte() {
        moteurPorte.set(0.0);
    }

    public boolean ballonPresent() {
        return ultraSons.getAverageVoltage() <= K.Intake.VALEUR_DETECTER_BALLON;
    }

}



