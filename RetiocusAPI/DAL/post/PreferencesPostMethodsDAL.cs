using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.post
{
    public static class PreferencesPostMethodsDAL
    {
        /// <summary>
        /// Añade un listado de preferencias a la base de datos
        /// </summary>
        /// <param name="uidUsuario">UID del Usuario propietario de esas preferencias</param>
        /// <param name="temas">Temas a los que se refieren las preferencias</param>
        /// <returns>true si se han insertado, false si no</returns>
        public static bool postPreferenciasNuevas(String uidUsuario, List<int> temas)
        {
            bool success = false;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroUID = new SqlParameter();

            SqlParameter parameterTema= new SqlParameter();

            try
            {
                SqlConnection refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.StoredProcedure;

                miComando.CommandText = "AnhadirPreferencia";

                parametroUID.ParameterName = "@uidUsuario";

                parametroUID.SqlDbType = SqlDbType.VarChar;

                parametroUID.Value = uidUsuario;

                miComando.Parameters.Add(parametroUID);

                parameterTema.ParameterName = "@idtema";

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
