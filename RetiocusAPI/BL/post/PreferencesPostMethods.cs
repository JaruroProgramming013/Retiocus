using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.post;

namespace BL.post
{
    public static class PreferencesPostMethods
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

            
            try
            {
                success = PreferencesPostMethodsDAL.postPreferenciasNuevas(uidUsuario, temas);
            }
            catch (SqlException)
            {
                throw;
            }

            return success;
        }
    }
}
