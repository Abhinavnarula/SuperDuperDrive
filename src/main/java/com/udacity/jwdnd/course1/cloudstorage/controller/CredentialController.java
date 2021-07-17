package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")

// Controller for accessing and basic CRUD operations in the credential saving
// section.
public class CredentialController {

    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final UserService userService;

    @Autowired
    public CredentialController(CredentialService credentialService, EncryptionService encryptionService,
            UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    // for displaying home page
    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newCredential") CredentialForm newCredential, @ModelAttribute("newNote") NoteForm newNote,
            Model model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", this.credentialService.getCredentialListings(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @PostMapping("addCredential")
    public String newCredential(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newCredential") CredentialForm newCredential, @ModelAttribute("newNote") NoteForm newNote,
            Model model) {

        // getting url, username and password from form fields
        String userName = authentication.getName();
        String newUrl = newCredential.getUrl();
        String credentialIdString = newCredential.getCredentialId();
        String password = newCredential.getPassword();

        // random number generation for encrypting the credentials
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        // if present then added as a new value, else updated.
        if (credentialIdString.isEmpty()) {
            credentialService.addCredential(newUrl, userName, newCredential.getUserName(), encodedKey,
                    encryptedPassword);
        } else {
            Credential existingCredential = getCredential(Integer.parseInt(credentialIdString));
            credentialService.updateCredential(existingCredential.getCredentialid(), newCredential.getUserName(),
                    newUrl, encodedKey, encryptedPassword);
        }

        // passed to the thymeleaf template "result"
        User user = userService.getUser(userName);
        model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    // get request for getting credential values
    @GetMapping("/getCredential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    // delete operation.
    @GetMapping(value = "/deleteCredential/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId,
            @ModelAttribute("newCredential") CredentialForm newCredential, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {
        credentialService.deleteCredential(credentialId);
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }
}