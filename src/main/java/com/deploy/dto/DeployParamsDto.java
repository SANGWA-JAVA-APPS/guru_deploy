/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeployParamsDto {
    private String repo_name;
    private String username;
    private String token;
    private String clone_location_win;
    private String clone_location_linux;
    private String project_final_name;
    private String final_deploy_location_linux;
    private String final_deploy_location_win;
    private String coppy_from_win;
    private String coppy_from_linux;
    private String files_to_coppy;
    private String commands_linux;
    private String commands_win;
    private String status;
    private String language;

    private int deployPort;
}
