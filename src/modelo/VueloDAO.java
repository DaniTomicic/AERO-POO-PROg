package modelo;

import DBRelated.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;


public class VueloDAO {
    Connection con;
    private String vueloSQL;

    public VueloDAO(Connection con) {
        this.con = con;
    }

    public void insertVuelo(Vuelo vuelo) throws SQLException {
        vueloSQL = "insert into vuelos values(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(vueloSQL);

            ps.setString(1, vuelo.getCodVuelo());
            ps.setDate(2, convertir(vuelo.getFechaSalida()));
            ps.setString(3, vuelo.getDestino());
            ps.setString(4,vuelo.getPorcedencia());
            ps.executeUpdate();
    }
    public void updateVuelo(Vuelo nuevoVuelo,Vuelo vueloExistente) throws SQLException {
        vueloSQL = "UPDATE vuelos SET cod_vuelo = ? WHERE cod_vuelo = ?";

        PreparedStatement ps = con.prepareStatement(vueloSQL);

        ps.setString(1, nuevoVuelo.getCodVuelo());
        ps.setString(2, vueloExistente.getCodVuelo());
        ps.executeUpdate();

    }
    public String search() throws SQLException {
        vueloSQL = "SELECT cod_vuelo FROM vuelos";
        StringBuilder s = new StringBuilder();

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(vueloSQL)) {
            while (rs.next()) {
                s.append(rs.getString("cod_vuelo")).append("\n");
            }
        }

        return s.toString();
    }

    public java.sql.Date convertir(LocalDate fechaSalida) {
        java.sql.Date fecha;
        fecha = java.sql.Date.valueOf(fechaSalida);
        return fecha;
    }


    public static Vuelo findByCodVuelo(String codVuelo, Vuelo vuelo) {
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
                vuelo.setPorcedencia(vueloResultSet.getString(4));

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
        return vuelo;
    }
}
