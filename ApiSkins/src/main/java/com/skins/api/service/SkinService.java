package com.skins.api.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skins.api.exceptions.SkinDeleteException;
import com.skins.api.exceptions.SkinSaveException;
import com.skins.api.model.Skin;
import com.skins.api.repositories.SkinRepository;

@Service
public class SkinService { //Esta clase será responsable de manejar la lógica relacionada con la lectura de datos desde un archivo JSON y la conversión de estos datos en objetos Java.

	private ObjectMapper objectMapper = new ObjectMapper(); //es una clase de Jackson que te permite convertir entre objetos Java y JSON.
	
	@Autowired
	SkinRepository skinRepository;
	
    public List<Skin> readSkinsFromJsonFile(String filePath) throws IOException {
        List<Skin> skins = objectMapper.readValue(filePath, new TypeReference<List<Skin>>() {});
        
        guardarenBBDDSkins(skins);
        
        return skins;
    }
	
	private void guardarenBBDDSkins(List<Skin> skins) {
		skinRepository.saveAll(skins);
	}

	public Skin saveSkin(Skin skin) throws SkinSaveException {
		try {
			return skinRepository.save(skin);
		} catch (Exception e) {
			throw new SkinSaveException("No se pudo guardar la skin en la base de datos.", e);
		}
	}

	public void deleteSkins(Skin skin) throws SkinDeleteException {
		try {
			skinRepository.delete(skin);
		} catch (Exception e) {
			throw new SkinDeleteException("No se pudo eliminar la skin.", e);
		}
	}

	public List<Skin> getAllAvailableSkins() {
		 return (List<Skin>) skinRepository.findAll();
	}
	
	public Integer buySkin(Skin skin, Long userId) throws SkinSaveException {
        Skin availableSkin = skinRepository.findById(skin.getId()).orElse(null);
        
        if (availableSkin != null) {
            Skin purchasedSkin = new Skin();
            purchasedSkin.setId(availableSkin.getId());
            purchasedSkin.setNombre(availableSkin.getNombre());
            purchasedSkin.setTipo(availableSkin.getTipo());
            purchasedSkin.setPrecio(availableSkin.getPrecio());
            purchasedSkin.setColor(skin.getColor());
            
            return skinRepository.savePurchasedSkin(purchasedSkin.getId(), userId);
        } else {
            throw new SkinSaveException("La skin no está disponible para la compra.");
        }
    }

	public List<Skin> getUserSkins(Long userId) {
		return skinRepository.findByUserId(userId);
	}

	public Skin changeSkinColor(Skin skin, Long userId) throws SkinSaveException{
		Skin purchasedSkin = getUserPurchasedSkinId(userId, skin.getId());
		
		if(null != purchasedSkin){
            purchasedSkin.setColor(skin.getColor());
            
            return saveSkin(purchasedSkin);
        }else {
            throw new SkinSaveException("La skin no se ha podido cambiar de color");
        }
	}

	private Skin getUserPurchasedSkinId(Long userId, Long skinId) {
		Skin availablePurchasedSkin = skinRepository.findByPurchasedAndUserId(userId, skinId).orElse(null);
		
		return availablePurchasedSkin;
	}

	public void deleteSkinPurchasedById(Long skinId, Long userId) {
		Skin purchasedSkin = getUserPurchasedSkinId(userId, skinId);
		
		if(null != purchasedSkin) {
			skinRepository.deleteById(skinId);
		}
	}

	public Optional<Skin> getSkinById(Long id) {
		return skinRepository.findById(id);
	}

}
