using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.post;
using Entities;

namespace BL.post
{
    public static class ThemesPostMethods
    {
        /// <summary>
        /// Añade un nuevo tema a la BBDD
        /// </summary>
        /// <param name="tema">Tema a insertar</param>
        /// <returns>true si se ha insertado, false si no</returns>
        public static bool postTemaNuevo(Tema tema)
        {
            bool success = false;

            try
            {
                success = ThemesPostMethodsDAL.postTemaNuevo(tema);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return success;
        }
    }
}
