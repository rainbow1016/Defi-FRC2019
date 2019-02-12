package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

    private VictorSP moteurRouleauHaut;
    private VictorSP moteurRouleauBas;
    private VictorSP moteurPorte;
    private DigitalInput photocell;

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

        photocell = new DigitalInput(K.Ports.INTAKE_PHOTOCELL);
        addChild("Photocell", photocell);
        BadLog.createTopic("Intake/Photocell valeur", BadLog.UNITLESS, () -> {
            if (photocell.get() == true) {
                return 1.0;
            } else {
                return -1.0;
            }
        });

    }

    @Override
    protected void initDefaultCommand() {

    }

    public void prendreBallon() {
        moteurRouleauHaut.set(K.Intake.MOTEUR_HAUT_PRENDRE_BALLON);
        moteurRouleauBas.set(K.Intake.MOTEUR_BAS_PRENDRE_BALLON);

        Robot.ntinst.getEntry("ROULEAU_ON").setBoolean(true);
    }

    public void transfererBallon() {
        moteurRouleauHaut.set(K.Intake.MOTEUR_HAUT_TRANSFERER_BALLON);
        moteurRouleauBas.set(K.Intake.MOTEUR_BAS_TRANSFERER_BALLON);

        Robot.ntinst.getEntry("ROULEAU_ON").setBoolean(true);
    }

    public void arreterMoteurs() {
        moteurRouleauHaut.set(0.0);
        moteurRouleauBas.set(0.0);

        Robot.ntinst.getEntry("ROULEAU_ON").setBoolean(false);
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
        return photocell.get();
    }

}
