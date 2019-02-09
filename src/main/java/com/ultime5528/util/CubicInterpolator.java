package com.ultime5528.util;

public class CubicInterpolator {

	private double courbure, deadzoneVitesse, deadzoneJoystick;

	/**
	 * 
	 * @param courbure Définit le niveau de courbure. 0 : DROIT => 1 : COURBE MAXIMALE
	 * @param deadzoneVitesse Définit le niveau minimal de vitesse. 
	 * @param deadzoneJoystick Définit le niveau minimal de poussée sur le joystick.
	 */
	public CubicInterpolator(double courbure, double deadzoneVitesse, double deadzoneJoystick) {

		this.courbure = courbure;
		this.deadzoneVitesse = deadzoneVitesse;
		this.deadzoneJoystick = deadzoneJoystick;
	}

	public double interpolate(double valeur) {

		if (valeur >= deadzoneJoystick) {

			return deadzoneVitesse + (1 - deadzoneVitesse) * (courbure * valeur * valeur * valeur + (1 - courbure) * valeur);

		} else if (valeur <= -deadzoneJoystick) {

			return -deadzoneVitesse + (1 - deadzoneVitesse) * (courbure * valeur * valeur * valeur + (1 - courbure) * valeur);

		} else {

			return interpolate(deadzoneJoystick) / deadzoneJoystick * valeur;

		}
	}

	public double getCourbure() {
		return courbure;
	}

	public void setCourbure(double courbure) {

		this.courbure = courbure;
	}

	public double getDeadzoneVitesse() {
		return deadzoneVitesse;
	}

	public void setDeadzoneVitesse(double deadzoneVitesse) {
		this.deadzoneVitesse = deadzoneVitesse;
	}

	public double getDeadzoneJoystick() {
		return deadzoneJoystick;
	}

	public void setDeadzoneJoystick(double deadzoneJoystick) {
		this.deadzoneJoystick = deadzoneJoystick;
	}

}