using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using BL.delete;
using BL.post;
using Entities;

namespace API.Controllers
{
    public class PreferencesController : ApiController
    {
        // GET: api/Preferences
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/Preferences/5
        public string Get(int id)
        {
            return "value";
        }

        [Route("api/Preferences/{uid}")]
        [HttpPost]
        // POST: api/Preferences/5
        public void Post([FromBody] List<int> temas, String uid)
        {
            HttpResponseMessage respuesta;
            bool success = false;

            try
            {
                success = PreferencesPostMethods.postPreferenciasNuevas(uid,temas);
                respuesta = Request.CreateResponse(!success ? HttpStatusCode.NoContent : HttpStatusCode.OK);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }
            
            throw new HttpResponseException(respuesta);
        }

        // PUT: api/Preferences/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Preferences/5
        public void Delete(string uid, [FromBody] List<int> temas)
        {
            HttpResponseMessage respuesta;

            try
            {
                bool success = PreferencesDeleteMethods.eliminarSugerencias(uid, temas);
                respuesta = Request.CreateResponse(!success ? HttpStatusCode.NoContent : HttpStatusCode.OK);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }

            throw new HttpResponseException(respuesta);
        }
    }
}
