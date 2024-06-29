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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentAllFields {
    Long id;
    String repo_name;
    String username;
    String token;
    String clone_location_win;
    String clone_location_linux;
    String project_final_name;
    String final_deploy_location_linux;
    String final_deploy_location_win;
    String coppy_from_win;
    String coppy_from_linux;
    String files_to_coppy;
    int port;
    String commands_linux;
    String commands_win;
    String status;
    String date_time_project_start;
    String date_time_last_commit;
    String date_time_take_war;
    String deploy_logs;
    String Language;

}
