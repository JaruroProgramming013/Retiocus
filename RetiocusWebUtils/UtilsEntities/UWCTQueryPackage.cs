using System;
using System.Collections.Generic;
using System.Text;

namespace UtilsEntities
{
    public class UWCTQueryPackage
    {
        public List<UserDetailWCT> listadoUsuariosDetalladosWCT { get; }

        public UWCTQueryPackage(List<UserDetailWCT> lista)
        {
            listadoUsuariosDetalladosWCT = lista;
        }
    }
}
