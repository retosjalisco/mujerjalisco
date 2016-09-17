<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="EstudioEconomico.aspx.cs" Inherits="WebMujeresJalico.Contenido.EstudioEconomico" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <style type="text/css">
        .auto-style1 {
            font-size: x-large;
        }
    </style>
</head>
<body style="height: 483px">
    <form id="form1" runat="server" enctype="multipart/form-data">
    <div>
    
        <div style="height: 101px" >
            <center>
            <img alt="" src="../Imagenes/logo%20horizontal.png" style="height: 89px; width: 243px;  margin-top:0px;" /></div>
        </center>
    
    </div>
    <div style="background-color: #E967A0; text-align: center; font-weight: 700; color: #FFFFFF; font-size: large;" class="auto-style1">
        Registro</div>
    <div>
        <br />
    </div>
    <div>
        <center >
        <div style="padding-top: inherit; border-style: double; border-color: #D7D7D7; line-height: inherit;left: 14px; right: 45px; height: 182px; width:80%; text-align: center;">
            <div style="background-color: #D7D7D7; text-align: left;">
                <img alt="" src="../Imagenes/1.png" style="height: 24px; margin-left: 23px" /> Informacion</div>
            <br />
            &nbsp;&nbsp;
            <asp:TextBox ID="Nombre" runat="server"  placeholder="Nombre" BorderColor="#E0E0E0" BorderStyle="Groove" Width="285px" ></asp:TextBox>
            &nbsp;&nbsp; <asp:TextBox ID="ApellidoP" runat="server"  placeholder="Apellido Paterno" BorderColor="#E0E0E0" BorderStyle="Groove" Width="285px" ></asp:TextBox>
            &nbsp; &nbsp;<asp:TextBox ID="Fecha" runat="server"  placeholder="Fecha Nacimiento" BorderColor="#E0E0E0" BorderStyle="Groove" Width="293px" ></asp:TextBox>
            <br />
            <br />&nbsp; &nbsp;&nbsp;
            &nbsp; 
            &nbsp;&nbsp;
            <asp:DropDownList ID="Ciudad" runat="server" Height="20px" Width="230px" ></asp:DropDownList>
            &nbsp;&nbsp;
            <asp:TextBox ID="Direccion" runat="server"  placeholder="Direccion" BorderColor="#E0E0E0" BorderStyle="Groove" Width="216px" ></asp:TextBox>
            <asp:DropDownList ID="EsdoCivil" runat="server" Height="25px" Width="146px" placeholder="Estado Civil">
                <asp:ListItem>Soltera</asp:ListItem>
                <asp:ListItem Value="Casado">Casada</asp:ListItem>
                <asp:ListItem>Viuda</asp:ListItem>
                <asp:ListItem>Divorciada</asp:ListItem>
            </asp:DropDownList> 
            &nbsp; 
            <asp:TextBox ID="Telefono" runat="server"  placeholder="Telefono" BorderColor="#E0E0E0" BorderStyle="Groove" Width="216px" OnTextChanged="Telefono_TextChanged" ></asp:TextBox>
            &nbsp; <asp:TextBox ID="Correo" runat="server"  placeholder="Correo Electronico" BorderColor="#E0E0E0" BorderStyle="Groove" Width="285px" ></asp:TextBox>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <asp:TextBox ID="contraseña" runat="server"  placeholder="contraseña" BorderColor="#E0E0E0" BorderStyle="Groove" Width="122px" OnTextChanged="TextBox1_TextChanged"></asp:TextBox>
&nbsp;<br />
            </center>
        </div>
        <div>
            <br />
        </div>
        <div style="height: 127px">
        <center style="height: 107px">
        <div style="padding-top: inherit; border-style: double; border-color: #D7D7D7; line-height: inherit;left: 14px; right: 45px; height: 90px; width:80%; text-align: justify;">
            <div style="background-color: #D7D7D7; text-align: left;">
                <img alt="" src="../Imagenes/2.png" style="height: 24px; margin-left: 23px" />&nbsp; Documentacion</div>
            <br />
&nbsp;
            <asp:FileUpload ID="idfoto" runat="server" placeholder="Subir INE"/>
            &nbsp;&nbsp;
            <asp:FileUpload ID="comprobante" runat="server" placeholder="Subir foto de comprobante de domicilio" Width="448px"/>
            <br />
            <br />
            &nbsp;&nbsp;
            &nbsp;</center>
            <div>
            </div>
        </div>
        <div style="height: 221px">
        <center>
        <div style="padding-top: inherit; border-style: double; border-color: #D7D7D7; line-height: inherit;left: 14px; right: 45px; height: 207px; width:80%; text-align: justify;">
            <div style="background-color: #D7D7D7; text-align: left;">
                <img alt="" src="../Imagenes/3.png" style="height: 24px; margin-left: 23px" /> Estudio Socioeconomico</div>
            <br />
            &nbsp; Actualmente vives con:<br />
&nbsp;
            <asp:RadioButton ID="RadioButtonpadres" runat="server" Text="Padres/Tutores" />
&nbsp;&nbsp;
            <asp:RadioButton ID="RadioButtonfamilia" runat="server" Text="Familia" />
&nbsp;&nbsp;
            <asp:RadioButton ID="RadioButtonsola" runat="server" Text="Sola" />
            <br />
&nbsp;<br />
&nbsp; La casa donde vives es:<br />
&nbsp;
            <asp:RadioButton ID="RadioButtonpropia" runat="server" Text="Propia" />
&nbsp;&nbsp;
            <asp:RadioButton ID="RadioButtonrentada" runat="server" Text="Rentada" />
&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; .&nbsp;&nbsp;
            <asp:DropDownList ID="Drophabitantes" runat="server" Height="32px" Width="204px">
                <asp:ListItem>Numero de habitantes</asp:ListItem>
                <asp:ListItem></asp:ListItem>
            </asp:DropDownList>
            <br />
            <br />
&nbsp; ¿Trabajas acualmente?&nbsp;
            <asp:RadioButton ID="RadioButtonTsi" runat="server" Text="si" />
&nbsp;&nbsp;
            <asp:RadioButton ID="RadioButtonTno" runat="server" Text="No" />
            &nbsp;&nbsp; ¿Donde?<asp:TextBox ID="dondetrabajas" runat="server"></asp:TextBox>
            <br />
            
            </center>
        </div>
        <div>
            <div>
                <br />
            </div>
        </div>
        <div>
        <center style="height: 195px">
        <div style="padding-top: inherit; border-style: double; border-color: #D7D7D7; line-height: inherit;left: 14px; right: 45px; height: 182px; width:80%; text-align: justify;">
            <div style="background-color: #D7D7D7; text-align: left;">
                <img alt="" src="../Imagenes/4.png" style="height: 24px; margin-left: 23px" /> Dependientes</div>
            <br />
            &nbsp;&nbsp;
            ¿Cuantos dependientes tiene?&nbsp;
            <asp:DropDownList ID="DropDownList4" runat="server" Height="29px" Width="131px">
            </asp:DropDownList>
            <div>
            </div>
            
            </center>
        </div>
        <div style="text-align: center">
            <asp:Button ID="Button1" runat="server" BackColor="#E967A0" BorderStyle="None" Font-Bold="True" ForeColor="White" Text="Terminar" Width="131px" OnClick="Button1_Click" />
        </div>
    </form>
        <br />
    </div>
</body>
</html>
