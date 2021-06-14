using System;
using System.Collections.Generic;
using System.Text;

namespace UtilsEntities
{
    public class ChatDetailWLM : IComparable<ChatDetailWLM>, IEquatable<ChatDetailWLM>
    {
        public ChatDetail chat { get; }
        public Message ultimoMensaje { get; }

        public ChatDetailWLM(ChatDetail chat, Message last)
        {
            this.chat = chat;
            ultimoMensaje = last;
        }

        /// <summary>
        /// CompareTo necesario para filtrar la lista de chat detallados
        /// </summary>
        /// <param name="other"></param>
        /// <returns>El compareTo de la fecha de envio de los ultimos mensajes</returns>
        public int CompareTo(ChatDetailWLM other)
        {
            return ultimoMensaje.timeInMillis.CompareTo(other.ultimoMensaje.timeInMillis);
        }

        public bool Equals(ChatDetailWLM other)
        {
            return ultimoMensaje.timeInMillis.Equals(other.ultimoMensaje.timeInMillis);
        }
    }
}
