package modelo;

import DBRelated.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class PasajeroDAO {
    private Connection con;

    public PasajeroDAO(Connection con) {
        this.con = con;
    }

    public PasajeroDAO() {

    }

    public void insert(Pasajero pasajero) {
        try {
            Connection con = ConnectionDB.getConnection();

            PreparedStatement st = Objects.requireNonNull(con).prepareStatement("INSERT INTO pasajeros VALUES(?,?,?,?)");

            st.setString(1, pasajero.getDNI());
            st.setString(2, pasajero.getNombre());
            st.setString(3, pasajero.getTelefono());
            st.setString(4, pasajero.getCodVuelo());
            st.executeUpdate();
            System.out.println("Vuelo guardado exitosamente");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
