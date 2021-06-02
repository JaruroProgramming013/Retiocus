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
                listadoUidsUsuarios.Add(exSql.StackTrace); 
            }

            return listadoUidsUsuarios;
        }

    }
}
