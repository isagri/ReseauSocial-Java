package com.campusnumerique.reseauSocial7;


/**  
 * La classe <b> Personne </b> est une classe abstraite d�finissant les personnes du R�seau social
 * Elle est caract�ris�e par les informations suivantes : 
 * <ul>
 * <li> nom </li>
 * <li> prenom </li>
 * <li> annee (de naissance) - doit �tre > 1900   PersonneException </li>
 */

public abstract class Personne {

	protected int id;
	protected String nom;
	protected String prenom;

	public Personne() {
		// System.out.println("Cr�ation d'un user vide");
		setId(0);
		setNom("Inconnu");
		setPrenom("Inconnu");
	}
	

	/**  
	 * Constructeur de <b> Personne </b> 
	 * @param pNom = le nom de la personne
	 * @param pPrenom = le prenom de la personne
	 * @param pAnnee = l'annee de naissance de la personne
	 * @throws PersonneException   si nom = "" ou null
	 * @throws PersonneException   si prenom = "" ou null
	 * @throws PersonneException   si annee < 1900
	 */

	public Personne(int id, String nom, String prenom) {	
		//System.out.println("Creation d'une personne avec parametres id, nom, prenom");
		setId(id);
		setNom(nom);
		setPrenom(prenom);
	}

	
	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}

	public void setNom(String pNom) {
		nom = pNom;
	}
	
	public void setPrenom(String pPrenom) {
		prenom = pPrenom;
	}


	/**  
	 * affiche les infos profil de la personne 
	 */
	public String decrisPersonne() {
		return id + " - " + prenom + " " + nom;
	}


}
