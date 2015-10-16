package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by zassmin on 10/16/15.
 */
@ParseClassName("User")
public class User extends ParseUser {
    public static String GITHUB_ACCESS_TOKEN = "github_access_token";
    public static String GITHUB_ID = "github_id";
    
    public void setUserName(String userName) {
        this.setUsername(userName);
    }

    public String getUserName() {
        return this.getUserName();
    }

    // set a random password for a github user
    public void setPassword(String password) {
        this.setPassword(password);
    }

    public void setGithubAccessToken(String token) {
        this.put(GITHUB_ACCESS_TOKEN, token);
    }

    public String getGithubAccessToken() {
        return this.get(GITHUB_ACCESS_TOKEN).toString();
    }

    // for user's github id after github oauth call GET https://api.github.com/user
    public void setGithubId(long githubId) {
        this.put(GITHUB_ID, githubId);
    }

    public long getGithubId() {
        return (long) this.get(GITHUB_ID);
    }

    // for user's email after github oauth call GET https://api.github.com/user
    public void setEmail(String email) {
        // using parse email behavior we can leverage email validations
        this.setEmail(email);
    }

    public String getEmail() {
        return this.getEmail();
    }
}
