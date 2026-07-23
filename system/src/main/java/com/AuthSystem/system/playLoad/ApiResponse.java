package com.AuthSystem.system.playLoad;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

        private String message;
        private HttpStatus httpStatus;
        private boolean success;
        //

}

