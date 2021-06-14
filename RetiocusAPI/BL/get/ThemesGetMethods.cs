using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.get;
using Entities;

namespace BL.get
{
    public static class ThemesGetMethods
    {
        /// <summary>
        /// Trae el numero de temas comunes entre dos usuarios de la BBDD.
        /// </summary>
        /// <param name="uidSolicitante">ID del usuario que solicita la peticion</param>
        /// <param name="uidSolicitado">ID del usuario al que hay que evaluar respecto al solicitante</param>
        /// <returns>Numero de temas comunes entre los dos usuarios.</returns>
        public static int getNumeroTemasComunes(String uidSolicitante, String uidSolicitado)
        {
            int numTemas;

            try
            {
                numTemas = ThemesGetMethodsDAL.getNumeroTemasComunes(uidSolicitante, uidSolicitado);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return numTemas;
        }

        /// <summary>
        /// Trae los temas de un usuario en especifico de la BBDD
        /// </summary>
        /// <param name="uidSolicitante">UID del usuario con preferencia a los temas</param>
        /// <returns>Listado de temas. Si no hay datos, estará vacio</returns>
        public static List<Tema> getListadoTemasDeUsuario(String uidSolicitante)
        {
            List<Tema> listadoTemas = new List<Tema>();

            try
            {
                listadoTemas = ThemesGetMethodsDAL.getListadoTemasDeUsuario(uidSolicitante);
            }
            catch (SqlException exSql)
            {
                listadoTemas.Add(new Tema(0, "Error en la base de datos: " + exSql.StackTrace));
            }

            listadoTemas.Sort();
            return listadoTemas;
        }

        /// <summary>
        /// Trae el listado de temas completo de la BBDD
        /// </summary>
        /// <returns>El listado de temas completo</returns>
        public static List<Tema> getListadoTemas()
        {
            List<Tema> listadoTemas = new List<Tema>();

            try
            {
                listadoTemas = ThemesGetMethodsDAL.getListadoTemas();
            }
            catch (SqlException exSql)
            {
                listadoTemas.Add(new Tema(0, "Error en la base de datos: " + exSql.Message));
            }

            return listadoTemas;
        }
    }
}
