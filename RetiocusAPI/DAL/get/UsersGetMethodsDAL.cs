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
        /// <summary>
        /// Trae los UID de los usuarios con
        /// temas comunes y que no tengan un chat con respecto al solicitante de la BBDD.
        /// </summary>
        /// <param name="uidUsuarioSolicitante">UID del usuario solicitante de la peticion</param>
        /// <returns>Listado de usuarios con temas comunes. Si no hay datos, estará vacio</returns>
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

                miComando.CommandText = "SELECT uid_usuario FROM SeleccionarUsuariosConTemasComunes(@uidSolicitante)";

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
