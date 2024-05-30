package com.example.swankicksbackend.controller;

import com.example.swankicksbackend.controller.EmailSender.EmailService;
import com.example.swankicksbackend.persistence.DBManager;
import com.example.swankicksbackend.persistence.dao.utenteDao;
import com.example.swankicksbackend.persistence.model.utente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class registrazioneController {

    private EmailService emailService;

    public registrazioneController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/doRegistration")
    public String doRegistration(@RequestParam("name") String name,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("cf") String cf,
                                 @RequestParam("email") String email,
                                 @RequestParam("phone") Long phone,
                                 @RequestParam("role") String role,
                                 @RequestParam("password") String password,
                                 Model model) {

        utenteDao udao = DBManager.getInstance().getUtenteDAO();
        utente utente = udao.findByPrimaryKey(cf);
        utente utente2 = udao.findByEmail(email);

        if (utente == null && utente2 == null) {
            utente utReg = new utente(cf, name, surname, email, phone, role, password, false);
            boolean reg = udao.saveOrUpdate(utReg);
            if (reg) {
                // Registrazione andata a buon fine: popup
                String successMessage = "Utente registrato con successo! Effettua il login per procedere.";
                model.addAttribute("successMessage", successMessage);
                String htmlBody = "<html><head>"
                        + "<style>"
                        + ".bodyStyle {font-family: Arial, sans-serif; background-color: #ffffff; color: #333333; margin: 0; padding: 0;}"
                        + ".container {width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; color: #000000; padding: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);}"
                        + ".header {background-color: #4B9CD3; color: #ffffff; padding: 20px; text-align: center; border-radius: 5px 5px 0 0;}"
                        + ".textCenter {padding: 15px; text-align: center; font-size: 16px;}"
                        + ".footer {text-align: center; padding: 20px; background-color: #f0f0f0; color: #333; font-size: 14px; border-radius: 0 0 5px 5px;}"
                        + "</style>"
                        + "</head><body class=\"bodyStyle\">"
                        + "<div class=\"container\">"
                        + "<div class=\"header\">"
                        + "<img src='https://i.ibb.co/xsY7JRr/logo.png' alt='SwanKicks Logo' style='max-width: 100%; height: auto;'>"
                        + "<h1 style=\"margin: 0; font-size: 24px;\">SWANKICKS <br> Ciao "+utReg.getNome()+", grazie per esserti registrato!</h1>"
                        + "</div>"
                        + "<div class=\"textCenter\">Adesso puoi iniziare a vendere o acquistare le sneakers che preferisci!</div>"
                        + "<div class=\"textCenter\">Puoi partecipare ad aste in tempo reale, lasciare recensioni e tanto altro!</div>"
                        + "<div class=\"textCenter\">Inizia subito a navigare: http://localhost:4200/home</div>"
                        + "<div class=\"footer\">©2024 SwanKicks. Progettato dal team Cigni</div>"
                        + "</div></body></html>";




                emailService.sendEmail(
                        utReg.getEmail(),
                        "Benvenuto in SwanKicks" ,
                        htmlBody
                );
            } else {
                // Registrazione non andata a buon fine: popup
                String errorMessage = "Server error!";
                model.addAttribute("errorMessage", errorMessage);
            }
        } else {
            // Utente già esistente: popup
            String errorMessage = "Utente già esistente!";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "registrazione";
    }
}
