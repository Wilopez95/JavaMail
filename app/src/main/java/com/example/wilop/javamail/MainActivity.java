package com.example.wilop.javamail;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    String correo;
    String contraseña;
    EditText mensaje;
    EditText destinatario;
    Button enviar;
    Session  session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        destinatario = findViewById(R.id.correo);
        mensaje = findViewById(R.id.mensaje);
        enviar = findViewById(R.id.enviar);

        correo = "Foodmanagercr@gmail.com";
        contraseña = "FoodManager#admin";

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SendMail( mensaje.getText().toString(),destinatario.getText().toString());
            }
        });




    }
    public void SendMail(String mensaje , String destinatariao){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try{
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return  new PasswordAuthentication(correo,contraseña);
                }
            });

            if(session!= null){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Codigo de prueba");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatariao));
                message.setContent(mensaje,"text/html; charset=utf-8");
                Transport.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
