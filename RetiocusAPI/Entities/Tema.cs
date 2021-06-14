using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Entities
{
    public class Tema : IComparable<Tema>, IEquatable<Tema>
    {
        public int ID { get; }
        public string Nombre { get; }

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

        public int CompareTo(Tema other)
        {
            return String.Compare(Nombre, other.Nombre, StringComparison.OrdinalIgnoreCase);
        }

        public bool Equals(Tema other)
        {
            return String.Equals(Nombre, other.Nombre, StringComparison.OrdinalIgnoreCase);
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
