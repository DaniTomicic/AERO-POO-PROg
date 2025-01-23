package modelo;

import DBRelated.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;


public class VueloDAO {
    private Connection con =null;
    private String vueloSQL;
    private Vuelo v =new Vuelo();

    public VueloDAO(Connection con) {
        this.con = con;
    }
    public VueloDAO(Vuelo v) {
        this.v=v;
    }

    public VueloDAO() {

    }

    public void create(Vuelo vuelo) {
        vueloSQL = "insert into vuelos values(?,?,?,?)";

            try{
                this.con = ConnectionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(vueloSQL);

                ps.setString(1, vuelo.getCodVuelo());
                ps.setDate(2, convertir(vuelo.getFechaSalida()));
                ps.setString(3, vuelo.getDestino());
                ps.setString(4,vuelo.getPorcedencia());
                ps.executeUpdate();

            }catch (SQLIntegrityConstraintViolationException e){
                System.out.println("ERROR: se ha violado clave primario: "+ e.getMessage());
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }
    public Vuelo read() throws SQLException {
        vueloSQL = "SELECT * FROM vuelos";
        try {
            this.con = ConnectionDB.getConnection();
            Statement st = con.createStatement(); ResultSet rs = st.executeQuery(vueloSQL);
                if (rs.next()) {
                    return new Vuelo(
                            rs.getString(1),
                            LocalDate.parse(rs.getString(2)),
                            rs.getString(3),
                            rs.getString(4)
                    );
                }


        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("ERROR: se ha violado clave primaria: "+ e.getMessage());
        }catch (SQLException e){
            System.out.println("ERROR: "+ e.getMessage());
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


    public void delete(Vuelo vuelo){
        vueloSQL = "DELETE FROM vuelos WHERE cod_vuelo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(vueloSQL);

            ps.setString(1, vuelo.getCodVuelo());
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
