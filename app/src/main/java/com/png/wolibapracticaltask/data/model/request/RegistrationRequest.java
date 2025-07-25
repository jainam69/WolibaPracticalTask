package com.png.wolibapracticaltask.data.model.request;

public class RegistrationRequest {
    // REGISTER
    public String fname;
    public String lname;
    public String email;
    public String password;
    public String time_zone;
    public String token;
    public int language_id;
    public String birthday;
    public String phone_number;
    public int user_type;
    public boolean accepted_privacy_policy;
    public int[] areas_of_interest;
    public int[] wellbeing_pillars;

    public RegistrationRequest(String fname, String lname, String email, String password, String time_zone, String token, int language_id, String birthday, String phone_number, int user_type, boolean accepted_privacy_policy, int[] areas_of_interest, int[] wellbeing_pillars) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.time_zone = time_zone;
        this.token = token;
        this.language_id = language_id;
        this.birthday = birthday;
        this.phone_number = phone_number;
        this.user_type = user_type;
        this.accepted_privacy_policy = accepted_privacy_policy;
        this.areas_of_interest = areas_of_interest;
        this.wellbeing_pillars = wellbeing_pillars;
    }
}
