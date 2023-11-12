package domain;

import clase.ActividadDiaria;
import clase.Empresa;
import clase.Sesion;
import exception.NombreConNumero;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Log
public class EmpresaDAOImp implements EmpresaDAO {
    private static Connection connection;
    private static final String QUERY_LOAD = "select * from empresa where nombre = ?";
    private static final String QUERY_LOAD_ALL = "select * from empresa";
    private static final String actualizacion = "update empresa set email=?, nombre=?, telefono=?, responsable=?, observaciones=?";

    public EmpresaDAOImp(Connection conn) {
        connection = conn;
    }

    @Override
    public Empresa loadEnterprise(String dni){
        Empresa empresa = null;
        try {
            PreparedStatement pst = connection.prepareStatement(QUERY_LOAD);
            pst.setString(1, empresa.getNombre());
            ResultSet rs = pst.executeQuery();

            if (rs.next()){
                empresa = new Empresa(rs.getInt("id"),rs.getString("email"), rs.getString("nombre"), rs.getInt("telefono"), rs.getString("responsable"), rs.getString("observaciones"));
            } else {
                log.warning("La empresa no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empresa;
    }

    @Override
    public ArrayList<Empresa> loadAllEnterprise(){
        Empresa empresa;
        ArrayList<Empresa> allEnterprise = new ArrayList<>();
        try{
            PreparedStatement pst = connection.prepareStatement(QUERY_LOAD_ALL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                empresa = new Empresa(rs.getInt("id"),rs.getString("email"), rs.getString("nombre"), rs.getInt("telefono"), rs.getString("responsable"), rs.getString("observaciones"));
                allEnterprise.add(empresa);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return allEnterprise;
    }

    @Override
    public Empresa update(Empresa empresa) {
        Empresa empresaImp = empresa;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(actualizacion);
            preparedStatement.setString(1, empresaImp.getNombre());
            preparedStatement.setInt(2, empresaImp.getTelefono());
            preparedStatement.setString(3, empresaImp.getObservaciones());
            preparedStatement.setString(4, empresaImp.getResponsable());
            preparedStatement.setString(5, empresaImp.getEmail());
            int filas = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return empresaImp;
    }
}
