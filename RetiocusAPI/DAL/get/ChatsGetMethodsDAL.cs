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
    public class ChatsGetMethodsDAL
    {
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

                miComando.CommandText = "SELECT ChatsDeUsuario(@uidSolicitante)";

                parametro.ParameterName = "@uidSolicitante";

                parametro.Value = uidUsuarioSolicitante;

                miComando.Parameters.Add(parametro);

                lector = miComando.ExecuteReader();

                if (lector.HasRows)

                    while (lector.Read())
                    {
                        listadoChats.Add(new Chat((int)lector["ID"],
                            (String)lector["uid_usuario_1"],
                            (String)lector["uid_usuario_2"]));
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
