using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace UtilsEntities
{
    public class ChatDetail
    {
        public int ID { get; }
        public string usuarioSolicitante { get; }
        public UserDetail usuarioSolicitado { get; }

        public ChatDetail(int ID, string uUno, UserDetail uDos)
        {
            this.ID = ID;
            usuarioSolicitante = uUno;
            usuarioSolicitado = uDos;
        }

        public static async Task<ChatDetail> detallar(Chat chat, String uidRemitente)
        {
            UserDetail otroUsuario;

            if (uidRemitente.Equals(chat.UIDUno))
            {
                otroUsuario = await UserDetail.fromUid(chat.UIDDos);
            }
            else
            {
                otroUsuario = await UserDetail.fromUid(chat.UIDUno);
            }

            return new ChatDetail(chat.ID, uidRemitente, otroUsuario);
        }
    }
}
