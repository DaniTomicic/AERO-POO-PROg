package modelo;

import DBRelated.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;


public class VueloDAO {


    public void save(Vuelo vuelo) {
        String vueloSQL = "INSERT INTO vuelos(vuelos.cod_vuelo,vuelos.fecha_salida,vuelos.destino,vuelos.procedencia) VALUES(?,?,?,?)";

        String pasajeroSQl = "INSERT INTO vuelos(pasajeros.cod_vuelo,pasajeros.dni,pasajeros.nombre,pasajeros.telefono) VALUES(?,?,?,?)";

        try {
            Connection connection = ConnectionDB.getConnection();
            Connection con = ConnectionDB.getConnection();

            //guarda vuelo
            PreparedStatement vueloStatement = Objects.requireNonNull(con).prepareStatement(vueloSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            vueloStatement.setString(1,vuelo.getCodVuelo());
            vueloStatement.setDate(2, java.sql.Date.valueOf(vuelo.getFechaSalida()));
            vueloStatement.setString(3, vuelo.getDestino());
            vueloStatement.setString(4, vuelo.getOrigen());

            //obtener cod_vuelo
            ResultSet generatedKeys = vueloStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                String cod_vuelo = generatedKeys.getString(1);

                //guardar pasajeros
                PreparedStatement pasajeroStatement = connection.prepareStatement(pasajeroSQl);
                for (Pasajero p : vuelo.getPasajeros()) {

                    pasajeroStatement.setString(1, p.getCodVuelo());
                    pasajeroStatement.setString(3, p.getDNI());
                    pasajeroStatement.setString(2, p.getNombre());
                    pasajeroStatement.setString(3, p.getTelefono());
                }
                pasajeroStatement.executeUpdate();
            }

            vueloStatement.executeUpdate();
            System.out.println("Vuelo guardado exitosamente");

        }catch (SQLException e) {
            System.out.println("Error al guardar el vuelo");
            throw new RuntimeException(e);
        }
    }
    public Vuelo findByCodVuelo(String codVuelo, Vuelo vuelo) {
        String vueloSQL = "SELECT * FROM vuelos WHERE vuelos.cod_vuelo = ?";

        String pasajerosSQL = "SELECT * FROM pasajeros WHERE pasajeros.cod_vuelo = ?";

        try {
            Connection connection = ConnectionDB.getConnection();
            PreparedStatement vueloStatement = connection.prepareStatement(vueloSQL);
            vueloStatement.setString(1, codVuelo);
            ResultSet vueloResultSet = vueloStatement.executeQuery();

            if (vueloResultSet.next()) {
                vuelo = new Vuelo();
                vuelo.setCodVuelo(vueloResultSet.getString(1));
                vuelo.setFechaSalida(LocalDate.parse(vueloResultSet.getString(2)));
                vuelo.setDestino(vueloResultSet.getString(3));
                vuelo.setOrigen(vueloResultSet.getString(4));

                PreparedStatement pasajeroStatement = connection.prepareStatement(pasajerosSQL);
                pasajeroStatement.setString(1, codVuelo);
                ResultSet pasajeroResult = pasajeroStatement.executeQuery();
                while (pasajeroResult.next()) {
                    Pasajero p = new Pasajero();
                    p.setCodVuelo(pasajeroResult.getString(1));
                    p.setDNI(pasajeroResult.getString(2));
                    p.setNombre(pasajeroResult.getString(3));
                    p.setTelefono(pasajeroResult.getString(4));

                }
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
