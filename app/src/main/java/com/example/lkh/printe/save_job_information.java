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
    public String document_link;
    public String document_name;

    public save_job_information(){

    }




    public save_job_information(String job_id, String user_id, String shop_id, String double_sided, String copies, String document_link, String document_name) {

        this.job_id = job_id;
        this.user_id = user_id;
        this.shop_id = shop_id;
        this.double_sided = double_sided;
        this.copies = copies;
        this.document_link = document_link;
        this.document_name = document_name;
    }
}
