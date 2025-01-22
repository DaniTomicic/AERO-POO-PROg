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

    public VueloDAO() {

    }

    public void create(Vuelo vuelo) throws SQLException {
        vueloSQL = "insert into vuelos values(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(vueloSQL);

            ps.setString(1, vuelo.getCodVuelo());
            ps.setDate(2, convertir(vuelo.getFechaSalida()));
            ps.setString(3, vuelo.getDestino());
            ps.setString(4,vuelo.getPorcedencia());
            ps.executeUpdate();
    }

    public Vuelo read() throws SQLException {
        vueloSQL = "SELECT * FROM vuelos";

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(vueloSQL)) {
            if (rs.next()) {
                return new Vuelo(rs.getString(1),LocalDate.parse(rs.getString(2)),rs.getString(3),rs.getString(4));
            }
        }catch (SQLException i) {
            System.out.println(i.getMessage());
        }
        return null;
    }
    public Vuelo searchVuelo(Vuelo v) throws SQLException {
        vueloSQL = "SELECT * FROM vuelos where cod_vuelo = ?";

        try {
                PreparedStatement st = con.prepareStatement(vueloSQL);
                st.setString(1, v.getCodVuelo());
                ResultSet rs = st.executeQuery();
            if (rs.next()) {

                v.setCodVuelo(rs.getString("cod_vuelo"));
                v.setFechaSalida(rs.getDate("fecha_salida").toLocalDate());
                v.setDestino(rs.getString("destino"));
                v.setPorcedencia(rs.getString("procedencia"));

                return v;
            }
        }catch (SQLException i) {
            System.out.println(i.getMessage());
        }
        return null;
    }

    public void update(Vuelo nuevoVuelo,Vuelo vueloExistente) throws SQLException {
        vueloSQL = "UPDATE vuelos SET cod_vuelo = ? WHERE cod_vuelo = ?";

        PreparedStatement ps = con.prepareStatement(vueloSQL);

        ps.setString(1, nuevoVuelo.getCodVuelo());
        ps.setString(2, vueloExistente.getCodVuelo());
        ps.executeUpdate();

    }

    public void delete(Vuelo v) throws SQLException {
        vueloSQL="DELETE FROM vuelos WHERE cod_vuelo = ?";
        try {

            PreparedStatement ps = con.prepareStatement(vueloSQL);
            ps.setString(1, v.getCodVuelo());
            ps.executeUpdate();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public java.sql.Date convertir(LocalDate fechaSalida) {
        java.sql.Date fecha;
        fecha = java.sql.Date.valueOf(fechaSalida);
        return fecha;
    }

}
