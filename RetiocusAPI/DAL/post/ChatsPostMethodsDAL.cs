using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entities;

namespace DAL.post
{
    public static class ChatsPostMethodsDAL
    {
        public static bool postChatNuevo(Chat chat)
        {
            bool success = false;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroSolicitante = new SqlParameter(), parametroSolicitado=new SqlParameter();

            try
            {
                SqlConnection refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.StoredProcedure;

                miComando.CommandText = "AnhadirChat";

                parametroSolicitante.ParameterName = "@uidSolicitante";

                parametroSolicitante.SqlDbType = SqlDbType.VarChar;

                parametroSolicitante.Value = chat.UIDUno;

                miComando.Parameters.Add(parametroSolicitante);

                parametroSolicitado.ParameterName = "@uidSolicitado";

                parametroSolicitado.SqlDbType = SqlDbType.VarChar;

                parametroSolicitado.Value = chat.UIDDos;

                miComando.Parameters.Add(parametroSolicitado);

                int filasInsertadas = miComando.ExecuteNonQuery();

                conexion.closeConnection(ref refConexion);

                if (filasInsertadas > 0)
                    success = true;
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return success;
        }
    }
}
