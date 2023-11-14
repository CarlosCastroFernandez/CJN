package domain;

import clase.ActividadDiaria;
import clase.Empresa;
import clase.Sesion;
import exception.NombreConNumero;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;

@Log
public class EmpresaDAOImp implements EmpresaDAO {
    private static Connection connection;
    private static final String QUERY_LOAD = "select * from empresa where id = ?";
    private static final String QUERY_LOAD_ALL = "select * from empresa";
    private static final String actualizacion = "update empresa set email=?, nombre=?, telefono=?, responsable=?, observaciones=? where id=?";
    private static final String insertIntoEmpresa = "insert into empresa(email, nombre, telefono, responsable, observaciones)" + "values (?,?,?,?,?)";
    private static final String borrarEmpresa = "delete from empresa where id = ?";

    public EmpresaDAOImp(Connection conn) {
        connection = conn;
    }

    @Override
    public Empresa loadEnterprise(Integer id){
        Empresa empresa = null;
        try {
            PreparedStatement pst = connection.prepareStatement(QUERY_LOAD);
            pst.setInt(1, id);
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
            preparedStatement.setString(1, empresaImp.getEmail());
            preparedStatement.setString(2, empresaImp.getNombre());
            preparedStatement.setInt(3, empresaImp.getTelefono());
            preparedStatement.setString(4, empresaImp.getResponsable());
            preparedStatement.setString(5, empresaImp.getObservaciones());
            preparedStatement.setInt(6,empresaImp.getId());
            int filas = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return empresaImp;
    }

    @Override
    public  Empresa insert(Empresa empresa) {
        Empresa empresaIns = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertIntoEmpresa, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, empresa.getEmail());
            preparedStatement.setString(2, empresa.getNombre());
            preparedStatement.setInt(3, empresa.getTelefono());
            preparedStatement.setString(4, empresa.getResponsable());
            preparedStatement.setString(5, empresa.getObservaciones());
            int filas = preparedStatement.executeUpdate();
            if (filas == 1){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                empresaIns = empresa;
                empresaIns.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empresaIns;
    }

    @Override
    public void delete(Empresa empresa) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(borrarEmpresa);
            preparedStatement.setInt(1,empresa.getId());
            int filas = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
