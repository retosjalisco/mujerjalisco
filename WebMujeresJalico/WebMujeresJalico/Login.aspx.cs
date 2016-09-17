using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebMujeresJalico
{
    public partial class Login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            if ((TextBox1.Text == "admin") && (TextBox2.Text == "12345"))
            {
                Session["Login"] = "1";
                Response.Redirect("/Comunidad.aspx");
            }
        }
    }
}