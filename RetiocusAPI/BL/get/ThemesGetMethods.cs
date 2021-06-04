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
        public static List<Tema> getListadoTemasComunes(String uidSolicitante, String uidSolicitado)
        {
            List<Tema> listadoTemas = new List<Tema>();

            try
            {
                listadoTemas = ThemesGetMethodsDAL.getListadoTemasComunes(uidSolicitante, uidSolicitado);
            }
            catch (SqlException exSql)
            {
                listadoTemas.Add(new Tema(0,"Error en la base de datos: "+exSql.StackTrace));
            }

            return listadoTemas;
        }

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

            return listadoTemas;
        }
    }
}
