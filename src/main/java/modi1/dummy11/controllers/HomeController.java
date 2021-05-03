package modi1.dummy11.controllers;

import modi1.dummy11.Services.PlayerDataService;
import modi1.dummy11.models.Data;
import modi1.dummy11.models.VDU;
import modi1.dummy11.models.VaccineSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

import java.util.List;

@Controller
public class HomeController {


    @Autowired
   public PlayerDataService pds;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/sendForm")
    public String sendform(@RequestParam(value = "pincode", required = false) String pincode,
                           @RequestParam(value = "date", required = false) String date,
                           @RequestParam(value = "age", required = false) Integer age,
                           @RequestParam(value = "checkbox", required = false) boolean checkobox,
                           @RequestParam(value = "emailid", required = false) String emailid,Model model)throws AddressException, IOException, MessagingException, InterruptedException {
        if (pincode != null && date != null) {

            VDU vdu = new VDU();

        if (checkobox==false) {

            String got = "pincode=" + pincode + "&" + "date=" + date;
            vdu.setVirusDataUri(got);
            pds.fetchVirusData();
            System.out.println(vdu.getVirusDataUri());
            List<VaccineSession> allStats = pds.getVaccineSessions();
            model.addAttribute("allstats", allStats);
            int totalwardsavailable = allStats.size();
            int totalvaccineshots = allStats.stream().mapToInt(stat -> Integer.parseInt(stat.getAvailablity())).sum();
            model.addAttribute("totalWardsAvailable", totalwardsavailable);
            model.addAttribute("totalVaccineShots", totalvaccineshots);

        }
        else
        {
            String got = "pincode=" + pincode + "&" + "date=" + date;
            vdu.setVirusDataUri(got);
            pds.fetchVirusData();
            List<VaccineSession> allStats = pds.getVaccineSessions();
            model.addAttribute("allstats", allStats);
            int totalwardsavailable = allStats.size();
            int totalvaccineshots = allStats.stream().mapToInt(stat -> Integer.parseInt(stat.getAvailablity())).sum();
            model.addAttribute("totalWardsAvailable", totalwardsavailable);
            model.addAttribute("totalVaccineShots", totalvaccineshots);
            String Values ="VALUES ("+"'"+emailid+"'"+","+"'"+pincode+"'"+","+"'"+age+"'"+")";
            String sql="INSERT INTO Person (emailid, pincode,age)"+Values;
            jdbcTemplate.execute(sql);




        }



            vdu.setToOriginalString();

    }
        return "vaccinesessioninfo";
    }
    @RequestMapping("/getForm")

        public String getForm() throws AddressException, IOException, MessagingException, InterruptedException {

            return "home";

    }



}
