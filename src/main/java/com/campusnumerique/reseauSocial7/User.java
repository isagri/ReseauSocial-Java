package com.campusnumerique.reseauSocial7;


import java.util.ArrayList;


/**  
 * La classe <b> Utilisateur </b> est une classe fille de Personne
 * Elle est caract�ris�e par les informations suppl�mentaires suivantes : 
 * <ul>
 * <li> tMessage (tableau de Message) : contient les messages recus
 * <li> nbMessage (int) : nombre de msg recus
 * <li> maxMessage (final int) : nombre max de messages accept�s
 * <li> tAmi (tableau d'Ami) : contient les amis
 * <li> nbAmi (int) : nombre d'ami
 * <li> maxAmi (final int) : nombre max d'amis accept�s
 */
public class User extends Personne {
	
	protected String pseudo;
	protected ArrayList<User> collecAmi;
	

	/**  
	 * Constructeur de <b> User </b> 
	 * @param nom = le nom de la personne
	 * @param prenom = le prenom de la personne
	 * @param pseudo = le pseudo de la personne
	 * @param annee = l'annee de naissance de la personne
	 * @throws PersonneException   si nom = "" ou null
	 * @throws PersonneException   si pr�nom = "" ou null
	 * @throws PersonneException   si annee < 1900
	 */
	
	public User(int id, String nom, String prenom, String pseudo) {	
		super(id, nom, prenom);
		this.pseudo = pseudo;

		collecAmi = new ArrayList<User>();

	}

	public User() {	
		super();
		this.pseudo = "Inconnu";

		collecAmi = new ArrayList<User>();

	}

	
	public String getPseudo() {
		return pseudo;
	}
	
	/**  
	 * affiche les infos profil de l'utilisateur 
	 */
	public String decrisPersonne() {
		return super.decrisPersonne() + " alias " + pseudo;
	}
	
	
	/**  
	 * renvoi le niveau = 0 de l'utilisateur
	 */
	public int niveauUser() {
		return 0;
	}
				

	
	/**  
	 * permet a l'utilisateur d'ajouter un ami
	 * @param ami (Utilisateur) = Utilisateur ami � ajouter
	 */
	public void setAmi(User ami) {
		collecAmi.add(ami);
		System.out.println( "L'utilisateur " + ami.decrisPersonne() + " a été ajouté dans vos amis");  

	}

	public User getAmi(User ami) {
		for (int i = 0; i < this.collecAmi.size(); i++ ) {
			if (ami.equals(this.collecAmi.get(i)))  {
				return collecAmi.get(i);
			}
		}
		return null;
	}
	

	public void supprimeAmi(User ami) {
		collecAmi.remove(ami);
		System.out.println( "L'utilisateur " + ami.decrisPersonne() + " ne fait plus parti de vos amis");  
	}
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}

	public boolean equals(User util){
		if (!(this.getId() == util.getId())) {
			return false;
		} else {
			if (!(this.getNom().equals(util.getNom()))) {
				return false;
			} else {
				return this.getPrenom().equals(util.getPrenom());
			}
		}
	}

}

