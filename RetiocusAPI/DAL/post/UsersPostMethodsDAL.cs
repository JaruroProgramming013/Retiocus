
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.post
{   public static class UsersPostMethodsDAL
    {
        /// <summary>
        /// Añade un nuevo usuario a la BBDD
        /// </summary>
        /// <param name="uidUsuario">UID del Usuario a insertar</param>
        /// <returns>true si se ha insertado, false si no</returns>
        public static bool postUsuarioNuevo(String uidUsuario)
        {
            bool success = false;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametro = new SqlParameter();

            try
            {
                SqlConnection refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.StoredProcedure;

                miComando.CommandText = "AnhadirUsuario";

                parametro.ParameterName = "@uidUsuario";

                parametro.Value = uidUsuario;

                miComando.Parameters.Add(parametro);

                int filasInsertadas = miComando.ExecuteNonQuery();

                conexion.closeConnection(ref refConexion);

                if (filasInsertadas > 0)
                    success = true;
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return success;
        } 
    }
}
