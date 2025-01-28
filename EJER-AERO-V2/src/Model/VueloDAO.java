package Model;

import BD.DBCon;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VueloDAO {
    private String vueloSLQ;
    private ArrayList<Vuelo> vuelos=new ArrayList<>();
    private Vuelo v;
    //crud
    public void create(Vuelo vuelo) {
        vueloSLQ="INSERT INTO vuelos VALUES(?,?,?,?)";
        try (Connection con = DBCon.getConnection()){
            PreparedStatement ps = con.prepareStatement(vueloSLQ);

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
        vueloSLQ="SELECT * FROM vuelos";
        try(Connection con = DBCon.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(vueloSLQ);
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
    public void update(Vuelo vuelo) {}
    public void delete(Vuelo vuelo) {}
    public Vuelo search(String codVuelo) {
        vueloSLQ="SELECT * FROM vuelos WHERE codVuelo=?";
        try (Connection con = DBCon.getConnection()){


        }catch (SQLException e){
            System.out.println("search - ERROR: " +e.getMessage());
        }
    }
    public java.sql.Date convertir(LocalDate fechaSalida) {
        java.sql.Date fecha;
        fecha = java.sql.Date.valueOf(fechaSalida);
        return fecha;
    }
}
