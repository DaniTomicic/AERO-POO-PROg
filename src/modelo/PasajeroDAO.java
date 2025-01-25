package modelo;

import DBRelated.ConnectionDB;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PasajeroDAO {
    private Connection con;
    public String pasajeroSQL;
    public Pasajero p = new Pasajero();
    public PasajeroDAO(Connection con) {
        this.con = con;
    }

    public PasajeroDAO() {

    }


    public void create(Pasajero pasajero) {
        try {
            con = ConnectionDB.getConnection();
            pasajeroSQL = "INSERT INTO pasajeros VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, pasajero.getDNI());
            ps.setString(2, pasajero.getNombre());
            ps.setString(3, pasajero.getTelefono());
            ps.setString(4, pasajero.getCodVuelo());

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primario: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Pasajero> read() {
        pasajeroSQL = "SELECT * FROM pasajeros";
        ArrayList<Pasajero> pasajeros = new ArrayList<>();

        try (Connection con = ConnectionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(pasajeroSQL)) {

            while (rs.next()) {
                Pasajero p = new Pasajero(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );

                pasajeros.add(p);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primaria: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return pasajeros;
    }

    public void update(Pasajero nuevoPasajero, Pasajero p) throws SQLException {
        pasajeroSQL = "UPDATE pasajeros SET cod_vuelo = ? WHERE cod_vuelo = ?";
        try {
            con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, nuevoPasajero.getCodVuelo());
            ps.setString(2, p.getCodVuelo());
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primario: " + e.getMessage());
        }
    }

    public void delete(Pasajero pasajero) {
        try {
            con = ConnectionDB.getConnection();
            pasajeroSQL = "DELETE FROM pasajeros WHERE cod_vuelo = ?";
            PreparedStatement psVuelo = con.prepareStatement(pasajeroSQL);

            psVuelo.setString(1, pasajero.getCodVuelo());
            psVuelo.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primario: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Pasajero> readByFlight(Vuelo v)
    {
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        try {
            con = ConnectionDB.getConnection();
            pasajeroSQL = "SELECT * FROM pasajeros WHERE cod_vuelo = ?";
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);
            ps.setString(1, v.getCodVuelo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                new Pasajero (rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }
            pasajeros.add(new Pasajero());
            return pasajeros;

        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("ERROR: se ha violado clave primario: " + e.getMessage());
        }catch (SQLException e){
            System.out.println("ERROR: " + e.getMessage());
        }

        return pasajeros;
    }

    public Pasajero readByOneFlight(Vuelo v)
    {
        try {
            con = ConnectionDB.getConnection();
            pasajeroSQL = "SELECT * FROM pasajeros WHERE cod_vuelo = ?";
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);
            ps.setString(1, v.getCodVuelo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Pasajero(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }

        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("ERROR: se ha violado clave primario: " + e.getMessage());
        }catch (SQLException e){
            System.out.println("ERROR: " + e.getMessage());
        }

        return null;
    }
}
