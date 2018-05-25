package com.campusnumerique.reseauSocial7;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

	Scanner sc;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String hello = "Hello", world = "World 7";
		String phrase = hello + ' ' + world;
		System.out.println(phrase);

		new Menu();

		System.out.println("Bye bye World 7");

	}

	/**
	 * 
	 */
	private Menu() {
		sc = new Scanner(System.in);
		int choix = 100;

		while (choix != 0) {

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
				// inscription
				inscription();
				break;

			case 3:
				// connexion
				connexion();
				break;

			default:
				System.out.println("Merci de faire un choix parmi les choix proposes");

			}

		}

		DBConnection.closeInstance();
		sc = null;
	}

	/**
	 * 
	 */
	private void afficheMenu() {
		System.out.println(" ");
		System.out.println("Menu principal - Que voulez vous faire ?");
		System.out.println("1 - Afficher tous les utilisateurs");
		System.out.println("2 - S'inscrire");
		System.out.println("3 - Se connecter");

		System.out.println("0 - Sortir du programme");
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

	
	private void inscription() {
		String nom, prenom, pseudo;
		char connexion;
		int niveau;
		boolean erreur = false;
		User util;
		UserDAO userDAO;
		
		// saisie nom prenom
		System.out.println("nom : ");
		nom = sc.nextLine();
		System.out.println("prenom : ");
		prenom = sc.nextLine();

		// recherche si user existe déjà
		userDAO = new UserDAO(DBConnection.getInstance());
		util = userDAO.findNomPrenom(nom, prenom);
		if (util == null) {
		// l'utilisateur n'existe pas -> saisie pseudo et niveau 
			System.out.println("pseudo : ");
			pseudo = sc.nextLine();
			
			System.out.println("niveau de droit (0, 1, 2)");
			niveau = 0;
			erreur = true;
			do {
				try {
					niveau = sc.nextInt();
					erreur = false;
				} catch (InputMismatchException err) {
					System.out.println("merci de saisir un chiffre pour le niveau de droit" + err.getMessage());
					erreur = true;
				} finally {
					sc.nextLine();
				}
			} while (erreur);
			
			// creation du user
			if (niveau == 0) {
				util = new User(0, nom, prenom, pseudo);
			} else if (niveau == 1 || niveau == 2) {
				util = new Moderateur(0, nom, prenom, pseudo, niveau);
			} else {
				util = new Moderateur(0, nom, prenom, pseudo, niveau);
			}
			if (!userDAO.create(util)) {
				util = null;
			}
			
		}	else { 
			// Le user existe déjà
			System.out.println("Cet utilisateur existe déjà : " + util.decrisPersonne());
		}

		if (util == null) {
			System.out.println("une erreur s'est produite lors de la création du user - Veuillez recommencer");
		} else {
			// 	connexion ?
			System.out.println("Souhaitez vous vous connecter avec cet utilisateur (o/n)");
			do {
				connexion = sc.nextLine().toLowerCase().charAt(0);
			} while ( connexion != 'o'  && connexion != 'n' );
			
			if ( connexion == 'o' ) {
				new sousMenu(util);      
			}
		}
	}


	
	/**
	 *  Connexion 
	 */
	private void connexion() {
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
			System.out.println("L'utilisateur " + prenom + " " + nom + " n'existe pas");
		} else {
			new sousMenu(util);      
		}
		
	}

}
