package Control;

import Model.Pasajero;
import Model.PasajeroDAO;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasajeroControl {
    private final PasajeroDAO pasajeroDAO;

    public PasajeroControl(PasajeroDAO pasajeroDAO) {
        this.pasajeroDAO = pasajeroDAO;
    }

    public void insert(){
        try {
            Pasajero p = this.dataValidationApplication();
            this.pasajeroDAO.create(p);

            JOptionPane.showMessageDialog((Component) null,"Pasajero insertado correctamente");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private Pasajero dataValidationApplication(){
        String dni = this.dataValidation("DNI","teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$");
        String name =this.dataValidation("Name","teclea el nombre del pasajero","");
        String cellphone = this.dataValidation("Cellphone","teclea el numero de telefono del pasajero","^[678][0-9]{8}$");
        String codVuelo = this.searchAndAssignCodVuelo();

        return new Pasajero(dni,name,cellphone,codVuelo);
    }
    private String searchAndAssignCodVuelo(){
        boolean codVueloFinished = false;
        String option;
        String[] codVuelos = pasajeroDAO.read().stream()
                .map(Pasajero::getCodVuelo)
                .filter(codVuelo ->codVuelo.contains("A"))
                .toArray(String[]::new);
        do {
            option = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    codVuelos,
                    codVuelos[0]
            );
            if (option.isEmpty()){
                JOptionPane.showMessageDialog(null,"El codigo de vuelo no puede estar vacio");
            }else {
                codVueloFinished = true;
            }

        }while (!codVueloFinished);
        return option;
    }

    private String dataValidation(String data, String msj, String pattern){
        boolean funcfinished=false;
        String var="";
        do {
            try {
                var = JOptionPane.showInputDialog(null,msj);
                if (var.isEmpty()){
                    JOptionPane.showMessageDialog(null,"el dato " + data + " no puede estar vacio");
                }
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(var);

                if (m.matches()){
                    funcfinished=true;
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"No se aceptan numeros aqui");
            }

        }while (!funcfinished);
        return var;
    }
}
