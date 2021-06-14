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
    public class UWCTHub : Hub
    {
        public async Task SendMessage(String uidRemitente)
        {
            UWCTQueryPackage listadoUsuarios = await UtilsUWCT.getUWCT(uidRemitente);
            await Clients.Caller.SendAsync("RecieveMessage", listadoUsuarios);
        }
    }
}
