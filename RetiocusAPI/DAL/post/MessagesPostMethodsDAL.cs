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
    public class MessagesPostMethodsDAL
    {
        /// <summary>
        /// Añade un nuevo mensaje a la BBDD
        /// </summary>
        /// <param name="idChat">ID del chat en el que se escribió el mensaje</param>
        /// <param name="mensaje">Mensaje escrito</param>
        /// <returns>true si se ha insertado, false si no</returns>
        public static bool postMensajeNuevo(int idChat,Message mensaje)
        {
            bool success = false;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroUID = new SqlParameter(), 
                parametroIDChat=new SqlParameter(),
                parametroCuerpo = new SqlParameter(),
                parameterDateTime=new SqlParameter();

            try
            {
                SqlConnection refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.StoredProcedure;

                miComando.CommandText = "CrearMensaje";

                parametroIDChat.ParameterName = "@idChat";

                parametroIDChat.SqlDbType = SqlDbType.Int;

                parametroIDChat.Value = idChat;

                miComando.Parameters.Add(parametroIDChat);

                parametroUID.ParameterName = "@uidRemitente";

                parametroUID.SqlDbType = SqlDbType.VarChar;

                parametroUID.Value = mensaje.remitente;

                miComando.Parameters.Add(parametroUID);

                parametroCuerpo.ParameterName = "@cuerpo";

                parametroCuerpo.SqlDbType = SqlDbType.VarChar;

                parametroCuerpo.Value = mensaje.remitente;

                miComando.Parameters.Add(parametroCuerpo);

                parameterDateTime.ParameterName = "@fechaEnvio";

                parameterDateTime.SqlDbType = SqlDbType.DateTime;

                parameterDateTime.Value = new DateTime(mensaje.timeInMillis*10000);

                miComando.Parameters.Add(parameterDateTime);

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
