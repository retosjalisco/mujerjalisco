using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.IO;
using System.Security.Cryptography;
using System.Net.Mail;


namespace MujerJaliscoService
{
    // NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de interfaz "IService1" en el código y en el archivo de configuración a la vez.
    [ServiceContract]
    public interface IService1
    {


        [OperationContract]
        [WebGet(UriTemplate = "/sesion/?email={email}&clave={clave}", ResponseFormat = WebMessageFormat.Json)]
        Status getLogin(string email, string clave);

        [OperationContract]
        [WebGet(UriTemplate = "/registrarse/?nombre={nombre}&apellido={apellido}&email={email}&clave={clave}&direccion={direccion}&ciudad={ciudad}&telefono={telefono}&fotoid={fotoid}&fotocomp={fotocomp}&fechanac={fechanac}&ecivil={ecivil}", ResponseFormat = WebMessageFormat.Json)]
        Status setRegistro(string nombre, string apellido, string email, string clave, string direccion, string ciudad, string telefono, string fotoid, string fotocomp, string fechanac, string ecivil);

        [OperationContract]
        [WebGet(UriTemplate = "/socioeconomico/?idmujer={idmujer}&vivecon={vivecon}&viveconnombres={viveconnombres}&ingreso={ingreso}&casa={casa}&habitaciones={habitaciones}&trabaja={trabaja}&lugartrabajo={lugartrabajo}", ResponseFormat = WebMessageFormat.Json)]
        Status setSocioEconomico(int idmujer, string vivecon, string viveconnombres, string ingreso, int casa, int habitaciones, int trabaja, string lugartrabajo);

        [OperationContract]
        [WebGet(UriTemplate = "/agregardependientes/?idmujer={idmujer}&nombre={nombre}&apellido={apellido}&fechanac={fechanac}&masculino={masculino}&estudiante={estudiante}&fotoid={fotoid}&parentesco={parentesco}", ResponseFormat = WebMessageFormat.Json)]
        Status setAgregarDependientes(int idmujer, string nombre, string apellido, string fechanac, int masculino, int estudiante, string fotoid, string parentesco);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/subirimagen1")]
        Status FileUploadImagen1(Stream stream);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/subirimagen2")]
        Status FileUploadImagen2(Stream stream);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/subirimagen3")]
        Status FileUploadImagen3(Stream stream);

        [OperationContract]
        [WebGet(UriTemplate = "/asignarfotoid/?idmujer={idmujer}", ResponseFormat = WebMessageFormat.Json)]
        Status renameFileFotoId(int idmujer);

        [OperationContract]
        [WebGet(UriTemplate = "/asignarfotocomp/?idmujer={idmujer}", ResponseFormat = WebMessageFormat.Json)]
        Status renameFileFotoComp(int idmujer);

        [OperationContract]
        [WebGet(UriTemplate = "/asignarfotodependiente/?iddependiente={iddependiente}", ResponseFormat = WebMessageFormat.Json)]
        Status renameFileFotoDependiente(int iddependiente);

        [OperationContract]
        [WebGet(UriTemplate = "/enviarcomunidad/?idmujer={idmujer}&comentario={comentario}", ResponseFormat = WebMessageFormat.Json)]
        Status SetComunidad(int idmujer, string comentario);

        [OperationContract]
        [WebGet(UriTemplate = "/enviarcomentario/?idmujer={idmujer}&idcomunidad={idcomunidad}&comentario={comentario}", ResponseFormat = WebMessageFormat.Json)]
        Status SetComentario(int idmujer, int idcomunidad, string comentario);

        [OperationContract]
        [WebGet(UriTemplate = "/enviarmenaje/?idmujer={idmujer}&idsedi={idsedi}&mensaje={mensaje}&tipo={tipo}", ResponseFormat = WebMessageFormat.Json)]
        Status SetMensaje(int idmujer, int idsedi, string mensaje, int tipo);

        [OperationContract]
        [WebGet(UriTemplate = "/agregarproducto/?idmujer={idmujer}&nombre={nombre}&descripcion={descripcion}&precio={precio}", ResponseFormat = WebMessageFormat.Json)]
        Status SetProducto(int idmujer, string nombre, string descripcion, string precio);

        [OperationContract]
        [WebGet(UriTemplate = "/obtenercomunidad", ResponseFormat = WebMessageFormat.Json)]
        Comunidad getComunidad();

        [OperationContract]
        [WebGet(UriTemplate = "/obtenerventas", ResponseFormat = WebMessageFormat.Json)]
        Ventas getVentas();

        [OperationContract]
        [WebGet(UriTemplate = "/obtenermensaje/?idmujer={idmujer}", ResponseFormat = WebMessageFormat.Json)]
        Buzon getBuzon(int idmujer);

        [OperationContract]
        [WebGet(UriTemplate = "/obtenerproducto/?idproducto={idproducto}", ResponseFormat = WebMessageFormat.Json)]
        producto getProducto(int idproducto);

        [OperationContract]
        [WebGet(UriTemplate = "/detallemujer/?idmujer={idmujer}", ResponseFormat = WebMessageFormat.Json)]
        mujer getDetalleMujer(int idmujer);
    }

    [DataContract]
    public class Status
    {
        [DataMember]
        public string status { get; set; }
    }

    public class comunidad
    {
        [DataMember]
        public int idcomunidad;
        [DataMember]
        public string nombremujer;
        [DataMember]
        public string apellidomujer;
        [DataMember]
        public string imagen;
        [DataMember]
        public string comentario;
        [DataMember]
        public string toc;
    }

    public class mujer
    {
        [DataMember]
        public int idmujer;
        [DataMember]
        public string nombre;
        [DataMember]
        public string apellido;
        [DataMember]
        public string fotomujer;
        [DataMember]
        public string email;
        [DataMember]
        public string telefono;
        [DataMember]
        public string direccion;
    }
    

    [DataContract]
    public class Comunidad
    {
        [DataMember]
        public List<comunidad> listacomunidad { get; set; }
    }

    public class producto
    {
        [DataMember]
        public int idproducto;
        [DataMember]
        public string nombre;
        [DataMember]
        public string descripcion;
        [DataMember]
        public int precio;
        [DataMember]
        public string imagen;
        [DataMember]
        public string nombremujer;
        [DataMember]
        public string apellidomujer;
    }

    [DataContract]
    public class Ventas
    {
        [DataMember]
        public List<producto> listaproductos { get; set; }
    }

    [DataContract]
    public class Buzon
    {
        [DataMember]
        public List<mensaje> listamensajes { get; set; }
    }

    public class mensaje
    {
        [DataMember]
        public string nombremujer;
        [DataMember]
        public string imagenmujer;
        [DataMember]
        public string nombresedi;
        [DataMember]
        public string buzonmensaje;
        [DataMember]
        public int tipo;
    }

}
