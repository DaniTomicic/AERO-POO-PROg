package Model;

import BD.DBCon;

import java.sql.*;
import java.util.ArrayList;

public class PasajeroDAO {
    private String pasajeroSQL;

    //crud
    public void create(Pasajero p){
        pasajeroSQL = "INSERT INTO pasajeros VALUES(?,?,?,?)";
        try(Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(pasajeroSQL);

            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCodVuelo());
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
        try(Connection con = DBCon.getConnection()){


        } catch (SQLException e){
            System.out.println("update - ERROR :" + e.getMessage());
        }
    }

}
