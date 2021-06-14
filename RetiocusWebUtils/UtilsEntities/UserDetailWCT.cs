using System;
using System.Collections.Generic;
using System.Text;

namespace UtilsEntities
{
    public class UserDetailWCT : IEquatable<UserDetailWCT>, IComparable<UserDetailWCT>
    {
        public UserDetail usuario { get; }
        public int numeroTemas { get; }

        public UserDetailWCT(UserDetail user, int cTN)
        {
            usuario = user;
            numeroTemas = cTN;
        }

        
        public bool Equals(UserDetailWCT other)
        {
            return numeroTemas.Equals(other.numeroTemas);
        }

        /// <summary>
        /// CompareTo necesario para filtrar la lista de usuarios detallados
        /// </summary>
        /// <param name="other"></param>
        /// <returns>El compareTo del numero de temas en comun</returns>
        public int CompareTo(UserDetailWCT other)
        {
            return numeroTemas.CompareTo(other.numeroTemas);
        }
    }
}
