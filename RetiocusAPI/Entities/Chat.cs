using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entities
{
    public class Chat
    {
        public int ID { get; }
        public String UIDUno { get; set; }
        public String UIDDos { get; set; }

        public Chat()
        {
            ID = 0;
            UIDUno = "";
            UIDDos = "";
        }

        public Chat(int ID, String UIDUno, String UIDDos)
        {
            this.ID = ID;
            this.UIDUno = UIDUno;
            this.UIDDos = UIDDos;
        }

        public override bool Equals(object obj)
        {
            bool esIgual = false;

            if (this == obj)
            
                esIgual = true;
            
            else if (obj is Chat otro)
            {
                if (otro.ID == ID &&
                    otro.UIDUno == UIDUno &&
                    otro.UIDDos == UIDDos)

                    esIgual = true;
            }

            return esIgual;
        }

        public override int GetHashCode()
        {
            return ID.GetHashCode();
        }
    }
}
