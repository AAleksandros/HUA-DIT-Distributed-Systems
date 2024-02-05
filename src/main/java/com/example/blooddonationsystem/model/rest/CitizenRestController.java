/*package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.dao.CitizenDAO;
import com.example.blooddonationsystem.model.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenRestController {

    @Autowired
    private CitizenDAO citizenDao;

    // Get all citizens
    @GetMapping("")
    public ResponseEntity<List<Citizen>> getCitizens() {
        List<Citizen> citizens = citizenDao.getAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    // Save a new citizen or update an existing one
    @PostMapping("")
    public ResponseEntity<Citizen> saveOrUpdateCitizen(@RequestBody Citizen citizen) {
        Citizen savedCitizen = citizenDao.saveOrUpdateCitizen(citizen);
        return ResponseEntity.ok(savedCitizen);
    }

    // Delete a citizen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        citizenDao.deleteCitizen(id);
        return ResponseEntity.ok().build();
    }

}*/

package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.dao.CitizenDAO;
import com.example.blooddonationsystem.model.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenRestController {

    @Autowired
    private CitizenDAO citizenDao;

    @GetMapping("")
    public List<Citizen> getCitizens(){
        return citizenDao.getAllCitizens();
    }

    @PostMapping("")
    public Citizen saveOrUpdateCitizen(@RequestBody Citizen citizen){
        return citizenDao.saveOrUpdateCitizen(citizen);
    }

}
