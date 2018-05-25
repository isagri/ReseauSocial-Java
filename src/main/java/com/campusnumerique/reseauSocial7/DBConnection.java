package com.campusnumerique.reseauSocial7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	//URL de connexion
	private String url = "jdbc:mysql://localhost/rsocialjava?serverTimezone=UTC";
	//Nom du user
	private String user = "root";
	//Mot de passe de l'utilisateur
	private String passwd = "";
	//Objet Connection
	private static Connection connect;

	public DBConnection() {
		// TODO Auto-generated constructor stub
	    try {
			// Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Création d'une connexion;
	    	connect = DriverManager.getConnection(url, user, passwd);
	    } catch (SQLException e) {
	        e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
	    }
	    System.out.println("Connexion à la BDD réussie");
	}
	     
	//Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
	public static Connection getInstance() {
		if(connect == null) {
			new DBConnection();
		}
		return connect;   
	}

	public static void closeInstance() {
		if(connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Cloture de la BDD réussie");
	}
}
