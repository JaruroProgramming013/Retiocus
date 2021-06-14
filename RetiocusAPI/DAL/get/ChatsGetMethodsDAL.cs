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
    public static class ChatsGetMethodsDAL
    {
        /// <summary>
        /// Trae el listado de chats de la BBDD.
        /// </summary>
        /// <param name="uidUsuarioSolicitante">UID del usuario que pertenece a los chats traidos</param>
        /// <returns>Listado de chats. Si no hay datos, estará vacio.</returns>
        public static List<Chat> getChatsDeUsuario(String uidUsuarioSolicitante)
        {
            List<Chat> listadoChats = new List<Chat>();

            Conexion conexion = new Conexion();

            SqlConnection refConexion;

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametro = new SqlParameter();

            SqlDataReader lector;

            try
            {
                refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT ID, uid_usuario_1, uid_usuario_2 FROM ChatsDeUsuario(@uidSolicitante)";

                parametro.ParameterName = "@uidSolicitante";

                parametro.Value = uidUsuarioSolicitante;

                miComando.Parameters.Add(parametro);

                lector = miComando.ExecuteReader();

                if (lector.HasRows)

                    while (lector.Read())
                    {
                        listadoChats.Add(new Chat((int)lector[0],
                            (String)lector[1],
                            (String)lector[2]));
                    }

                else

                    listadoChats.Add(new Chat());

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return listadoChats;
        }
    }
}
