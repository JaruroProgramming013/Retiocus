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
    public static class ThemesGetMethodsDAL
    {
        public static List<Tema> getListadoTemasComunes(String uidSolicitante, String uidSolicitado)
        {
            List<Tema> listadoTemas = new List<Tema>();

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroSolicitante = new SqlParameter(), parametroSolicitado=new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT TemasComunesEntreDosUsuarios(@uidSolicitante, @uidSolicitado)";

                parametroSolicitante.ParameterName = "@uidSolicitante";

                parametroSolicitante.Value = uidSolicitante;

                miComando.Parameters.Add(parametroSolicitante);

                parametroSolicitado.ParameterName = "@uidSolicitado";

                parametroSolicitado.Value = uidSolicitado;

                miComando.Parameters.Add(parametroSolicitado);

                var lector = miComando.ExecuteReader();

                if (lector.HasRows)

                    while (lector.Read())
                    {
                        listadoTemas.Add(new Tema((int)lector["ID"], (string)lector["nombre"]));
                    }

                else

                    listadoTemas.Add(new Tema());

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return listadoTemas;
        }

        public static List<Tema> getListadoTemasDeUsuario(String uidSolicitante)
        {
            List<Tema> listadoTemas = new List<Tema>();

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroSolicitante = new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT TemasDeUsuario(@uidSolicitante)";

                parametroSolicitante.ParameterName = "@uidSolicitante";

                parametroSolicitante.Value = uidSolicitante;

                miComando.Parameters.Add(parametroSolicitante);

                var lector = miComando.ExecuteReader();

                if (lector.HasRows)

                    while (lector.Read())
                    {
                        listadoTemas.Add(new Tema((int)lector["ID"], (string)lector["nombre"]));
                    }

                else

                    listadoTemas.Add(new Tema());

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return listadoTemas;
        }
    }
}
