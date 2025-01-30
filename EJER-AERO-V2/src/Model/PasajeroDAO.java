package Model;

import BD.DBCon;

import java.sql.*;
import java.util.ArrayList;

public class PasajeroDAO {
    private String pasajeroSQL;

    //crud
    public void create(Pasajero p){
        pasajeroSQL = "INSERT INTO pasajeros VALUES(?,?,?)";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTelefono());
            ps.executeUpdate();

        } catch (SQLException e){
            System.out.println("create - ERROR :" + e.getMessage());
        }
    }
    public ArrayList<Pasajero> read(){
        pasajeroSQL = "SELECT * FROM pasajeros";
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        try(Connection con = DBCon.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(pasajeroSQL);
            while (rs.next()) {
                Pasajero p = new Pasajero(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );

                pasajeros.add(p);
            }

        }catch (SQLException e){
            System.out.println("read-ERROR :" + e.getMessage());
        }
        return pasajeros;
    }
    public void update(Pasajero p){
        pasajeroSQL="UPDATE pasajeros SET nombre = ?, telefono = ? WHERE DNI = ?";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getDni());
            ps.executeUpdate();

        } catch (SQLException e){
            System.out.println("update - ERROR :" + e.getMessage());
        }
    }
    public void delete(Pasajero p){
        pasajeroSQL="DELETE FROM pasajeros WHERE dni = ?";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, p.getDni());
            ps.executeUpdate();

        }catch (SQLException e){
            System.out.println("delete - ERROR :" + e.getMessage());
        }
    }

    public Pasajero search(String dni){
        pasajeroSQL = "SELECT * FROM pasajeros WHERE dni = ?";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);
            ps.setString(1,dni);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Pasajero(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                );
            }

        }catch (SQLException e){
            System.out.println("search - ERROR :" + e.getMessage());
        }
        return null;
    }
    public ArrayList<Vuelo> searchFlights(String dni) {
        pasajeroSQL = "SELECT v.cod_vuelo, v.fecha_salida, v.destino, v.procedencia " +
                "FROM vuelos v " +
                "JOIN vuelos_pasajeros vp ON v.cod_vuelo = vp.cod_vuelo " +
                "WHERE vp.dni = ?";

        ArrayList<Vuelo> vuelos = new ArrayList<>();

        try (Connection con = DBCon.getConnection()) {
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vuelo vuelo = new Vuelo(
                        rs.getString("cod_vuelo"),
                        rs.getDate("fecha_salida").toLocalDate(),
                        rs.getString("destino"),
                        rs.getString("procedencia")
                );
                vuelos.add(vuelo);
            }

        } catch (SQLException e) {
            System.out.println("search - ERROR: " + e.getMessage());
        }
        return vuelos;
    }


}
