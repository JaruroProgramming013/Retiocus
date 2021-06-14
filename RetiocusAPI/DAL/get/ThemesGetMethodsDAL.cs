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
        /// <summary>
        /// Trae el numero de temas comunes entre dos usuarios de la BBDD.
        /// </summary>
        /// <param name="uidSolicitante">ID del usuario que solicita la peticion</param>
        /// <param name="uidSolicitado">ID del usuario al que hay que evaluar respecto al solicitante</param>
        /// <returns>Numero de temas comunes entre los dos usuarios.</returns>
        public static int getNumeroTemasComunes(String uidSolicitante, String uidSolicitado)
        {
            int numeroTemas=0;

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();

            SqlParameter parametroSolicitante = new SqlParameter(), parametroSolicitado=new SqlParameter();

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT NumeroTemas FROM TemasComunesEntreDosUsuarios(@uidSolicitante, @uidSolicitado)";

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
                        numeroTemas=(int)lector["NumeroTemas"];
                    }
                

                lector.Close();
                conexion.closeConnection(ref refConexion);
            }
            catch (SqlException exSql)
            {
                throw;
            }

            return numeroTemas;
        }

        /// <summary>
        /// Trae los temas de un usuario en especifico de la BBDD
        /// </summary>
        /// <param name="uidSolicitante">UID del usuario con preferencia a los temas</param>
        /// <returns>Listado de temas. Si no hay datos, estará vacio</returns>
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

                miComando.CommandText = "SELECT ID,nombre FROM TemasDeUsuario(@uidSolicitante)";

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

        /// <summary>
        /// Trae el listado de temas completo de la BBDD
        /// </summary>
        /// <returns>El listado de temas completo</returns>
        public static List<Tema> getListadoTemas()
        {
            List<Tema> listadoTemas = new List<Tema>();

            Conexion conexion = new Conexion();

            SqlCommand miComando = new SqlCommand();
            

            try
            {
                var refConexion = conexion.getConnection();

                miComando.Connection = refConexion;

                miComando.CommandType = CommandType.Text;

                miComando.CommandText = "SELECT ID,nombre FROM TodosLosTemas()";

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
