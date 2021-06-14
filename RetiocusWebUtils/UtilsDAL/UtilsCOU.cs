using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using FirebaseAdmin.Messaging;
using Newtonsoft.Json;
using UtilsEntities;
using Message = UtilsEntities.Message;

namespace UtilsDAL
{
    public static class UtilsCOU
    {
        private static String uriBaseApi = "https://retiocusapi.azurewebsites.net/api/";

        /// <summary>
        /// Trae el listado de chats detallados del usuario solicitante
        /// </summary>
        /// <param name="uid"></param>
        /// <returns>Un JSON con el listado de usuarios de forma asíncrona</returns>
        public static async Task<string> getCOU(string uid)
        {
            HttpClient clienteCWLM = new HttpClient(), clienteUltMensaje = new HttpClient();
            Uri uri = new Uri($"{uriBaseApi}Chats/{uid}");
            List<Chat> listadoChats = new List<Chat>();
            List<ChatDetailWLM> listadoDetallado = new List<ChatDetailWLM>();

            try
            {
                var respuesta = await clienteCWLM.GetAsync(uri);
                if (respuesta.IsSuccessStatusCode)
                {
                    var respuestaJson = await clienteCWLM.GetStringAsync(uri);
                    clienteCWLM.Dispose();

                    if (!String.IsNullOrEmpty(respuestaJson))
                        listadoChats = JsonConvert.DeserializeObject<List<Chat>>(respuestaJson);
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            if (listadoChats.Count != new List<Chat>().Count)
                foreach (Chat chatActual in listadoChats)
                {
                    try
                    {
                        Uri uriUltMensaje = new Uri($"{uriBaseApi}Messages/{chatActual.ID}/lastMessage");
                        HttpResponseMessage respuesta = await clienteUltMensaje.GetAsync(uriUltMensaje);
                        if (respuesta.IsSuccessStatusCode)
                        {
                            String respuestaJson = await clienteUltMensaje.GetStringAsync(uriUltMensaje);

                            Message message = JsonConvert.DeserializeObject<Message>(respuestaJson);

                            listadoDetallado.Add(
                                new ChatDetailWLM(
                                    await ChatDetail.detallar(chatActual, uid), message));
                        }
                    }
                    catch (Exception e)
                    {
                        listadoDetallado.Add(
                            new ChatDetailWLM(
                                await ChatDetail.detallar(chatActual, uid),new Message("","",DateTime.Now.Ticks / 10000)));
                    }
                }
            else
            {
                listadoDetallado.Add(new ChatDetailWLM(new ChatDetail(0,"",new UserDetail("","")),new Message("","", DateTime.Now.Ticks / 10000)));
            }
            
            listadoDetallado.Sort();
            listadoDetallado.Reverse();
            CWLMQueryPackage query = new CWLMQueryPackage(listadoDetallado);

            return JsonConvert.SerializeObject(query);
        }
    }
}
