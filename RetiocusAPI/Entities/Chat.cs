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
        public String UIDUno { get; }
        public String UIDDos { get; }

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

        /// <summary>
        /// Equals necesario para saber cuando no llegan datos de la BBDD
        /// </summary>
        /// <param name="obj">Objeto a comparar</param>
        /// <returns>true si es igual, false si no lo es</returns>
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
