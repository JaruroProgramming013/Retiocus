using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;

namespace RetiocusWebUtils.Hubs
{
    public class ChatHub : Hub
    {
        public override Task OnConnectedAsync()
        {
            return base.OnConnectedAsync();
        }

        public async Task SendMessage(String uidRemitente, String cuerpo, String uidDestinatario)
        {
            await Clients.User(uidDestinatario).SendAsync("RecieveMessage", uidRemitente, cuerpo, DateTime.Now);
        }
    }
}
