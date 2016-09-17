<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="WebMujeresJalico.Login" EnableEventValidation="false" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <div class="loginWrapper">
	<!-- Current user form -->
    <form action="index.html" id="login">
        <div class="loginPic"">
            <img src="Imagenes/logo%20vertical.png" alt="" style=" height: 225px; width: 177px;">&nbsp;</a>
            <span>&nbsp; </span>
            <div class="loginActions">
                <div>
                </div>
                <div>
                </div>
            </div>
        </div>
        
        <asp:TextBox ID="TextBox1" runat="server" placeholder="Ingresa Email"></asp:TextBox>
       <asp:TextBox ID="TextBox2" runat="server" placeholder="Contraseña" AutoPostBack="True"></asp:TextBox>
        
        <div class="logControl">
            <div class="memory"><div class="checker" id="uniform-remember1"><span><input type="checkbox" checked="checked" class="check" id="remember1" style="opacity: 0;"></span></div><label for="remember1">Remember  
                <asp:Button ID="Button1" runat="server" ForeColor="White" OnClick="Button1_Click" Text="Iniciar Session" BackColor="#FCA7A1" Font-Bold="True" />
        </div>
    </div>
    </form>
        </div>

<script class="cssdeck"
    

    </div>
    </form>
</body>
</html>
