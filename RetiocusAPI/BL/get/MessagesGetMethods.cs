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
    public static class MessagesGetMethods
    {
        /// <summary>
        /// Trae el ultimo mensaje de un chat de la BBDD.
        /// </summary>
        /// <param name="idChat">ID del chat al que pertence el mensaje</param>
        /// <returns>El ultimo mensaje del chat. Si no existe, estará vacio.</returns>
        public static Message getUltimoMensaje(int idChat)
        {
            Message mensaje;

            try
            {
                mensaje = MessagesGetMethodsDAL.getUltimoMensaje(idChat);
            }
            catch (SqlException sqlEx)
            {
                mensaje=new Message("Error en la base de datos",sqlEx.Message,DateTime.Now.Ticks / 10000);
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
            List<Message> mensajes = new List<Message>();

            try
            {
                mensajes = MessagesGetMethodsDAL.getRegistroMensajesDesde(idChat, fechaMensaje);
            }
            catch (SqlException sqlEx)
            {
                mensajes.Add(new Message("Error en la base de datos",sqlEx.Message, DateTime.Now.Ticks / 10000));
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
            
            try
            {
                mensajes = MessagesGetMethodsDAL.getPrimerRegistroMensajes(idChat);
            }
            catch (SqlException sqlEx)
            {
                mensajes.Add(new Message("Error en la base de datos", sqlEx.Message, DateTime.Now.Ticks / 10000));
            }

            return mensajes;
        }
    }
}
