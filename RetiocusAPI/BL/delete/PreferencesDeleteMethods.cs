using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.post;

namespace BL.delete
{
    public static class PreferencesDeleteMethods
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

            try
            {
                success = PreferencesPostMethodsDAL.postPreferenciasNuevas(uidSolicitante, temas);

            }
            catch (SqlException)
            {
                throw;
            }

            return success;
        }
    }
}
