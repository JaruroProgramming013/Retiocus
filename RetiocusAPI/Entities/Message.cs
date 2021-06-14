using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entities
{
    public class Message
    {
        public string remitente { get; }
        public string cuerpo { get; }
        public long timeInMillis { get; }

        public Message()
        {
            remitente = "";
            cuerpo = "";
            timeInMillis = DateTime.Now.Ticks / 10000;
        }

        public Message(string remitente, string cuerpo, long fecha)
        {
            this.remitente = remitente;
            this.cuerpo = cuerpo;
            timeInMillis = fecha;
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

            else if (obj is Message otro)
            {
                if (otro.remitente == remitente &&
                    otro.cuerpo == cuerpo)

                    esIgual = true;
            }

            return esIgual;
        }

        public override int GetHashCode()
        {
            return remitente.GetHashCode()+cuerpo.GetHashCode();
        }
    }
}
