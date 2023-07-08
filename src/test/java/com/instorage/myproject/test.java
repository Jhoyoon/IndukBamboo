package com.instorage.myproject;

import jakarta.validation.constraints.AssertTrue;
import org.junit.Test;

import java.time.LocalDate;

public class test {
    @Test
    public void main(){
        LocalDate today = LocalDate.now();

        System.out.println(today);

    }
}
