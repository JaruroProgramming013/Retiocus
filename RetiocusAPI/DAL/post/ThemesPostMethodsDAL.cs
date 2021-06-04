using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entities;

namespace DAL.post
{
    public static class ThemesPostMethodsDAL
    {
        public static bool postTemaNuevo(Tema tema)
        {
            bool success = false;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroNombre = new SqlParameter();

            try
            {
                SqlConnection refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.StoredProcedure;

                miComando.CommandText = "AnhadirChat";

                parametroNombre.ParameterName = "@uidNombre";

                parametroNombre.SqlDbType = SqlDbType.VarChar;

                parametroNombre.Value = tema.Nombre;

                miComando.Parameters.Add(parametroNombre);

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
