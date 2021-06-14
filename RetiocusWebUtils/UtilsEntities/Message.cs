using System;
using System.Collections.Generic;
using System.Text;

namespace UtilsEntities
{
    public class Message
    {
        public string remitente { get; }
        public string cuerpo { get; }
        public long timeInMillis { get; }

        public Message(string remitente, string cuerpo, long timeInMillis)
        {
            this.remitente = remitente;
            this.cuerpo = cuerpo;
            this.timeInMillis = timeInMillis;
        }
    }
}
