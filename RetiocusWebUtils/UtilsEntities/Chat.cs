using System;
using System.Collections.Generic;
using System.Text;

namespace UtilsEntities
{
    public class Chat
    {
        public int ID { get; }
        public string UIDUno { get; }
        public string UIDDos { get; }

        public Chat(int ID, string UIDUno, string UIDDos)
        {
            this.ID = ID;
            this.UIDUno = UIDUno;
            this.UIDDos = UIDDos;
        }
    }
}
