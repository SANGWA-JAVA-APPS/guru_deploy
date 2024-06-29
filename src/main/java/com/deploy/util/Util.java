/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
@Data
public class Util {
    public static boolean isFSBS = (System.getProperty("os.name").equals("Linux"));
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());
}
