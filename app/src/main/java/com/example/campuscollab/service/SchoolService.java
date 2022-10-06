package com.example.campuscollab.service;

import com.example.campuscollab.repository.SchoolRepository;

public class SchoolService {

    private static SchoolService instance;
    private final SchoolRepository schoolRepository;

    public static synchronized SchoolService getInstance() {
        if(instance == null) {
            instance = new SchoolService();
        }
        return instance;
    }

    private SchoolService() {
        schoolRepository = new SchoolRepository();
    }

    public void getAllSchools() {
        throw new RuntimeException("getAllSchools not implemented");
    }

    public void getSchool(String id) {
        throw new RuntimeException("getSchool not implemented");
    }

}
