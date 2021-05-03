package modi1.dummy11.Services;


import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import modi1.dummy11.models.Data;
import modi1.dummy11.models.MailTemplate;
import modi1.dummy11.models.VDU;
import modi1.dummy11.models.VaccineSession;
import org.apache.http.util.Asserts;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class PlayerDataService {
    public ResponseEntity<Data>stats;
    public List<VaccineSession>vaccineSessions=new ArrayList<>();


    public List<VaccineSession> getVaccineSessions() {
        return vaccineSessions;
    }

    public void fetchVirusData() throws IOException,InterruptedException
   {
       List<VaccineSession>dummyvaccinesessions=new ArrayList<>();
       VDU vdu=new VDU();
       System.out.println(vdu.getVirusDataUri());
       System.out.println("pds");
       HttpClient client=HttpClient.newHttpClient();
       HttpRequest request= HttpRequest.newBuilder()
               .uri(URI.create(vdu.getVirusDataUri()))
               .GET()
               .header("Accept-Language", "en_US")
              // .header("x-rapidapi-host", "covid-193.p.rapidapi.com")
               .build();
       getCurrentTimeUsingCalendar();

       HttpResponse<String>response=client.send(request,HttpResponse.BodyHandlers.ofString());
       System.out.println(response.body());

       JSONObject obj = new JSONObject(response.body());
       JSONArray sessions=obj.getJSONArray("sessions");

       for(int i=0;i<sessions.length();i++)
       {
           VaccineSession vaccineSession=new VaccineSession();
           ObjectMapper objectMapper = new ObjectMapper();
           System.out.println(sessions.get(i));
           JsonNode rootNode = objectMapper.readTree(String.valueOf(sessions.get(i)));
           vaccineSession.setAge_limit(rootNode.path("min_age_limit").asText());
           vaccineSession.setFees(rootNode.path("fee_type").asText());
           vaccineSession.setAvailablity(rootNode.path("available_capacity").asText());
           vaccineSession.setName(rootNode.path("name").asText());
           dummyvaccinesessions.add(vaccineSession);


       }
       System.out.println(dummyvaccinesessions.size());
       this.vaccineSessions=dummyvaccinesessions;

   }
    public void sendmail(MailTemplate mailTemplate) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("parammodi0305@gmail.com", "pmfwvrdgtgrozuic");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("parammodi05@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTemplate.getTo()));
        msg.setSubject(mailTemplate.getSubject());
        msg.setContent(mailTemplate.getBody(), "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mailTemplate.getBody(), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
    }


    public static void getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
    }




}
