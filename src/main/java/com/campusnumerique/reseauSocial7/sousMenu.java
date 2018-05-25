package com.campusnumerique.reseauSocial7;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class sousMenu {

	Scanner sc;
	int choix, i = 0;

	/**
	 * 
	 */
	public sousMenu(User utilMe) {
		
		sc = new Scanner(System.in);
		choix = 100;

		while (choix != 0) {
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("je suis connecté en tant que " + utilMe.decrisPersonne());
			afficheMenu();

			// saisie du choix
			try {
				choix = sc.nextInt();
			} catch (InputMismatchException err) {
				System.out.println("merci de saisir un chiffre - " + err.getMessage());
				choix = 100;
			} finally {
				sc.nextLine();
			}

			switch (choix) {
				case 0:
					// Sortir de l'application
					break;

				case 1:
					// Afficher tous les utilisateurs
					afficheUtilisateurs();
					break;

				case 2:
					// Rechercher un utilisateur
					rechercheUtilisateur();
					break;

				case 11:
					// Afficher mes amis
					afficheAmis(utilMe);
					break;

				case 12:
					// Rechercher un ami
					rechercheAmi(utilMe);
					break;

				case 13:
					// Ajouter un ami
					ajouteAmi(utilMe);
					break;

				case 14:
					// Supprimer un ami
					supprimeAmi(utilMe);
					break;

					
				default:
					System.out.println("Merci de faire un choix parmi les choix proposes");

			}
		}

		sc = null;

	}

	/**
	 * 
	 */
	private void afficheMenu() {
		System.out.println("Sous-menu - Que voulez vous faire ?");
		System.out.println(" ");
		System.out.println("1 - Afficher tous les utilisateurs");
		System.out.println("2 - Rechercher un utilisateur");
		System.out.println(" ");
		System.out.println("11 - Afficher mes amis");
		System.out.println("12 - Rechercher un ami");
		System.out.println("13 - Ajouter un ami");
		System.out.println("14 - Supprimer un ami");

		System.out.println(" ");
		System.out.println("0 - Se déconnecter");
	}

	private void afficheUtilisateurs() {
		
		ArrayList<User> collecUtil;
		UserDAO userDAO;

		userDAO = new UserDAO(DBConnection.getInstance());
		collecUtil = userDAO.findAll();
		
		for (User util : collecUtil) {
			System.out.println(util.decrisPersonne());
		}
	}


	private void rechercheUtilisateur() {
		String nom, prenom;
		UserDAO userDAO;
		User util;

		System.out.println("nom : ");
		nom = sc.nextLine();
		System.out.println("prenom : ");
		prenom = sc.nextLine();

		userDAO = new UserDAO(DBConnection.getInstance());
		util = userDAO.findNomPrenom(nom, prenom);
		if (util == null) {
			System.out.println("Désolé, cet utilisateur n'existe pas !");
		} else {
			System.out.println("Utilisateur : " + util.decrisPersonne() );
		}
	}
	
	/**  
	 * affiche les amis de l'utilisateur 
	 */
	public void afficheAmis(User utilMe) {
		System.out.println("Vos amis : ");
		for (User util : utilMe.collecAmi) {
			System.out.println(util.decrisPersonne());
		}

	}
	


	private void rechercheAmi(User utilMe) {
		String nom, prenom;
		UserDAO userDAO;
		User ami;
		
		System.out.println("nom : ");
		nom = sc.nextLine();
		System.out.println("prenom : ");
		prenom = sc.nextLine();

		userDAO = new UserDAO(DBConnection.getInstance());
		ami = userDAO.findNomPrenom(nom, prenom);
		if (ami == null) {
			System.out.println("Désolé, cet utilisateur n'existe pas !");
		} else {
			ami = utilMe.getAmi(ami);
			if (ami == null) {
				System.out.println("Cet utilisateur ne fait pas parti de vos amis");
			} else {
				System.out.println("Votre ami : " + ami.decrisPersonne());
			}
		}
			
	}
	
	
	private void ajouteAmi(User utilMe) {
		String nom, prenom;
		UserDAO userDAO;
		User ami;
		
		System.out.println("nom : ");
		nom = sc.nextLine();
		System.out.println("prenom : ");
		prenom = sc.nextLine();

		// recherche de l'ami dans les user
		userDAO = new UserDAO(DBConnection.getInstance());
		ami = userDAO.findNomPrenom(nom, prenom);
		if (ami == null) {
			// Ce user n'existe pas
			System.out.println("Désolé, cet utilisateur n'existe pas !");
		} else {
			if ( ami.equals(utilMe) ) {
				// L'ami c'est moi-meme
				System.out.println("Vous êtes déjà votre meilleur ami");
			} else if (utilMe.getAmi(ami) != null) {
				// L'ami existe déja dans mes amis
				System.out.println("L'utilisateur " + ami.decrisPersonne() + " est déjà votre ami");
			} else {
				// Ajout du user dans les ami -> dans collecAmis et dans BDD
				utilMe.setAmi(ami);
				userDAO.createAmi(utilMe, ami);
			}
		}

	}


	
	
	private void supprimeAmi(User utilMe) {
		String nom, prenom;
		UserDAO userDAO;
		User ami;
		
		System.out.println("nom : ");
		nom = sc.nextLine();
		System.out.println("prenom : ");
		prenom = sc.nextLine();

		userDAO = new UserDAO(DBConnection.getInstance());
		ami = userDAO.findNomPrenom(nom, prenom);
		if (ami == null) {
			System.out.println("Cet utilisateur n'existe pas !");
		} else {
			ami = utilMe.getAmi(ami);
			if (ami == null) {
				System.out.println("Cet utilisateur ne fait pas parti de vos amis");
			} else {
				utilMe.supprimeAmi(ami);
				userDAO.deleteAmi(utilMe, ami);
			}
		}
			
	}
	
}
