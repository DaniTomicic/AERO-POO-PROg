package Model;

import BD.DBCon;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VueloDAO {
    private String vueloSQL;
    private ArrayList<Vuelo> vuelos=new ArrayList<>();
    private Vuelo v;
    //crud
    public void create(Vuelo vuelo) {
        vueloSQL="INSERT INTO vuelos VALUES(?,?,?,?)";
        try (Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(vueloSQL);

            ps.setString(1, vuelo.getCodVuelo());

            ps.setDate(2, convertir(vuelo.getFechaSalida()));

            ps.setString(3, vuelo.getDestino());

            ps.setString(4, vuelo.getProcedencia());
            ps.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("create - ERROR en FK o PK: " +e.getMessage());
        }catch (SQLException e){
            System.out.println("create - ERROR Gral.: " +e.getMessage());
        }
    }
    public ArrayList<Vuelo> read() {
        vueloSQL="SELECT * FROM vuelos";
        try(Connection con = DBCon.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(vueloSQL);
            while (rs.next()){
                v = new Vuelo(
                        rs.getString(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getString(3),
                        rs.getString(4)
                );
                vuelos.add(v);
            }
            return vuelos;
        }catch (SQLException e){
            System.out.println("read - ERROR: " +e.getMessage());
        }
        return vuelos;
    }
    public void update(Vuelo nuevoVuelo) {
        String vueloSQL = "UPDATE vuelos SET fecha_salida = ?, destino = ?, procedencia = ? WHERE cod_vuelo = ?";

        try (Connection con = DBCon.getConnection()) {
            PreparedStatement ps = con.prepareStatement(vueloSQL);

            ps.setDate(1, convertir(nuevoVuelo.getFechaSalida()));
            ps.setString(2, nuevoVuelo.getDestino());
            ps.setString(3, nuevoVuelo.getProcedencia());
            ps.setString(4, nuevoVuelo.getCodVuelo());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: Violación de clave primaria o foránea: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR - ERROR: " +e.getMessage());
        }
    }
    public void delete(Vuelo vuelo) {

        try (Connection con = DBCon.getConnection()) {
            vueloSQL = "DELETE FROM vuelos_pasajeros WHERE cod_vuelo = ?";

            try (PreparedStatement ps1 = con.prepareStatement(vueloSQL)) {
                ps1.setString(1, vuelo.getCodVuelo());
                ps1.executeUpdate();
            }

            vueloSQL = "DELETE FROM vuelos WHERE cod_vuelo = ?";
            try (PreparedStatement ps2 = con.prepareStatement(vueloSQL)) {
                ps2.setString(1, vuelo.getCodVuelo());
                ps2.executeUpdate();
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: Restricción de clave foránea o primaria: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR General: " + e.getMessage());
        }
    }

    public Vuelo search(String codVuelo) {
        vueloSQL="SELECT * FROM vuelos WHERE cod_vuelo=?";
        try (Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(vueloSQL);
            ps.setString(1, codVuelo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
               return new Vuelo(
                        rs.getString(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getString(3),
                        rs.getString(4)
                );
            }

        }catch (SQLException e){
            System.out.println("search - ERROR: " +e.getMessage());
        }
        return null;
    }
    public ArrayList<Vuelo> searchByDestination(String destino) {
        vueloSQL="SELECT * FROM vuelos WHERE destino=?";
        try (Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(vueloSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Vuelo vuelo = new Vuelo(
                rs.getString(1),
                rs.getDate(2).toLocalDate(),
                rs.getString(3),
                rs.getString(4));
            vuelos.add(vuelo);
            }
            return vuelos;


        }catch (SQLException e){
            System.out.println("searchByDestination - ERROR: " +e.getMessage());
        }
        return vuelos;
    }
    public ArrayList<Vuelo> searchByDate(LocalDate fechaSalida) {
        vueloSQL="SELECT * FROM vuelos WHERE fecha_salida=?";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(vueloSQL);
            ps.setDate(1, convertir(fechaSalida));
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                v = new Vuelo(
                        rs.getString(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getString(3),
                        rs.getString(4)
                );

                vuelos.add(new Vuelo());
            }

        }catch (SQLException e){
            System.out.println("searchByDate - ERROR: " +e.getMessage());
        }
        return vuelos;
    }
    public void setVuelosPasajeros(String codVuelo, String dni) {
        vueloSQL="INSERT INTO vuelos_pasajeros VALUES(?,?)";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(vueloSQL);

            ps.setString(1, codVuelo);
            ps.setString(2, dni);
            ps.executeUpdate();

        }catch (SQLException e){
            System.out.println("setVuelosPasajeros - ERROR: " +e.getMessage());
        }
    }

    public java.sql.Date convertir(LocalDate fechaSalida) {
        java.sql.Date fecha;
        fecha = java.sql.Date.valueOf(fechaSalida);
        return fecha;
    }
}
