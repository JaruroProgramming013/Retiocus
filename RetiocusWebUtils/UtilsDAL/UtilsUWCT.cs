using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using UtilsEntities;

namespace UtilsDAL
{
    public static class UtilsUWCT
    {
        private static String uriBaseApi = "https://retiocusapi.azurewebsites.net/api/";

        /// <summary>
        /// Trae un listado de usuarios detallados con temas comunes
        /// </summary>
        /// <param name="uid"></param>
        /// <returns>Un paquete enviable en SignalR
        /// con el listado de usuarios de forma asíncrona</returns>
        public static async Task<UWCTQueryPackage> getUWCT(string uid)
        {

            HttpClient clienteUWCT = new HttpClient(), clienteNum = new HttpClient();
            Uri uriUWCT = new Uri($"{uriBaseApi}Users/{uid}");
            List<string> listadoUsuarios=new List<string>();
            List<UserDetailWCT> listadoDetallado = new List<UserDetailWCT>();

            try
            {
                HttpResponseMessage respuesta = await clienteUWCT.GetAsync(uriUWCT);
                if (respuesta.IsSuccessStatusCode)
                {
                    String respuestaJson = await clienteUWCT.GetStringAsync(uriUWCT);
                    clienteUWCT.Dispose();

                    if(!String.IsNullOrEmpty(respuestaJson))
                        listadoUsuarios = JsonConvert.DeserializeObject<List<string>>(respuestaJson);
                }
            }
            catch (Exception e)
            {
                throw e;
            }

            if(listadoUsuarios.Count != new List<string>().Count)
                foreach (String uidActual in listadoUsuarios)
                {
                    try
                    {
                        Uri uriNumTemas = new Uri($"{uriBaseApi}Themes/{uid}/sharedWith/{uidActual}");
                        HttpResponseMessage respuesta = await clienteNum.GetAsync(uriNumTemas);
                        if (respuesta.IsSuccessStatusCode)
                        {
                            String respuestaJson = await clienteNum.GetStringAsync(uriNumTemas);
                        
                            listadoDetallado.Add(new UserDetailWCT(await UserDetail.fromUid(uidActual), JsonConvert.DeserializeObject<int>(respuestaJson)));
                        }
                    }
                    catch (Exception)
                    {
                        listadoDetallado.Add(new UserDetailWCT(await UserDetail.fromUid(uidActual), 0));
                    }
                
                }
            else
            {
                listadoDetallado.Add(new UserDetailWCT(new UserDetail("",""),0));
            }

            listadoDetallado.Sort();
            listadoDetallado.Reverse();
            return new UWCTQueryPackage(listadoDetallado);
        }
    }
}
