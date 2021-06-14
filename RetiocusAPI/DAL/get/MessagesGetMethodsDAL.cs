using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entities;

namespace DAL.get
{
    public static class MessagesGetMethodsDAL
    {
        /// <summary>
        /// Trae el ultimo mensaje de un chat de la BBDD.
        /// </summary>
        /// <param name="idChat">ID del chat al que pertence el mensaje</param>
        /// <returns>El ultimo mensaje del chat. Si no existe, estará vacio.</returns>
        public static Message getUltimoMensaje(int idChat)
        {
            Message mensaje;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametro = new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT Remitente,Cuerpo,FechaHoraEnvio FROM UltimoMensajeDelChat(@chatID)";

                parametro.ParameterName = "@chatID";

                parametro.Value = idChat;

                miComando.Parameters.Add(parametro);

                var lector = miComando.ExecuteReader();

                if (lector.HasRows)
                {
                    lector.Read();

                    mensaje = new Message((String) lector[0], (String) lector[1], ((DateTime) lector[2]).Ticks/10000);
                }
                else
                {
                    mensaje = new Message();
                }

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException sqlEx)
            {
                throw;
            }

            return mensaje;
        }

        /// <summary>
        /// Trae los ultimos 50 mensajes de un chat partiendo de un instante temporal de la BBDD.
        /// </summary>
        /// <param name="idChat">ID del chat al que pertencen los mensajes</param>
        /// <param name="fechaMensaje">Instante desde el cual se traen mensajes</param>
        /// <returns>Listado de mensajes. Si no hay datos, estará vacio.</returns>
        public static List<Message> getRegistroMensajesDesde(int idChat, DateTime fechaMensaje)
        {
            List<Message> mensajes=new List<Message>();

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametro = new SqlParameter(), parametroFecha=new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT Remitente,Cuerpo,FechaHoraEnvio FROM RegistroMensajesPreviosDelChat(@chatID, @dateTime)";

                parametro.ParameterName = "@chatID";

                parametro.Value = idChat;

                miComando.Parameters.Add(parametro);

                parametroFecha.ParameterName = "@dateTime";

                parametroFecha.SqlDbType = SqlDbType.DateTime;

                parametroFecha.Value = idChat;

                
                var lector = miComando.ExecuteReader();

                if (lector.HasRows)
                {
                    while (lector.Read())
                    {
                        mensajes.Add(new Message((String)lector[0], (String)lector[1], ((DateTime)lector[2]).Ticks / 10000));  
                    }
                    
                }
                else
                {
                    mensajes.Add(new Message());
                }

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException sqlEx)
            {
                throw;
            }

            return mensajes;
        }

        /// <summary>
        /// Trae los ultimos 50 mensajes de un chat de la BBDD
        /// </summary>
        /// <param name="idChat">ID del chat al que pertenecen los mensajes</param>
        /// <returns>Un listado de mensajes</returns>
        public static List<Message> getPrimerRegistroMensajes(int idChat)
        {
            List<Message> mensajes = new List<Message>();

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametro = new SqlParameter(), parametroFecha = new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT Remitente,Cuerpo,FechaHoraEnvio FROM RegistroMensajesInicialesDelChat(@chatID)";

                parametro.ParameterName = "@chatID";

                parametro.Value = idChat;

                var lector = miComando.ExecuteReader();

                if (lector.HasRows)
                {
                    while (lector.Read())
                    {
                        mensajes.Add(new Message((String)lector[0], (String)lector[1], ((DateTime)lector[2]).Ticks / 10000));
                    }

                }
                else
                {
                    mensajes.Add(new Message());
                }

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException sqlEx)
            {
                throw;
            }

            return mensajes;
        }
    }
}
