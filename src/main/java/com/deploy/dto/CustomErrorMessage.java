/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.deploy.dto;

import lombok.Data;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
@Data
public class CustomErrorMessage {
    private String message;

    public CustomErrorMessage(String message) {
        this.message = message;
    }
}
