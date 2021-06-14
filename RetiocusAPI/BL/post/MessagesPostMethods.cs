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
    public static class MessagesPostMethods
    {
        /// <summary>
        /// Añade un nuevo mensaje a la BBDD
        /// </summary>
        /// <param name="idChat">ID del chat en el que se escribió el mensaje</param>
        /// <param name="mensaje">Mensaje escrito</param>
        /// <returns>true si se ha insertado, false si no</returns>
        public static bool postMensajeNuevo(int idChat, Message mensaje)
        {
            bool success = false;

            try
            {
                success = MessagesPostMethodsDAL.postMensajeNuevo(idChat, mensaje);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return success;
        }
    }
}
