using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entities;

namespace DAL.get
{
    public static class UsersGetMethodsDAL
    {
        public static List<String> getListadoUsuariosTemasComunes(String uidUsuarioSolicitante)
        {
            List<String> listadoUidsUsuarios = new List<String>();

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametro = new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT SeleccionarUsuariosConTemasComunes(@uidSolicitante)";

                parametro.ParameterName = "@uidSolicitante";

                parametro.Value = uidUsuarioSolicitante;

                miComando.Parameters.Add(parametro);

                var lector = miComando.ExecuteReader();

                if(lector.HasRows)

                    while (lector.Read())
                    {
                        listadoUidsUsuarios.Add((String)lector[0]);
                    }

                else

                    listadoUidsUsuarios.Add("No hay resultados");

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return listadoUidsUsuarios;
        }

    }
}
