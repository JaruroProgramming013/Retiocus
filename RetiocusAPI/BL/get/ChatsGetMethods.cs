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
    public static class ChatsGetMethods
    {
        /// <summary>
        /// Trae el listado de chats de la BBDD.
        /// </summary>
        /// <param name="uidUsuarioSolicitante">UID del usuario que pertenece a los chats traidos</param>
        /// <returns>Listado de chats. Si no hay datos, estará vacio.</returns>
        public static List<Chat> getChatsDeUsuario(String uidUsuarioSolicitante)
        {
            List<Chat> listadoChats = new List<Chat>();

            try
            {
                listadoChats = ChatsGetMethodsDAL.getChatsDeUsuario(uidUsuarioSolicitante);
            }
            catch (SqlException exSql)
            {
                listadoChats.Add(new Chat(0,"Error en la base de datos",exSql.Message));
            }

            return listadoChats;
        }
    }
}
