using System;
using System.Collections.Generic;
using System.Text;

namespace UtilsEntities
{
    public class CWLMQueryPackage
    {
        public List<ChatDetailWLM> listadoChats { get; }

        public CWLMQueryPackage(List<ChatDetailWLM> listadoChats)
        {
            this.listadoChats = listadoChats;
        }
    }
}
