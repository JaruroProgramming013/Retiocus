using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DAL.get;
using DAL.post;
using Entities;

namespace BL.post
{
    public static class ChatsPostMethods
    {
        /// <summary>
        /// Añade un nuevo chat a la BBDD
        /// </summary>
        /// <param name="chat">El chat a añadir</param>
        /// <returns>true si se ha insertado, false si no</returns>
        public static bool postChatNuevo(Chat chat)
        {
            bool success = false;

            try
            {
                success = ChatsPostMethodsDAL.postChatNuevo(chat);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return success;
        }
    }
}
