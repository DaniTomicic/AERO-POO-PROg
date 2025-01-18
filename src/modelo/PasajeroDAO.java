package modelo;

import DBRelated.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class PasajeroDAO {


    public void save(Pasajero pasajero) {
        try {
            Connection con = ConnectionDB.getConnection();

            PreparedStatement statement = Objects.requireNonNull(con).prepareStatement("INSERT INTO vuelos(pasajeros.cod_vuelo,pasajeros.dni,pasajeros.nombre,pasajeros.telefono) VALUES(?,?,?,?)");

            statement.setString(1, pasajero.getCodVuelo());

            statement.setString(2, pasajero.getDNI());

            statement.setString(3, pasajero.getNombre());

            statement.setString(4, pasajero.getTelefono());

            statement.executeUpdate();
            System.out.println("Vuelo guardado exitosamente");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
