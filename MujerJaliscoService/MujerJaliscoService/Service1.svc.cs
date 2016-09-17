using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data.SqlClient;
using System.Data.Sql;
using System.Data;
using System.IO;
using System.Security.Cryptography;
using System.Net.Mail;
using System.Net;

namespace MujerJaliscoService
{
    // NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de clase "Service1" en el código, en svc y en el archivo de configuración.
    // NOTE: para iniciar el Cliente de prueba WCF para probar este servicio, seleccione Service1.svc o Service1.svc.cs en el Explorador de soluciones e inicie la depuración.
    public class Service1 : IService1
    {
        public string StringConexion = "Data Source=mujerjalisco.db.9692238.hostedresource.com; Initial Catalog=mujerjalisco; User ID=mujerjalisco; Password='Saguchi1!';";
        //public string StringConexion = @"Server=DESKTOP-F14HC8G\SQLEXPRESS;Database=mujerjalisco;Integrated Security=True;";

        public Status getLogin(string email, string clave)
        {
            string resultado = "Error";
            string query = "SELECT * FROM mujeres WHERE email='" + email + "' AND clave='" + clave + "' ";
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;
            conn.Open();

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand(query, conn);
            SqlDataAdapter adapter = new SqlDataAdapter(cmd);
            adapter.Fill(ds);

            try
            {
                if (ds.Tables[0].Rows.Count > 0)
                {
                    resultado = ds.Tables[0].Rows[0]["idmujer"].ToString();
                }
            }
            catch
            {
            }

            return new Status { status = resultado };
        }

        public Status setRegistro(string nombre, string apellido, string email, string clave, string direccion,string ciudad, string telefono, string fotoid, string fotocomp, string fechanac, string ecivil)
        {
            string resultado = "Error";
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;
            conn.Open();

            string query = "SELECT COUNT (*) FROM mujeres WHERE email='" + email + "' AND clave='" + clave + "' ";
            SqlCommand cmd = new SqlCommand(query, conn);
            int existe = Convert.ToInt16(cmd.ExecuteScalar());
            conn.Close();

            if (existe > 0)
                resultado = "existe";
            else
            {
                try
                {
                    conn.Open();
                    using (SqlCommand command = conn.CreateCommand())
                    {
                        command.CommandText = "INSERT INTO mujeres([nombre],[apellido], [email], [clave], [direccion],[ciudad], [telefono], [fotoid], [fotocomprobante],[fechanacimiento],[estadocivil]) VALUES (@nombre,@apellido, @email, @clave, @direccion, @ciudad, @telefono, @fotoid, @fotocomp, @fechanac, @ecivil)";
                        command.Parameters.AddRange(new SqlParameter[]
                            {
                                new SqlParameter("@nombre", nombre),
                                new SqlParameter("@apellido", apellido),
                                new SqlParameter("@email", email),
                                new SqlParameter("@clave", clave),
                                new SqlParameter("@direccion", direccion),
                                new SqlParameter("@ciudad", ciudad),
                                new SqlParameter("@telefono", telefono),
                                new SqlParameter("@fotoid", fotoid),
                                new SqlParameter("@fotocomp", fotocomp),
                                new SqlParameter("@fechanac", fechanac),
                                new SqlParameter("@ecivil", ecivil)
                            });
                        command.ExecuteNonQuery();
                        resultado = "ok";
                    }
                    conn.Close();


                }
                catch
                {

                }

                string query2 = "SELECT * FROM mujeres WHERE email='" + email + "' AND clave='" + clave + "'";
                SqlConnection conn2 = new SqlConnection();
                conn2.ConnectionString = StringConexion;
                conn2.Open();

                DataSet ds = new DataSet();
                SqlCommand command2 = new SqlCommand(query2, conn2);
                SqlDataAdapter adapter = new SqlDataAdapter(command2);
                adapter.Fill(ds);
                conn.Close();

                try
                {
                    if (ds.Tables[0].Rows.Count > 0)
                    {
                        resultado = ds.Tables[0].Rows[0]["idmujer"].ToString();
                    }
                }
                catch
                {
                }
            }
            return new Status { status = resultado };
        }

        public Status setSocioEconomico(int idmujer, string vivecon, string viveconnombres, string ingreso, int casa, int habitaciones, int trabaja, string lugartrabajo)
        {
            string resultado = "Error";
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;

            try
            {
                conn.Open();
                using (SqlCommand command = conn.CreateCommand())
                {
                    command.CommandText = "INSERT INTO [estudiosocioeconomico] ([vivecon],[viveconnombres], [ingresomensual], [casapropia], [nohabitaciones], [trabajasactualmente], [lugardetrabajo], [idmujer]) VALUES (@vivecon, @viveconnombres, @ingresomensual, @casapropia, @nohabitaciones, @trabajasactualmente, @lugardetrabajo, @idmujer)";
                    command.Parameters.AddRange(new SqlParameter[]
                            {
                                new SqlParameter("@vivecon", vivecon),
                                new SqlParameter("@viveconnombres", viveconnombres),
                                new SqlParameter("@ingresomensual", ingreso),
                                new SqlParameter("@casapropia", casa),
                                new SqlParameter("@nohabitaciones", habitaciones),
                                new SqlParameter("@trabajasactualmente", trabaja),
                                new SqlParameter("@lugardetrabajo", lugartrabajo),
                                new SqlParameter("@idmujer", idmujer)
                            });
                    command.ExecuteNonQuery();
                    resultado = "ok";
                }
                conn.Close();
               
            }
            catch{

            }
            return new Status { status = resultado };
        }

        public Status setAgregarDependientes(int idmujer, string nombre, string apellido, string fechanac, int masculino, int estudiante, string fotoid, string parentesco)
        {
            string resultado = "Error";
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;

            try
            {
                conn.Open();
                using (SqlCommand command = conn.CreateCommand())
                {
                    command.CommandText = "INSERT INTO [dependientes] ([nombre],[apellido], [fechanacimiento], [masculino], [estudiante], [fotoid], [parentesco],[idmujer]) VALUES (@nombre, @apellido, @fechanacimiento, @masculino, @estudiante, @fotoid, @parentesco,@idmujer)";
                    command.Parameters.AddRange(new SqlParameter[]
                            {
                                new SqlParameter("@nombre", nombre),
                                new SqlParameter("@apellido", apellido),
                                new SqlParameter("@fechanacimiento", fechanac),
                                new SqlParameter("@masculino", masculino),
                                new SqlParameter("@estudiante", estudiante),
                                new SqlParameter("@fotoid", fotoid),
                                new SqlParameter("@parentesco", parentesco),
                                new SqlParameter("@idmujer", idmujer)
                            });
                    command.ExecuteNonQuery();
                    resultado = "ok";
                }
                conn.Close();

                string query2 = "SELECT * FROM dependientes WHERE nombre='" + nombre + "' AND apellido='" + apellido + "' AND idmujer=" + idmujer + "";
                SqlConnection conn2 = new SqlConnection();
                conn2.ConnectionString = StringConexion;
                conn2.Open();

                DataSet ds = new DataSet();
                SqlCommand command2 = new SqlCommand(query2, conn2);
                SqlDataAdapter adapter = new SqlDataAdapter(command2);
                adapter.Fill(ds);
                conn.Close();

                try
                {
                    if (ds.Tables[0].Rows.Count > 0)
                    {
                        resultado = ds.Tables[0].Rows[0]["iddependiente"].ToString();
                    }
                }
                catch
                {
                }

            }
            catch
            {
            }
            return new Status { status = resultado };
        }
        public Status FileUploadImagen1(Stream stream)
        {
            FileStream f = new FileStream("D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\imagen1.jpg", FileMode.OpenOrCreate);

            const int bufferLen = 4096;
            byte[] buffer = new byte[bufferLen];

            int count = 0;

            while ((count = stream.Read(buffer, 0, bufferLen)) > 0)
            {
                f.Write(buffer, 0, count);
            }

            f.Close();
            stream.Close();
            return new Status { status = "completado" };
        }

        public Status FileUploadImagen2(Stream stream)
        {
            FileStream f = new FileStream("D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\imagen2.jpg", FileMode.OpenOrCreate);

            const int bufferLen = 4096;
            byte[] buffer = new byte[bufferLen];

            int count = 0;

            while ((count = stream.Read(buffer, 0, bufferLen)) > 0)
            {
                f.Write(buffer, 0, count);
            }

            f.Close();
            stream.Close();
            return new Status { status = "completado" };
        }

        public Status FileUploadImagen3(Stream stream)
        {
            FileStream f = new FileStream("D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\imagen3.jpg", FileMode.OpenOrCreate);

            const int bufferLen = 4096;
            byte[] buffer = new byte[bufferLen];

            int count = 0;

            while ((count = stream.Read(buffer, 0, bufferLen)) > 0)
            {
                f.Write(buffer, 0, count);
            }

            f.Close();
            stream.Close();
            return new Status { status = "completado" };
        }

        public Status renameFileFotoId(int idmujer)
        {
            string path = "D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\";
            string Fromfile = path + "imagen1.jpg";
            string Tofile = path + idmujer + ".jpg";
            int incremento = 0;
            string filename = "";
            do
            {
                incremento++;
                filename = idmujer + incremento + ".jpg";
                Tofile = path + filename;
            } while (File.Exists(Tofile));
            File.Move(Fromfile, Tofile);

            using (SqlConnection conn = new SqlConnection(StringConexion))
            {
                conn.Open();
                SqlCommand cmd = conn.CreateCommand();


                cmd.CommandText = "UPDATE mujeres SET fotoid = @fotoid WHERE idcocinero=" + Convert.ToInt16(idmujer) + "";
                cmd.Parameters.Add(new SqlParameter("@fotoid", "http://dotstudioservices.com/promosbc/imagenesMujerJalisco/" + filename + ""));
                cmd.ExecuteNonQuery();
                conn.Close();
            }

            return new Status { status = "completado" };
        }

        public Status  renameFileFotoDependiente(int iddependiente)
        {
            string path = "D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\";
            string Fromfile = path + "imagen3.jpg";
            string Tofile = path + iddependiente + ".jpg";
            int incremento = 0;
            string filename = "";
            do
            {
                incremento++;
                filename = iddependiente + incremento + ".jpg";
                Tofile = path + filename;
            } while (File.Exists(Tofile));
            File.Move(Fromfile, Tofile);

            using (SqlConnection conn = new SqlConnection(StringConexion))
            {
                conn.Open();
                SqlCommand cmd = conn.CreateCommand();


                cmd.CommandText = "UPDATE dependientes SET fotoid = @fotoid WHERE iddependiente=" + Convert.ToInt16(iddependiente) + "";
                cmd.Parameters.Add(new SqlParameter("@fotoid", "http://dotstudioservices.com/promosbc/imagenesMujerJalisco/" + filename + ""));
                cmd.ExecuteNonQuery();
                conn.Close();
            }

            return new Status { status = "completado" };
        }

        public Status renameFileFotoComp(int idmujer)
        {
            string path = "D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\";
            string Fromfile = path + "imagen2.jpg";
            string Tofile = path + idmujer + ".jpg";
            int incremento = 0;
            string filename = "";
            do
            {
                incremento++;
                filename = idmujer + incremento + ".jpg";
                Tofile = path + filename;
            } while (File.Exists(Tofile));
            File.Move(Fromfile, Tofile);

            using (SqlConnection conn = new SqlConnection(StringConexion))
            {
                conn.Open();
                SqlCommand cmd = conn.CreateCommand();


                cmd.CommandText = "UPDATE mujeres SET fotocomprobante = @fotocomprobante WHERE idcocinero=" + Convert.ToInt16(idmujer) + "";
                cmd.Parameters.Add(new SqlParameter("@fotocomprobante", "http://dotstudioservices.com/promosbc/imagenesMujerJalisco/" + filename + ""));
                cmd.ExecuteNonQuery();
                conn.Close();
            }

            return new Status { status = "completado" };
        }

        public Status SetComunidad(int idmujer, string comentario)
        {
            string path = "D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\";
            //string path = "C:\\imagenes\\";
            string Fromfile = path + "imagen1.jpg";
            string Tofile = path + "comunidad" + idmujer + ".jpg";

            int incremento = 0;
            string filename = "";
            string imagenpath = "";

            if (File.Exists(Fromfile))
            {
                do
                {
                    incremento++;
                    filename = "comunidad" + idmujer + incremento + ".jpg";
                    Tofile = path + filename;
                } while (File.Exists(Tofile));

                File.Move(Fromfile, Tofile);
                imagenpath = "http://www.dotstudioservices.com/imagenesMujerJalisco/" + filename;
            }

            string resultado = "Error";

            try
            {

                using (SqlConnection conn = new SqlConnection(StringConexion))
                {
                    conn.Open();
                    SqlCommand cmd = conn.CreateCommand();

                    cmd.CommandText = "INSERT INTO comunidad ([idmujer],[imagen],[comentario]) VALUES (@idmujer,@imagen,@comentario)";
                    cmd.Parameters.AddRange(new SqlParameter[]
                            {
                    
                        new SqlParameter ("@idmujer",idmujer),
                        new SqlParameter ("@imagen",imagenpath),
                        new SqlParameter ("@comentario",comentario)                    
                    });
  
                    cmd.ExecuteNonQuery();
                    resultado = "completado";

                    conn.Close();

                }
            }
            catch
            { }
            return new Status { status = resultado };
        }

        public Status SetComentario(int idmujer, int idcomunidad, string comentario)
        {
            string resultado = "Error";

            try
            {

                using (SqlConnection conn = new SqlConnection(StringConexion))
                {
                    conn.Open();
                    SqlCommand cmd = conn.CreateCommand();

                    cmd.CommandText = "INSERT INTO comentarios ([idmujer],[idcomunidad],[comentario]) VALUES (@idmujer,@idcomunidad,@comentario)";
                    cmd.Parameters.AddRange(new SqlParameter[]
                            {
                    
                        new SqlParameter ("@idmujer",idmujer),
                        new SqlParameter ("@idcomunidad",idcomunidad),
                        new SqlParameter ("@comentario",comentario)                    
                    });

                    cmd.ExecuteNonQuery();
                    resultado = "completado";

                    conn.Close();

                }
            }
            catch
            { }
            return new Status { status = resultado };
        }

        public Status SetMensaje (int idmujer, int idsedi, string mensaje, int tipo)
        {
            string resultado = "Error";

            try
            {

                using (SqlConnection conn = new SqlConnection(StringConexion))
                {
                    conn.Open();
                    SqlCommand cmd = conn.CreateCommand();

                    cmd.CommandText = "INSERT INTO buzon ([idmujer],[idsedi],[mensaje],[tipo]) VALUES (@idmujer,@idsedi,@mensaje,@tipo)";
                    cmd.Parameters.AddRange(new SqlParameter[]
                            {
                    
                        new SqlParameter ("@idmujer",idmujer),
                        new SqlParameter ("@idsedi",idsedi),
                        new SqlParameter ("@mensaje",mensaje),        
                        new SqlParameter ("@tipo",tipo)
                    });

                    cmd.ExecuteNonQuery();
                    resultado = "completado";

                    conn.Close();

                }
            }
            catch
            { }
            return new Status { status = resultado };
        }

        public Status SetProducto(int idmujer, string nombre, string descripcion, string precio)
        {
            //string path = "D:\\Hosting\\9692238\\html\\promosbc\\imagenesMujerJalisco\\";
            string path = "C:\\imagenes\\";
            string Fromfile = path + "imagen2.jpg";
            string Tofile = path + "producto" + idmujer + ".jpg";

            int incremento = 0;
            string filename = "";
            string imagenpath = "";

            if (File.Exists(Fromfile))
            {
                do
                {
                    incremento++;
                    filename = "producto" + idmujer + incremento + ".jpg";
                    Tofile = path + filename;
                } while (File.Exists(Tofile));

                File.Move(Fromfile, Tofile);
                imagenpath = "http://www.dotstudioservices.com/imagenesMujerJalisco/" + filename;
            }

            string resultado = "Error";

     //       try
      //      {
                using (SqlConnection conn = new SqlConnection(StringConexion))
                {
                    conn.Open();
                    SqlCommand cmd = conn.CreateCommand();

                    cmd.CommandText = "INSERT INTO productos ([idmujer],[nombre],[precio],[foto],[descripcion]) VALUES (@idmujer,@nombre,@precio,@foto,@descripcion)";
                    cmd.Parameters.AddRange(new SqlParameter[]
                            {
                    
                        new SqlParameter ("@idmujer",idmujer),
                        new SqlParameter ("@nombre",nombre),
                        new SqlParameter ("@precio",precio),
                        new SqlParameter ("@foto",imagenpath),
                        new SqlParameter ("@descripcion",descripcion)                    
                    });

                    cmd.ExecuteNonQuery();
                    resultado = "completado";

                    conn.Close();

                }
           // }
         //   catch
         //   { }
            return new Status { status = resultado };
        }

        public Comunidad getComunidad()
        {
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;

            Comunidad com = new Comunidad();
            com.listacomunidad = new List<comunidad>();

            //            string query = "SELECT ordenes.idorden,ordenes.idplatillo,ordenes.idcomensal, platillos.nombre,ordenes.cantidad,ordenes.estado,ordenes.domicilioenvio,ordenes.observacion,ordenes.tiempoentrega,ordenes.totalpagar,ordenes.Fecha,comensales.foto,comensales.nombre FROM ordenes,platillos,comensales WHERE ordenes.idplatillo = " + idplatillo + " and platillos.idplatillo = " + idplatillo + " and ordenes.idcomensal = comensales.idcomensal";
            string query = "SELECT comunidad.idcomunidad,comunidad.toc,mujeres.nombre,mujeres.apellido,comunidad.comentario, comunidad.imagen FROM comunidad,mujeres WHERE  comunidad.idmujer=mujeres.idmujer";

            conn.Open();

            DataSet ds = new DataSet();
            SqlCommand command = new SqlCommand(query, conn);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            adapter.Fill(ds);
            conn.Close();

            try
            {

                for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                {

                    comunidad o = new comunidad();
                    o.idcomunidad = Convert.ToInt16(ds.Tables[0].Rows[i]["idcomunidad"]);
                    o.nombremujer = ds.Tables[0].Rows[i]["nombre"].ToString();
                    o.apellidomujer = ds.Tables[0].Rows[i]["apellido"].ToString();
                    o.comentario = ds.Tables[0].Rows[i]["comentario"].ToString();
                    o.imagen = ds.Tables[0].Rows[i]["imagen"].ToString();
                    o.toc = ds.Tables[0].Rows[i]["toc"].ToString();
                    com.listacomunidad.Add(o);

                }

            }
            catch
            { }
            return com;
        }

        public Ventas getVentas()
        {
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;

            Ventas com = new Ventas();
            com.listaproductos = new List<producto>();

            //            string query = "SELECT ordenes.idorden,ordenes.idplatillo,ordenes.idcomensal, platillos.nombre,ordenes.cantidad,ordenes.estado,ordenes.domicilioenvio,ordenes.observacion,ordenes.tiempoentrega,ordenes.totalpagar,ordenes.Fecha,comensales.foto,comensales.nombre FROM ordenes,platillos,comensales WHERE ordenes.idplatillo = " + idplatillo + " and platillos.idplatillo = " + idplatillo + " and ordenes.idcomensal = comensales.idcomensal";
            string query = "SELECT productos.idproducto,productos.nombreproducto,productos.descripcion,productos.precio,productos.foto,mujeres.apellido,mujeres.nombre FROM productos,mujeres WHERE  productos.idmujer=mujeres.idmujer";

            conn.Open();

            DataSet ds = new DataSet();
            SqlCommand command = new SqlCommand(query, conn);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            adapter.Fill(ds);
            conn.Close();

          //  try
           // {

                for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                {

                    producto o = new producto();
                    o.idproducto = Convert.ToInt16(ds.Tables[0].Rows[i]["idproducto"]);
                    o.nombre = ds.Tables[0].Rows[i]["nombreproducto"].ToString();
                    o.nombremujer = ds.Tables[0].Rows[i]["nombre"].ToString();
                    o.apellidomujer = ds.Tables[0].Rows[i]["apellido"].ToString();
                    o.imagen = ds.Tables[0].Rows[i]["foto"].ToString();
                    o.descripcion = ds.Tables[0].Rows[i]["descripcion"].ToString();
                    o.precio = Convert.ToInt16(ds.Tables[0].Rows[i]["precio"]);
                    com.listaproductos.Add(o);
                }

           // }
           // catch
           // { }
            return com;
        }

    }
}
