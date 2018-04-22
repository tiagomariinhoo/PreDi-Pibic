package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import app.com.example.wagner.meupredi.View.Account.EmailConfig;

public class SendMail extends AsyncTask<Void,Void,Void> {

    private Context context;
    private Session session;

    private String email;
    private String subject;
    private String message;

    public SendMail(Context context, String email, String subject, String message){

        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {

        Properties props = new Properties();

        //configuracoes para usar gmail como conta principal do app
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EmailConfig.EMAIL, EmailConfig.PASSWORD);
                    }
                });

        try {

            MimeMessage mm = new MimeMessage(session);

            //email do remetente
            mm.setFrom(new InternetAddress(EmailConfig.EMAIL));
            //destinatario
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //assunto
            mm.setSubject(subject);
            //mensagem
            mm.setText(message);

            //enviando email
            Transport.send(mm);

            /* TODO: tratar MailConnectException (sem conexao) e SendFailedException (email invalido) */

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}