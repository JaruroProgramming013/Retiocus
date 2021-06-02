using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL;
using DAL.post;

namespace BL.post
{
    public static class UsersPostMethods
    {
        public static bool postUsuarioNuevo(String uidUsuario)
        {
            bool success = false;

            try
            {
                success = UsersPostMethodsDAL.postUsuarioNuevo(uidUsuario);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return success;
        }
    }
}
