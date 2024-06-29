package com.deploy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mdl_forPush {
    String path;
    String cloneDir;
    int projectPort;
}
