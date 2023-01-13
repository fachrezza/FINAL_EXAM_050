/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PWS.FinalExam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import PWS.FinalExam.Surat;
import PWS.FinalExam.SuratJpaController;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author A S U S
 */

@RestController
@CrossOrigin
@ResponseBody
public class FinalExamController {
    
    Surat data = new Surat();
    SuratJpaController control = new SuratJpaController();
    
    @GetMapping(value="/GET", produces = APPLICATION_JSON_VALUE)
    public List<Surat> getNoSurat(){
        List<Surat> buffer = new ArrayList<>();
        buffer = control.findSuratEntities();
        return buffer;
    }
    
    @PostMapping(value = "/POST", produces = APPLICATION_JSON_VALUE)
    public String sendData(HttpEntity<String> datasend) throws JsonProcessingException{
        ObjectMapper export = new ObjectMapper();
        String feedback = "";
        data = export.readValue(datasend.getBody(), Surat.class);
        try{
            control.create(data);
            feedback = data.getNoSurat()+" Saved";
        }catch (Exception error){
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    @PutMapping (value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String putData(HttpEntity<String> datasend) throws JsonProcessingException{
        ObjectMapper export = new ObjectMapper();
        String feedback = "";
        data = export.readValue(datasend.getBody(), Surat.class);
        try {
            control.edit(data);
            feedback = data.getNoSurat()+" edited";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    @DeleteMapping(value = "/DEL", consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> datasend) throws JsonProcessingException{
        ObjectMapper export = new ObjectMapper();
        String feedback = "";
        data = export.readValue(datasend.getBody(), Surat.class);
        try {
            control.destroy(data.getId());
            feedback = "id = " + data.getId().toString()+ " deleted";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
}
