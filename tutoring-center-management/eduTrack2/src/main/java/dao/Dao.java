package dao;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import entities.*;
public class Dao implements IDao {
	@Override
	public int saveAbsence(Absence absence) {
        String insertQuery = "INSERT INTO absence (fk_seance, fk_eleve, justification) VALUES (?, ?, ?)";
        int test=0;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, absence.getSeance().getIdSeance());
            preparedStatement.setInt(2, absence.getEleve().getIdEleve());
            preparedStatement.setString(3, absence.getJustification());

            test=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
		return test;
    }
	
	@Override
	public List<Absence> getAbsencesByEleveId(int eleveId) {
	    List<Absence> absences = new ArrayList<>();
	    String selectQuery = "SELECT a.id_absence, a.fk_seance, a.fk_eleve, a.justification " +
	                         "FROM absence a " +
	                         "JOIN seance s ON a.fk_seance = s.id_seance " +
	                         "WHERE a.fk_eleve = ? " +
	                         "ORDER BY s.date_seance ASC";

	    try (Connection connection = DBUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

	        preparedStatement.setInt(1, eleveId);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                Absence absence = new Absence();
	                absence.setIdAbsence(resultSet.getInt("id_absence"));;
	                absence.setSeance(getSeanceById(resultSet.getInt("fk_seance")));
	                absence.setEleve(getEleveById(resultSet.getInt("fk_eleve")));
	                absence.setJustification(resultSet.getString("justification"));

	                
	                absences.add(absence);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return absences;
	}
	@Override
	public Eleve getEleveById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection =DBUtil.getConnection();
            String sql = "SELECT e.*, u.* " +
                         "FROM eleve e " +
                         " inner JOIN utilisateur u ON e.id_eleve = u.id_utilisateur " +
                         "WHERE e.id_eleve = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Eleve eleve = mapResultSetToEleve(resultSet);
                //serialisation happens here that why i did that you should check this function with the others
               // eleve.setParent(getParentById(resultSet.getInt("fk_parent")));
                return eleve;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
        }

        return null; 
    }

    private Eleve mapResultSetToEleve(ResultSet resultSet) throws SQLException {
        Eleve eleve = new Eleve();
        eleve.setIdEleve(resultSet.getInt("id_eleve"));
        eleve.setDateValidation(resultSet.getTimestamp("date_validation").toLocalDateTime());
        eleve.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
        eleve.setNom(resultSet.getString("nom"));
        eleve.setPrenom(resultSet.getString("prenom"));
        eleve.setEmail(resultSet.getString("email"));
        eleve.setPassword(resultSet.getString("passwordd"));
        eleve.setAge(resultSet.getInt("age"));
        eleve.setAdresse(resultSet.getString("adresse"));
        eleve.setPhone(resultSet.getString("phone"));
        eleve.setSexe(resultSet.getString("sexe"));
        eleve.setRolee(resultSet.getString("rolee"));
        eleve.setDateCreation(resultSet.getDate("dateCreation"));
        eleve.setTypeUtil(resultSet.getString("type_util"));

        return eleve;
    }
 

	@Override
	public int insertDocument(Document document , int idMatiere, int idProf) {
        String sql = "INSERT INTO document (nom_doc, taille, fk_enseignant, typeDoc, fk_matiere) VALUES (?, ?, ?, ?, ?)";
        int test =0;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        	 preparedStatement.setString(1, document.getNomDoc());
             preparedStatement.setFloat(2, document.getTaille());
             preparedStatement.setInt(3, idProf);
             preparedStatement.setString(4, document.getTypeDoc());
             preparedStatement.setInt(5, idMatiere);
            test= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return test;
    }
	@Override
	public List<Document> getDocumentsByTeacherId(int teacherId) {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM document WHERE fk_enseignant = ?";
        try (Connection connection = DBUtil.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teacherId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Document document = new Document();
                    document.setIdDocument(resultSet.getInt("id_document"));
                    document.setNomDoc(resultSet.getString("nom_doc"));
                    document.setTaille(resultSet.getFloat("taille"));
                    // Set other attributes accordingly (enseignant, matiere, typeDoc)

                    documents.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
        return documents;
    }
	@Override
	public List<Document> getDocumentsByMatiereIdAndProfId(int matiereId,int profId ) {
	    List<Document> documents = new ArrayList<>();
	    String query = "SELECT d.* from document d inner join enseignant_groupe_matiere e where e.fk_enseignant =?  and e.fk_matiere=?";
	    try (Connection connection = DBUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, profId);
	        preparedStatement.setInt(2, matiereId);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                Document document = new Document();
	                document.setIdDocument(resultSet.getInt("id_document"));
	                document.setNomDoc(resultSet.getString("nom_doc"));
	                document.setTaille(resultSet.getFloat("taille"));
	                document.setTypeDoc(resultSet.getString("typeDoc"));
	                document.setDate_de_creation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());;
	                documents.add(document);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
	    return documents;
	}
	@Override
	public List<Document> getCoursDocumentsByMatiereIdAndStudent(int matiereId, int studentId) {
		List<Document> documents = new ArrayList<>();
		try (Connection connection = DBUtil.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "SELECT d.* FROM document d " +
	                             "INNER JOIN affectation a ON a.fk_matiere = d.fk_matiere " +
	                             "WHERE d.typeDoc = 'cour' AND a.fk_eleve = ? AND d.fk_matiere = ? " +
	                             "ORDER BY d.date_de_creation DESC"
	             )) {

	            preparedStatement.setInt(1, studentId);
	            preparedStatement.setInt(2, matiereId);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                	Document document = new Document();
	                	document.setIdDocument(resultSet.getInt("id_document"));
		                document.setNomDoc(resultSet.getString("nom_doc"));
		                document.setTaille(resultSet.getFloat("taille"));
		                document.setTypeDoc(resultSet.getString("typeDoc"));
		                document.setDate_de_creation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());;
		                documents.add(document);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	        return documents;
	}

	@Override
	public List<Document> getDevoirsDocumentsByMatiereIdAndStudent(int matiereId, int studentId) {
		
		List<Document> documents = new ArrayList<>();
		try (Connection connection = DBUtil.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "SELECT d.* FROM document d " +
	                             "INNER JOIN affectation a ON a.fk_matiere = d.fk_matiere " +
	                             "WHERE d.typeDoc = 'devoir' AND a.fk_eleve = ? AND d.fk_matiere = ? " +
	                             "ORDER BY d.date_de_creation DESC"
	             )) {

	            preparedStatement.setInt(1, studentId);
	            preparedStatement.setInt(2, matiereId);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                	Document document = new Document();
	                	document.setIdDocument(resultSet.getInt("id_document"));
		                document.setNomDoc(resultSet.getString("nom_doc"));
		                document.setTaille(resultSet.getFloat("taille"));
		                document.setTypeDoc(resultSet.getString("typeDoc"));
		                document.setDate_de_creation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());;
		                documents.add(document);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	        return documents;
	}

	@Override
	public List<String> getDevoirDocumentNamesByMatiereId(int matiereId) {
	    List<String> documentNames = new ArrayList<>();
	    String query = "SELECT nom_doc FROM document WHERE fk_matiere = ? AND typeDoc = 'devoir' ORDER BY date_de_creation ";
	    try (Connection connection = DBUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, matiereId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                documentNames.add(resultSet.getString("nom_doc"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
	    return documentNames;
	}
	
	@Override
	public List<String> getCoursDocumentNamesByMatiereId(int matiereId) {
	    List<String> documentNames = new ArrayList<>();
	    String query = "SELECT nom_doc FROM document WHERE fk_matiere = ? AND typeDoc = 'cour' ORDER BY date_de_creation DESC";
	    try (Connection connection = DBUtil.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, matiereId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                documentNames.add(resultSet.getString("nom_doc"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return documentNames;
	}
	@Override
    public int addSalle(Salle salle) {
        int generatedId = -1;
        String query = "INSERT INTO salle (capacite, disponible, fk_centre) VALUES (?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, salle.getCapacite());
            preparedStatement.setBoolean(2, salle.isDisponible());
            //TODO : if you have more than one centre you should modify this 
            preparedStatement.setInt(3, getMainCentreId()); 

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        generatedId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return generatedId;
    }



    @Override
    public List<Salle> getAllSalles() {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salle";
        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
            	Salle salle = new Salle();
                salle.setIdSalle(resultSet.getInt("id_salle"));
                salle.setCapacite(resultSet.getInt("capacite"));
                salle.setDisponible(resultSet.getBoolean("disponible"));
                salle.setOccuper(resultSet.getBoolean("occuper"));
                salles.add(salle);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
        return salles;
    }

    @Override
    public Salle getSalleById(int salleId) {
        Salle salle = null;
        String query = "SELECT * FROM salle WHERE id_salle = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, salleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                	salle = new Salle();
                    salle.setIdSalle(resultSet.getInt("id_salle"));
                    salle.setCapacite(resultSet.getInt("capacite"));
                    salle.setDisponible(resultSet.getBoolean("disponible"));
                    salle.setOccuper(resultSet.getBoolean("occuper"));

                    return salle;                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salle;
    }

	@Override
	public int notAvailableSalle(int salleId,boolean newAvailability) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = DBUtil.getConnection();
			 preparedStatement = connection.prepareStatement("UPDATE salle SET disponible = ? WHERE id_salle = ?");
			 preparedStatement.setBoolean(1, newAvailability);
	            preparedStatement.setInt(2, salleId);

	            int rowsAffected = preparedStatement.executeUpdate();
	            return rowsAffected;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
		}
	}

	@Override
	public Utilisateur authentifier(String email, String password) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = " select * from utilisateur where email = ? and passwordd = ? ";
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()) {
				Utilisateur u = getUtilisateurById(rs.getInt("id_utilisateur"));
				return u;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {			}
		}
		return null;
	}

	

	@Override
	public List<Matiere> getAllMatieresOfEleve(int idEleve) {
		List<Matiere> matieres = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT m.* " +
                         "FROM matiere m " +
                         "JOIN affectation a ON m.id_matiere = a.fk_matiere " +
                         "WHERE a.fk_eleve = ?";

            connection = DBUtil.getConnection();

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idEleve);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Matiere matiere = new Matiere();
                    matiere.setIdMatiere(resultSet.getInt("id_matiere"));
                    matiere.setDescriptionMatiere(resultSet.getString("description_matiere"));
                    matiere.setPrix(resultSet.getFloat("prix"));
                    matiere.setDateDeCreation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());
                    Niveau niveau = getNiveauById(resultSet.getInt("fk_niveau"));
                    matiere.setNiveau(niveau);
                    matieres.add(matiere);
                }
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }

        return matieres;
    }

	

	@Override
	public List<Matiere> getAllMatieresOfProf(int idProf) {
		List<Matiere> matieres = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        connection = DBUtil.getConnection();

	        // Assuming enseignant_groupe_matiere has the foreign key fk_enseignant referencing id_enseignant in the enseignant table
	        String query = "SELECT m.* " +
	                       "FROM matiere m " +
	                       "JOIN enseignant_groupe_matiere egm ON m.id_matiere = egm.fk_matiere " +
	                       "WHERE egm.fk_enseignant = ?";
	        ps = connection.prepareStatement(query);
	        ps.setInt(1, idProf);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Matiere matiere = getMatiereById(rs.getInt("id_matiere"));
	            matieres.add(matiere);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return matieres;
	}

	@Override
	public List<Matiere> getAllMatieres() {
		 Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Matiere> matieres = new ArrayList<>();

	        try {
	            // SQL query to select all subjects
	            String sql = "SELECT * FROM matiere";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);

	                resultSet = preparedStatement.executeQuery();

	                while (resultSet.next()) {
	                    Matiere matiere = new Matiere();
	                    matiere.setIdMatiere(resultSet.getInt("id_matiere"));
	                    matiere.setDescriptionMatiere(resultSet.getString("description_matiere"));
	                    matiere.setPrix(resultSet.getFloat("prix"));
	                    matiere.setDateDeCreation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());
	                    Niveau niveau = getNiveauById(resultSet.getInt("fk_niveau"));
	                    matiere.setNiveau(niveau);

	                    matieres.add(matiere);
	                }

	                return matieres;
	            } catch (Exception e) {
	                e.printStackTrace();
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return null;
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (Exception e) {
	                e.printStackTrace(); 
	            }
	        }
	    }

	

	@Override
	public List<Matiere> getAllMatieresExcept(int idMatiere) {
		List<Matiere> matieres = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        connection = DBUtil.getConnection();

	        String query = "SELECT * FROM matiere WHERE id_matiere <> ?";
	        ps = connection.prepareStatement(query);
	        ps.setInt(1, idMatiere);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Matiere matiere = getMatiereById(rs.getInt("id_matiere"));
	            
	            matieres.add(matiere);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return matieres;
	}

	@Override
	public int addSpecialitiesToProf(Map<Matiere, List<Matiere>> specialities,int idProf) {
		
		 Connection connection = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        connection = DBUtil.getConnection();
		        connection.setAutoCommit(false);

		        String query = "INSERT INTO enseignant_specialite (fk_specialite, fk_enseignant, main) VALUES (?, ?, ?)";
		        ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		        for (Map.Entry<Matiere, List<Matiere>> entry : specialities.entrySet()) {
		            Matiere mainSpecialite = entry.getKey();
		            List<Matiere> relatedSpecialites = entry.getValue();
		            ps.setInt(1, mainSpecialite.getIdMatiere());
		            ps.setInt(2, idProf);
		            ps.setBoolean(3, true);
		            ps.executeUpdate();
		            rs = ps.getGeneratedKeys();
		            int mainSpecialiteId = 0;
		            if (rs.next()) {
		                mainSpecialiteId = rs.getInt(1);
		            }

		            for (Matiere relatedSpecialite : relatedSpecialites) {
		                ps.setInt(1, relatedSpecialite.getIdMatiere());
		                ps.setInt(2, idProf);
		                ps.setBoolean(3, false);
		                ps.executeUpdate();
		            }
		        }

		        connection.commit();
		        return 1; 
		    } catch (SQLException e) {
		        try {
		            if (connection != null) {
		                connection.rollback();
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        e.printStackTrace();
		        return 0;
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
	}



	@Override
	public int deleteMatiere(int matiereId) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM matiere WHERE id_matiere = ?";

            connection = DBUtil.getConnection();

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, matiereId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace(); 
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return 0;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
    }
	

		@Override
		public boolean validerEleve(int eleveId) {
			Connection connection = null;
		    PreparedStatement preparedStatement = null;

		    try {
		        String updateSql = "UPDATE eleve SET date_validation = ? WHERE id_eleve = ?";
		        String selectMatieresSql = "SELECT fk_matiere FROM affectation WHERE fk_eleve = ?";
		        String insertPaiementSql = "INSERT INTO paiement (fk_matiere, fk_eleve) VALUES (?, ?)";

		        connection = DBUtil.getConnection();
		        connection.setAutoCommit(false);

		        try {
		            // Update date_validation in the eleve table
		            preparedStatement = connection.prepareStatement(updateSql);
		            LocalDateTime currentDate = LocalDateTime.now();
		            preparedStatement.setTimestamp(1, Timestamp.valueOf(currentDate));
		            preparedStatement.setInt(2, eleveId);
		            preparedStatement.executeUpdate();

		            // Retrieve matiere IDs for the student
		            preparedStatement = connection.prepareStatement(selectMatieresSql);
		            preparedStatement.setInt(1, eleveId);
		            ResultSet resultSet = preparedStatement.executeQuery();

		            // Insert payment records for each matiere
		            preparedStatement = connection.prepareStatement(insertPaiementSql);
		            while (resultSet.next()) {
		                int matiereId = resultSet.getInt("fk_matiere");
		                preparedStatement.setInt(1, matiereId);
		                preparedStatement.setInt(2, eleveId);
		                preparedStatement.executeUpdate(); 
		            }

		            connection.commit();
		            return true;
		        } catch (Exception e) {
		            connection.rollback();
		            e.printStackTrace();
		            return false;
		        } finally {
		            connection.setAutoCommit(true); 
		            try {
		                if (preparedStatement != null) preparedStatement.close();
		                if (connection != null) connection.close();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
			 
		}

	

	

	@Override
	public Map<Integer, String> validerProf(int idProf) {
		//TODO : for this affect the prof to a random group in each matiere check his main specialities and check that the group doesnt have already an prof if you dont find a place by his main specialities use the other
		  Connection connection = null;
		    Map<Integer,String> result = new HashMap<>();

		    try {
		    	connection = DBUtil.getConnection();
		    	connection.setAutoCommit(false);

		        // Retrieve the main speciality of the professor
		        int mainSpecialityId = getMainSpecialityForProf(idProf, connection);

		        //Find and assign a group for the main speciality
		        boolean groupAssigned = assignGroupForSpeciality(idProf, mainSpecialityId,connection);
                
		        if (groupAssigned) {
		        	 String updateDateValidationQuery = "UPDATE enseignant SET date_validation = CURRENT_TIMESTAMP WHERE id_enseignant = ?";
		                try (PreparedStatement updateStatement = connection.prepareStatement(updateDateValidationQuery)) {
		                    updateStatement.setInt(1, idProf);
		                    updateStatement.executeUpdate();
		                }
		            connection.commit();
		            result.put(1, "the prof is assigned");
		            return result;  
		        }

		        //  If no group is found, try with other specialities
		        List<Integer> otherSpecialities = getOtherSpecialitiesNotMain(idProf,connection);

		        for (Integer otherSpeciality : otherSpecialities) {
		            groupAssigned = assignGroupForSpeciality(idProf, otherSpeciality,connection);

		            if (groupAssigned) {
		            	// Update date_validation in the enseignant table
		                String updateDateValidationQuery = "UPDATE enseignant SET date_validation = CURRENT_TIMESTAMP WHERE id_enseignant = ?";
		                try (PreparedStatement updateStatement = connection.prepareStatement(updateDateValidationQuery)) {
		                    updateStatement.setInt(1, idProf);
		                    updateStatement.executeUpdate();
		                }
		                connection.commit();
			            result.put(1, "the prof is assigned");
		                return result;  
		            }
		        }

		        //  No group is available for any speciality
		        result.put(0, "No group available");
		        connection.rollback();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        try {
		            if (connection != null) {
		                connection.rollback();
		            }
		        } catch (SQLException rollbackException) {
		            rollbackException.printStackTrace();
		        }
		        result.put(-1, "Error occurred during validation");
		    } finally {
		    	try {
		    		if(connection != null) connection.close();					
				} catch (SQLException e2) {
					result.put(-1, e2.getMessage());
				}
		    }

		    return result;
		
		

		 
	}



	private int getMainSpecialityForProf(int idProf, Connection connection) {
		 String query = "SELECT fk_specialite FROM enseignant_specialite WHERE fk_enseignant = ? AND main = 1";
		    PreparedStatement preparedStatement = null;
		    ResultSet resultSet = null;

		    try {
		        preparedStatement = connection.prepareStatement(query);
		        preparedStatement.setInt(1, idProf);

		        resultSet = preparedStatement.executeQuery();

		        if (resultSet.next()) {
		            return resultSet.getInt("fk_specialite");
		        }

		        return -1; 
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    } finally {
		        try {
		            if (resultSet != null) {
		                resultSet.close();
		            }
		            if (preparedStatement != null) {
		                preparedStatement.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace(); 
		        }
		    }
	}

	private List<Integer> getOtherSpecialitiesNotMain(int idProf, Connection connection) {
		String query = "SELECT fk_specialite FROM enseignant_specialite WHERE fk_enseignant = ? AND main = 0";
	    List<Integer> otherSpecialities = new ArrayList<>();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, idProf);

	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            int specialityId = resultSet.getInt("fk_specialite");
	            otherSpecialities.add(specialityId);
	        }

	        return otherSpecialities;
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    } finally {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private boolean assignGroupForSpeciality(int idProf, Integer otherSpeciality, Connection connection) {
		String query = "SELECT  a.fk_groupe " +
                "FROM affectation a " +
                "INNER JOIN groupe g ON a.fk_groupe = g.id_groupe " +
                "LEFT JOIN enseignant_groupe_matiere egm ON g.id_groupe = egm.fk_groupe " +
                "AND egm.fk_matiere = a.fk_matiere " +
                "WHERE egm.id_e_g_m is null AND a.fk_matiere = ?";

 try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
     preparedStatement.setInt(1, otherSpeciality);
     ResultSet resultSet = preparedStatement.executeQuery();

     if (resultSet.next()) {
         int groupId = resultSet.getInt("a.fk_groupe");

         // Insert the assignment into enseignant_groupe_matiere
         String insertQuery = "INSERT INTO enseignant_groupe_matiere (fk_enseignant, fk_groupe, fk_matiere) VALUES (?, ?, ?)";
         try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
             insertStatement.setInt(1, idProf);
             insertStatement.setInt(2, groupId);
             insertStatement.setInt(3, otherSpeciality);

             int rowsAffected = insertStatement.executeUpdate();

             return rowsAffected > 0;
         }
     }
 } catch (SQLException e) {
     e.printStackTrace();
 }

 return false;
	}

	@Override
	public List<Enseignant> getAllProfs() {
		 List<Enseignant> enseignants = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        connection = DBUtil.getConnection();
		        String query = "SELECT * FROM enseignant";
		        ps = connection.prepareStatement(query);
		        rs = ps.executeQuery();

		        while (rs.next()) {
		            Enseignant enseignant = getEnseignantById(rs.getInt("id_enseignant"));
		            
		            enseignants.add(enseignant);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return enseignants;
	}

	
	@Override
	public List<Eleve> getAllEleves() {
		List<Eleve> eleves = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        connection = DBUtil.getConnection();
	        String query = "SELECT * FROM eleve ";
	        ps = connection.prepareStatement(query);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Eleve eleve = new Eleve();
	            Utilisateur user = getUtilisateurById(rs.getInt("id_eleve"));
	            eleve.setIdEleve(rs.getInt("id_eleve"));
	            eleve.setDateValidation(rs.getTimestamp("date_validation")!= null ? rs.getTimestamp("date_validation").toLocalDateTime() : null );
	            eleve.setParent(getParentByEleve(rs.getInt("id_eleve")));
	            eleve.setNom(user.getNom());
	            eleve.setPrenom(user.getPrenom());
	            eleve.setEmail(user.getEmail());
	            eleve.setDateCreation(user.getDateCreation());
	            eleves.add(eleve);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return eleves;
	}

	@Override
	public int deleteProf(int profId) {
		 Connection connection = null;
		    PreparedStatement deleteEnseignant = null;
		    PreparedStatement deleteUtilisateur = null;

		    try {
		        connection = DBUtil.getConnection();
		        connection.setAutoCommit(false);

		        String deleteEnseignantQuery = "DELETE FROM enseignant WHERE id_enseignant = ?";
		        deleteEnseignant = connection.prepareStatement(deleteEnseignantQuery);
		        deleteEnseignant.setInt(1, profId);
		        int rowsAffectedEnseignant = deleteEnseignant.executeUpdate();
		        String deleteUtilisateurQuery = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
		        deleteUtilisateur = connection.prepareStatement(deleteUtilisateurQuery);
		        deleteUtilisateur.setInt(1, profId);
		        int rowsAffectedUtilisateur = deleteUtilisateur.executeUpdate();

		        connection.commit();

		        return Math.min(rowsAffectedEnseignant, rowsAffectedUtilisateur);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        try {
		            if (connection != null) {
		                connection.rollback();
		            }
		        } catch (SQLException rollbackException) {
		            rollbackException.printStackTrace();
		        }
		        return 0; 
		    } finally {
		        try {
		            if (deleteEnseignant != null) {
		                deleteEnseignant.close();
		            }
		            if (deleteUtilisateur != null) {
		                deleteUtilisateur.close();
		            }
		            if (connection != null) {
		                connection.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
	}

	@Override
	public int deleteEleve(int eleveId) {
		Connection connection = null;
	    PreparedStatement psEleve = null;
	    PreparedStatement psUtilisateur = null;

	    try {
	        connection = DBUtil.getConnection();
	        connection.setAutoCommit(false);
	        String deleteEleveQuery = "DELETE FROM eleve WHERE id_eleve = ?";
	        psEleve = connection.prepareStatement(deleteEleveQuery);
	        psEleve.setInt(1, eleveId);
	        int rowsAffectedEleve = psEleve.executeUpdate();
	        String deleteUtilisateurQuery = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
	        psUtilisateur = connection.prepareStatement(deleteUtilisateurQuery);
	        psUtilisateur.setInt(1, eleveId);
	        int rowsAffectedUtilisateur = psUtilisateur.executeUpdate();
	        connection.commit();

	        return Math.min(rowsAffectedEleve, rowsAffectedUtilisateur);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (connection != null) {
	                connection.rollback();
	            }
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }

	        return 0;
	    } finally {
	        try {
	            if (psEleve != null) psEleve.close();
	            if (psUtilisateur != null) psUtilisateur.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public int payMatiere(int matiereId, int eleveId) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String sql = "INSERT INTO paiement (fk_matiere, fk_eleve) VALUES (?, ?)";

            connection = DBUtil.getConnection();

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, matiereId);
                preparedStatement.setInt(2, eleveId);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace(); 
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return 0;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
	}

	@Override
	public List<Eleve> getAllElevesByCriteria(Integer niveauId, Integer matiereId, Integer groupId) {
		 Connection connection = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        connection = DBUtil.getConnection();
		        StringBuilder query = new StringBuilder("SELECT e.* FROM eleve e " +
		                                                "INNER JOIN affectation a ON e.id_eleve = a.fk_eleve " +
		                                                "INNER JOIN groupe g ON a.fk_groupe = g.id_groupe " +
		                                                "INNER JOIN matiere m ON a.fk_matiere = m.id_matiere " +
		                                                "INNER JOIN niveau n ON g.fk_niveau = n.id_niveau " +
		                                                "WHERE 1=1");

		        if (niveauId != null) {
		            query.append(" AND n.id_niveau = ?");
		        }
		        if (matiereId != null) {
		            query.append(" AND m.id_matiere = ?");
		        }
		        if (groupId != null) {
		            query.append(" AND g.id_groupe = ?");
		        }

		        ps = connection.prepareStatement(query.toString());

		        // Set parameters based on the provided criteria
		        int paramIndex = 1;
		        if (niveauId != null) {
		            ps.setInt(paramIndex++, niveauId);
		        }
		        if (matiereId != null) {
		            ps.setInt(paramIndex++, matiereId);
		        }
		        if (groupId != null) {
		            ps.setInt(paramIndex, groupId);
		        }

		        rs = ps.executeQuery();

		        List<Eleve> eleves = new ArrayList<>();

		        while (rs.next()) {
		            Eleve eleve = getEleveById(rs.getInt("id_eleve"));		            
		            eleves.add(eleve);
		        }

		        return eleves;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null;
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
	}

	@Override
	public int addEleve(Map<String, Object> attributes) {
		
		try (Connection connection = DBUtil.getConnection();
				
		         PreparedStatement addUtilisateur = connection.prepareStatement(
		                 "INSERT INTO utilisateur (nom, prenom, email, passwordd, age, phone, sexe, type_util, adresse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
		                 Statement.RETURN_GENERATED_KEYS);
		         PreparedStatement addParent = connection.prepareStatement(
		                 "INSERT INTO utilisateur (nom, email, passwordd, adresse, phone, sexe, type_util, prenom) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
		                 Statement.RETURN_GENERATED_KEYS);
		         PreparedStatement addParent2 = connection.prepareStatement("INSERT INTO parent (id_parent) VALUES (?)");
		         PreparedStatement addEleve = connection.prepareStatement("INSERT INTO eleve (id_eleve, date_validation, fk_parent) VALUES (?, ?, ?)");
		         PreparedStatement addAffectation = connection.prepareStatement(
		                 "INSERT INTO affectation (fk_eleve, fk_groupe, fk_matiere) VALUES (?, ?, ?)")) {
			 if (connection.getAutoCommit()) {
		            connection.setAutoCommit(false);
		        }
		        // Set parameters for Utilisateur
		        addUtilisateur.setString(1, (String) attributes.get("nom"));
		        addUtilisateur.setString(2, (String) attributes.get("prenom"));
		        addUtilisateur.setString(3, (String) attributes.get("email"));
		        addUtilisateur.setString(4, (String) attributes.get("password"));
		        addUtilisateur.setInt(5, (int) attributes.get("age"));
		        addUtilisateur.setString(6, (String) attributes.get("phone"));
		        addUtilisateur.setString(7, String.valueOf(attributes.get("sexe")));
		        addUtilisateur.setString(8, "student");
		        addUtilisateur.setString(9, (String) attributes.get("adresse"));

		        // Execute the INSERT statement for Utilisateur
		        addUtilisateur.executeUpdate();

		        // Get the generated user ID
		        int userId;
		        try (ResultSet generatedUserKeys = addUtilisateur.getGeneratedKeys()) {
		            if (generatedUserKeys.next()) {
		                userId = generatedUserKeys.getInt(1);
		            } else {
		                throw new SQLException("Failed to get the generated user ID.");
		            }
		        }

		        // Set parameters for Parent
		        addParent.setString(1, (String) attributes.get("parentName"));
		        addParent.setString(2, (String) attributes.get("parentEmail"));
		        addParent.setString(3, generateRandomPassword(8));
		        addParent.setString(4, (String) attributes.get("parentAddress"));
		        addParent.setString(5, (String) attributes.get("phone"));
		        addParent.setString(6, String.valueOf(attributes.get("parentGender")));
		        addParent.setString(7, "prent");
		        addParent.setString(8, (String) attributes.get("parentPrenome"));

		        // Execute the INSERT statement for Parent
		         addParent.executeUpdate();

		        // Get the generated parent ID
		        int parentId;
		        try (ResultSet generatedParentKeys = addParent.getGeneratedKeys()) {
		            if (generatedParentKeys.next()) {
		                parentId = generatedParentKeys.getInt(1);
		            } else {
		                throw new SQLException("Failed to get the generated parent ID.");
		            }
		        }

		        // Execute the INSERT statement for Parent2
		        addParent2.setInt(1, parentId);
		        addParent2.executeUpdate();

		        // Set parameters for Eleve
		        addEleve.setInt(1, userId);
		        addEleve.setObject(2, null); 
		        addEleve.setInt(3, parentId);

		        // Execute the INSERT statement for Eleve
		        addEleve.executeUpdate();

		        // Add Affectation
		        List<Integer> matieres = (List<Integer>) attributes.get("matieres");
		        int levelId = (int) attributes.get("Level");

		        // iterate every matiere for eleves
		        for (Integer matiereId : matieres) {
		            int groupId = createOrGenerateRandomGroupFromLevel(levelId, connection, matiereId);
		            addAffectation.setInt(1, userId);
		            addAffectation.setInt(2, groupId);
		            addAffectation.setInt(3, matiereId);
		            addAffectation.executeUpdate(); // Assuming no generated keys needed
		        }

		        connection.commit();

		        return userId;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return 0;
		    }
	}

	private String generateRandomPassword(int i) {
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?/[]{},.:;";
        String allChars = upperCase + numbers + specialChars;

        StringBuilder password = new StringBuilder();
        password.append(upperCase.charAt((int) (Math.random() * upperCase.length())));
        password.append(numbers.charAt((int) (Math.random() * numbers.length())));
        password.append(specialChars.charAt((int) (Math.random() * specialChars.length())));

        for (int i1 = 3; i1 < i; i1++) {
            password.append(allChars.charAt((int) (Math.random() * allChars.length())));
        }

        return password.toString();
	}

	@Override
	public int addProf(Map<String, Object> attributes) {
		
		 Connection connection = null;
		    PreparedStatement addUtilisateur = null;
		    PreparedStatement addEnseignant = null;
		    PreparedStatement addSpecialities = null;

		    try {
		    	 connection = DBUtil.getConnection();
		         connection.setAutoCommit(false);


		        String addUtilisateurQuery = "INSERT INTO utilisateur (nom, prenom, email, passwordd, age, adresse, phone, sexe, type_util) " +
		                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        addUtilisateur = connection.prepareStatement(addUtilisateurQuery, Statement.RETURN_GENERATED_KEYS);

		        // Set parameters for Utilisateur
		        addUtilisateur.setString(1, (String) attributes.get("nom"));
		        addUtilisateur.setString(2, (String) attributes.get("prenom"));
		        addUtilisateur.setString(3, (String) attributes.get("email"));
		        addUtilisateur.setString(4, (String) attributes.get("password"));
		        addUtilisateur.setInt(5, (int) attributes.get("ageProf"));
		        addUtilisateur.setString(6, (String) attributes.get("adresse"));
		        addUtilisateur.setString(7, (String) attributes.get("phoneProf"));
		        addUtilisateur.setString(8, String.valueOf(attributes.get("profGender")));
		        addUtilisateur.setString(9, "professor");

		        addUtilisateur.executeUpdate();

		        // Get the generated user ID
		        ResultSet generatedUserKeys = addUtilisateur.getGeneratedKeys();
		        int userId = -1;
		        if (generatedUserKeys.next()) {
		            userId = generatedUserKeys.getInt(1);
		        } else {
		            throw new SQLException("Failed to get the generated user ID.");
		        }

		        // Step 3: Add Enseignant
		        String addEnseignantQuery = "INSERT INTO enseignant (id_enseignant, avec_experience, date_validation,fk_centre) " +
		                "VALUES (?, ?, ?,?)";
		        addEnseignant = connection.prepareStatement(addEnseignantQuery);

		        addEnseignant.setInt(1, userId);
		        addEnseignant.setBoolean(2, (boolean) attributes.get("avecExperience"));
		        addEnseignant.setObject(3, null);
		        
		        addEnseignant.setInt(4, getMainCentreId());


		         addEnseignant.executeUpdate();
		     // Step 3: Add Specialities
		        String addSpecialitiesQuery = "INSERT INTO enseignant_specialite (fk_specialite, fk_enseignant, main) VALUES (?, ?, ?)";
		        addSpecialities = connection.prepareStatement(addSpecialitiesQuery);

		        int mainSpecialty = (int) attributes.get("mainSpecialty");
		        addSpecialities.setInt(1, mainSpecialty);
	            addSpecialities.setInt(2, userId);
	            addSpecialities.setBoolean(3, true);
	            addSpecialities.executeUpdate();
		        List<Integer> specialities =  attributes.get("specialities") != null ? (List<Integer>) attributes.get("specialities") : new ArrayList<>();
		        if(!specialities.isEmpty()) {
		        	  for (Integer speciality : specialities) {
				            addSpecialities.setInt(1, speciality);
				            addSpecialities.setInt(2, userId);
				            addSpecialities.setBoolean(3, false);
				            addSpecialities.executeUpdate();
				        }
		          }
		        
		  

		        connection.commit();

		        return userId;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        try {
		            if (connection != null) {
		                connection.rollback();
		            }
		        } catch (SQLException rollbackException) {
		            rollbackException.printStackTrace();
		        }
		        return 0;
		    } finally {
		    	 try {

		                if (addEnseignant != null) {
		                    addEnseignant.close();
		                }
		                if (addUtilisateur != null) {
		                	addUtilisateur.close();
		                }
		               

		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
	



	@Override
	public int updateSeance(int idSession) {
		//TODO : update seance
		return 0;
	}
// this is for agents
	@Override
	public int deleteSeance(int idSession) {
		 Connection connection = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            String sql = "DELETE FROM seance WHERE id_seance = ?";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idSession);

	                int rowsAffected = preparedStatement.executeUpdate();

	                return rowsAffected;
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	                return 0;
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return 0;
	        } finally {
	            try {
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }
	    }	
//this is for prof just uppdate a field annule true and post add it in ProfSchedule
	@Override
	public int closeSeance(int idSession,String reason,int idProf) {
		Connection connection = null;
        PreparedStatement updateSeanceStatement = null;
        PreparedStatement insertProfScheduleStatement = null;

        try {
            String updateSeanceSql = "UPDATE seance SET annule = true WHERE id_seance = ?";
            String insertProfScheduleSql = "INSERT INTO prof_schedule (fk_enseignant, specific_date, start_time, end_time, is_whole_day, reason) VALUES (?, ?, ?, ?, ?, ?)";

            connection = DBUtil.getConnection();

            try {
                connection.setAutoCommit(false);

                updateSeanceStatement = connection.prepareStatement(updateSeanceSql);
                updateSeanceStatement.setInt(1, idSession);
                int updateSeanceRowsAffected = updateSeanceStatement.executeUpdate();

                Seance seance = getSeanceById(idSession);
                
                insertProfScheduleStatement = connection.prepareStatement(insertProfScheduleSql);
                insertProfScheduleStatement.setInt(1, idProf);
                insertProfScheduleStatement.setTimestamp(2, Timestamp.valueOf(seance.getDateSeance()));
                insertProfScheduleStatement.setTime(3, Time.valueOf(seance.getHeureDebut()));
                insertProfScheduleStatement.setTime(4, Time.valueOf(seance.getHeureFin()));
                insertProfScheduleStatement.setBoolean(5, false); 
                insertProfScheduleStatement.setString(6, reason);
                int insertProfScheduleRowsAffected = insertProfScheduleStatement.executeUpdate();

                if (updateSeanceRowsAffected > 0 && insertProfScheduleRowsAffected > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                }

                return updateSeanceRowsAffected;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace(); 
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return 0;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (updateSeanceStatement != null) updateSeanceStatement.close();
                if (insertProfScheduleStatement != null) insertProfScheduleStatement.close();
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }	}

	@Override
	public int closeDayOrHours(int prof, profSchedule profSchedul) {
		
		
		
		 int affectedRows = 0;
		 Connection connection = null;
	        try {
	   		    connection = DBUtil.getConnection();
               
	            connection.setAutoCommit(false); // Start transaction
                 
	           
	            	
	            	
	            	
	            	
	            
	            	 String updateQuery = "UPDATE seance s JOIN enseignant_groupe_matiere egm ON s.fk_groupe = egm.fk_groupe SET s.annule = ? "
	 	            		+ "WHERE egm.fk_enseignant = ? AND Date(s.date_seance) = ? AND s.heure_debut >= ? AND s.heure_fin <= ?";
	 	            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
	 	            updateStatement.setBoolean(1, true);
	 	            updateStatement.setInt(2, prof);
	 	            updateStatement.setDate(3, (java.sql.Date) profSchedul.getSpecificDate()); 
	 	            updateStatement.setTime(4, profSchedul.getStartTime()); 
	 	            updateStatement.setTime(5, profSchedul.getEndTime()); 
	 	            affectedRows += updateStatement.executeUpdate();
	 	            updateStatement.close();
	            	
	        
	           

	            // Insert new sessions if necessary
	            if (!profSchedul.isWholeDay()) {
	                String insertQuery = "INSERT INTO prof_schedule (start_time, end_time, is_whole_day,specific_date, fk_enseignant) VALUES ( ?, ?, ?, ?)";
	                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
	                insertStatement.setTime(1, profSchedul.getStartTime());
	                insertStatement.setTime(2, profSchedul.getEndTime());
	                insertStatement.setBoolean(3, profSchedul.isWholeDay()); 
	                insertStatement.setDate(4, (java.sql.Date) profSchedul.getSpecificDate());
	                insertStatement.setInt(5, prof); 
	                affectedRows += insertStatement.executeUpdate();
	                insertStatement.close();
	            }

	            connection.commit(); 
	        } catch (SQLException e) {
	            try {
	                connection.rollback(); 
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            try {
	                connection.setAutoCommit(true); 
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return affectedRows;
	    }
	
	//this is for the agent

	@Override
	public List<Seance> getAllSeanceByCurrentWeek() {
        List<Seance> seances = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	 LocalDateTime currentDateTime = LocalDateTime.now();
             LocalDateTime startOfWeek = currentDateTime.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
             LocalDateTime endOfWeek = currentDateTime.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59);
        	String sql = "select * from seance where date_seance between ? and ?";
        	connection = DBUtil.getConnection();
        	ps = connection.prepareStatement(sql);
        	ps.setObject(1, startOfWeek);
        	ps.setObject(2, endOfWeek);
			rs = ps.executeQuery();
			while(rs.next()) {
				Seance seance = getSeanceById(rs.getInt("id_seance"));
				seances.add(seance);	
			}
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }

        return seances;
	}
	
	
	//this return the day name and Seance

		@Override
		public Map<String, List<Seance>> getAllSeanceByCurrentWeekMap() {
			Map<String, List<Seance>> seances = new HashMap<>();
			 seances.put("MONDAY", new ArrayList<>());
			 seances.put("TUESDAY", new ArrayList<>());
			 seances.put("WEDNESDAY", new ArrayList<>());
			 seances.put("THURSDAY", new ArrayList<>());
			 seances.put("FRIDAY", new ArrayList<>());
			 seances.put("SATURDAY", new ArrayList<>());
			 seances.put("SUNDAY", new ArrayList<>());
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	        	LocalDateTime currentDate = LocalDateTime.now();
	        	LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
	        	LocalDateTime endOfWeek = currentDate.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59);
	        	String query = "SELECT * FROM seance WHERE date_seance BETWEEN ? AND ?";
	        	connection = DBUtil.getConnection();
	        	ps = connection.prepareStatement(query);
	        	ps.setObject(1, startOfWeek);
	        	ps.setObject(2, endOfWeek);
	        	rs = ps.executeQuery();
	        	while (rs.next()) {
	            Seance seance = getSeanceById(rs.getInt("id_seance"));
		        String key = seance.getDateSeance().getDayOfWeek().toString();
		        seances.computeIfAbsent(key,e -> new ArrayList<>()).add(seance);
		        
				}
	        			
	        }catch(SQLException e) {
	        	e.printStackTrace();
	        }finally {
	        	try {
					if(rs != null) rs.close();
					if(ps != null) ps.close();
					if(connection != null) connection.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					}
	        }

			return seances;
		}
		// this for return the all sessions of a prof
		@Override
		public Map<String, List<Seance>> getAllSeanceByCurrentWeekMapByIdProf(int idProf) {
			
			Map<String, List<Seance>> seances = new HashMap<>();
			 seances.put("MONDAY", new ArrayList<>());
			 seances.put("TUESDAY", new ArrayList<>());
			 seances.put("WEDNESDAY", new ArrayList<>());
			 seances.put("THURSDAY", new ArrayList<>());
			 seances.put("FRIDAY", new ArrayList<>());
			 seances.put("SATURDAY", new ArrayList<>());
			 seances.put("SUNDAY", new ArrayList<>());
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	        	LocalDateTime currentDate = LocalDateTime.now();
	        	LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
	        	LocalDateTime endOfWeek = currentDate.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59);
	        	String query = "select id_seance from seance s inner join enseignant_groupe_matiere egm  on s.fk_groupe = egm.fk_groupe where egm.fk_enseignant = ?"
	        			+ " and date_seance BETWEEN ? AND ?";
	        	connection = DBUtil.getConnection();
	        	ps = connection.prepareStatement(query);
	        	ps.setInt(1, idProf);
	        	ps.setObject(2, startOfWeek);
	        	ps.setObject(3, endOfWeek);
	        	rs = ps.executeQuery();
	        	while (rs.next()) {
	            Seance seance = getSeanceById(rs.getInt("id_seance"));
		        String key = seance.getDateSeance().getDayOfWeek().toString();
		        seances.computeIfAbsent(key,e -> new ArrayList<>()).add(seance);
		        
				}
	        			
	        }catch(SQLException e) {
	        	e.printStackTrace();
	        }finally {
	        	try {
					if(rs != null) rs.close();
					if(ps != null) ps.close();
					if(connection != null) connection.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					}
	        }

			return seances;
		}
		

	@Override
	public List<Seance> getAllSeanceByDay(String day) {
		//TODO
		
		return null;
	}

	@Override
	public List<Seance> getAllSeancesByEleve(int idEleve) {
		 List<Seance> seances = new ArrayList<>();
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            String sql = "SELECT s.* " +
	                         "FROM seance s " +
	                         "JOIN affectation a ON s.fk_groupe = a.fk_groupe " +
	                         "WHERE a.fk_eleve = ?";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idEleve);

	                resultSet = preparedStatement.executeQuery();

	                while (resultSet.next()) {
	                    Seance seance = new Seance();
	                    seance.setIdSeance(resultSet.getInt("id_seance"));
	                    seance.setHeureDebut(resultSet.getTime("heure_debut").toLocalTime());
	                    seance.setHeureFin(resultSet.getTime("heure_fin").toLocalTime());
	                    seance.setAnnule(resultSet.getBoolean("annule"));
	                    seance.setMiseAJour(resultSet.getBoolean("mise_a_jour"));
	                    seance.setDateSeance(resultSet.getTimestamp("date_seance").toLocalDateTime());
	                    Salle salle = getSalleById(resultSet.getInt("fk_salle"));
	                    seance.setSalle(salle);
	                    Groupe groupe = getGroupeById(resultSet.getInt("fk_groupe"));
	                    seance.setGroupe(groupe);
	                    seance.setReason(resultSet.getString("reason"));

	                    seances.add(seance);
	                }
	            } catch (Exception e) {
	                e.printStackTrace(); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (Exception e) {
	                e.printStackTrace(); 
	            }
	        }

	        return seances;	}

	@Override
	public int updateProfile(Map<String,Object> values){
		
		Connection connection = null;
	    PreparedStatement statement = null;
	    int rowsAffected = 0;

	    try {
	        connection = DBUtil.getConnection();

	        StringBuilder sql = new StringBuilder("UPDATE utilisateur SET");

	        if (values.containsKey("email") ) {
	            sql.append(" email=?,");
	        }
	        if (values.containsKey("password") ) {
	            sql.append(" passwordd=?,");
	        }
	        if (values.containsKey("age") ) {
	            sql.append(" age=?,");
	        }
	        if (values.containsKey("adr") ) {
	            sql.append(" adresse=?,");
	        }
	        if (values.containsKey("phone") ) {
	            sql.append(" phone=?,");
	        }

	        // Remove the trailing comma from the query
	        sql.deleteCharAt(sql.length() - 1);

	        sql.append(" WHERE id_utilisateur=?");

	        statement = connection.prepareStatement(sql.toString());
	        int parameterIndex = 1;
	        if (values.get("email") != null) {
	            statement.setString(parameterIndex++, (String) values.get("email"));
	        }
	        if (values.get("password") != null) {
	            statement.setString(parameterIndex++, (String) values.get("password"));
	        }
	        if (values.containsKey("age")) {
	            statement.setInt(parameterIndex++, (int)values.get("age"));
	        }
	        if (values.get("adr") != null) {
	            statement.setString(parameterIndex++, (String) values.get("adr"));
	        }
	        if (values.get("phone") != null) {
	            statement.setString(parameterIndex++, (String) values.get("phone"));
	        }

	        statement.setInt(parameterIndex, (int) values.get("id_utile"));
	        System.out.println("SQL Query: " + statement.toString());

	        for (Map.Entry<String, Object> entry : values.entrySet()) {
	            System.out.println("Parameter " + entry.getKey() + ": " + entry.getValue());
	        }

	        rowsAffected = statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return rowsAffected;
	}

	@Override
	public Parent getParentByEleve(int idEleve) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "SELECT p.* FROM parent p JOIN eleve e ON p.id_parent = e.fk_parent WHERE e.id_eleve = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idEleve);
			rs = ps.executeQuery();
			if (rs.next()) {
				Parent p = new Parent();
				p.setIdParent(rs.getInt("id_parent"));
				Utilisateur u=getUtilisateurById(rs.getInt("id_parent"));
				p.setAdresse(u.getAdresse());
				p.setEmail(u.getEmail());
				p.setNom(u.getNom());
				p.setPrenom(u.getPrenom());
				p.setPhone(u.getPhone());
				p.setAge(u.getAge());
				p.setRolee(u.getRolee());
				p.setPassword(u.getPassword());
				
				
				return p;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
					if(ps != null) ps.close();
					if(connection != null) connection.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		return null;
	}


	
	@Override
	public Utilisateur getUtilisateurById(int idUtilisateur) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUtilisateur);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToUtilisateur(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
        	try {
        		if(resultSet != null) {resultSet.close();}
				if(preparedStatement != null)  preparedStatement.close();
				if(connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }

		
		return null;
	}
	private Utilisateur mapResultSetToUtilisateur(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
        utilisateur.setNom(resultSet.getString("nom"));
        utilisateur.setPrenom(resultSet.getString("prenom"));
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setPassword(resultSet.getString("passwordd"));
        utilisateur.setAge(resultSet.getInt("age") );
        utilisateur.setAdresse(resultSet.getString("adresse") == null ? " undefined" : resultSet.getString("adresse"));
        utilisateur.setPhone(resultSet.getString("phone"));
        utilisateur.setSexe(resultSet.getString("sexe"));
        utilisateur.setRolee(resultSet.getString("rolee"));
        utilisateur.setDateCreation(resultSet.getDate("dateCreation"));
        utilisateur.setTypeUtil(resultSet.getString("type_util"));
        return utilisateur;
    }

	@Override
	public Parent getParentById(int idParent) {
		Utilisateur u = getUtilisateurById(idParent);
		List<Eleve> children = getElevesByIdParent(idParent);
		Parent p = new Parent();
		p.setEnfants(children);
		p.setAdresse(u.getAdresse());
		p.setAge(u.getAge());
		p.setEmail(u.getEmail());
		p.setPassword(u.getPassword());
		p.setPrenom(u.getPrenom());
		p.setPhone(u.getPhone());
		p.setDateCreation(u.getDateCreation());
		p.setRolee(u.getRolee());
		p.setSexe(u.getSexe());
		p.setTypeUtil(u.getTypeUtil());
		return p;
	}

	@Override
	public List<Eleve> getElevesByIdParent(int idParent) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Eleve> eleves = new ArrayList<>();
		try { String query = " select id_eleve from eleve where fk_parent=?";
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(query);
			ps.setInt(1, idParent);
			rs = ps.executeQuery();		
			while (rs.next()) {
				Eleve e = getEleveById(rs.getInt("id_eleve"));
				
				
				eleves.add(e);
				//System.out.println("i am in getElevesByIdParent   " + eleves.isEmpty());
			}}catch(SQLException  e) {
				e.printStackTrace();
			}finally {
					try {
						if(rs != null) rs.close();
						if(ps != null) ps.close();
						if(connection != null) connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}			
			}
		
		return eleves;
	}

	@Override
	public int isPayThisMonth(int idEleve , int idMatiere) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
        int result = 0;
        try {
        	String query = "select count(*) from paiement where fk_eleve = ? and fk_matiere = ? and  MONTH(date_paiment) = MONTH(NOW())";
        	connection = DBUtil.getConnection();
        	ps = connection.prepareStatement(query);
        	ps.setInt(1, idEleve);
        	ps.setInt(2, idMatiere);
        	rs = ps.executeQuery();
        	
        	if(rs.next()) {
        		result = rs.getInt(1);
        	}
        }catch(SQLException e) {
        	e.printStackTrace();
        }finally{
        	try {
        		if(rs != null) rs.close();
        		if(ps != null) ps.close();
        		if(connection != null) connection.close();
				
			} catch (SQLException e) {}
        }
        
        
		return result;
	}

	@Override
	public int isExistEmail(String email) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			String query = " select * from utilisateur where email = ? ";
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
		    rs.last();
			count = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            try {
            	if(rs != null) rs.close();
            	if(ps != null) ps.close();
            	if(connection != null) connection.close();	
			} catch (Exception e) { e.printStackTrace();}
		}
		return count;
	}
	
	@Override
	public Seance getSeanceById(int idSeance) {
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	          
	            String sql = "SELECT * FROM seance where id_seance=?";

	            connection = DBUtil.getConnection();
	            
            try {
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setInt(1, idSeance) ;
	                

	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	               		    Seance seance = new Seance();
	                        seance.setIdSeance(resultSet.getInt("id_seance"));
	                        seance.setHeureDebut(resultSet.getTime("heure_debut").toLocalTime());
	                        seance.setHeureFin(resultSet.getTime("heure_fin").toLocalTime());
	                        seance.setAnnule(resultSet.getBoolean("annule"));
	                        seance.setMiseAJour(resultSet.getBoolean("mise_a_jour"));
	                        seance.setDateSeance(resultSet.getTimestamp("date_seance").toLocalDateTime());
	                        Groupe group = getGroupeById(resultSet.getInt("fk_groupe"));
	                        seance.setGroupe(group);
	                        Salle salle = getSalleById(resultSet.getInt("fk_salle"));
	                        seance.setSalle(salle);
	                        return seance;
	                    }
	                }
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return null;
	    }

	@Override
	public Salle getSalleByIdSeance(int idSeance) {
		Salle salle = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT s.* FROM salle s JOIN seance se ON s.id_salle = se.fk_salle WHERE se.id_seance = ?";

            connection = DBUtil.getConnection();
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idSeance);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    salle = getSalleById(resultSet.getInt("id_salle"));
                    
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }

        return salle;
    }
	

	@Override
	public Groupe getGroupeByIdSeance(int idSeance) {
		 Groupe groupe = null;
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        try {
	            String sql = "SELECT g.* FROM groupe g " +
	                         "JOIN seance s ON g.id_groupe = s.fk_groupe " +
	                         "WHERE s.id_seance = ?";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idSeance);

	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    groupe = getGroupeById(resultSet.getInt("id_groupe"));
	                   
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return groupe;
	    }

		@Override
		public Groupe getGroupeById(int idGroupe) {
			Groupe groupe = null;
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            String sql = "SELECT * FROM groupe WHERE id_groupe = ?";
	            
	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idGroupe);

	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    groupe = new Groupe();
	                    groupe.setIdGroupe(resultSet.getInt("id_groupe"));
	                    groupe.setCapacite(resultSet.getInt("capacite"));
	                    groupe.setNbrEleve(resultSet.getInt("nbr_eleve"));
	                    groupe.setDescriptionGroupe(resultSet.getString("description_groupe"));
	                    groupe.setDateDeCreation(resultSet.getTimestamp("date_de_creation"));
	                    Classe classe = getClasseById(resultSet.getInt("fk_classe"));
	                    groupe.setClasse(classe);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	     
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return groupe;
	    }

		@Override
		public Classe getClasseById(int idClasse) {
			Classe classe = null;
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            String sql = "SELECT * FROM classe WHERE id_classe = ?";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idClasse);

	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    classe = new Classe();
	                    classe.setIdClasse(resultSet.getInt("id_classe"));
	                    classe.setDescriptionClasse(resultSet.getString("description_classe"));
	                    classe.setMainClasse(resultSet.getBoolean("main_classe"));
	                    classe.setNiveau(getNiveauById(resultSet.getInt("fk_niveau"))   );
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return classe;
		}

		@Override
		public Niveau getNiveauById(int idNiveau) {
			Niveau niveau = null;
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            String sql = "SELECT * FROM niveau WHERE id_niveau = ?";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idNiveau);

	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    niveau = new Niveau();
	                    niveau.setIdNiveau(resultSet.getInt("id_niveau"));
	                    niveau.setDescriptionNiveau(resultSet.getString("descriptionNiveau"));
	                    niveau.setDateDeCreation(resultSet.getTimestamp("dateDeCreation"));

	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return niveau;		}

		@Override
		public int saveNiveau(Niveau niveau) {
			 Connection connection = null;
			    PreparedStatement preparedStatement = null;

			    try {
			        String sql = "INSERT INTO niveau (descriptionNiveau, fk_centre) VALUES (?, ?)";

			        connection = DBUtil.getConnection();

			        try {
			            connection.setAutoCommit(false);
			            preparedStatement = connection.prepareStatement(sql);
			            preparedStatement.setString(1, niveau.getDescriptionNiveau());
			            
			            int mainCentreId = getMainCentreId();
			            if (mainCentreId != 0) {
			                preparedStatement.setInt(2, mainCentreId);
			            } else {
			                connection.rollback();
			                return 0;
			            }

			            int rowsAffected = preparedStatement.executeUpdate();
			            connection.commit();

			            return rowsAffected;
			        } catch (SQLException e) {
			            connection.rollback();
			            e.printStackTrace();
			            return 0;
			        }
			    } catch (Exception e) {
			        e.printStackTrace();
			        return 0;
			    } finally {
			        try {
			            if (connection != null) {
			                connection.setAutoCommit(true);
			                connection.close();
			            }
			            if (preparedStatement != null) preparedStatement.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }
	    }

		@Override
		public int getMainCentreId() {
			  Connection connection = null;
		        PreparedStatement preparedStatement = null;
		        ResultSet resultSet = null;

		        try {
		            String sql = "SELECT id_centre FROM centre WHERE main_centre = 1";

		            connection = DBUtil.getConnection();

		            try {
		                preparedStatement = connection.prepareStatement(sql);
		                resultSet = preparedStatement.executeQuery();

		                if (resultSet.next()) {
		                    return resultSet.getInt("id_centre");
		                } else {
		                    
		                    return 0; 
		                }
		            } catch (SQLException e) {
		                e.printStackTrace(); 
		                return 0; 
		            }
		        } catch (Exception e) {
		            e.printStackTrace(); 
		            return 0; 
		        } finally {
		            try {
		                if (resultSet != null) resultSet.close();
		                if (preparedStatement != null) preparedStatement.close();
		                if (connection != null) connection.close();
		            } catch (SQLException e) {
		                e.printStackTrace(); 
		            }
		        }
		    }

		@Override
		public Matiere getMatiereById(int idMatiere) {
			Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            String sql = "SELECT * FROM matiere WHERE id_matiere = ?";
	            connection = DBUtil.getConnection();
	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idMatiere);
	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    Matiere matiere = new Matiere();
	                    matiere.setIdMatiere(resultSet.getInt("id_matiere"));
	                    matiere.setDescriptionMatiere(resultSet.getString("description_matiere"));
	                    matiere.setPrix(resultSet.getFloat("prix"));
	                    matiere.setDateDeCreation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());
	                    Niveau niveau = getNiveauById(resultSet.getInt("fk_niveau"));
	                    matiere.setNiveau(niveau);
	                    return matiere;
	                } else {
	                    return null;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return null;
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (Exception e) {
	                e.printStackTrace(); 
	            }
	        }
	    }

		@Override
		public Map<Integer, Object> addSeance(Seance seance, Groupe group, Salle salle) {
			Map<Integer, Object> map = new HashMap<>();
		    Connection connection = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        connection = DBUtil.getConnection();
		        connection.setAutoCommit(false);

		        // 1. Check salle availability
		        if (!isSalleAvailable(connection,salle, seance.getDateSeance(), seance.getHeureDebut(), seance.getHeureFin())) {
		            map.put(0, "Salle not available at the given date and time");
		            return map;
		        }

		        // 2. Check group availability
		        if (!isGroupAvailable( connection,group, seance.getDateSeance(), seance.getHeureDebut(), seance.getHeureFin())) {
		            map.put(0, "Group already has a seance at the given date and time");
		            return map;
		        }
                 int idProf = getEnseignantIdByGroupe(connection,group.getIdGroupe());
		        // 3. Check professor availability
		        if (!isProfessorAvailable(connection,idProf, seance.getDateSeance(), seance.getHeureDebut(), seance.getHeureFin())) {
		            map.put(0, "Professor is not available at the given date and time");
		            return map;
		        }

		        // 4. Check each student availability
		        if (!areStudentsAvailable(connection, group, seance.getDateSeance(), seance.getHeureDebut(), seance.getHeureFin())) {
		            map.put(0, "One or more students have a seance at the given date and time");
		            return map;
		        }
		        
		        String insertSeanceQuery = "INSERT INTO seance (heure_debut, heure_fin, annule, mise_a_jour, date_seance, fk_salle, fk_groupe) VALUES (?, ?, ?, ?, ?, ?, ?)";
		        ps = connection.prepareStatement(insertSeanceQuery, Statement.RETURN_GENERATED_KEYS);

		        ps.setTime(1, Time.valueOf(seance.getHeureDebut()));
		        ps.setTime(2, Time.valueOf(seance.getHeureFin()));
		        ps.setBoolean(3, seance.isAnnule());
		        ps.setBoolean(4, seance.isMiseAJour());
		        ps.setTimestamp(5, Timestamp.valueOf(seance.getDateSeance()));
		        ps.setInt(6, salle.getIdSalle());
		        ps.setInt(7, group.getIdGroupe());

		        int affectedRows = ps.executeUpdate();
		        if (affectedRows == 0) {
		            throw new SQLException("Creating seance failed, no rows affected.");
		        }
		        

		        rs = ps.getGeneratedKeys();
		        if(rs.next()) {
		        	seance.setIdSeance(rs.getInt(1));
		        	
		        } else {
	                throw new SQLException("Creating seance failed, no ID obtained.");
	            }

		        // Commit the transaction
		        connection.commit();
		        
		        map.put(1, seance);  //  1 indicates success
		    } catch (Exception e) {
		    	try{
			       if(connection != null) connection.rollback();

		    	}catch (SQLException ex) {
		    		ex.printStackTrace();
				}
		        e.printStackTrace();
		        map.put(0, "Error adding seance: " + e.getMessage());
		    } finally {
		    	try {
					if(rs != null) rs.close();
					if (ps != null) ps.close();
					if (connection != null) connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }

		    return map;
		}

		@Override
		public boolean isSalleAvailable(Connection connection, Salle salle, LocalDateTime dateSeance,LocalTime heureDebut, LocalTime heureFin) {
			 String query = "SELECT COUNT(*) FROM seance WHERE fk_salle = ? " +
                     "AND date_seance = ? " +
                     "AND ((heure_debut >= ? AND heure_debut < ?) OR " +
                     "(heure_fin > ? AND heure_fin <= ?) OR " +
                     "(heure_debut <= ? AND heure_fin >= ?))";
			    PreparedStatement ps = null;
			    ResultSet rs = null;
			 try {
				    ps = connection.prepareStatement(query);
				    ps.setInt(1, salle.getIdSalle());
		            ps.setObject(2, dateSeance);
		            ps.setObject(3, heureDebut);
		            ps.setObject(4, heureFin);
		            ps.setObject(5, heureDebut);
		            ps.setObject(6, heureFin);
		            ps.setObject(7, heureDebut);
		            ps.setObject(8, heureFin);
		            rs = ps.executeQuery();
		            if (rs.next()) {
	                    int count = rs.getInt(1);
	                    return count == 0 && salle.isDisponible();
	                }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				
					try {
						if(rs != null) rs.close();
						if (ps != null) ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
				
			}
			 
			return false;
		}

		@Override
		public boolean isGroupAvailable(Connection connection, Groupe group, LocalDateTime dateSeance,LocalTime heureDebut, LocalTime heureFin) {
			String query = "SELECT COUNT(*) FROM seance WHERE fk_groupe = ? " +
                    "AND date_seance = ? " +
                    "AND ((heure_debut >= ? AND heure_debut < ?) OR " +
                    "(heure_fin > ? AND heure_fin <= ?) OR " +
                    "(heure_debut <= ? AND heure_fin >= ?))";
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				
				ps = connection.prepareStatement(query);
				ps.setInt(1, group.getIdGroupe());
	            ps.setObject(2, dateSeance);
	            ps.setObject(3, heureDebut);
	            ps.setObject(4, heureFin);
	            ps.setObject(5, heureDebut);
	            ps.setObject(6, heureFin);
	            ps.setObject(7, heureDebut);
	            ps.setObject(8, heureFin);
	            rs = ps.executeQuery();
	            if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0;
                }
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs != null) rs.close();
					if (ps != null) ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return false;
		}

		@Override
		public boolean areStudentsAvailable(Connection connection, Groupe group, LocalDateTime dateSeance,
				LocalTime heureDebut, LocalTime heureFin) {
	        List<Eleve> students = getStudentsInGroup( connection,  group.getIdGroupe());
	        for (Eleve student : students) {
	            if (!isStudentAvailable(connection,student.getIdEleve(), dateSeance, heureDebut, heureFin)) {
	                return false; 
	                
	            }
	        }
	        return true;		}

		private List<Eleve> getStudentsInGroup(Connection connection, int idGroupe) {
			 List<Eleve> students = new ArrayList<>();
		        String query = "SELECT DISTINCT e.* FROM eleve e " +
		                       "INNER JOIN affectation a ON e.id_eleve = a.fk_eleve " +
		                       "WHERE a.fk_groupe = ?";
		        PreparedStatement ps = null;
		        ResultSet rs = null;
		        try  {
		        	ps = connection.prepareStatement(query);
		            ps.setInt(1, idGroupe);
		            rs = ps.executeQuery();
		           
		                while (rs.next()) {
		                    Eleve eleve = new Eleve();
		                    eleve.setIdEleve(rs.getInt("id_eleve"));
		                  
		                    students.add(eleve);
		                }
		            
		        }catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if(rs != null) rs.close();
						if (ps != null) ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

		        return students;
		    }
		

		private boolean isStudentAvailable(Connection connection,int idEleve, LocalDateTime dateSeance, LocalTime heureDebut,
				LocalTime heureFin) {
			String query = "SELECT COUNT(*) FROM seance s " +
                    "JOIN affectation a ON s.fk_groupe = a.fk_groupe WHERE s.date_seance = ?"+
                    " AND s.heure_debut < ? AND s.heure_fin > ? AND a.fk_eleve = ?";
			 PreparedStatement ps = null;
		     ResultSet rs = null;
		        try  {
		        	ps = connection.prepareStatement(query);
		        	ps.setObject(1, dateSeance);
		            ps.setObject(2, heureFin);
		            ps.setObject(3, heureDebut);
		            ps.setInt(4, idEleve);
		            rs = ps.executeQuery();
		           
		            if (rs.next()) {
	                    int count = rs.getInt(1);
	                    return count == 0;  // Return true if the student is available (no seance at the specified time)
	                }
		            
		        }catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if(rs != null) rs.close();
						if (ps != null) ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			return false;
		}
		
		private boolean isProfessorAvailable(Connection connection,int idProf, LocalDateTime dateSeance, LocalTime heureDebut,LocalTime heureFin) {
			String scheduleConflictQuery = "SELECT COUNT(*) FROM prof_schedule " +
                    "WHERE fk_enseignant = ? " +
                    "AND specific_date = ? " +
                    "AND ((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";

          String ongoingSeanceQuery = "SELECT COUNT(*) FROM seance inner join enseignant_groupe_matiere ON seance.fk_groupe = enseignant_groupe_matiere.fk_groupe " +
                  "WHERE enseignant_groupe_matiere.fk_enseignant = ? " +
                  "AND seance.date_seance = ? " +
                  "AND ((seance.heure_debut < ? AND seance.heure_fin > ?) OR (seance.heure_debut < ? AND seance.heure_fin > ?))";
           
           PreparedStatement scheduleConflictPs = null;
           PreparedStatement ongoingSeancePs = null;
           ResultSet scheduleConflictRs  = null;
           ResultSet ongoingSeanceRs = null;
		        try  {
		        	
		        	scheduleConflictPs = connection.prepareStatement(scheduleConflictQuery);
		        	ongoingSeancePs = connection.prepareStatement(ongoingSeanceQuery);
		        	scheduleConflictPs.setInt(1, idProf);
		            scheduleConflictPs.setObject(2, dateSeance);
		            scheduleConflictPs.setObject(3, heureDebut);
		            scheduleConflictPs.setObject(4, heureDebut);
		            scheduleConflictPs.setObject(5, heureFin);
		            scheduleConflictPs.setObject(6, heureFin);
		            ongoingSeancePs.setInt(1, idProf);
		            ongoingSeancePs.setObject(2, dateSeance);
		            ongoingSeancePs.setObject(3, heureDebut);
		            ongoingSeancePs.setObject(4, heureDebut);
		            ongoingSeancePs.setObject(5, heureFin);
		            ongoingSeancePs.setObject(6, heureFin);
		            scheduleConflictRs = scheduleConflictPs.executeQuery();
		            ongoingSeanceRs = ongoingSeancePs.executeQuery();
		           
		            if (scheduleConflictRs.next() && ongoingSeanceRs.next()) {
		            	int scheduleConflictCount = scheduleConflictRs.getInt(1);
	                    int ongoingSeanceCount = ongoingSeanceRs.getInt(1);

	                    // Return true only if there are no conflicts
	                    return scheduleConflictCount == 0 && ongoingSeanceCount == 0;
	                }
		            
		        }catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if(scheduleConflictRs!= null) scheduleConflictRs.close();
						if(ongoingSeanceRs!= null) ongoingSeanceRs.close();
						if (ongoingSeancePs != null) ongoingSeancePs.close();
						if (scheduleConflictPs != null) scheduleConflictPs.close();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			
		        return false;
	
		}

		@Override
		public int getEnseignantIdByGroupe(Connection connection, int idGroupe) {
	        String query = "SELECT fk_enseignant FROM enseignant_groupe_matiere WHERE fk_groupe = ?";
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
				
	        	ps = connection.prepareStatement(query);
	        	ps.setInt(1, idGroupe);
	        	rs = ps.executeQuery();
	        	if (rs.next()) {
	        		return rs.getInt("fk_enseignant");
					
				}
	        	
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally {
				try {
					if(rs != null) rs.close();
					if(ps!= null) ps.close();
					
				} catch (SQLException e2) {
				}
			}
	        
	        return -1;
		}

		@Override
		public List<EnseignantSpecialite> getEnseignantSpecialitesByEnseignantId(int idEnseignant) {
			 List<EnseignantSpecialite> enseignantSpecialites = new ArrayList<>();
			    Connection connection = null;
			    PreparedStatement ps = null;
			    ResultSet rs = null;

			    try {
			        connection = DBUtil.getConnection();
			        String query = "SELECT * FROM enseignant_specialite WHERE fk_enseignant = ?";
			        ps = connection.prepareStatement(query);
			        ps.setInt(1, idEnseignant);
			        rs = ps.executeQuery();

			        while (rs.next()) {
			            EnseignantSpecialite enseignantSpecialite = new EnseignantSpecialite();
			            enseignantSpecialite.setIdEnseignantSpecialite(rs.getInt("id_enseignant_specialite"));
			            enseignantSpecialite.setSpecialite(getMatiereById(rs.getInt("fk_specialite")));
			           // enseignantSpecialite.setEnseignant(getEnseignantById(rs.getInt("fk_enseignant")));
			            enseignantSpecialite.setMain(rs.getBoolean("main"));
			            enseignantSpecialites.add(enseignantSpecialite);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    } finally {
			        try {
			            if (rs != null) rs.close();
			            if (ps != null) ps.close();
			            if (connection != null) connection.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }

			    return enseignantSpecialites;
		}

		@Override
		public Enseignant getEnseignantById(int idEnseignant) {
			
			Enseignant enseignant = null;
		    Connection connection = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        connection = DBUtil.getConnection();
		        String query = "SELECT * FROM enseignant WHERE id_enseignant = ?";
		        ps = connection.prepareStatement(query);
		        ps.setInt(1, idEnseignant);
		        rs = ps.executeQuery();

		        if (rs.next()) {
		            enseignant = new Enseignant();
		            Utilisateur u = getUtilisateurById(idEnseignant);
		            enseignant.setAdresse(u.getAdresse());
		            enseignant.setPhone(u.getPhone() != null ? u.getPhone() : "");
		            enseignant.setNom(u.getNom() != null ? u.getNom() : "");
		            enseignant.setPrenom(u.getPrenom() != null ? u.getPrenom() : "");
		            enseignant.setDateCreation(u.getDateCreation() );
		            enseignant.setEmail(u.getEmail() != null ? u.getEmail() : "");
		            enseignant.setTypeUtil(u.getTypeUtil());
		            enseignant.setIdEnseignant(rs.getInt("id_enseignant"));
		            enseignant.setAvecExperience(rs.getBoolean("avec_experience"));
		            
		            //TODO : correct that 
		            enseignant.setDateValidation(rs.getDate("date_validation") != null ? rs.getDate("date_validation") : null);
		            //enseignant.setEnseignantSpecialite(getEnseignantSpecialitesByEnseignantId(idEnseignant));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return enseignant;
		}

		@Override
		public int createOrGenerateRandomGroupFromLevel(int levelId, Connection connection, int matiereId) {
		    try {
		        // Check if there's an existing group for the matiere with available capacity
		        String checkExistingGroupQuery = "SELECT g.id_groupe, g.capacite, g.nbr_eleve " +
		                "FROM groupe g " +
		                "INNER JOIN affectation a ON g.id_groupe = a.fk_groupe " +
		                "WHERE g.fk_classe = (SELECT id_classe FROM classe WHERE fk_niveau = ? AND main_classe = 1) " +
		                "AND a.fk_matiere = ? " +
		                "AND g.capacite > g.nbr_eleve + 1 " +
		                "LIMIT 1";

		        try (PreparedStatement checkExistingGroupStatement = connection.prepareStatement(checkExistingGroupQuery)) {
		            checkExistingGroupStatement.setInt(1, levelId);
		            checkExistingGroupStatement.setInt(2, matiereId);
		            ResultSet resultSet = checkExistingGroupStatement.executeQuery();

		            if (resultSet.next()) {
		                // Found an existing group with available capacity
		                return resultSet.getInt("id_groupe");
		            } else {
		                // No existing group found, create a new group
		                String createNewGroupQuery = "INSERT INTO groupe (fk_classe, capacite, nbr_eleve, description_groupe) " +
		                        "VALUES ((SELECT id_classe FROM classe WHERE fk_niveau = ? AND main_classe = 1), 15, 0, ?)";

		                try (PreparedStatement createNewGroupStatement = connection.prepareStatement(createNewGroupQuery, Statement.RETURN_GENERATED_KEYS)) {
		                    createNewGroupStatement.setInt(1, levelId);
		                    createNewGroupStatement.setString(2, "group ");
		                    createNewGroupStatement.executeUpdate();

		                    // Get the generated group ID
		                    ResultSet generatedKeys = createNewGroupStatement.getGeneratedKeys();
		                    if (generatedKeys.next()) {
		                        int newGroupId = generatedKeys.getInt(1);

		                        // Close resources
		                        generatedKeys.close();

		                        return newGroupId;
		                    } else {
		                        throw new SQLException("Failed to get the generated group ID.");
		                    }
		                }
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return 0;
		    }
		}
		@Override
		public List<Niveau> getAllNiveaus() {
         Connection connection = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         List<Niveau> niveaux = new ArrayList<>();
         try {
        	 connection = DBUtil.getConnection();
        	 String query = "select id_niveau from niveau";
        	 ps = connection.prepareStatement(query);
        	 rs = ps.executeQuery();
        	 while (rs.next()) {
				Niveau niveau = getNiveauById(rs.getInt("id_niveau"));
				niveaux.add(niveau);		
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(connection != null) connection.close();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
			
			return niveaux;
		}

		@Override
		public List<Matiere> getMatieresByNiveau(String selectedNiveau) {
			
			Connection connection = null;
	         PreparedStatement ps = null;
	         ResultSet rs = null;
	         List<Matiere> matieres = new ArrayList<>();
	         try {
	        	 connection = DBUtil.getConnection();
	        	 String query = "select m.id_matiere from matiere m inner join niveau n on n.id_niveau = m.fk_niveau where n.descriptionNiveau = ?";
	        	 ps = connection.prepareStatement(query);
	        	 ps.setString(1, selectedNiveau);
	        	 rs = ps.executeQuery();
	        	 while (rs.next()) {
					Matiere matiere = getMatiereById(rs.getInt("m.id_matiere"));
					matieres.add(matiere);		
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs != null) rs.close();
					if(ps != null) ps.close();
					if(connection != null) connection.close();
					
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
				
				return matieres;		}

		@Override
		public List<Matiere> getMatieresByNiveauId(int selectedNiveau) {
			Connection connection = null;
	         PreparedStatement ps = null;
	         ResultSet rs = null;
	         List<Matiere> matieres = new ArrayList<>();
	         try {
	        	 connection = DBUtil.getConnection();
	        	 String query = "select m.id_matiere from matiere m  where  m.fk_niveau =? ";
	        	 ps = connection.prepareStatement(query);
	        	 ps.setInt(1, selectedNiveau);
	        	 rs = ps.executeQuery();
	        	 while (rs.next()) {
					Matiere matiere = getMatiereById(rs.getInt("m.id_matiere"));
					matieres.add(matiere);		
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs != null) rs.close();
					if(ps != null) ps.close();
					if(connection != null) connection.close();
					
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
				
				return matieres;	
		}

		@Override
		public List<Groupe> getAllGroupesOfProf(int idProf) {
			
			List<Groupe> groupes = new ArrayList<>();
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        Connection connection = null;

	        try {
	            connection = DBUtil.getConnection();
	            String sql = "SELECT * FROM  enseignant_groupe_matiere WHERE fk_enseignant = ?";

	            statement = connection.prepareStatement(sql);
	            statement.setInt(1, idProf);

	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                Groupe groupe = getGroupeById(resultSet.getInt("fk_groupe"));
	                
	                groupes.add(groupe);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (statement != null) statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return groupes;
	    }

	   
		

		@Override
		public List<Groupe> getAllGroupes() {
			List<Groupe> groupes = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        try {
	            conn = DBUtil.getConnection();
	            String sql = "SELECT id_groupe FROM groupe";
	            stmt = conn.prepareStatement(sql);
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                Groupe groupe = getGroupeById(rs.getInt("id_groupe"))!= null ? getGroupeById(rs.getInt("id_groupe")) : new Groupe() ;
	                groupes.add(groupe);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
					try {
						if(rs != null) rs.close();
						if(stmt != null) stmt.close();
						if(conn != null) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        	
	        }

	        return groupes;
		}

		@Override
		public int closeDayOrHours(int prof, String day, String startTime, String endTime, Boolean isWholeDay) {
		    System.out.println(" start  " + startTime + " startTime");
		    System.out.println(endTime + " endTime");
		    System.out.println(day + " day");
		    System.out.println(" id Prof " + prof);
		    System.out.println(" is whole day" + isWholeDay);
		    int affectedRows = 0;
		    Connection connection = null;
		    try {
		        connection = DBUtil.getConnection();
		        connection.setAutoCommit(false);

		        String updateQuery = "UPDATE seance s " +
		                "inner JOIN enseignant_groupe_matiere egm ON s.fk_groupe = egm.fk_groupe " +
		                "SET s.annule = ? " +
		                "WHERE egm.fk_enseignant = ? AND DATE(s.date_seance) = ? ";

		        System.out.println("Update Query: " + updateQuery);

		        if (startTime != null && endTime != null) {
		            updateQuery += "AND s.heure_debut >= ? AND s.heure_fin <= ?";
		        }

		        System.out.println("Update Query after condition: " + updateQuery);

		        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
		        updateStatement.setBoolean(1, true);
		        updateStatement.setInt(2, prof);
		        updateStatement.setDate(3, java.sql.Date.valueOf(day));
		       // System.out.println(startTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$") && endTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$"));

		        if (startTime != null && endTime != null ) {
		            System.out.println("I am in updated " + startTime + " startTime");
		            System.out.println(endTime + " endTime");
		            // Trim the strings to remove leading and trailing whitespace
		            startTime = startTime.trim();
		            endTime = endTime.trim();
		            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		            Date parsedStartTime = sdf.parse(startTime);
		            Date parsedEndTime = sdf.parse(endTime);
		            // Convert parsed time to java.sql.Time
		            Time sqlStartTime = new Time(parsedStartTime.getTime());
		            Time sqlEndTime = new Time(parsedEndTime.getTime());
		            updateStatement.setTime(4, sqlStartTime);
		            updateStatement.setTime(5, sqlEndTime);
                   
		        }

		        affectedRows += updateStatement.executeUpdate();
		        updateStatement.close();

		        String insertQuery = "INSERT INTO prof_schedule (start_time, end_time, is_whole_day, specific_date, fk_enseignant) VALUES (?, ?, ?, ?, ?)";
		        System.out.println("Insert Query: " + insertQuery);

		        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
		        if (startTime != null && endTime != null) {
		            System.out.println("I am in inserted " + startTime + " startTime");
		            System.out.println(startTime + " endTime");
		            startTime = startTime.trim();
		            endTime = endTime.trim();
		            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		            Date parsedStartTime = sdf.parse(startTime);
		            Date parsedEndTime = sdf.parse(endTime);
		            Time sqlStartTime = new Time(parsedStartTime.getTime());
		            Time sqlEndTime = new Time(parsedEndTime.getTime());
		            insertStatement.setTime(1, sqlStartTime);
		            insertStatement.setTime(2, sqlEndTime);
		        } else {
		            insertStatement.setNull(1, java.sql.Types.TIME);
		            insertStatement.setNull(2, java.sql.Types.TIME);
		        }
		        insertStatement.setBoolean(3, isWholeDay);
		        insertStatement.setDate(4, java.sql.Date.valueOf(day));
		        insertStatement.setInt(5, prof);
		        affectedRows += insertStatement.executeUpdate();
		        insertStatement.close();

		        connection.commit();
		    } catch (SQLException e) {
		        try {
		            if (connection != null)
		                connection.rollback();
		        } catch (SQLException rollbackException) {
		            rollbackException.printStackTrace();
		        }
		        e.printStackTrace();
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        try {
		            if (connection != null) {
		                connection.setAutoCommit(true);
		                connection.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return affectedRows;
		}

		@Override
		public int closeDayOrHours(String day, String startTime, String endTime) {
			int affectedRows = 0;
		    Connection connection = null;
		    try {
		        connection = DBUtil.getConnection();
		        connection.setAutoCommit(false);
		        String updateQuery = "UPDATE seance SET annule = ? WHERE DATE(date_seance) = ? ";

		        if (startTime != null && endTime != null) {
		            updateQuery += "AND heure_debut >= ? AND heure_fin <= ?";
		        }

		        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
		        updateStatement.setBoolean(1, true);
		        updateStatement.setDate(2, java.sql.Date.valueOf(day));

		        if (startTime != null && endTime != null) {
		            updateStatement.setTime(3, java.sql.Time.valueOf(startTime));
		            updateStatement.setTime(4, java.sql.Time.valueOf(endTime));
		        }

		        affectedRows += updateStatement.executeUpdate();
		        updateStatement.close();

		        connection.commit();
		    } catch (SQLException e) {
		        try {
		            if (connection != null)
		                connection.rollback();
		        } catch (SQLException rollbackException) {
		            rollbackException.printStackTrace();
		        }
		        e.printStackTrace();
		    } finally {
		        try {
		            if (connection != null) {
		                connection.setAutoCommit(true);
		                connection.close(); 
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return affectedRows;
		}
 // this for the dashboard
		// {mothName , numberOfIncriptions}
		// if no inscription of this month 0
		@Override
		public Map<String, Long> inscriptionsOfCurrentYearByMonth() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Map<String, Long> result = new HashMap<>();
			// Initialize the map with all months and 0 as the initial count
		    String[] months = new DateFormatSymbols(Locale.ENGLISH).getMonths();
		    for (String month : months) {
		        result.put(month, 0L);
		    }
			
			try {
				con = DBUtil.getConnection();
				
				String query = "select count(*) as count,  monthname(dateCreation) AS month_name from utilisateur  where type_util<>\"agent\" and type_util<>\"directeur\" and Year(dateCreation)=? group by  monthname(dateCreation) ";
				ps = con.prepareStatement(query);
		        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		        ps.setInt(1, currentYear);
		        rs = ps.executeQuery();
		        
		        while (rs.next()) {
		            String monthName = rs.getString("month_name");
		            Long numberOfInscriptions = rs.getLong("count");
		            result.put(monthName, numberOfInscriptions);
		        }
			} catch (Exception e1) {
				 e1.printStackTrace();
			}finally {
				
					try {
						if(rs != null) rs.close();
						if(ps != null) ps.close();
						if(con != null) con.close();


						
					} catch (SQLException e2) {
						
						e2.printStackTrace();
					}
				
			}
			return result;
			
		}

		@Override
		public Double incomeOfCurrentYear() {
			
		    Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    Double income = 0.0;
		    
		    try {
		        con = DBUtil.getConnection();
		        String query = "SELECT SUM(m.prix) AS total_income FROM paiement p INNER JOIN matiere m ON m.id_matiere = p.fk_matiere WHERE YEAR(p.date_paiment) = ?";
		        ps = con.prepareStatement(query);
		        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		        ps.setInt(1, currentYear);
		        
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		            income = rs.getDouble("total_income");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return income;
		}
		// {type_util , numberOfIncriptions}
		
		@Override
		public Map<String, Long>   inscriptionsOfcurrentYear() {
			Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    Map<String, Long> result = new HashMap<>();
		    
		    try {
		        con = DBUtil.getConnection();
		        String query = "SELECT type_util, COUNT(*) AS count FROM utilisateur WHERE YEAR(dateCreation) = ? GROUP BY type_util";
		        ps = con.prepareStatement(query);
		        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		        ps.setInt(1, currentYear);
		        
		        rs = ps.executeQuery();
		        
		        while (rs.next()) {
		            String userType = rs.getString("type_util");
		            long count = rs.getLong("count");
		            result.put(userType, count);
		        }
		    } catch (SQLException e1) {
		        e1.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
		    }
		    return result;
		}
		//{levelName , numberOfIncriptions}
		// seulements les etudiants validees
		@Override
		public Map<String, Long> inscriptionsByLevel() {
			 Connection con = null;
			    PreparedStatement ps = null;
			    ResultSet rs = null;
			    Map<String, Long> inscriptionsByLevel = new HashMap<>();
			    
			    try {
			        con = DBUtil.getConnection();
			        String query = "SELECT COUNT(DISTINCT a.fk_eleve) AS number_of_inscriptions, n.descriptionNiveau " +
			                       "FROM affectation a " +
			                       "INNER JOIN matiere m ON  m.id_matiere = a.fk_matiere  " +
			                       "INNER JOIN niveau n ON n.id_niveau = m.fk_niveau  " +
			                       "INNER JOIN eleve e ON a.fk_eleve = e.id_eleve " +
			                       "WHERE e.date_validation IS NOT NULL " +
			                       "GROUP BY n.descriptionNiveau";
			        ps = con.prepareStatement(query);
			        rs = ps.executeQuery();
			        
			        while (rs.next()) {
			            String levelDescription = rs.getString("descriptionNiveau");
			            long numberOfInscriptions = rs.getLong("number_of_inscriptions");
			            inscriptionsByLevel.put(levelDescription, numberOfInscriptions);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    } finally {
			        try {
			            if (rs != null) rs.close();
			            if (ps != null) ps.close();
			            if (con != null) con.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }
			    return inscriptionsByLevel;
		}
		//{monthName , income}
		@Override
		public Map<String, Object> incomeByLevel() {
			
			Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    Map<String, Object> incomeByLevel = new HashMap<>();
		    
		    try {
		        con = DBUtil.getConnection();
		        String query = "SELECT SUM(m.prix) AS total_income, n.descriptionNiveau " +
		                       "FROM paiement p " +
		                       "INNER JOIN matiere m ON m.id_matiere = p.fk_matiere " +
		                       "INNER JOIN niveau n ON n.id_niveau = m.fk_niveau " +
		                       "GROUP BY n.descriptionNiveau";
		        ps = con.prepareStatement(query);
		        rs = ps.executeQuery();
		        
		        while (rs.next()) {
		            String levelDescription = rs.getString("descriptionNiveau");
		            double totalIncome = rs.getDouble("total_income");
		            incomeByLevel.put(levelDescription, totalIncome);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return incomeByLevel;
		}
        //validate students only
		@Override
		public Long numberOfValidateStudent() {
			Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    Long numberOfValidatedStudents = 0L;
		    
		    try {
		        con = DBUtil.getConnection();
		        String query = "SELECT COUNT(*) AS num_validated_students FROM eleve WHERE date_validation IS NOT NULL";
		        ps = con.prepareStatement(query);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		            numberOfValidatedStudents = rs.getLong("num_validated_students");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return numberOfValidatedStudents;
		}
        //validate profs only

		@Override
		public Long numberOfValidateProfs() {
			Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    Long numberOfValidatedProfs = 0L;
		    
		    try {
		        con = DBUtil.getConnection();
		        String query = "SELECT COUNT(*) AS num_validated_profs FROM enseignant WHERE date_validation IS NOT NULL";
		        ps = con.prepareStatement(query);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		            numberOfValidatedProfs = rs.getLong("num_validated_profs");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return numberOfValidatedProfs;
		}

		@Override
		public List<Utilisateur> getAllElevesByIdGroup(int idGroup) {
			 List<Utilisateur> students = new ArrayList<>();
		        String query = "SELECT DISTINCT e.* FROM eleve e " +
		                       "INNER JOIN affectation a ON e.id_eleve = a.fk_eleve " +
		                       "WHERE a.fk_groupe = ?";
		        PreparedStatement ps = null;
		        ResultSet rs = null;
		        Connection connection = null;
		        try  {
		        	connection = DBUtil.getConnection();
		        	ps = connection.prepareStatement(query);
		            ps.setInt(1, idGroup);
		            rs = ps.executeQuery();
		           
		                while (rs.next()) {
		                    Utilisateur eleve = getUtilisateurById(rs.getInt("id_eleve"));
		                    students.add(eleve);
		                }
		            
		        }catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if(rs != null) rs.close();
						if (ps != null) ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

		        return students;
		}

		@Override
		public List<Seance> getAllSeancesByGroupe(int idGroup) {
			List<Seance> seances = new ArrayList<>();
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	        	
	        	String sql = "select * from seance where  fk_groupe =? ";
	        	connection = DBUtil.getConnection();
	        	ps = connection.prepareStatement(sql);
	        	ps.setObject(1, idGroup);
	        	
				rs = ps.executeQuery();
				while(rs.next()) {
					Seance seance = getSeanceById(rs.getInt("id_seance"));
					seances.add(seance);	
				}
			} catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return seances;
		}

		@Override
		public boolean isAbsent(int idStudent, int idSeance) {
			
			String query = "SELECT * FROM absence WHERE fk_seance = ? AND fk_eleve = ?";
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;
	        try  {
	        	connection = DBUtil.getConnection();
	        	statement = connection.prepareStatement(query);
	            statement.setInt(1, idSeance);
	            statement.setInt(2, idStudent);

	             resultSet = statement.executeQuery();
	            
	            if (resultSet.next()) {
	                return true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return false;
		}

		@Override
		public Map<String, List<Seance>> getAllSeanceByCurrentWeekMapByIdStudent(int idStudent) {
			Map<String, List<Seance>> seances = new HashMap<>();
			 seances.put("MONDAY", new ArrayList<>());
			 seances.put("TUESDAY", new ArrayList<>());
			 seances.put("WEDNESDAY", new ArrayList<>());
			 seances.put("THURSDAY", new ArrayList<>());
			 seances.put("FRIDAY", new ArrayList<>());
			 seances.put("SATURDAY", new ArrayList<>());
			 seances.put("SUNDAY", new ArrayList<>());
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	        	LocalDateTime currentDate = LocalDateTime.now();
	        	LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
	        	LocalDateTime endOfWeek = currentDate.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59);
	        	String query = "SELECT * FROM seance s inner JOIN affectation a ON s.fk_groupe = a.fk_groupe  WHERE s.date_seance BETWEEN ? AND ? and a.fk_eleve = ?";
	        	
	        	connection = DBUtil.getConnection();
	        	ps = connection.prepareStatement(query);
	        	ps.setObject(1, startOfWeek);
	        	ps.setObject(2, endOfWeek);
	        	ps.setInt(3, idStudent);
	        	rs = ps.executeQuery();
	        	while (rs.next()) {
	            Seance seance = getSeanceById(rs.getInt("id_seance"));
		        String key = seance.getDateSeance().getDayOfWeek().toString();
		        seances.computeIfAbsent(key,e -> new ArrayList<>()).add(seance);
		        
				}
	        			
	        }catch(SQLException e) {
	        	e.printStackTrace();
	        }finally {
	        	try {
					if(rs != null) rs.close();
					if(ps != null) ps.close();
					if(connection != null) connection.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					}
	        }

			return seances;
		}

		@Override
		public Matiere getMatiereByGroupeId(int idGroup) {
			Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    Matiere matiere = null;

		    try {
		        conn = DBUtil.getConnection(); // Assuming getConnection() returns a valid database connection
		        String query = "SELECT DISTINCT fk_matiere FROM affectation WHERE fk_groupe = ?";
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idGroup);
		        rs = stmt.executeQuery();

		        if(rs.next()) {
		             matiere = getMatiereById( rs.getInt("fk_matiere"))  ;
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return matiere;
		}

		@Override
		public Map<Integer, Matiere> getAllMatieresAndGroupsIdOfEleve(int idEleve) {
			Map<Integer, Matiere> matieresAndGroups = new HashMap<>();
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            String sql = "SELECT m.id_matiere , a.fk_groupe FROM matiere m inner JOIN affectation a ON m.id_matiere = a.fk_matiere WHERE a.fk_eleve = ?";

	            connection = DBUtil.getConnection();

	            try {
	                preparedStatement = connection.prepareStatement(sql);
	                preparedStatement.setInt(1, idEleve);

	                resultSet = preparedStatement.executeQuery();

	                while (resultSet.next()) {
	                    Matiere matiere = getMatiereById(resultSet.getInt("id_matiere"));
	                    int idGroup = resultSet.getInt("fk_groupe");
	                    matieresAndGroups.put(idGroup, matiere);
	                    
	                }
	            } catch (Exception e) {
	                e.printStackTrace(); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (Exception e) {
	                e.printStackTrace(); 
	            }
	        }

	        return matieresAndGroups;
		}

		@Override
		public List<Seance> getAllSeancesDepuisInscriptionOfAstudent(int idGroup, int idStudent) {
			List<Seance> seances = new ArrayList<>();
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	        	
	        	String sql = "select * from seance where  fk_groupe =? and  Date(date_seance) > ( select Date(date_validation) from eleve where id_eleve = ? ) ";
	        	connection = DBUtil.getConnection();
	        	ps = connection.prepareStatement(sql);
	        	ps.setObject(1, idGroup);
	        	
	        	ps.setInt(2, idStudent);
				rs = ps.executeQuery();
				while(rs.next()) {
					Seance seance = getSeanceById(rs.getInt("id_seance"));
					seances.add(seance);	
				}
			} catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }

	        return seances;
		}

		@Override
		public Long numberOfDocuments() {
			Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    Long count = 0L;

		    try {
		        connection = DBUtil.getConnection();
		        String sql = "SELECT COUNT(*) AS count FROM document";
		        statement = connection.prepareStatement(sql);
		        resultSet = statement.executeQuery();

		        if (resultSet.next()) {
		            count = resultSet.getLong("count");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (resultSet != null) {
		                resultSet.close();
		            }
		            if (statement != null) {
		                statement.close();
		            }
		            if (connection != null) {
		                connection.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return count;
		}

		@Override
		public Utilisateur getUtilisateurByEmait(String email) {
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Utilisateur user = null;
			try {
				String query = " select id_utilisateur from utilisateur where email = ? ";
				connection = DBUtil.getConnection();
				ps = connection.prepareStatement(query);
				ps.setString(1, email);
				rs = ps.executeQuery();
			    if(rs.next()) {
			    	user = getUtilisateurById(rs.getInt("id_utilisateur"));
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
	            try {
	            	if(rs != null) rs.close();
	            	if(ps != null) ps.close();
	            	if(connection != null) connection.close();	
				} catch (Exception e) { e.printStackTrace();}
			}
			return user;
		}

		@Override
		public Utilisateur updatePassword(int userId, String newPassword) {
			Connection connection = null;
			PreparedStatement ps = null;
			int result = 0;
			Utilisateur user = null;
			try {
				String query = "UPDATE utilisateur SET passwordd = ? WHERE id_utilisateur = ?";
				connection = DBUtil.getConnection();
				ps = connection.prepareStatement(query);
				ps.setString(1, newPassword);
				ps.setInt(2, userId);
				result = ps.executeUpdate();
			    if(result != 0) {
			    	user = getUtilisateurById(userId);
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
	            try {
	            	
	            	if(ps != null) ps.close();
	            	if(connection != null) connection.close();	
				} catch (Exception e) { e.printStackTrace();}
			}
			
			return user ;
		}

		@Override
		public List<Matiere> getAllMatieresNotAssignedToAstudent(int studentId ) {
			Connection connection = null;
		    PreparedStatement statement = null;
		    ResultSet resultSet = null;
		    List<Matiere> matieres = new ArrayList<>();

		    try {
		        connection = DBUtil.getConnection();

		        // Query to get the level of the student
		        String levelQuery = "SELECT DISTINCT n.id_niveau FROM niveau n " +
		                            "INNER JOIN matiere m ON n.id_niveau = m.fk_niveau " +
		                            "INNER JOIN affectation a ON m.id_matiere = a.fk_matiere " +
		                            "WHERE a.fk_eleve = ?";
		        statement = connection.prepareStatement(levelQuery);
		        statement.setInt(1, studentId);
		        ResultSet levelResult = statement.executeQuery();

		        int idNiveau = -1; 
		        if (levelResult.next()) {
		            idNiveau = levelResult.getInt(1);
		        }
		        levelResult.close();
		        statement.close();

		        String sql = "SELECT * FROM matiere m WHERE m.fk_niveau = ? " +
		                     "AND m.id_matiere NOT IN (SELECT a.fk_matiere FROM affectation a " +
		                     "INNER JOIN matiere m2 ON m2.id_matiere = a.fk_matiere WHERE a.fk_eleve = ?)";
		        statement = connection.prepareStatement(sql);
		        statement.setInt(1, idNiveau);
		        statement.setInt(2, studentId);
		        resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		        	 Matiere matiere = new Matiere();
		             matiere.setIdMatiere(resultSet.getInt("id_matiere"));
		             matiere.setDescriptionMatiere(resultSet.getString("description_matiere"));
		             matiere.setPrix(resultSet.getFloat("prix"));
		            // matiere.setDateDeCreation(resultSet.getTimestamp("date_de_creation").toLocalDateTime());
		            
		             matieres.add(matiere);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (resultSet != null) resultSet.close();
		            if (statement != null) statement.close();
		            if (connection != null) connection.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return matieres;
		}

		
		
		
	}

	
   

