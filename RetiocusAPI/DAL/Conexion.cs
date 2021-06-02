using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL
{
    public class Conexion
    {
        //Atributos
        public String Server { get; set; }
        public String DataBase { get; set; }
        public String User { get; set; }
        public String Pass { get; set; }

        //Constructores

        public Conexion()
        {

            this.Server = "jruiz.database.windows.net";
            this.DataBase = "BBDDAzure";
            this.User = "jaruro2810";
            this.Pass = "mitesoro1.";

        }
        //Con parámetros por si quisiera cambiar las conexiones
        public Conexion(String server, String database, String user, String pass)
        {
            this.Server = server;
            this.DataBase = database;
            this.User = user;
            this.Pass = pass;
        }


        //METODOS

        /// <summary>
        /// Método que abre una conexión con la base de datos
        /// </summary>
        /// <pre>Nada.</pre>
        /// <returns>Una conexión abierta con la base de datos</returns>
        public SqlConnection getConnection()
        {
            SqlConnection connection = new SqlConnection();

            try
            {
                connection.ConnectionString = $"server={Server};database={DataBase};uid={User};pwd={Pass};";
                connection.Open();
            }
            catch (SqlException)
            {
                throw;
            }

            return connection;

        }




        /// <summary>
        /// Este metodo cierra una conexión con la Base de datos
        /// </summary>
        /// <post>La conexion es cerrada</post>
        /// <param name="connection">SqlConnection pr referencia. Conexion a cerrar
        /// </param>
        public void closeConnection(ref SqlConnection connection)
        {
            try
            {
                connection.Close();
            }
            catch (SqlException)
            {
                throw;
            }
            catch (InvalidOperationException)
            {
                throw;
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
