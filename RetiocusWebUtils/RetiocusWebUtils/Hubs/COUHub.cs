using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using Microsoft.AspNetCore.SignalR;
using UtilsDAL;
using UtilsEntities;

namespace RetiocusWebUtils.Hubs
{
    public class COUHub : Hub
    {
        public async Task SendMessage(String uidRemitente)
        {
            string chatListadoSerializadoCou = await UtilsCOU.getCOU(uidRemitente);
            await Clients.Caller.SendAsync("RecieveMessage", chatListadoSerializadoCou);
        }
    }
}
