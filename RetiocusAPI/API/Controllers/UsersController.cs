using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Formatting;
using System.Runtime.Remoting.Messaging;
using System.Web.Http;
using BL.get;
using BL.post;

namespace API.Controllers
{
    public class UsersController : ApiController
    {

        // GET: api/Users
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }


        [Route("api/Users/{uid}/usersWithCommonThemes")]
        [HttpGet]
        public void Get(String uid)
        {
            HttpResponseMessage respuesta;

            List<String> uidUsuarios = UsersGetMethods.getListadoUsuariosTemasComunes(uid);

            if (uidUsuarios[0].Contains("Error de la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,uidUsuarios[1]);

            }else if (uidUsuarios[0].Contains("No hay resultados"))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK,uidUsuarios);
            }

            throw new HttpResponseException(respuesta);

        }
        
        // POST: api/Users
        public void Post([FromBody]string value)
        {
            HttpResponseMessage respuesta;

            bool success = false;

            try
            {
                success = UsersPostMethods.postUsuarioNuevo(value);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }

            respuesta = Request.CreateResponse(!success ? HttpStatusCode.NoContent : HttpStatusCode.OK);

            throw new HttpResponseException(respuesta);
        }
        
        // PUT: api/Users/5
        public void Put(String uid)
        {
            
        }

        // DELETE: api/Users/5
        public void Delete(int id)
        {
        }
    }
}
