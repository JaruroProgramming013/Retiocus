using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.delete
{
    public static class PreferencesDeleteMethodsDAL
    {
        /// <summary>
        /// Elimina un listado de preferencias de la BBDD.
        /// </summary>
        /// <param name="uidSolicitante">El UID del usuario con posesion de las preferencias.</param>
        /// <param name="temas">Temas a los que se refieren las preferencias a borrar</param>
        /// <returns>true si se han eliminado, false si no</returns>
        public static bool eliminarSugerencias(String uidSolicitante, List<int> temas)
        {
            bool success = false;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroUID = new SqlParameter();

            SqlParameter parameterTema = new SqlParameter();

            try
            {
                SqlConnection refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.StoredProcedure;

                miComando.CommandText = "BorrarPreferencia";

                parametroUID.ParameterName = "@uidSolicitante";

                parametroUID.SqlDbType = SqlDbType.VarChar;

                parametroUID.Value = uidSolicitante;

                miComando.Parameters.Add(parametroUID);

                parameterTema.ParameterName = "@idTema";

                parameterTema.SqlDbType = SqlDbType.Int;

                int filasInsertadas = 0;

                foreach (int idTemaActual in temas)
                {
                    parameterTema.Value = idTemaActual;

                    miComando.Parameters.Add(parameterTema);

                    filasInsertadas += miComando.ExecuteNonQuery();

                    miComando.Parameters.Remove(parameterTema);
                }

                conexion.closeConnection(ref refConexion);

                if (filasInsertadas == temas.Count)
                    success = true;

            }
            catch (SqlException)
            {
                throw;
            }

            return success;
        }
    }
}
