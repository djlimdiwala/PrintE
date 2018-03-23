package com.example.lkh.printe;

/**
 * Created by DJL on 3/8/2018.
 */

public class save_job_information
{
    public String job_id;
    public String user_id;
    public String shop_id;
    public String double_sided;
    public String copies;
    public String coloured;
    public String document_link;
    public String document_name;
    public String created_on;
    public String finished_on;

    public save_job_information(){

    }




    public save_job_information(String job_id, String user_id, String shop_id, String double_sided, String copies, String coloured, String document_link, String document_name,String created_on, String finished_on) {

        this.job_id = job_id;
        this.user_id = user_id;
        this.shop_id = shop_id;
        this.double_sided = double_sided;
        this.copies = copies;
        this.coloured = coloured;
        this.document_link = document_link;
        this.document_name = document_name;
        this.created_on = created_on;
        this.finished_on = finished_on;
    }
}
