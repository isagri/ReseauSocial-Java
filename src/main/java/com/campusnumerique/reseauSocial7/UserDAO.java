package com.campusnumerique.reseauSocial7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * UserDAO = Accès aux données User en BDD
 */
public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public boolean create(User util)  {
		String sql;
		PreparedStatement pdst = null;
		ResultSet rs;
		boolean ok = true;
				
		try {
			// Création de l'objet requete préparée
			sql = "INSERT INTO user (nom, prenom, pseudo, niveau) VALUES (?, ?, ?, ?);";
			pdst = this.connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			// Remplissage des paramètres de la requete
			pdst.setString(1, util.getNom());
			pdst.setString(2, util.getPrenom());
			pdst.setString(3, util.getPseudo());
			pdst.setInt(4, util.niveauUser());
			
			// Exécution de la requete Update
			pdst.executeUpdate();
			
			// récupération de l'id auto-généré 
			rs = pdst.getGeneratedKeys();
			if (rs.first()) {
				util.setId(rs.getInt(1));
			}
			
			System.out.println("créé en BDD : " + util.decrisPersonne());
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			// fermer le statement
			try {
				pdst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ok;
	
	}

	public boolean createAmi(User util, User ami)  {
		String sql;
		PreparedStatement pdst = null;
		ResultSet rs;
		boolean ok = true;
				
		try {
			// Création de l'objet requete préparée
			sql = "INSERT INTO ami (id1, id2) VALUES (?, ?);";
			pdst = this.connect.prepareStatement(sql);

			// Remplissage des paramètres de la requete
			pdst.setInt(1, util.getId());
			pdst.setInt(2, ami.getId());
			
			// Exécution de la requete Update
			pdst.executeUpdate();
			
			System.out.println("ami créé en BDD : " + ami.decrisPersonne());
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			// fermer le statement
			try {
				pdst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ok;
	
	}

	public boolean deleteAmi(User util, User ami)  {
		String sql;
		PreparedStatement pdst = null;
		ResultSet rs;
		boolean ok = true;
				
		try {
			// Création de l'objet requete préparée
			sql = "DELETE FROM ami WHERE id1 = ? AND id2 = ?";
			pdst = this.connect.prepareStatement(sql);

			// Remplissage des paramètres de la requete
			pdst.setInt(1, util.getId());
			pdst.setInt(2, ami.getId());
			
			// Exécution de la requete Update
			pdst.executeUpdate();
			
			System.out.println("ami supprimé en BDD : " + ami.decrisPersonne());
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			// fermer le statement
			try {
				pdst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ok;
	}
	
	
	
	public boolean delete(User util)  {
		return false;
	}

	public boolean update(User util) {
		return false;
	}

	public User find(int id) {
		String sql;
		Statement st1 = null;
		Statement st2 = null;
		ResultSet rs = null;
		User util = null;
		User ami = null;
		
		try {

			// Recherche du user 
			st1 = this.connect.createStatement();
			sql = "SELECT * FROM user where id = '" + String.valueOf(id) + "'" ;
			rs = st1.executeQuery(sql);
			if (rs.first()) {
				// user trouvé -> création de util
				if (rs.getInt("niveau") == 0) {
					util = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"));
				} else {
					util = new Moderateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"), rs.getInt("niveau"));
				}
				
				// recherche des amis
				st2 = this.connect.createStatement();
				sql = "SELECT id, nom, prenom, pseudo, niveau FROM ami "
						+ "INNER JOIN user ON id2 = id " 
						+ "where id1 = '" + util.getId() + "'";
				// Exécution de la requete
				rs = st2.executeQuery(sql);
				// parcours ResultSet
				while (rs.next()) {
					// amis trouves -> Chargement de collecAmis 
					if (rs.getInt("niveau") == 0) {
						ami = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"));
					} else {
						ami = new Moderateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"), rs.getInt("niveau"));
					}
					util.setAmi(ami);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fermer les statements
			try {
				st1.close();
				st2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
			}
		}

		return util;

	}

	public User findNomPrenom(String nom, String prenom) {

		String sql;
		Statement st1 = null;
		Statement st2 = null;
		ResultSet rs = null;
		User util = null;
		User ami = null;
		
		try {

			// Recherche du user 
			st1 = this.connect.createStatement();
			sql = "SELECT * FROM user where nom = '" + nom +"' and prenom = '" + prenom + "'" ;
			rs = st1.executeQuery(sql);
			if (rs.first()) {
				// user trouvé -> création de util
				if (rs.getInt("niveau") == 0) {
					util = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"));
				} else {
					util = new Moderateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"), rs.getInt("niveau"));
				}
				
				// recherche des amis
				st2 = this.connect.createStatement();
				sql = "SELECT id, nom, prenom, pseudo, niveau FROM ami "
						+ "INNER JOIN user ON id2 = id " 
						+ "where id1 = '" + util.getId() + "'";
				// Exécution de la requete
				rs = st2.executeQuery(sql);
				// parcours ResultSet
				while (rs.next()) {
					// amis trouves -> Chargement de collecAmis 
					if (rs.getInt("niveau") == 0) {
						ami = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"));
					} else {
						ami = new Moderateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"), rs.getInt("niveau"));
					}
					util.setAmi(ami);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fermer les statements
			try {
				
				st1.close();
				st2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				//e.printStackTrace();
			}
		}

		return util;

	}


	public ArrayList<User> findAll() {
		String sql;
		Statement st1 = null;
		ResultSet rs = null;
		User util = null;
		ArrayList<User> collecUtil = new ArrayList<User>();
		
		try {
			// Recherche du user 
			st1 = this.connect.createStatement();
			sql = "SELECT * FROM user";
			rs = st1.executeQuery(sql);
			while (rs.next()) {
				// user trouvé -> création de util
				if (rs.getInt("niveau") == 0) {
					util = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"));
				} else {
					util = new Moderateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("pseudo"), rs.getInt("niveau"));
				}
				// ajout dans la collection
				collecUtil.add(util);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fermer les statements
			try {
				
				st1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				//e.printStackTrace();
			}
		}

		return collecUtil;

	}
	
}
