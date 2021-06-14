using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.get;
using Entities;

namespace BL.get
{
    public static class UsersGetMethods
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

            try
            {
                listadoUidsUsuarios = UsersGetMethodsDAL.getListadoUsuariosTemasComunes(uidUsuarioSolicitante);
            }
            catch (SqlException exSql)
            {
                listadoUidsUsuarios.Add("Error de la base de datos");
                listadoUidsUsuarios.Add(exSql.Message); 
            }

            return listadoUidsUsuarios;
        }

    }
}
