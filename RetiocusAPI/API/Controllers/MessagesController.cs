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
    public class MessagesController : ApiController
    {
        // GET: api/Messages
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        [Route("api/Messages/{chatID}")]
        [HttpGet]
        // GET: api/Messages/5
        public void Get(int chatID)
        {
            HttpResponseMessage respuesta;

            List<Message> listadoMensajes = MessagesGetMethods.getPrimerRegistroMensajes(chatID);

            if (listadoMensajes[0].remitente.Contains("Error en la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, listadoMensajes[0].cuerpo);

            }
            else if (listadoMensajes[0].Equals(new Message()))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK, listadoMensajes);
            }

            throw new HttpResponseException(respuesta);
        }

        [Route("api/Messages/{chatID}/lastMessage")]
        [HttpGet]
        // GET: api/Messages/5/lastMessage
        public void GetLastMessage(int chatID)
        {
            HttpResponseMessage respuesta;

            Message mensaje = MessagesGetMethods.getUltimoMensaje(chatID);

            if (mensaje.remitente.Contains("Error en la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, mensaje.cuerpo);

            }
            else if (mensaje.Equals(new Message()))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK, mensaje);
            }

            throw new HttpResponseException(respuesta);
        }

        [Route("api/Messages/{chatID}/since/{dateTime}")]
        [HttpGet]
        // GET: api/Messages/5
        public void Get(int chatID, DateTime dateTime)
        {
            HttpResponseMessage respuesta;

            List<Message> listadoMensajes = MessagesGetMethods.getRegistroMensajesDesde(chatID,dateTime);

            if (listadoMensajes[0].remitente.Contains("Error en la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, listadoMensajes[0].cuerpo);

            }
            else if (listadoMensajes[0].Equals(new Message()))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK, listadoMensajes);
            }

            throw new HttpResponseException(respuesta);
        }

        [Route("api/Messages/{chatID}")]
        // POST: api/Messages
        public void Post(int chatID,[FromBody]Message message)
        {
            HttpResponseMessage respuesta;

            try
            {
                bool success = MessagesPostMethods.postMensajeNuevo(chatID,message);
                respuesta = Request.CreateResponse(!success ? HttpStatusCode.NoContent : HttpStatusCode.OK);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }

            throw new HttpResponseException(respuesta);
        }

        // PUT: api/Messages/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Messages/5
        public void Delete(int id)
        {
        }
    }
}
