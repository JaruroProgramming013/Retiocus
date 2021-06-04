using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entities
{
    public class Tema
    {
        public int ID { get; }
        public string Nombre { get; set; }

        public Tema()
        {
            ID = 0;
            Nombre = "";
        }

        public Tema(int id, string nombre)
        {
            ID = id;
            Nombre = nombre;
        }

        public override bool Equals(object obj)
        {
            bool esIgual = false;

            if (this == obj)

                esIgual = true;

            else if (obj is Tema otro)
            {
                if (otro.ID == ID &&
                    otro.Nombre==Nombre)

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
