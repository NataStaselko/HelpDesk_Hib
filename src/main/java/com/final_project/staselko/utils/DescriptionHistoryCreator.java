package com.final_project.staselko.utils;

import org.springframework.stereotype.Component;

@Component
public class DescriptionHistoryCreator {
    public String actionCreate(){
        return "Ticket is created";
    }

    public String descriptionCreate(){
        return "Ticket is created";
    }

    public String actionEdit(){
        return "Ticket is edited";
    }
    public String descriptionEdit(){
        return "Ticket is edited";
    }

    public String actionChangeState(){
        return "Ticket status is changed";
    }

    public String descriptionChangeState(String oldState, String newState){
        return "Ticket status is changed from " + oldState + " to " + newState;
    }
    public String actionAddFile(){
        return "File is attached ";
    }
    public String descriptionAddFile(String fileName){
        return "File is attached: " + fileName;
    }
    public String actionRemoveFile(){
        return "File is removed ";
    }
    public String descriptionRemoveFile(String fileName){
        return "File is removed: " + fileName;
    }


}
