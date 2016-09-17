using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data.Sql;
using System.Data;

namespace WebMujeresJalico.Contenido
{
    public partial class EstudioEconomico : System.Web.UI.Page
    {
        public string StringConexion = "Data Source=mujerjalisco.db.9692238.hostedresource.com; Initial Catalog=mujerjalisco; User ID=mujerjalisco; Password='Saguchi1!';";

        protected void Page_Load(object sender, EventArgs e)
        {

        }
        public string setRegistro(string nombre, string apellido, string email, string clave, string direccion, string ciudad, string telefono, string fotoid, string fotocomp, string fechanac, string ecivil)
        {
            string resultado = "Error";
            SqlConnection conn = new SqlConnection();
            conn.ConnectionString = StringConexion;
            conn.Open();

            string query = "SELECT COUNT (*) FROM mujeres WHERE email='" + email + "'";
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

                string query2 = "SELECT * FROM mujeres WHERE email='" + email + "'";
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
            return resultado;
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            int idmujer = Convert.ToInt16(setRegistro(Nombre.Text, ApellidoP.Text, Fecha.Text,Ciudad.Text,Direccion.Text,EsdoCivil.Text,Telefono.Text,Correo.Text, contraseña.Text,"e", "d"));
           string vivecon="0";

                    if (RadioButtonpadres.Checked)
                        vivecon = " Padres/Tutores";
                    else if (RadioButtonfamilia.Checked)
                        vivecon = " Familia";

                    else if (RadioButtonsola.Checked)
                        vivecon = "Sola";
            int casa=0;
            if (RadioButtonpropia.Checked)
                casa = 0;
            else if (RadioButtonrentada.Checked)
                casa = 1;
            int trabaja=0;
            if(RadioButtonTsi.Checked)
                trabaja=0;
            else if(RadioButtonTno.Checked)
                trabaja=1;

            string resultado = setSocioEconomico(idmujer,vivecon,"mi ama","$2000.00 ",casa, Convert.ToInt16(Drophabitantes.SelectedItem.ToString()),trabaja,dondetrabajas.Text);
        }


        public string setSocioEconomico(int idmujer, string vivecon, string viveconnombres, string ingreso, int casa, int habitaciones, int trabaja, string lugartrabajo)
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
            catch
            {

            }
            return resultado;
        }

        
        
    }
}