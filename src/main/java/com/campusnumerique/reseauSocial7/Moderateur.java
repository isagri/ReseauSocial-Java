package com.campusnumerique.reseauSocial7;

/**  
 * La classe <b> Moderateur </b> est une classe fille de Utilisateur
 * Elle est caract�ris�e par les informations suppl�mentaires suivantes : 
 * <ul>
 * <li> niveau : donne des droits suppl�mentaires 
 *      1 = droit de supprimer les messages des utilisateurs - 2 = droit 1 + supprimer des utilisateurs </li>
 */
public class Moderateur extends User {

	int niveau;

	/**  
	 * Constructeur de <b> Moderateur </b> 
	 * @param nom = le nom de la personne
	 * @param prenom = le prenom de la personne
	 * @param annee = l'annee de naissance de la personne
	 * @param pseudo = le pseudo de l'utilisateur
	 * @param niveau = le niveau du mod�rateur (1 = droit de supprimer les messages des utilisateurs - 2 = droit 1 + supprimer des utilisateurs)
	 * @throws PersonneException   si nom = "" ou null
	 * @throws PersonneException   si pr�nom = "" ou null
	 * @throws PersonneException   si annee < 1900
	 * @throws ModerateurException si niveau <> 1 et 2 
	 */
	public Moderateur(int id, String nom, String prenom, String pseudo, int niveau) {
		super(id, nom, prenom, pseudo);
		this.niveau = niveau;
	}

	
	/**  
	 * affiche les infos profil du mod�rateur
	 */
	public String decrisPersonne() {
			return super.decrisPersonne() + " - Modérateur niveau "  + String.valueOf(niveau);
	}
		
	/**  
	 * renvoi le niveau du moderateur
	 * @return niveau du mod�rateur
	 */
	public int niveauUtilisateur() {
		return niveau;
	}

	public String recevoirPaiement() { 
		return "recoit des bons cadeaux";
	}

}
