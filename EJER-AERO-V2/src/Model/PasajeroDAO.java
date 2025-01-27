package Model;

import BD.DBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public Pasajero read(){
        pasajeroSQL = "SELECT * FROM pasajeros";
        try(Connection con = DBCon.getConnection()){

        }catch (SQLException e){
            System.out.println("read-ERROR :" + e.getMessage());
        }
    }

}
