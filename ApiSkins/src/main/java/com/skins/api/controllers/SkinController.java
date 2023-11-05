package com.skins.api.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skins.api.exceptions.SkinSaveException;
import com.skins.api.model.Skin;
import com.skins.api.service.SkinService;

@RestController
@RequestMapping("/skins")
public class SkinController {
	@Autowired
	private SkinService skinService;
	
	@GetMapping("/available")
    public List<Skin> getAllAvailableSkins(Model model) {
		
		return skinService.getAllAvailableSkins();
	}
	
	@PostMapping("/buy")
    public boolean buySkin(@RequestBody Skin skin) {
        try {
        	//Hardcodeamos un id de usuario para pruebas.
        	Long userId=1L;
            if(skinService.buySkin(skin, userId) > 0)
            	return true;
        } catch (SkinSaveException e) {
        }
        return false;
    }

	@GetMapping("/myskins")
	public List<Skin> getUserSkins(@RequestParam("userId") Long userId) {
		 return skinService.getUserSkins(userId);
	}

	@PutMapping("/color")
	public ResponseEntity<Skin> changeSkinColor(@RequestBody Skin skin) {
		try {
			//Hardcodeamos un id de usuario para pruebas.
	    	Long userId=1L;
			Skin updatedSkin = skinService.changeSkinColor(skin, 1L);
	        return ResponseEntity.ok(updatedSkin);
		}catch (SkinSaveException e) {
	    }
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/getskin/{id}")
	public ResponseEntity<Skin> getSkinById(@PathVariable Long id) {
		Optional<Skin> skin = skinService.getSkinById(id);
        if (skin.isPresent()) {
            return ResponseEntity.ok(skin.get());
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
	@PostMapping("/uploadJsonFile")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> uploadJsonFile(@RequestParam("file") MultipartFile file) {
	    if (file != null && !file.isEmpty()) {
	        try {
				String content = new String(file.getBytes());
				skinService.readSkinsFromJsonFile(content);
				return ResponseEntity.ok("Archivo JSON: " + file.getOriginalFilename() + "cargado");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return ResponseEntity.badRequest().body("Archivo JSON NO válido.");
	}
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkin(@PathVariable Long id) {
		//Hardcodeamos un id de usuario para pruebas.
    	Long userId=1L;
		skinService.deleteSkinPurchasedById(id, userId);
		
        return new ResponseEntity<>("La skin ha sido eliminada", HttpStatus.NO_CONTENT);
    }
}
 

