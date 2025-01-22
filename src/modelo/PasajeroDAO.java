package modelo;

import DBRelated.ConnectionDB;

import java.sql.*;
import java.util.Objects;


public class PasajeroDAO {
    private Connection con;
    private String pasajeroSQL;
    public PasajeroDAO(Connection con) {
        this.con = con;
    }

    public PasajeroDAO() {

    }

    public void create(Pasajero pasajero) {
        try {
            this.con = ConnectionDB.getConnection();
            pasajeroSQL="INSERT INTO pasajeros VALUES(?,?,?,?)";

            PreparedStatement st = Objects.requireNonNull(con).prepareStatement(pasajeroSQL);

            st.setString(1, pasajero.getDNI());
            st.setString(2, pasajero.getNombre());
            st.setString(3, pasajero.getTelefono());
            st.setString(4, pasajero.getCodVuelo());
            st.executeUpdate();

            System.out.println("Pasajero guardado exitosamente");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Pasajero read() throws SQLException {
        pasajeroSQL = "SELECT * FROM pasajeros";

        try {
            this.con = ConnectionDB.getConnection();
            Statement st = Objects.requireNonNull(con).createStatement();

            ResultSet rs = st.executeQuery(pasajeroSQL);
            if (rs.next()) {
                return new Pasajero(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4));
            }
        }catch (SQLException i) {
            System.out.println(i.getMessage());
        }
        return null;
    }
    public void update(Pasajero pasajero){
        pasajeroSQL = "UPDATE vuelos SET cod_vuelo = ? WHERE cod_vuelo = ?";

        try {
            this.con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, pasajero.getCodVuelo());
            ps.executeUpdate();

            System.out.println("Pasajero actualizado exitosamente");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void delete(Pasajero pasajero){}
}
