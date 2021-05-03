package modi1.dummy11.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import modi1.dummy11.interfaces.PersonRepo;
import modi1.dummy11.models.MailTemplate;
import modi1.dummy11.models.Person;
import modi1.dummy11.models.VDU;
import modi1.dummy11.models.VaccineSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service

public class AutomaticMailSender  {
    @Autowired
    PlayerDataService pds;



    @Autowired
    private PersonRepo personRepo;



    public void FetchAvailabilityData(String pincode,String date,String emailId,String age) throws Exception {


        List<VaccineSession> dummyvaccinesessions = new ArrayList<>();
        VDU vdu = new VDU();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin/?pincode="+pincode+"&"+"date=+"+date))
                .GET()
                .header("Accept-Language", "en_US")
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        JSONObject obj = new JSONObject(response.body());
        JSONArray sessions = obj.getJSONArray("sessions");
        Integer age_of_person=Integer.parseInt(age);


        for (int i = 0; i < sessions.length(); i++) {
            VaccineSession vaccineSession = new VaccineSession();
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(String.valueOf(sessions.get(i)));
            vaccineSession.setAge_limit(rootNode.path("min_age_limit").asText());
            vaccineSession.setFees(rootNode.path("fee_type").asText());
            vaccineSession.setAvailablity(rootNode.path("available_capacity").asText());
            vaccineSession.setName(rootNode.path("name").asText());

            Integer min_age_limit=Integer.parseInt(rootNode.path("min_age_limit").asText());

            if(age_of_person>=18&&age_of_person<45&&min_age_limit==18)
            {
                dummyvaccinesessions.add(vaccineSession);

            }
            if(age_of_person>=45&&(min_age_limit==18||min_age_limit==45))
            {
                dummyvaccinesessions.add(vaccineSession);

            }


        }
        if (!dummyvaccinesessions.isEmpty()) {
            String content = "";
            for (int i = 0; i < dummyvaccinesessions.size(); i++) {
                String temp = "Ward:\n" + dummyvaccinesessions.get(i).getName() + "\n" + "Fees:" + dummyvaccinesessions.get(i).getFees() + "\n"
                        + "Min-Age-Limit:" + dummyvaccinesessions.get(i).getAge_limit() + "\n"
                        + "Dose Availability" + dummyvaccinesessions.get(i).getAvailablity() + "\n";
                content += (temp + "\n" + "\n");
                temp = "";

            }

            MailTemplate mailTemplate = new MailTemplate(emailId, "VaccineSessionAvailable", content);
            pds.sendmail(mailTemplate);


        }
    }

        @PostConstruct
        @Scheduled(cron = "* * * * * *")
        public void AllUsers() throws Exception {
            List<Person>Users=personRepo.findAll();
            for(Person User:Users)
            {
                FetchAvailabilityData(User.getPincode(),getCurrentTimeUsingCalendar(),User.getEmailiD(),User.getAge());
            }



        }
    public String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate=dateFormat.format(date);
        return formattedDate;

    }






}
