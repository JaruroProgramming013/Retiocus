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
    public class ChatsController : ApiController
    {
        // GET: api/Chats
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        [Route("api/Chats/{uid}/chatsOfUser")]
        [HttpGet]
        // GET: api/Chats/5
        public string Get(String uid)
        {
            HttpResponseMessage respuesta;

            List<Chat> listadoChats=ChatsGetMethods.getChatsDeUsuario(uid);

            if (listadoChats[0].UIDUno.Contains("Error en la base de datos"))
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable, listadoChats[0].UIDDos);

            }
            else if (listadoChats[0].Equals(new Chat()))
            {
                respuesta = Request.CreateResponse(HttpStatusCode.NoContent);
            }
            else
            {
                respuesta = Request.CreateResponse(HttpStatusCode.OK, listadoChats);
            }

            throw new HttpResponseException(respuesta);
        }

        // POST: api/Chats
        public void Post([FromBody]Chat chat)
        {
            HttpResponseMessage respuesta;

            bool success = false;

            try
            {
                success = ChatsPostMethods.postChatNuevo(chat);
            }
            catch (SqlException sqlEx)
            {
                respuesta = Request.CreateErrorResponse(HttpStatusCode.ServiceUnavailable,
                    "Error en la base de datos: " + sqlEx.StackTrace);
            }

            respuesta = Request.CreateResponse(!success ? HttpStatusCode.NoContent : HttpStatusCode.OK);

            throw new HttpResponseException(respuesta);
        }

        // PUT: api/Chats/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Chats/5
        public void Delete(int id)
        {
        }
    }
}
