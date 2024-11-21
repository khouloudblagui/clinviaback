package org.example.authentification.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {



    private Long id;

    private Integer userKy; // Référence à l'ID de l'utilisateur dans le service d'authentification

    private String mobile;
    private String name ;

    private String specialization; // Spécialité du docteur


    private String department; // Département auquel le docteur est associé


    private String degree;


    private Date dateOfJoining;


}
