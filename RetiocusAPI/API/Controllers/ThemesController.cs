using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using BL.get;
using BL.post;
using Entities;

namespace API.Controllers
{
    public class ThemesController : ApiController
    {
        // GET: api/Themes
        public void Get()
        {
            HttpResponseMessage respuesta;

            List<Tema> listadoTemas = ThemesGetMethods.getListadoTemas();

            if (listadoTemas[0].Nombre.Contains("Error en la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, listadoTemas[0].Nombre);

            }
            else if (listadoTemas[0].Equals(new Tema()))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK, listadoTemas);
            }

            throw new HttpResponseException(respuesta);
        }

        [Route("api/Themes/{uidSolicitante}/sharedWith/{uidSolicitado}")]
        // GET: api/Themes/5/sharedWith/4
        public void Get(String uidSolicitante, String uidSolicitado)
        {
            HttpResponseMessage respuesta;

            try
            {
                int numeroTemas=ThemesGetMethods.getNumeroTemasComunes(uidSolicitante, uidSolicitado);
                respuesta = numeroTemas == 0 ? Request.CreateResponse(HttpStatusCode.NoContent) : Request.CreateResponse(HttpStatusCode.OK, numeroTemas);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }

            throw new HttpResponseException(respuesta);
        }

        [Route("api/Themes/{uidSolicitante}")]
        // GET: api/Themes/5
        public void Get(String uidSolicitante)
        {
            HttpResponseMessage respuesta;

            List<Tema> listadoTemas = ThemesGetMethods.getListadoTemasDeUsuario(uidSolicitante);

            if (listadoTemas[0].Nombre.Contains("Error en la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, listadoTemas[0].Nombre);

            }
            else if (listadoTemas[0].Equals(new Tema()))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK, listadoTemas);
            }

            throw new HttpResponseException(respuesta);
        }

        // POST: api/Themes
        public void Post([FromBody]Tema tema)
        {
            HttpResponseMessage respuesta;

            try
            {
                bool success = ThemesPostMethods.postTemaNuevo(tema);
                respuesta = Request.CreateResponse(!success ? HttpStatusCode.NoContent : HttpStatusCode.OK);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }

            throw new HttpResponseException(respuesta);
        }

        // PUT: api/Themes/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Themes/5
        public void Delete(int id)
        {
        }
    }
}
