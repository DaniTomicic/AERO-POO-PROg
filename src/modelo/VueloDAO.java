package modelo;

import DBRelated.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class VueloDAO {
    private Connection con =null;
    private String vueloSQL;
    private ArrayList<Vuelo> vuelos;
    private Vuelo v = new Vuelo();

    public VueloDAO(Connection con) {
        this.con = con;
    }
    public VueloDAO(Vuelo v) {
        this.v = v;
    }

    public VueloDAO() {

    }

    public void create(Vuelo vuelo) {
        String vueloSQL = "INSERT INTO vuelos VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(vueloSQL)) {

            ps.setString(1, vuelo.getCodVuelo());
            ps.setDate(2, convertir(vuelo.getFechaSalida()));
            ps.setString(3, vuelo.getDestino());
            ps.setString(4, vuelo.getPorcedencia());

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("ERROR: Violación de clave primaria. " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR en la base de datos: " + e.getMessage());
        }
    }

    public ArrayList<Vuelo> read() {
        vueloSQL = "SELECT * FROM vuelos";
        ArrayList<Vuelo> vuelos = new ArrayList<>();

        try (Connection con = ConnectionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(vueloSQL)) {

            while (rs.next()) {
                Vuelo vuelo = new Vuelo(
                        rs.getString(1),
                        LocalDate.parse(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4)
                );
                vuelos.add(vuelo);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primaria: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return vuelos;
    }
    public Vuelo readByCode(String codVuelo) {
        vueloSQL = "SELECT * FROM vuelos WHERE codVuelo = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(vueloSQL)) {

            ps.setString(1, codVuelo);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Vuelo v = new Vuelo();
                    v.setCodVuelo(rs.getString(1));
                    v.setFechaSalida(LocalDate.parse(rs.getString(2)));
                    v.setDestino(rs.getString(3));
                    v.setPorcedencia(rs.getString(4));
                    return v;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primaria: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null; // Devuelve null si no se encuentra el vuelo
    }

    public String readByDestination(String destino) {
        vueloSQL = "SELECT cod_vuelo FROM vuelos where destino = ?";

        try {
            this.con = ConnectionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(vueloSQL);
            ps.setString(1, destino);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                v.setCodVuelo(rs.getString(1));
            }
        }catch (SQLException e){
            System.out.println("ERROR: se ha violado clave primaria: " + e.getMessage());
        }
        return v.getCodVuelo();
    }

        public ArrayList<Vuelo> readcodVuelo() {
        vueloSQL = "SELECT * FROM vuelos";
        ArrayList<Vuelo> vuelos = new ArrayList<>();

        try (Connection con = ConnectionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(vueloSQL)) {

            while (rs.next()) {
                Vuelo vuelo = new Vuelo(
                        rs.getString("cod_vuelo"),
                        LocalDate.parse(rs.getString("fecha_salida")),
                        rs.getString("destino"),
                        rs.getString("procedencia")
                        );
                vuelos.add(vuelo);
            }
            return vuelos;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: se ha violado clave primaria: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return vuelos; // Devuelve la lista (puede estar vacía si hubo un error o no hay datos)
    }


    public void update(Vuelo nuevoVuelo,Vuelo vueloExistente) throws SQLException {
        vueloSQL = "UPDATE vuelos SET cod_vuelo = ? WHERE cod_vuelo = ?";

        PreparedStatement ps = con.prepareStatement(vueloSQL);

        ps.setString(1, nuevoVuelo.getCodVuelo());
        ps.setString(2, vueloExistente.getCodVuelo());
        ps.executeUpdate();

    }


    public void delete(String s) {
        try {
            // Elimina pasajeros relacionados antes
            vueloSQL = "DELETE FROM pasajeros WHERE cod_vuelo = ?";
            try (PreparedStatement psPasajeros = con.prepareStatement(vueloSQL)) {
                psPasajeros.setString(1, s);
                psPasajeros.executeUpdate();
            }

            // Ahora elimina el vuelo
            vueloSQL = "DELETE FROM vuelos WHERE cod_vuelo = ?";
            try (PreparedStatement psVuelo = con.prepareStatement(vueloSQL)) {
                psVuelo.setString(1, s);
                psVuelo.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public java.sql.Date convertir(LocalDate fechaSalida) {
        java.sql.Date fecha;
        fecha = java.sql.Date.valueOf(fechaSalida);
        return fecha;
    }


}
